package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {
  
  Optional<UnitOfMeasure> findById(String id);
  
  Optional<UnitOfMeasure> findByDescription(String description);
  
}