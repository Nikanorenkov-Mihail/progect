import org.jetbrains.annotations.NotNull;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {
    @Option(name = "-r", usage = "Regex")
    private Boolean rgx = false; // регексы, нигде не используем :(

    @Option(name = "-v", usage = "Invert")
    private Boolean invert = false; // инвертирование

    @Option(name = "-i", usage = "Ignore")
    private Boolean ign = false; // игнорирование регистра

    @Argument
    private List<String> arguments = new ArrayList<String>(2); //File

    public void parseArguments(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (arguments.isEmpty()) {
                System.out.println("ERROR of enter");
            }

        } catch (Exception CmdLineException) {
            System.err.println(CmdLineException.getMessage());
            parser.printUsage(System.err);
            throw new IllegalArgumentException("");
        }

        String word = arguments.get(0);
        String file = arguments.get(1);

        grep(word, ign, invert, file);
    }

    public void grep(String word, Boolean ign, Boolean invert, String file) throws IOException {
        grepWord(word, ign, invert, file);
    }

    public void grepWord(String word, @NotNull Boolean ign, Boolean invert, String file) throws IOException {
        StringBuilder result = new StringBuilder();

        Pattern pattern = Pattern.compile(word); // задаем выражение для поиска
        if (ign) pattern = Pattern.compile(word.toLowerCase(Locale.ROOT)); // игнорим регистр, если был флаг

        int counter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String str = reader.readLine();
            while (str != null) {

                Matcher matcher = pattern.matcher(str);
                if (ign) // флаг на игнорирование
                    matcher = pattern.matcher(str.toLowerCase(Locale.ROOT)); // все еще игнорим с флагом

                if (invert) { // флаг на инвертирование
                    if (!matcher.find()) { // запишем все, что не нашли
                        result.append(str).append("\n");
                        counter++;
                    }
                } else if (matcher.find()) { // запишем все, что нашли
                    result.append(str).append("\n");
                    counter++;
                }
                str = reader.readLine();

            }
        }

        System.out.println("Matches found: " + counter);
        System.out.println("----------------------------------------");
        System.out.println(result);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/newTestGrep.txt"))) {
            writer.write(String.valueOf(result));
        }
    }

    public static void main(String[] args) throws IOException {
        new Grep().parseArguments(args);
    }
}