package guru.springframework.repositories.reactive;

import static org.junit.Assert.assertEquals;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {
  private static final String CUP = "Cup";
  @Autowired
  UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

  @Before
  public void setUp(){
    unitOfMeasureReactiveRepository.deleteAll();
  }

  @Test public void testSaveUnitOfMeasure() {
    
    final UnitOfMeasure newUom = new UnitOfMeasure();
    newUom.setDescription("Spoon");

    final Mono<UnitOfMeasure> savedEntity = unitOfMeasureReactiveRepository.save(newUom);

    assertEquals(newUom.getDescription(), savedEntity.block().getDescription());
    assertEquals(1, unitOfMeasureReactiveRepository.findAll().count().block().intValue());
  }

  @Test public void testFindByDescription() {
    final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setDescription(CUP);

    unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

    final UnitOfMeasure fetchedUnitOfMeasure 
        = unitOfMeasureReactiveRepository.findByDescription(CUP).block();

    assertEquals(CUP, fetchedUnitOfMeasure.getDescription());

  }
  
}