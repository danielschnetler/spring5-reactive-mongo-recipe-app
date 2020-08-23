package guru.springframework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Spring5MongoRecipeAppApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(Spring5MongoRecipeAppApplication.class, args);
    log.debug("Project started");
  }
  
}
