package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
  public void getRecipeByIdTest() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId("1");

    when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

    Recipe returnedRecipe = recipeService.findById("1").block();

    assertNotNull("Null Recipe returned", returnedRecipe);
    verify(recipeRepository, never()).findAll();
    verify(recipeRepository, times(1)).findById(anyString());

  }

  @Test public void getRecipeByIdTestNotFount() {
    
    when(recipeRepository.findById(anyString())).thenReturn(Mono.empty());
    
    Recipe recipeReturned =  recipeService.findById("1").block();
    
    //Then
    assertNull(recipeReturned);
  }

  @Test @Ignore public void getRecipeCommandByIdTest() throws Exception {
    //todo
  }
  
  @Test
  public void testGetRecipes() {
            
    when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe()));
    
    List<Recipe> recipes = recipeService.getRecipes().collectList().block();
    
    assertEquals(1, recipes.size());
    
    verify(recipeRepository, times(1)).findAll();
  }
  
  @Test
  public void testDeleteRecipe() throws Exception {
    //Given
    String recipeId = "2";
    
    //when
    when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());
    
    recipeService.deleteById(recipeId).block();
    
    verify(recipeRepository, times(1)).deleteById(anyString());
  }
  
}
