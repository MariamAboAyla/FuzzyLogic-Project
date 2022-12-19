import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    static void mainMenu (int[] cnt) throws IOException {
        FuzzyLogicSystem fuzzyLogicSystem = new FuzzyLogicSystem ();

        System.out.println ("Enter the system's name and a brief description:\n"
            +"------------------------------------------------");

        String systemName = Files.readAllLines(Paths.get("input.txt")).get(cnt[0]++);
        String description = "";
        while(true)
        {
            String line = Files.readAllLines(Paths.get("input.txt")).get(cnt[0]++);
            description+=line;
            if(line.charAt ( line.length ()-1 ) == '.' )
                break;

        }

        fuzzyLogicSystem.name = systemName;
        fuzzyLogicSystem.description = description;

        String choice;
        while (true) {

            System.out.println ("Main Menu:\n"
            +"==========\n"
            +"1- Add variables.\n"
            +"2- Add fuzzy sets to an existing variable.\n"
            +"3- Add rules.\n"
            +"4- Run the simulation on crisp values.");

            choice =Files.readAllLines(Paths.get("input.txt")).get(cnt[0]++);
            if ( choice.equals ( "1" ) ) {
                fuzzyLogicSystem.addVariables(cnt);
            } else if ( choice.equals ( "2" ) ) {
                fuzzyLogicSystem.addFuzzySets(cnt);
            } else if ( choice.equals ( "3" ) ) {
                fuzzyLogicSystem.addRules(cnt);
            } else if ( choice.equals ( "4" ) ) {
                fuzzyLogicSystem.runSimulationOnCrisp(cnt);
            } else if ( choice.equalsIgnoreCase ( "close" )) {
                break;
            } else {
                System.out.println ( "Error => no such choice exists!\n");
            }

        }

    }


    static void fuzzyLogicToolBox (int[] cnt) throws IOException {
        String choice;
        while(true)
        {
            System.out.println ("Fuzzy Logic Toolbox\n"
                    +"===================\n"
                    +"1- Create a new fuzzy system\n"
                    +"2- Quit");

            choice =Files.readAllLines(Paths.get("input.txt")).get(cnt[0]++);
            if(choice.equals("1"))
                mainMenu(cnt);
            else
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter("output.txt");
        writer.print("");
        writer.close();
        int cnt[]={0};
        fuzzyLogicToolBox(cnt);
    }


}
