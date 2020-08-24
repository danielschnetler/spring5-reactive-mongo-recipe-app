package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.reactive.IngredientReactiveRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

public class IngredientServiceImplTest {

  IngredientServiceImpl ingredientService;
  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
  private final IngredientCommandToIngredient ingredientCommandToIngredient;
 
  @Mock
  RecipeReactiveRepository recipeRepository;

  @Mock
  IngredientReactiveRepository ingredientRepository;

  @Mock
  UnitOfMeasureReactiveRepository unitOfMeasureRepository;

  public IngredientServiceImplTest() {
    this.ingredientToIngredientCommand = 
        new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    this.unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
    this.ingredientCommandToIngredient = 
        new IngredientCommandToIngredient(this.unitOfMeasureCommandToUnitOfMeasure);
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);    
    ingredientService = new IngredientServiceImpl(recipeRepository, 
        ingredientToIngredientCommand, unitOfMeasureRepository);
  }

  @Test @Ignore public void testSaveRecipeCommand() throws Exception {
    //Todo
  }
  @Test @Ignore public void testDeleteById() throws Exception {
    //todo
  }
  
  @Test
  public void testFindByRecipeIdAndIngredientId() {
    // Given
    Recipe recipe = new Recipe();
    recipe.setId("1");
    
    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId("1");
    
    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId("2");
    
    Ingredient ingredient3 = new Ingredient();
    ingredient3.setId("3");
    
    recipe.addIngredient(ingredient1);
    recipe.addIngredient(ingredient2);
    recipe.addIngredient(ingredient3);
    
    when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
    
    // When
    IngredientCommand ingredientCommand =
          ingredientService.findByRecipeIdAndIngredientId("1", "3").block();
    
    assertNotNull(ingredientCommand);
    assertEquals("3", ingredientCommand.getId());
    assertEquals("1", ingredientCommand.getRecipeId());
    verify(recipeRepository, times(1)).findById(anyString());
  }
  
  @Test
  public void testSaveExistingIngredient() {
    // Given
    UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setDescription("Kilogram");
    unitOfMeasureCommand.setId("1");
    
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId("3");
    ingredientCommand.setRecipeId("2");
    ingredientCommand.setUom(unitOfMeasureCommand);

    Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
    
    Recipe savedRecipe = new Recipe();
    savedRecipe.setId("2");
    savedRecipe.addIngredient(ingredient);

    when(recipeRepository.findById(anyString())).thenReturn(Mono.just(savedRecipe));
    when(recipeRepository.save(any())).thenReturn(Mono.just(savedRecipe));
   
    UnitOfMeasure unitOfMeasure = 
        unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);

    when(unitOfMeasureRepository.findById(anyString()))
        .thenReturn(Mono.just(unitOfMeasure));
    
    // When
    IngredientCommand savedCommand =
        ingredientService.saveIngredientCommand(ingredientCommand).block();
    
    // Then
    assertNotNull(savedCommand);
    assertEquals(ingredientCommand.getId(), savedCommand.getId());
    
    verify(recipeRepository, times(1)).save(any(Recipe.class));
    verify(recipeRepository, times(1)).findById(anyString());
    
  }
  
  @Test
  public void deleteIngredient() {
    // Given
    String recipeId = "1";
    String ingredientId = "3";
    
    Recipe recipe = new Recipe();
    recipe.setId(recipeId);
    
    Ingredient ingredient = new Ingredient();
    ingredient.setId(ingredientId);
    
    recipe.addIngredient(ingredient);
    
    when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
    when(recipeRepository.save(any(Recipe.class))).thenReturn(Mono.empty());
    
    // When
    ingredientService.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);
    
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }
  
}
