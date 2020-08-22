package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.reactive.CategoryReactiveRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
  
  private final RecipeReactiveRepository recipeRepository;
  private final CategoryReactiveRepository categoryRepository;
  private final UnitOfMeasureReactiveRepository uomRepository;
 
  public RecipeBootstrap(RecipeReactiveRepository recipeRepository,
      CategoryReactiveRepository categoryRepository,
      UnitOfMeasureReactiveRepository uomRepository) {
    super();
    this.recipeRepository = recipeRepository;
    this.categoryRepository = categoryRepository;
    this.uomRepository = uomRepository;
    log.debug("Bootstrap Class injected");
  }
  
  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    log.debug("loading bootstrap data");
  
    loadCategories();
    loadUom();
    recipeRepository.saveAll(getRecipes());
  }
  
  private void loadUom() {
    UnitOfMeasure uom1 = new UnitOfMeasure();
    uom1.setDescription("Teaspoon");
    uomRepository.save(uom1);
    
    UnitOfMeasure uom2 = new UnitOfMeasure();
    uom2.setDescription("Tablespoon");
    uomRepository.save(uom2);
    
    UnitOfMeasure uom3 = new UnitOfMeasure();
    uom3.setDescription("Cup");
    uomRepository.save(uom3);
    
    UnitOfMeasure uom4 = new UnitOfMeasure();
    uom4.setDescription("Pinch");
    uomRepository.save(uom4);
    
    UnitOfMeasure uom5 = new UnitOfMeasure();
    uom5.setDescription("Ounce");
    uomRepository.save(uom5);
    
    UnitOfMeasure uom6 = new UnitOfMeasure();
    uom6.setDescription("Each");
    uomRepository.save(uom6);
    
    UnitOfMeasure uom7 = new UnitOfMeasure();
    uom7.setDescription("Pint");
    uomRepository.save(uom7);
    
    UnitOfMeasure uom8 = new UnitOfMeasure();
    uom8.setDescription("Dash");
    uomRepository.save(uom8);
    
  }
  
  private void loadCategories() {
    Category cat1 = new Category();
    cat1.setDescription("American");
    categoryRepository.save(cat1);
    
    Category cat2 = new Category();
    cat2.setDescription("Italian");
    categoryRepository.save(cat2);
    
    Category cat3 = new Category();
    cat3.setDescription("Mexican");
    categoryRepository.save(cat3);
    
    Category cat4 = new Category();
    cat4.setDescription("Fast Food");
    categoryRepository.save(cat4);
    
  }
  
  private List<Recipe> getRecipes() {
    //UnitOfMeasure pinchUom = uomRepository.findByDescription("Pinch").block();
    //UnitOfMeasure ounceUom = uomRepository.findByDescription("Ounce").block();
    //Category italianCategory = categoryRepository.findByDescription("Italian").block();    
    //Category fastFoodCategory = categoryRepository.findByDescription("Fast Food").block();
    
    /*****Guacamole Recipe*****/
    
    Recipe guacamoleRecipe = new Recipe();
    guacamoleRecipe.setDescription("The Perfect Guacamole Recipe");
    guacamoleRecipe.setPrepTime(10);
    guacamoleRecipe.setCookTime(0);
    guacamoleRecipe.setDifficulty(Difficulty.EASY);
    guacamoleRecipe.setDirections("1 Cut avocado, remove flesh: " 
        + "Cut the avocados in half. Remove seed. "
        + "Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon."
        + "\n"
        + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The "
        + "guacamole should be a little chunky.)" + "\n" 
        + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice." 
        + " The acid in the lime juice will provide some balance to the richness of the avocado " 
        + "and will help delay the avocados from turning brown. Add the chopped onion, cilantro, "
        + "black pepper, and chiles. Chili peppers vary individually in their hotness. So, start "
        + "with a half of one chili pepper and add to "
        + "the guacamole to your desired degree of hotness.Remember that much of this is done to "
        + "taste because "
        + "of the variability in the fresh ingredients. Start with this recipe and adjust to your "
        + "taste." + "\n"
        + "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the "
        + "guacamole cover it "
        + "and to prevent air reaching it. (The oxygen in the air causes oxidation which will " 
        + "turn the guacamole "
        + "brown.) Refrigerate until ready to serve. Chilling tomatoes hurts their flavor, so "
        + "if you want to "
        + "add chopped tomato to your guacamole, add it just before serving.");
    guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
    guacamoleRecipe.setSource("Simply Recipes");
    guacamoleRecipe.setServings(4);
    
    Notes guacamoleNotes = new Notes();
    guacamoleNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix "
        + "it in with your "
        + "mashed avocados.Feel free to experiment! One classic Mexican guacamole has pomegranate "
        + "seeds and "
        + "chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, "
        + "mango, "
        + "or strawberries (see our Strawberry Guacamole).The simplest version of guacamole is just"
        + " mashed "
        + "avocados with salt. Don't let the lack of availability of other ingredients stop you "
        + "from making "
        + "guacamole. To extend a limited supply of avocados, add either sour cream or cottage "
        + "cheese to "
        + "your guacamole dip. Purists may be horrified, but so what? It tastes great.For a "
        + "deviled egg "
        + "version with guacamole, try our Guacamole Deviled Eggs!");
    guacamoleRecipe.setNotes(guacamoleNotes);
   
    UnitOfMeasure eachUom = uomRepository.findByDescription("Each").block();
    guacamoleRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
    
    UnitOfMeasure teaspoonUom = uomRepository.findByDescription("Teaspoon").block(); 
    guacamoleRecipe.addIngredient(new Ingredient("Kosher salt", 
        new BigDecimal("0.5"), teaspoonUom));

    UnitOfMeasure tablespoonUom = uomRepository.findByDescription("Tablespoon").block();
    guacamoleRecipe.addIngredient(new Ingredient("Fresh Lime or Lemon juice", 
        new BigDecimal(1), tablespoonUom));
    guacamoleRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", 
        new BigDecimal(2), tablespoonUom));
    guacamoleRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", 
        new BigDecimal(2), eachUom));
    guacamoleRecipe.addIngredient(
        new Ingredient("cilantro (leaves and tender stems), finely chopped", 
        new BigDecimal(2), tablespoonUom));

    UnitOfMeasure dashUom = uomRepository.findByDescription("Dash").block();
    guacamoleRecipe.addIngredient(new Ingredient("freshly grated black pepper", 
        new BigDecimal(1), dashUom));
    guacamoleRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped",
        new BigDecimal("0.5"), eachUom));
    
    Category americanCategory = categoryRepository.findByDescription("American").block();
    Category mexicanCategory = categoryRepository.findByDescription("Mexican").block();
    guacamoleRecipe.getCategories().add(americanCategory);
    guacamoleRecipe.getCategories().add(mexicanCategory);
    
    List<Recipe> recipies = new ArrayList<>(2);
    recipies.add(guacamoleRecipe);
    
    //Yummy Tacos
    Recipe tacosRecipe = new Recipe();
    tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
    tacosRecipe.setCookTime(9);
    tacosRecipe.setPrepTime(20);
    tacosRecipe.setDifficulty(Difficulty.MODERATE);
    
    tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" 
        + "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili "
        + "powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange "
        + "juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to "
        + "coat all over.\n" 
        + "Set aside to marinate while the grill heats and you prepare the rest of the toppings."
        + "\n\n\n" 
        + "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a "
        + "thermometer inserted into the thickest part of the meat registers 165F. Transfer to "
        + "a plate and rest for 5 minutes.\n" 
        + "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over "
        + "medium-high heat. As soon as you see pockets of the air start to puff up in the "
        + "tortilla, turn it with tongs and heat for a few seconds on the other side.\n" 
        + "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" 
        + "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a "
        + "small handful of arugula. Top with chicken slices, sliced avocado, radishes, "
        + "tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime "
        + "wedges.\n\n\n" 
        + "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");
    
    Notes tacoNotes = new Notes();
    tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in "
        + "a tortilla.\n" 
        + "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy "
        + "dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma "
        + "of tortillas heating in a hot pan on the stove comes wafting through the house.\n" 
        + "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight "
        + "snack!\n" 
        + "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, "
        + "cumin, and sweet orange juice while the grill is heating. You can also use this time to "
        + "prepare the taco toppings.\n" 
        + "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to " 
        + "assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" 
        + "\n\n" 
        + "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
    tacosRecipe.setNotes(tacoNotes);
    
    tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder",
        new BigDecimal(2), tablespoonUom));
    tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoonUom));
    tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoonUom));
    tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoonUom));
    tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoonUom));
    tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Chopped", 
        new BigDecimal(1), eachUom));
    tacosRecipe.addIngredient(new Ingredient("finely grated orange zest", 
        new BigDecimal(1), tablespoonUom));
    tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", 
        new BigDecimal(3), tablespoonUom));
    tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tablespoonUom));
    tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", 
        new BigDecimal(4), tablespoonUom));
    tacosRecipe.addIngredient(new Ingredient("small corn tortillas", new BigDecimal(8), eachUom));
    
    UnitOfMeasure cupUom = uomRepository.findByDescription("Cup").block();
    tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupUom));
    
    tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, sliced", 
        new BigDecimal(2), eachUom));
    tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", 
        new BigDecimal(4), eachUom));
    
    UnitOfMeasure pintUom = uomRepository.findByDescription("Pint").block();
    tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", 
        new BigDecimal(".5"), pintUom));
    tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", 
        new BigDecimal(".25"), eachUom));
    tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", 
        new BigDecimal(4), eachUom));
    tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk",
        new BigDecimal(4), cupUom));
    tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", 
        new BigDecimal(4), eachUom));
    
    tacosRecipe.getCategories().add(americanCategory);
    tacosRecipe.getCategories().add(mexicanCategory);
    
    recipies.add(tacosRecipe);
    return recipies;
  }
  
  
  
}
