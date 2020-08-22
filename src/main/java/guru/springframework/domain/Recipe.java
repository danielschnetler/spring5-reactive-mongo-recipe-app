package guru.springframework.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Recipe {

  private String id;
  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String source;
  private String url;
  private String directions;
  private Difficulty difficulty;
  private List<Ingredient> ingredients = new ArrayList<>();
  private Byte[] image;
  private Notes notes;
  
  @DBRef
  private List<Category> categories = new ArrayList<>();

  public Recipe addIngredient(Ingredient ingredient) {
    this.ingredients.add(ingredient); 
    return this;
  }

  public void setNotes(Notes notes) {
    if (notes != null) {
      this.notes = notes;
    }
  }
}
