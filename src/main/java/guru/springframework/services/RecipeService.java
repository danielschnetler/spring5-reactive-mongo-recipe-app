package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import reactor.core.publisher.Flux;

public interface RecipeService {
  
  Flux<Recipe> getRecipes();
  
  Recipe findById(String id);
  
  RecipeCommand saveRecipeCommand(RecipeCommand command);
  
  RecipeCommand findCommandById(String id);
  
  void deleteById(String id);
  
}
