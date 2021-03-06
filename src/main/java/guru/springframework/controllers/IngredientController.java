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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
  private WebDataBinder webDataBinder;
  
  public IngredientController(RecipeService recipeService, IngredientService ingredientService,
      UnitOfMeasureService unitOfMeasureService) {
    super();
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.unitOfMeasureService = unitOfMeasureService;
  }
  
  @InitBinder("ingredient")
  private void initBinder(WebDataBinder webDataBinder) {
    this.webDataBinder = webDataBinder;
  }
  
  @GetMapping("/recipe/{id}/ingredients")
  public String listIngredients(@PathVariable String id, Model model) {
    log.debug("Listing ingredients for recipe " + id);
    
    model.addAttribute("recipe", recipeService.findCommandById(id));
    
    return "recipe/ingredient/list";
  }
  
  @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
  public String viewIngredient(Model model, 
      @PathVariable String recipeId, @PathVariable String ingredientId) {
    
    model.addAttribute(INGREDIENT, 
        ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
    
    return "/recipe/ingredient/show";
  }
  
  @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
  public String updateRecipeIngredient(Model model, @PathVariable String recipeId,
      @PathVariable String ingredientId) {
    model.addAttribute(INGREDIENT,
        ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
    
    return "/recipe/ingredient/ingredientform";
  }
  
  @GetMapping("/recipe/{recipeId}/ingredient/new")
  public String newRecipeIngredient(Model model, @PathVariable String recipeId) {
    
    RecipeCommand command = recipeService.findCommandById(recipeId).block();
    //todo Raise exception if null
    
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(command.getId());
    ingredientCommand.setUom(new UnitOfMeasureCommand());
    
    model.addAttribute(INGREDIENT, ingredientCommand);
       
    return "/recipe/ingredient/ingredientform";
  }  
  
  @PostMapping("/recipe/{recipeId}/ingredient")
  public String saveOrUpdateCommand(@ModelAttribute("ingredient") IngredientCommand command, 
      @PathVariable String recipeId, Model model) {
    
    webDataBinder.validate();
    BindingResult bindingResult = webDataBinder.getBindingResult();
    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors().forEach(error -> log.debug(error.toString()));
      return "/recipe/ingredient/ingredientform";
    }
    
    command.setRecipeId(recipeId);
    Mono<IngredientCommand> savedCommand = ingredientService.saveIngredientCommand(command);
    
    log.debug("Saved Recipe " + savedCommand.block().getRecipeId());
    log.debug("Saved Ingredient " + savedCommand.block().getId());
    
    return "redirect:/recipe/" + savedCommand.block().getRecipeId() 
        + "/ingredient/" + savedCommand.block().getId() + "/show";
  }
  
  @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
  public String deleteIngredient(@PathVariable String recipeId, 
      @PathVariable String ingredientId) {
    
    ingredientService.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);
    
    return "redirect:/recipe/" + recipeId + "/ingredients";
  }

  @ModelAttribute("uomList")
  public Flux<UnitOfMeasureCommand> populateUomList(){
    return unitOfMeasureService.findAll();
  }
  
}
