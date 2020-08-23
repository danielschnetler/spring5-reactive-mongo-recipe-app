package guru.springframework.repositories.reactive;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import guru.springframework.domain.Recipe;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {

  @Autowired
  RecipeReactiveRepository recipeReactiveRepository;

  @Before
  public void setUp() {
    recipeReactiveRepository.deleteAll().block();
    assertEquals(0L, recipeReactiveRepository.findAll().count().block());
  }

  @Test public void testRecipeSave() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setDescription("Italian Pizza");

    recipeReactiveRepository.save(recipe).block();

    Long count = recipeReactiveRepository.count().block();

    assertEquals(Long.valueOf(1L), count);
  }
  
  @Test public void testFindAll() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setDescription("Italian Pizza");

    recipeReactiveRepository.save(recipe).block(); 

    List<Recipe> recipeList = recipeReactiveRepository.findAll().collectList().block();
    
    assertEquals(recipe.getDescription(), recipeList.get(0).getDescription());
    assertNotNull(recipeList.get(0).getId());
  }

  @Test public void testFindById() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setDescription("Italian Pasta");

    recipeReactiveRepository.save(recipe).block(); 

    List<Recipe> recipeList = recipeReactiveRepository.findAll().collectList().block();
    
    String recipeId = recipeList.get(0).getId();

    Recipe returnedRecipe = recipeReactiveRepository.findById(recipeId).block();
    assertEquals(recipe.getDescription(), returnedRecipe.getDescription());
  }
}