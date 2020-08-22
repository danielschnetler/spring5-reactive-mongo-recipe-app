package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
  
  private final UnitOfMeasureReactiveRepository uomRepository;
  private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
  
  public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository uomRepository,
      UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
    super();
    this.uomRepository = uomRepository;
    this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
  }
  
  @Override
  public Flux<UnitOfMeasureCommand> findAll() {
    return uomRepository
        .findAll()
        .map(unitOfMeasureToUnitOfMeasureCommand::convert);
  }  
}
