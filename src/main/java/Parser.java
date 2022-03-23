
import org.kohsuke.args4j.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    @Option(name = "word", usage = "Word")
    private String word; // поиск слова

    @Option(name = "-r", usage = "Regex")
    private String rgx; // регексы

    @Option(name = "-v", usage = "Invert")
    private String invert; // игнорирование

    @Option(name = "-i", usage = "Ignore")
    private String ign; // игнорирование

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public void format() {
        System.out.println("Option " + word + rgx + invert + ign);
    }

    public void parseArguments(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (Exception CmdLineException) {
            System.err.println(CmdLineException.getMessage());
            parser.printUsage(System.err);
            throw new IllegalArgumentException("");
        }
        //передаём имя входного файла
        String input = arguments.get(1);
        //передаём нашей основной функции парсированные агрументы
        //PackRLEKt.packRLE(pack, input, out);

    }
    public static void main(String[] args){
        new Parser().parseArguments(args);


    }
}
