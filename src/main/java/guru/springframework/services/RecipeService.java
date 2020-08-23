package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
  
  Flux<Recipe> getRecipes();
  
  Mono<Recipe> findById(String id);
  
  RecipeCommand saveRecipeCommand(RecipeCommand command);
  
  RecipeCommand findCommandById(String id);
  
  void deleteById(String id);
  
}
