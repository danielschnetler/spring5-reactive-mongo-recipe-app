package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
  
  private final RecipeReactiveRepository recipeRepository;
  
  public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
    super();
    this.recipeRepository = recipeRepository;
  }
  
  @Override
  public void saveImageFile(String recipeId, MultipartFile file) {
    log.debug("Recieved image for upload");
    
    try {
      Recipe recipe = recipeRepository.findById(recipeId).block();
      
      Byte[] byteObjects = new Byte[file.getBytes().length];
      
      int i = 0;
      
      for (byte b : file.getBytes()) {
        byteObjects[i++] = b;
      }
      
      recipe.setImage(byteObjects);
      
      recipeRepository.save(recipe);
    } catch (IOException e) {
      log.error("Error occurred", e);
      e.printStackTrace();
    }
  }
}
