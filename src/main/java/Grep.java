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
    private String rgx; // регексы

    @Option(name = "-v", usage = "Invert")
    private String invert; // инвертирование

    @Option(name = "-i", usage = "Ignore")
    private String ign; // игнорирование регистра

    private String word; // поиск слова

    @Argument
    private List<String> arguments = new ArrayList<String>(10); //File

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

        //передаём имя входного файла
        String File = arguments.get(0);
        if (rgx == null && invert == null && ign == null && word == null) { // если нет флага, ищем по слову
            word = arguments.get(0); //без флагов первым аргументом будет слово для поиска
            File = arguments.get(1); //без флагов вторым аргументом будет имя файла
        }
        grep(ign, invert, rgx, word, File);
    }

    public String grep(String ign, String invert, String rgx, String word, String File) throws IOException {

        if (ign != null) {
            //Grep.ignore(File, ign);
            System.out.println("Hello ignore");

            StringBuilder result = new StringBuilder();

            Pattern pattern = Pattern.compile(ign.toLowerCase(Locale.ROOT)); // игнорим регистр
            int counter = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(File))) {
                String str = reader.readLine();
                while (str != null) {

                    Matcher matcher = pattern.matcher(str.toLowerCase(Locale.ROOT)); // все еще игнорим, но запишем первоначальный вид
                    if (matcher.find()) {
                        result.append(str).append("\n");
                        counter++;
                    }
                    str = reader.readLine();
                }
            }

            System.out.println("Matches found: " + counter);
            System.out.println("----------------------------------------");
            System.out.println(result);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/newTestIGNORE.txt"))) {
                writer.write(String.valueOf(result));
            }
            return result.toString();
        } else if (invert != null) {
            //Grep.invert(File, invert);
            System.out.println("Hello invert");
            StringBuilder result = new StringBuilder();

            Pattern pattern = Pattern.compile(invert);
            int counter = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(File))) {
                String str = reader.readLine();
                while (str != null) {

                    Matcher matcher = pattern.matcher(str);
                    if (!matcher.find()) { // запишем все, что не нашли
                        result.append(str).append("\n");
                        counter++;
                    }
                    str = reader.readLine();
                }
            }
            System.out.println("No matches found: " + counter);
            System.out.println("----------------------------------------");
            System.out.println(result);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/newTestINVERT.txt"))) {
                writer.write(String.valueOf(result));
            }
            return result.toString();
        } else if (rgx != null) {
            //Grep.regex(File, rgx);
            System.out.println("Hello regex");

            StringBuilder result = new StringBuilder();

            Pattern pattern = Pattern.compile(rgx);
            int counter = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(File))) { // читаем файл, сразу выполняем поиск
                String str = reader.readLine();
                while (str != null) {

                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        result.append(str).append("\n");
                        counter++;
                    }
                    str = reader.readLine();
                }
            }

            System.out.println("Matches found: " + counter);
            System.out.println("----------------------------------------");
            System.out.println(result);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/newTestREGEX.txt"))) {
                writer.write(String.valueOf(result)); // по заданию выводим результат на консоль, но тесты интереснее делать с файлами
            }
            return result.toString();
        } else { //нет флагов, ищем по слову
            //Grep.regex(File, word); //а нужно ли делать отдельно поиск по слову, когда есть поиск по выражению...
            System.out.println("Hello regex");

            StringBuilder result = new StringBuilder();

            Pattern pattern = Pattern.compile(word);
            int counter = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(File))) { // читаем файл, сразу выполняем поиск
                String str = reader.readLine();
                while (str != null) {

                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        result.append(str).append("\n");
                        counter++;
                    }
                    str = reader.readLine();
                }
            }

            System.out.println("Matches found: " + counter);
            System.out.println("----------------------------------------");
            System.out.println(result);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/newTestREGEX.txt"))) {
                writer.write(String.valueOf(result)); // по заданию выводим результат на консоль, но тесты интереснее делать с файлами
            }
            return result.toString();
        }

    }

    public static void main(String[] args) throws IOException {
        new Grep().parseArguments(args);
    }
}

