package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
  
  private final RecipeReactiveRepository recipeRepository;
  
  public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
    super();
    this.recipeRepository = recipeRepository;
  }
  
  @Override
  public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
    log.debug("Recieved image for upload");

    Mono<Recipe> recipeMono = recipeRepository.findById(recipeId)
        .map(recipe -> {
          Byte[] byteObjects = new Byte[0];
          try {
            byteObjects = new Byte[file.getBytes().length];
            int i = 0;
          
            for (byte b : file.getBytes()) {
              byteObjects[i++] = b;
            }
            
            recipe.setImage(byteObjects);
            return recipe;
          } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
          }
        });
    
    recipeRepository.save(recipeMono.block()).block();
    return Mono.empty();
  }
}
