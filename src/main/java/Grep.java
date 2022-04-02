import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {
    public static void main(String[] args) throws IOException {
        Parser.main(args);
    }


    public static void grep(String ign, String invert, String rgx, String word, String file) throws IOException {
        System.out.println("________________________________________"); // красиво
        //какой флаг, туда и идем
        if (ign != null) {
            Grep.ignore(file, ign);
        } else if (invert != null) {
            Grep.invert(file, invert);
        } else if (rgx != null) {
            Grep.regex(file, rgx);
        } else { //нет флагов, ищем по слову
            Grep.regex(file, word); //а нужно ли делать отдельно поиск по слову, когда есть поиск по выражению...
        }
    }

    public static void regex(String file, String rgx) throws IOException {
        System.out.println("Hello regex");

        StringBuilder result = new StringBuilder();

        Pattern pattern = Pattern.compile(rgx);
        int counter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { // читаем файл, сразу выполняем поиск
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
    }

    public static void ignore(String file, @NotNull String word) throws IOException {
        System.out.println("Hello ignore");

        StringBuilder result = new StringBuilder();

        Pattern pattern = Pattern.compile(word.toLowerCase(Locale.ROOT)); // игнорим регистр
        int counter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
    }


    public static void invert(String file, String word) throws IOException {
        System.out.println("Hello invert");
        StringBuilder result = new StringBuilder();

        Pattern pattern = Pattern.compile(word);
        int counter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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

    }
}

