package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
  
  private final RecipeReactiveRepository recipeRepository;
  private final RecipeCommandToRecipe recipeCommandToRecipe;
  private final RecipeToRecipeCommand recipeToRecipeCommand;

  public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, 
      RecipeCommandToRecipe recipeCommandToRecipe,
      RecipeToRecipeCommand recipeToRecipeCommand) {
    super();
    this.recipeRepository = recipeRepository;
    this.recipeCommandToRecipe = recipeCommandToRecipe;
    this.recipeToRecipeCommand = recipeToRecipeCommand;
  }

  @Override
  public Flux<Recipe> getRecipes() {
    log.debug("I'm in the service -  Get Recipies");
    return recipeRepository.findAll();
  }
    
  @Override
  public Mono<Recipe> findById(String id) {
    log.debug("I'm in the service -  Find Recipy by ID");
    return recipeRepository.findById(id);
  }
  
  @Override
  public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
    log.debug("Saving RecipeId: " + command.getId());
    return recipeRepository.save(recipeCommandToRecipe.convert(command))
        .map(recipeToRecipeCommand::convert);    
  }
  
  @Override
  public Mono<RecipeCommand> findCommandById(String id) {
    return recipeRepository.findById(id)
        .map(recipe -> {
          RecipeCommand command = recipeToRecipeCommand.convert(recipe);
          command.getIngredients().forEach(rc -> 
              rc.setRecipeId(command.getId()));
          return command;
        });
  }
  
  @Override
  public Mono<Void> deleteById(String id) {
    recipeRepository.deleteById(id).block();
    return Mono.empty();
  }
  
}
