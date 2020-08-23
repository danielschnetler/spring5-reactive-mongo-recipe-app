package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
  
  private final RecipeReactiveRepository recipeRepository;
  private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  
  public IngredientServiceImpl(RecipeReactiveRepository recipeRepository,
      UnitOfMeasureReactiveRepository unitOfMeasureRepository,
      IngredientToIngredientCommand ingredientToIngredientCommand) {
    super();
    this.recipeRepository = recipeRepository;
    this.unitOfMeasureRepository = unitOfMeasureRepository;
    this.ingredientToIngredientCommand = ingredientToIngredientCommand;
  }
  
  @Override
  public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
    Recipe recipe = recipeRepository.findById(recipeId).block();
    
    Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
        .filter(ingredient -> ingredient.getId().equals(ingredientId))
        .findFirst();

    if (!ingredientOptional.isPresent()) {
      log.error("Could not find ingredient with ID " + ingredientId);
    }
    IngredientCommand ingredientCommand = ingredientToIngredientCommand
        .convert(ingredientOptional.get());
    ingredientCommand.setRecipeId(recipeId);
    
    return ingredientCommand;
  }
  
  @Override
  public IngredientCommand saveIngredientCommand(IngredientCommand command) {
    
    Recipe recipe = recipeRepository.findById(command.getRecipeId()).block();
    
    Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
        .filter(ingredient -> ingredient.getId().equals(command.getId()))
        .findFirst();
    
    if (!ingredientOptional.isPresent()) {
      log.error("Could not find ingredient with ID " + command.getId());
    }

    Ingredient ingredient = ingredientOptional.get();
    ingredient.setAmount(command.getAmount());
    ingredient.setDescription(command.getDescription());
    ingredient.setUom(unitOfMeasureRepository.findById(command.getUom().getId()).block());
    
    Recipe savedRecipe = recipeRepository.save(recipe).block();
  
    Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
        .stream()
        .filter(savedIngredient -> savedIngredient.getId().equals(command.getId()))
        .findFirst();
  
    if (!savedIngredientOptional.isPresent()) {
      savedIngredientOptional = savedRecipe.getIngredients().stream()
          .filter(recipeIngredients -> recipeIngredients
              .getDescription().equals(command.getDescription()))
          .filter(recipeIngredients -> recipeIngredients
              .getAmount().equals(command.getAmount()))
          .filter(recipeIngredients -> recipeIngredients.getUom()
              .getId().equals(command.getUom().getId()))
          .findFirst();
    }
    IngredientCommand ingredientCommand = ingredientToIngredientCommand
        .convert(savedIngredientOptional.get());
    ingredientCommand.setRecipeId(recipe.getId());
    return ingredientCommand;
  }

  @Override
  public void deleteByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
    
    Recipe recipe = recipeRepository.findById(recipeId).block();

    Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
        .filter(ingredient -> ingredient.getId().equals(ingredientId))
        .findFirst();
    
    if (!ingredientOptional.isPresent()) {
      log.error("Ingredient " + ingredientId + " not found for Recipe " 
          + recipeId + ". Unable to perform delete action on ingredient.");
    }
    
    Ingredient ingredient = ingredientOptional.get();
 
    recipe.getIngredients().remove(ingredient);
    
    recipeRepository.save(recipe).block();    
  }
}
