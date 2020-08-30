package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
public class RecipeController {
  
  private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
  
  private final RecipeService recipeService;
  
  public RecipeController(final RecipeService recipeService) {
    super();
    this.recipeService = recipeService;
  }
  
  @GetMapping("/recipe/{id}/show")
  public String showById(@PathVariable final String id, final Model model) {
    
    model.addAttribute("recipe", recipeService.findById(id).block());
    
    return "recipe/show";
  }
  
  @GetMapping("/recipe/new")
  public String newRecipe(final Model model) {
    model.addAttribute("recipe", new RecipeCommand());
    
    return RECIPE_RECIPEFORM_URL;
  }
  
  @GetMapping("/recipe/{id}/update")
  public String updateRecipe(@PathVariable final String id, final Model model) {
    model.addAttribute("recipe", recipeService.findCommandById(id).block());
    
    return RECIPE_RECIPEFORM_URL;
  }
  
  @PostMapping("/recipe")
  public String saveOrUpdate(@Valid @ModelAttribute("recipe") final RecipeCommand command,
      final BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
      return RECIPE_RECIPEFORM_URL;
    }
    final RecipeCommand savedCommand = recipeService.saveRecipeCommand(command).block();
    
    return "redirect:/recipe/" + savedCommand.getId() + "/show";
  }
  
  @GetMapping("/recipe/{id}/delete")
  public String deleteRecipe(@PathVariable final String id) {
    log.debug("Deleting Recipe" + id);
    recipeService.deleteById(id).block();
    
    return "redirect:/";
  }
  
  /*@ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ModelAndView handleNotFound(final Exception exception) {
    log.error("Handling not found exception");
    log.error(exception.getMessage());
    
    final ModelAndView modelAndView = new ModelAndView();
    
    modelAndView.setViewName("404error");
    modelAndView.addObject("exception", exception);
    
    return modelAndView;
  }*/
  
}
