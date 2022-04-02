import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class grepTest {

    private void equalsFiles(String inputName, String expectedOutputInFile) throws IOException {

        StringBuilder result1 = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(inputName))) {
            String str = read.readLine();
            while (str != null) {
                result1.append(str).append("\n");
                str = read.readLine();
            }
        }
        StringBuilder result2 = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(expectedOutputInFile))) {
            String str1 = read.readLine();
            while (str1 != null) {
                result2.append(str1).append("\n");
                str1 = read.readLine();
            }
        }

        assertEquals(result2.toString().trim(), result1.toString().trim());
    }

    @Test
    void test() throws IOException {
        Grep.main("words input/input.txt".trim().split(" "));
        equalsFiles( "output/newTestREGEX.txt", "input/test1 (word).txt");
        new File("output/newTestREGEX.txt").delete(); // идея говорит, что игнорит delete, но работает

        Grep.main("-v necEssarily input/input.txt".trim().split(" "));
        equalsFiles( "output/newTestINVERT.txt", "input/test2 (invert).txt");
        new File("output/newTestINVERT.txt").delete(); // работает, но все равно выделяет как замечание...

        Grep.main("-v something, input/input.txt".trim().split(" "));
        equalsFiles( "output/newTestINVERT.txt", "input/test3 (non invert).txt");
        new File("output/newTestINVERT.txt").delete();

        Grep.main("-i later input/input.txt".trim().split(" "));
        equalsFiles( "output/newTestIGNORE.txt", "input/test4 (ignore).txt");
        new File("output/newTestIGNORE.txt").delete();

        Grep.main("-r need, input/input.txt".trim().split(" "));
        equalsFiles( "output/newTestREGEX.txt", "input/test5 (regex).txt");
        new File("output/newTestREGEX.txt").delete(); // не очень приятно, когда она все выделяет
    }
}
