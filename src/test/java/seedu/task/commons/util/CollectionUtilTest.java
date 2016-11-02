package seedu.task.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CollectionUtilTest {
    
    ArrayList<String> list;
    String str1;
    String str2;
    String str3;
    String str4;
    String str5;
    
    @Before
    public void setup() {
        str1 = null;
        str2 = new String("test");
        str3 = str2;
        str4 = new String("demo");
        str5 = new String("exam");
    }
    
    @Test
    public void isAnyNull_hasNullArgs_returnTrue() {
        assertTrue("Should have at least 1 null args", CollectionUtil.isAnyNull(str1, str2));
    }
    
    @Test
    public void isAnyNull_hasNoNullArgs_returnFalse() {
        assertFalse("Should have no null args", CollectionUtil.isAnyNull(str4, str2));
    }
    
    @Test
    public void elementsAreUnique_allElementsAreUnique_returnTrue() {
        list = new ArrayList<String>();
        list.add(str2);
        list.add(str4);
        list.add(str5);
        assertTrue("All elements should be unique", CollectionUtil.elementsAreUnique(list));
    }
    
    @Test
    public void elementsAreUnique_hasNonUniqueElements_returnFalse() {
        list = new ArrayList<String>();
        list.add(str2);
        list.add(str3);
        list.add(str5);
        assertFalse("Should have non unique elements", CollectionUtil.elementsAreUnique(list));
    }

}
