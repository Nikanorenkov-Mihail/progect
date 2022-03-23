
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {
    public static void main(String[] args) {
        Parser.main(args);
    }


    public static void grep(@NotNull String ign, String invert, String rgx, String word, String input) {
        String[] inputFileInList = new File(input).list();
        assert inputFileInList != null;

        if (!ign.isEmpty()) {
            Grep.ignore(inputFileInList, word);
        } else if (!invert.isEmpty()) {
            Grep.invert(inputFileInList, word);
        } else if (!rgx.isEmpty()) {
            Grep.regex(inputFileInList, word);
        } else {
            System.out.println("ERROR");
        }

    }

    public static @NotNull String regex(String @NotNull [] inputFileInList, String rgx) {
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile(rgx);
        int counter = 0;
        for (String line : inputFileInList) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                result.append(line).append("\n");
                System.out.println(line);
                counter++;
            }
        }
        System.out.println("Найдено совпадений: " + counter);
        return result.toString();
    }

    public static @NotNull String invert(String @NotNull [] inputFileInList, String word) {
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile(word);
        for (String line : inputFileInList) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                result.append(line).append("\n");
                System.out.println(line);
            }
        }
        return result.toString();
    }

    public static @NotNull String ignore(String @NotNull [] inputFileInList, @NotNull String word) {
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile(word.toLowerCase(Locale.ROOT));
        int counter = 0;
        for (String line : inputFileInList) {
            Matcher matcher = pattern.matcher(line.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                result.append(line).append("\n");
                System.out.println(line);
                counter++;
            }
        }
        System.out.println("Найдено совпадений: " + counter);
        return result.toString();
    }
}

