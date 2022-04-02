import org.kohsuke.args4j.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

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

        if (rgx == null & invert == null & ign == null & word == null) { // если нет флага, ищем по слову
            word = arguments.get(0); //без флагов первым аргументом будет слово для поиска
            File = arguments.get(1); //без флагов вторым аргументом будет имя файла
        }
        //передаём нашей основной функции парсированные агрументы
        Grep.grep(ign, invert, rgx, word, File);
    }

    public static void main(String[] args) throws IOException {
        new Parser().parseArguments(args);
    }

}
