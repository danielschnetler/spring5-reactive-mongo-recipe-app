package guru.springframework.repositories.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReactiveRepositoryIT {
  @Autowired
  CategoryReactiveRepository categoryReactiveRepository;
  @Autowired
  RecipeReactiveRepository recipeReactiveRepository;
  @Autowired
  UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
  @Autowired
  IngredientReactiveRepository ingredientReactiveRepository;

  @Before public void setUp() {
    categoryReactiveRepository.deleteAll();
    recipeReactiveRepository.deleteAll();
    unitOfMeasureReactiveRepository.deleteAll();
    ingredientReactiveRepository.deleteAll();
  }

  @Test public void testSaveRecipe() {

    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setDescription("Cup");
    unitOfMeasure.setId("1");
    unitOfMeasureReactiveRepository.save(unitOfMeasure);

    Ingredient newIngredient = new Ingredient();
    newIngredient.setDescription("Water");
    newIngredient.setAmount(new BigDecimal(5));
    newIngredient.setUom(unitOfMeasure);
    ingredientReactiveRepository.save(newIngredient);

    Category newCategory = new Category();
    newCategory.setDescription("Italian");
    newCategory.setId("1");
    categoryReactiveRepository.save(newCategory);

    Recipe newRecipe = new Recipe();
    newRecipe.addIngredient(newIngredient);
    newRecipe.getCategories().add(newCategory);
    newRecipe.setDescription("Italian Pasta");
    newRecipe.setCookTime(5);
    newRecipe.setPrepTime(10);
    newRecipe.setDifficulty(Difficulty.EASY);
    newRecipe.setServings(3);
    newRecipe.setDirections("Cook some pasta and add tomato");
    
    Mono<Recipe> savedMonoRecipe = recipeReactiveRepository.save(newRecipe);
    Recipe savedRecipe = savedMonoRecipe.block();

    assertEquals(newRecipe.getCategories().size(), savedRecipe.getCategories().size());
    assertEquals(newRecipe.getIngredients().size(), savedRecipe.getIngredients().size());
    assertEquals(newRecipe.getDescription(), savedRecipe.getDescription());

    assertEquals(Long.valueOf(1L), recipeReactiveRepository.count().block());
  }

}