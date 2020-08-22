package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;

  @Mock
  RecipeReactiveRepository recipeRepository;

  @Mock
  RecipeToRecipeCommand recipeToRecipeCommand;

  @Mock
  RecipeCommandToRecipe recipeCommandToRecipe;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    recipeService = new RecipeServiceImpl(recipeRepository, 
        recipeCommandToRecipe, recipeToRecipeCommand);
  }

  @Test
  public void getRecipeByIdTest() {
    Recipe recipe = new Recipe();
    recipe.setId("1");
    Mono<Recipe> monoRecipe = Mono.just(recipe);

    when(recipeRepository.findById(anyString())).thenReturn(monoRecipe);

    Recipe returnedRecipe = recipeService.findById("1");

    assertNotNull(returnedRecipe);
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, never()).findAll();

  }

  private VerificationMode times(int i) {
    return null;
  }

  @Test(expected = NotFoundException.class)
  public void getRecipeByIdTestNotFount() {
    
    Mono<Recipe> monoRecipe = Mono.empty();
    
    when(recipeRepository.findById(anyString())).thenReturn(monoRecipe);
    Recipe recipeReturned =  recipeService.findById("1");
    
    //Then
    assertNull(recipeReturned);
  }
  
  @Test
  public void testGetRecipes() {
    
    Recipe recipe = new Recipe();
    Flux<Recipe> recipesData = Flux.just(recipe);
        
    when(recipeRepository.findAll()).thenReturn(recipesData);
    
    List<Recipe> recipes = recipeService.getRecipes().collectList().block();
    
    assertEquals(1, recipes.size());
    
    verify(recipeRepository, times(1)).findAll();
  }
  
  @Test
  public void testDeleteRecipe() {
    //Given
    String recipeId = "2";
    
    //when
    recipeService.deleteById(recipeId);
    
    verify(recipeRepository, times(1)).deleteById(anyString());
  }
  
}
