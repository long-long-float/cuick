/**
 * 
 */
package jp.long_long_float.cuick.type;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author kazuki
 *
 */
public class TypeTest {

    /**
     * {@link java.lang.Object#equals(java.lang.Object)} のためのテスト・メソッド。
     */
    @Test
    public void testEqualsObject() {
        {
            Type type1 = new NamedType("tuple", null);
            List<Type> templs1 = new ArrayList<Type>();
            templs1.add(new BasicType("int", null));
            type1.setTemplateTypes(templs1);
            Type type2 = new NamedType("tuple", null);
            List<Type> templs2 = new ArrayList<Type>();
            templs2.add(new BasicType("int", null));
            type2.setTemplateTypes(templs2);
            
            assertEquals(type1, type2);
        }
        {
            Type type1 = new NamedType("tuple", null);
            List<Type> templs1 = new ArrayList<Type>();
            templs1.add(new BasicType("int", null));
            type1.setTemplateTypes(templs1);
            Type type2 = new NamedType("tuple", null);
            List<Type> templs2 = new ArrayList<Type>();
            templs2.add(new BasicType("float", null));
            type2.setTemplateTypes(templs2);
            
            assertNotEquals(type1, type2);
        }
    }

}
