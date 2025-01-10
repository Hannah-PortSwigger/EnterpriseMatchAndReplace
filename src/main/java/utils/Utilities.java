package utils;

public class Utilities
{
    public static String escapeString(String input)
    {
        return input
                .replaceAll("\\\\n", "\n")
                .replaceAll("\\\\", "");
    }
}
