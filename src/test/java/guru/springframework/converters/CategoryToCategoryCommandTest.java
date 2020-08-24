package guru.springframework.converters;

import static org.junit.Assert.*;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

public class CategoryToCategoryCommandTest {
  
  private static final String ID_VALUE = "1";
  private static final String DESCRIPTION = "description";
  CategoryToCategoryCommand converter;
  
  @Before
  public void setUp() throws Exception {
    converter = new CategoryToCategoryCommand();
  }
  
  @Test
  public void testNullObject() {
    assertNull(converter.convert(null));
  }
  
  @Test
  public void testEmptyObject() {
    assertNotNull(converter.convert(new Category()));
  }
  
  @Test
  public void testConvert() {
    //given
    Category category = new Category();
    category.setId(ID_VALUE);
    category.setDescription(DESCRIPTION);
    
    //When
    CategoryCommand categoryCommand = converter.convert(category);
    
    assertEquals(ID_VALUE, categoryCommand.getId());
    assertEquals(DESCRIPTION, categoryCommand.getDescription());
  }
  
}
