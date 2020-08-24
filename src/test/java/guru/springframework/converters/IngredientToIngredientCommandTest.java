package guru.springframework.converters;

import static org.junit.Assert.*;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

public class IngredientToIngredientCommandTest {
  
  public static final Recipe RECIPE = new Recipe();
  public static final BigDecimal AMOUNT = new BigDecimal("1");
  public static final String DESCRIPTION = "description";
  public static final String ID_VALUE = "1";
  public static final String UOM_ID = "2";
  
  IngredientToIngredientCommand converter;
  
  @Before
  public void setUp() throws Exception {
    converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
  }
  
  @Test
  public void testNullObject() {
    assertNull(converter.convert(null));
  }
  
  @Test
  public void testEmptyObject() {
    assertNotNull(converter.convert(new Ingredient()));
  }
  
  @Test
  public void testConvert() {
    //Given
    Ingredient ingredient = new Ingredient();
    ingredient.setId(ID_VALUE);
    ingredient.setAmount(AMOUNT);
    ingredient.setDescription(DESCRIPTION);
    ingredient.setId(ID_VALUE);
    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(UOM_ID);
    ingredient.setUom(uom);
    
    //when
    IngredientCommand command = converter.convert(ingredient);
    
    //Then
    assertNotNull(command);
    assertNotNull(command.getUom());
    assertEquals(ID_VALUE, command.getId());
    assertEquals(AMOUNT, command.getAmount());
    assertEquals(DESCRIPTION, command.getDescription());
    assertEquals(UOM_ID, command.getUom().getId());
    
  }
  
  @Test
  public void testConvertWithNullUom() {
    //Given
    Ingredient ingredient = new Ingredient();
    ingredient.setId(ID_VALUE);
    ingredient.setAmount(AMOUNT);
    ingredient.setDescription(DESCRIPTION);
    ingredient.setId(ID_VALUE);
    
    //when
    IngredientCommand command = converter.convert(ingredient);
    
    //Then
    assertNotNull(command);
    assertNull(command.getUom());
    assertEquals(ID_VALUE, command.getId());
    assertEquals(AMOUNT, command.getAmount());
    assertEquals(DESCRIPTION, command.getDescription());
  }
  
}
