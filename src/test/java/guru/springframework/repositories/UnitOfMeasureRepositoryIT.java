package guru.springframework.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import guru.springframework.bootstrap.RecipeBootstrap;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.reactive.CategoryReactiveRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {
  
  @Autowired
  UnitOfMeasureReactiveRepository unitOfMeasureRepository;
  
  @Autowired
  CategoryReactiveRepository categoryRepository;
  
  @Autowired
  RecipeReactiveRepository recipeRepository;
  
  @Before
  public void setUp() throws Exception {
    
    unitOfMeasureRepository.deleteAll();
    categoryRepository.deleteAll();
    recipeRepository.deleteAll();
    
    RecipeBootstrap recipeBootstrap = new RecipeBootstrap(recipeRepository, 
        categoryRepository, unitOfMeasureRepository);
    
    recipeBootstrap.onApplicationEvent(null);
    
  }
  
  @Test
  public void findByDescription() throws Exception {
    
    UnitOfMeasure uom = unitOfMeasureRepository.findByDescription("Teaspoon").block();
    
    assertEquals("Teaspoon", uom.getDescription());
  }
  
  @Test
  public void findAnotherByDescription() throws Exception {
    
    UnitOfMeasure uom = unitOfMeasureRepository.findByDescription("Cup").block();
    
    assertEquals("Cup", uom.getDescription());
  }
  
  @After
  public void cleanUp(){
    
  }
}