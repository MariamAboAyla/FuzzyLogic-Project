import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main extends Menu {


    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter("output.txt");
        writer.print("");
        writer.close();
        int cnt[]={0};
        fuzzyLogicToolBox(cnt, "input.txt", "output.txt");
    }


}
