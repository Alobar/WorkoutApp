package alobar.util;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link NullObject}
 */
public class NullObjectTests {

    @Test
    public void commandFunctionsAreNoOperations() {
        Foo foo = NullObject.get(Foo.class);
        assertNotNull(foo);

        foo.command(1, Math.PI, null);
    }

    @Test
    public void nonPrimitiveQueryFunctionsReturnNullObjects() {
        Bar bar = NullObject.get(Bar.class);
        assertNotNull(bar);

        Foo foo = bar.query();
        assertNotNull(foo);

        foo.command(1, Math.PI, null);
    }

    @Test
    public void nullObjectsAreSingletons() {
        Foo foo1 = NullObject.get(Foo.class);
        Foo foo2 = NullObject.get(Foo.class);
        assertTrue(foo1 == foo2);
    }

    @Test
    public void circularDependenciesAllowed() {
        Bacon bacon1 = NullObject.get(Bacon.class);
        Cheese cheese1 = NullObject.get(Cheese.class);

        assertTrue(bacon1 != cheese1);

        Bacon bacon = bacon1;
        Cheese cheese = cheese1;
        for (int i = 0; i < 1000; i += 1) {
            cheese = bacon.getCheeze();
            bacon = cheese.getBacon();
        }

        assertTrue(bacon == bacon1);
        assertTrue(cheese == cheese1);
    }

    @SuppressWarnings({"PointlessBooleanExpression", "RedundantCast"})
    @Test
    public void primitiveQueriesReturnDefaultValues() {
        Primitives primitives = NullObject.get(Primitives.class);
        assertTrue(primitives.getBoolean() == false);
        assertTrue(primitives.getByte() == (byte) 0);
        assertTrue(primitives.getShort() == (short) 0);
        assertTrue(primitives.getInt() == (int) 0);
        assertTrue(primitives.getLong() == (long) 0);
        assertTrue(primitives.getFloat() == (float) 0);
        assertTrue(primitives.getDouble() == (double) 0);
        assertTrue(primitives.getChar() == (char) 0);
    }

    @Test
    public void boxedQueriesReturnNull() {
        Boxedes boxedes = NullObject.get(Boxedes.class);
        assertNull(boxedes.getBoolean());
        assertNull(boxedes.getByte());
        assertNull(boxedes.getShort());
        assertNull(boxedes.getInt());
        assertNull(boxedes.getLong());
        assertNull(boxedes.getFloat());
        assertNull(boxedes.getDouble());
        assertNull(boxedes.getChar());
    }

    @Test
    public void otherQueriesReturnNull() {
        Others others = NullObject.get(Others.class);
        assertNull(others.getObject());
        assertNull(others.getDate());
        assertNull(others.getEnum());
        assertNull(others.getString());
        assertNull(others.getThrowable());
        assertNull(others.getClazz());
    }

    @SuppressWarnings("ObjectEqualsNull")
    @Test
    public void equalsWorks() {
        Foo foo1 = NullObject.get(Foo.class);
        Foo foo2 = NullObject.get(Foo.class);
        Foo foo3 = new Foo() {
            @Override
            public void command(int a, double b, Bar c) {
            }
        };

        assertTrue(foo1.equals(foo2));
        assertTrue(foo2.equals(foo1));
        assertFalse(foo1.equals(null));

        assertFalse(foo1.equals(foo3));
        assertFalse(foo3.equals(foo1));
    }

    @Test
    public void hashCodeWorks() {
        Foo foo1 = NullObject.get(Foo.class);
        Foo foo2 = NullObject.get(Foo.class);
        Foo foo3 = new Foo() {
            @Override
            public void command(int a, double b, Bar c) {
            }
        };

        assertTrue(foo1.hashCode() == foo2.hashCode());
        assertTrue(foo1.hashCode() != foo3.hashCode());
    }

    public interface Foo {
        void command(int a, double b, Bar c);
    }

    public interface Bar {
        Foo query();
    }

    public interface Bacon {
        Cheese getCheeze();
    }

    public interface Cheese {
        Bacon getBacon();
    }

    public interface Primitives {
        boolean getBoolean();
        byte getByte();
        short getShort();
        int getInt();
        long getLong();
        float getFloat();
        double getDouble();
        char getChar();
    }

    public interface Boxedes {
        Boolean getBoolean();
        Byte getByte();
        Short getShort();
        Integer getInt();
        Long getLong();
        Float getFloat();
        Double getDouble();
        Character getChar();
    }

    public interface Others {
        Object getObject();
        String getString();
        Date getDate();
        Throwable getThrowable();
        Enum getEnum();
        Class getClazz();
    }
}
