package guru.springframework.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

public class IndexControllerTest {
  
  @Mock
  RecipeService recipeService;
  
  @Mock
  Model model;
  
  IndexController indexController;
  
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    indexController = new IndexController(recipeService);
  }
  
  @Test
  public void testMockMvc() throws Exception {
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

    when(recipeService.getRecipes()).thenReturn(Flux.empty());

    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));
  }
  
  @Test
  public void testGetIndexPage() throws Exception {
    
    //Given
    Recipe recipe = new Recipe();
    recipe.setId("2");
    Set<Recipe> recipes = new HashSet<>();

    recipes.add(new Recipe());
    recipes.add(recipe);
    
    when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));
    
    ArgumentCaptor<List<Recipe>> argumentCaptor 
        = ArgumentCaptor.forClass(List.class);
    
    //When
    String viewName = indexController.getIndexPage(model);
    
    //Then
    assertEquals("index", viewName);
    
    verify(recipeService, times(1)).getRecipes();
    verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
    
    List<Recipe> recipeList = argumentCaptor.getValue();
    assertEquals(2, recipeList.size());
  }
  
}
