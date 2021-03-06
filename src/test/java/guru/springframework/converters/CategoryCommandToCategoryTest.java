package guru.springframework.converters;

import static org.junit.Assert.*;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

public class CategoryCommandToCategoryTest {
  
  private static final String ID_VALUE = "1";
  private static final String DESCRIPTION = "description";
  CategoryCommandToCategory converter;
  
  @Before
  public void setUp() throws Exception {
    converter = new CategoryCommandToCategory();
  }
  
  @Test
  public void testNullObject() {
    assertNull(converter.convert(null));
  }
  
  @Test
  public void testEmptyObject() {
    assertNotNull(converter.convert(new CategoryCommand()));
  }
  
  @Test
  public void testConvert() {
    //given
    CategoryCommand categoryCommand = new CategoryCommand();
    categoryCommand.setId(ID_VALUE);
    categoryCommand.setDescription(DESCRIPTION);
    
    //when
    Category category = converter.convert(categoryCommand);
    
    assertEquals(ID_VALUE, category.getId());
    assertEquals(DESCRIPTION, category.getDescription());
  }
  
}
