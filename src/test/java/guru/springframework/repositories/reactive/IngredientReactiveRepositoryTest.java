package guru.springframework.repositories.reactive;

import static org.junit.Assert.assertEquals;

import guru.springframework.domain.Ingredient;
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
public class IngredientReactiveRepositoryTest {
  
  @Autowired
  IngredientReactiveRepository ingredientReactiveRepository;
  @Autowired
  UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

  @Before public void setUp() {
    ingredientReactiveRepository.deleteAll();
    unitOfMeasureReactiveRepository.deleteAll();
  }

  @Test public void testSaveIngredient() {
    
    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setDescription("Cup");
    unitOfMeasure.setId("1");
    unitOfMeasureReactiveRepository.save(unitOfMeasure);

    Ingredient newIngredient = new Ingredient();
    newIngredient.setDescription("Water");
    newIngredient.setAmount(new BigDecimal(5));
    newIngredient.setUom(unitOfMeasure);
    Mono<Ingredient> savedIngredient = ingredientReactiveRepository.save(newIngredient);

    assertEquals(newIngredient.getDescription(), savedIngredient.block().getDescription());
    assertEquals(newIngredient.getUom(), savedIngredient.block().getUom());
    assertEquals(newIngredient.getAmount(), savedIngredient.block().getAmount());

  }
  
}