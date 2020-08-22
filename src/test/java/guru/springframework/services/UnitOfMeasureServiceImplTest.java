package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

public class UnitOfMeasureServiceImplTest {
  
  UnitOfMeasureService service;
  UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand = new UnitOfMeasureToUnitOfMeasureCommand();
  
  @Mock
  UnitOfMeasureReactiveRepository uomRepository;
  
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    service = new UnitOfMeasureServiceImpl(uomRepository, uomToUomCommand);
  }
  
  @Test
  public void testFindAll() {
    //Given
    Set<UnitOfMeasure> unitOfMeasures = new HashSet<UnitOfMeasure>();
    UnitOfMeasure uom1 = new UnitOfMeasure();
    uom1.setId("1");
    unitOfMeasures.add(uom1);
    
    UnitOfMeasure uom2 = new UnitOfMeasure();
    uom2.setId("2");
    unitOfMeasures.add(uom2);
    
    when(uomRepository.findAll()).thenReturn(Flux.just(uom1, uom2));
    
    //When
    List<UnitOfMeasureCommand> uomCommandSet = service.findAll().collectList().block();
    
    //Then
    assertNotNull(uomCommandSet);
    assertEquals(unitOfMeasures.size(), uomCommandSet.size());
    
    verify(uomRepository, times(1)).findAll();
  }
  
}
