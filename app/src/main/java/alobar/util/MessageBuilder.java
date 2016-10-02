package alobar.util;

/**
 * Convenience class to build multi-line messages
 */
public class MessageBuilder {

    private final StringBuilder builder;

    public MessageBuilder(StringBuilder builder) {
        this.builder = builder;
    }

    public void appendLine(String str) {
        if (str != null) {
            if (builder.length() > 0)
                builder.append("\n");
            builder.append(str);
        }
    }

    public int length() {
        return builder.length();
    }

    @Override
    public String toString() {
        return builder.length() > 0 ? builder.toString() : null;
    }
}
