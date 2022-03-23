
import org.kohsuke.args4j.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    @Option(name = "-r", usage = "Regex")
    private String rgx; // регексы

    @Option(name = "-v", usage = "Invert")
    private String invert; // игнорирование

    @Option(name = "-i", usage = "Ignore")
    private String ign; // игнорирование

    //@Option(name = "word", usage = "Word")
    //private String word; // поиск слова

    @Argument
    private static final List<String> arguments = new ArrayList<String>();

    public void parseArguments(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (arguments.isEmpty() || (rgx.isEmpty() && invert.isEmpty() && ign.isEmpty()) || (!arguments.get(0).equals("grep") || arguments.size() != 3)) {
                System.out.println("ERROR of enter");
            }

        } catch (Exception CmdLineException) {
            System.err.println(CmdLineException.getMessage());
            parser.printUsage(System.err);
            throw new IllegalArgumentException("");
        }
        //передаём слово из входного файла
        final String word = arguments.get(1);
        //передаём имя входного файла
        final String input = arguments.get(2);
        //передаём нашей основной функции парсированные агрументы
        Grep.grep(ign, invert, rgx, word, input);

    }

    String[] args = {"grep -r word exam.txt"};

    public static void main(String[] args) {
        new Parser().parseArguments(args);
        //System.out.println(arguments);

    }

}
