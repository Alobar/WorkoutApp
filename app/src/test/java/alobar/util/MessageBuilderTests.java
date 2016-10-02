package alobar.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link MessageBuilder}
 */
public class MessageBuilderTests {

    @Test
    public void emptyShouldReturnNull() {
        MessageBuilder builder = new MessageBuilder(new StringBuilder());
        assertEquals(builder.length(), 0);
        assertEquals(builder.toString(), null);
    }

    @Test
    public void nullsShouldBeIgnored() {
        MessageBuilder builder = new MessageBuilder(new StringBuilder());
        builder.appendLine(null);
        assertEquals(builder.length(), 0);
        assertEquals(builder.toString(), null);
    }

    @Test
    public void singleLine() {
        MessageBuilder builder = new MessageBuilder(new StringBuilder());
        builder.appendLine("foo");
        assertEquals(builder.length(), 3);
        assertEquals(builder.toString(), "foo");
    }

    @Test
    public void multiLine() {
        MessageBuilder builder = new MessageBuilder(new StringBuilder());
        builder.appendLine("foo\nbar");
        assertEquals(builder.length(), 7);
        assertEquals(builder.toString(), "foo\nbar");
    }

    @Test
    public void appendShouldAddNewlines() {
        MessageBuilder builder = new MessageBuilder(new StringBuilder());
        builder.appendLine("foo");
        builder.appendLine("bar");
        assertEquals(builder.length(), 7);
        assertEquals(builder.toString(), "foo\nbar");
    }
}
