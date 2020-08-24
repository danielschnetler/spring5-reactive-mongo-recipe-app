package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
  
  private final RecipeReactiveRepository recipeRepository;
  private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  
  public IngredientServiceImpl(RecipeReactiveRepository recipeRepository,
      IngredientToIngredientCommand ingredientToIngredientCommand,
      UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
    super();
    this.recipeRepository = recipeRepository;
    this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
  }
  
  @Override
  public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, 
      String ingredientId) {
    return recipeRepository.findById(recipeId)
        .flatMapIterable(Recipe::getIngredients)
        .filter(ingredient -> ingredient.getId().equals(ingredientId))
        .single()
        .map(ingredient -> {
          IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
          command.setRecipeId(recipeId);
          return command;
        });
  }
  
  @Override
  public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {

    Recipe recipe = recipeRepository.findById(command.getRecipeId()).block();

    Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
        .filter(ingredient -> ingredient.getId().equals(command.getId()))
        .findFirst();
    
    if (!ingredientOptional.isPresent()){
      log.error("Could not find ingredient with ID " + command.getId());
    }
    
    Ingredient ingredient = ingredientOptional.get();
    ingredient.setAmount(command.getAmount());
    ingredient.setDescription(command.getDescription());
    ingredient.setUom(unitOfMeasureReactiveRepository.findById(command.getUom().getId()).block());

    recipeRepository.save(recipe).block();
    
    return Mono.just(command);
  }

  @Override
  public void deleteByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
    
    Recipe recipe = recipeRepository
        .findById(recipeId)
        .block();
    
    List<Ingredient> ingredients = recipe.getIngredients().stream()
        .filter(ingredient -> !ingredient.getId().equals(ingredientId))
        .collect(Collectors.toList());

    recipe.setIngredients(ingredients);

    recipeRepository.save(recipe).block();
  }
}
