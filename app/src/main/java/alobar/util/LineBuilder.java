package alobar.util;

public class LineBuilder {

    private StringBuilder result = new StringBuilder();

    public void appendLine(String line) {
        if (result.length() != 0)
            result.append("\n");
        result.append(line);
    }

    @Override
    public String toString() {
        return result.toString();
    }

    public int lenght() {
        return result.length();
    }
}
