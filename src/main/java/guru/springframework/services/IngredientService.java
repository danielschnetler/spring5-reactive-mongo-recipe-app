package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {
  Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
  
  Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);
  
  void deleteByRecipeIdAndIngredientId(String recipeId, String ingredientId);
}
