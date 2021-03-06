package guru.springframework.services;

import static org.junit.Assert.*;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {
  
  public static final String NEW_DESCRIPTION = "New Description";
  
  @Autowired
  RecipeService recipeService;
  
  @Autowired
  RecipeReactiveRepository recipeRepository;
  
  @Autowired
  RecipeCommandToRecipe recipeCommandToRecipe;
  
  @Autowired
  RecipeToRecipeCommand recipeToRecipeCommand;
  
  
  @Transactional
  @Test
  @Ignore("Not working")
  public void testSaveOfDescription() {
    Iterable<Recipe> recipes = recipeRepository.findAll().collectList().block();
    Recipe testRecipe = recipes.iterator().next();
    RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);
    
    //When
    testRecipeCommand.setDescription(NEW_DESCRIPTION);
    RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand).block();
    
    //then
    assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
    assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
    assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
    assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
  }
  
}
