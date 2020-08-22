package guru.springframework.repositories.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import guru.springframework.domain.Recipe;
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
  public void setUp(){
    recipeReactiveRepository.deleteAll();
  }

  @Test public void testRecipeSave() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setDescription("Italian Pizza");

    recipeReactiveRepository.save(recipe).block();

    Long count = recipeReactiveRepository.count().block();

    assertEquals(Long.valueOf(1L), count);
  }
  
}