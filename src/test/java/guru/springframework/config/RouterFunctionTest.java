package guru.springframework.config;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

public class RouterFunctionTest {

  WebTestClient webTestClient;

  @Mock
  RecipeService recipeService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    WebConfig webConfig = new WebConfig();

    RouterFunction<?> router = webConfig.routes(recipeService);

    webTestClient = WebTestClient.bindToRouterFunction(router).build();
  }

  @Test
  public void testApi() throws Exception {
    
    when(recipeService.getRecipes()).thenReturn(Flux.empty());

    webTestClient.get().uri("/api/recipes/")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk();        
  }

  @Test
  public void testApiWithBody() throws Exception {
    
    when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe(), new Recipe()));

    webTestClient.get().uri("/api/recipes/")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Recipe.class);
  }
  
}