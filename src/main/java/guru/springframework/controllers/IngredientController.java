package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {
  
  /**
   *
   */
  private static final String INGREDIENT = "ingredient";
  private final RecipeService recipeService;
  private final IngredientService ingredientService;
  private final UnitOfMeasureService unitOfMeasureService;
  
  public IngredientController(RecipeService recipeService, IngredientService ingredientService,
      UnitOfMeasureService unitOfMeasureService) {
    super();
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.unitOfMeasureService = unitOfMeasureService;
  }
  
  @GetMapping("/recipe/{id}/ingredients")
  public String listIngredients(@PathVariable String id, Model model) {
    log.debug("Listing ingredients for recipe " + id);
    
    model.addAttribute("recipe", recipeService.findCommandById(id).block());
    
    return "recipe/ingredient/list";
  }
  
  @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
  public String viewIngredient(Model model, 
      @PathVariable String recipeId, @PathVariable String ingredientId) {
    
    model.addAttribute(INGREDIENT, 
        ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());
    
    return "recipe/ingredient/show";
  }
  
  @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
  public String updateRecipeIngredient(Model model, @PathVariable String recipeId,
      @PathVariable String ingredientId) {
    model.addAttribute(INGREDIENT,
        ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());
    model.addAttribute("uomList", unitOfMeasureService.findAll().collectList().block());
    
    return "/recipe/ingredient/ingredientform";
  }
  
  @GetMapping("/recipe/{recipeId}/ingredient/new")
  public String newRecipe(Model model, @PathVariable String recipeId) {
    
    RecipeCommand command = recipeService.findCommandById(recipeId).block();
    //todo Raise exception if null
    
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(command.getId());
    ingredientCommand.setUom(new UnitOfMeasureCommand());
    
    model.addAttribute(INGREDIENT, ingredientCommand);
    model.addAttribute("uomList", unitOfMeasureService.findAll().collectList().block());
    
    return "/recipe/ingredient/ingredientform";
  }  
  
  @PostMapping("/recipe/{recipeId}/ingredient")
  public String saveCommand(@ModelAttribute IngredientCommand command) {
    IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();
    
    log.debug("Saved Recipe " + savedCommand.getRecipeId());
    log.debug("Saved Ingredient " + savedCommand.getId());
    
    return "redirect:/recipe/" + savedCommand.getRecipeId() 
        + "/ingredient/" + savedCommand.getId() + "/show";
  }
  
  @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
  public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
    
    ingredientService.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);
    
    return "redirect:/recipe/" + recipeId + "/ingredients";
  }
  
}
