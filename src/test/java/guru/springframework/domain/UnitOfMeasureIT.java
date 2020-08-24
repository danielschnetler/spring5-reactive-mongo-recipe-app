package guru.springframework.domain;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import guru.springframework.services.UnitOfMeasureService;
import guru.springframework.services.UnitOfMeasureServiceImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureIT {
  
  private static final String CUP = "Cup";
  private static final String TEASPOON = "Teaspoon";

  UnitOfMeasureService unitOfMeasureService;
  
  @Mock
  UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

  @Mock
  UnitOfMeasureReactiveRepository unitOfMeasureRepository;
  
  @Before
  public void setUp() throws Exception {
    
    MockitoAnnotations.initMocks(this);

    unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, 
        unitOfMeasureToUnitOfMeasureCommand);
  }
  
  @Test
  @Ignore("For now")
  public void findByDescription() throws Exception{
    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId("1");
    unitOfMeasure.setDescription(TEASPOON);

    when(unitOfMeasureRepository.findByDescription(anyString()))
        .thenReturn(Mono.just(unitOfMeasure));
    
    Mono<UnitOfMeasureCommand> unitOfMeasureCommand = 
        unitOfMeasureService.findByDescription(TEASPOON);
    UnitOfMeasureCommand command = unitOfMeasureCommand.block();

    assertEquals(TEASPOON, command.getDescription());
  }
  
  @Test
  @Ignore("For now")
  public void findByDescriptionCup() throws Exception{
    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId("1");
    unitOfMeasure.setDescription(CUP);

    when(unitOfMeasureRepository.findByDescription(anyString()))
        .thenReturn(Mono.just(unitOfMeasure));
    
    UnitOfMeasureCommand unitOfMeasureCommand = 
        unitOfMeasureService.findByDescription(CUP).block();

    assertEquals(CUP, unitOfMeasureCommand.getDescription());
  }
  
}
