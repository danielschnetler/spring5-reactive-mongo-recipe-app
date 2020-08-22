package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

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
  public Recipe findById(String id) {
    return recipeRepository.findById(id).block();
  }
  
  @Override
  @Transactional
  public RecipeCommand saveRecipeCommand(RecipeCommand command) {
    Recipe detatchedRecipe = recipeCommandToRecipe.convert(command);
    Recipe savedRecipe = recipeRepository.save(detatchedRecipe).block();
    log.debug("Saved RecipeId: " + savedRecipe.getId());
    return recipeToRecipeCommand.convert(savedRecipe);
  }
  
  @Override
  public RecipeCommand findCommandById(String id) {
    return recipeToRecipeCommand.convert(findById(id));
  }
  
  @Override
  public void deleteById(String id) {
    recipeRepository.deleteById(id).block();
  }
  
}
