import java.util.Scanner;

public class Main {

    static void mainMenu ( )
    {
        FuzzyLogicSystem fuzzyLogicSystem = new FuzzyLogicSystem ();

        System.out.println ("Enter the system's name and a brief description:\n"
            +"------------------------------------------------");

        Scanner scan = new Scanner ( System.in );

        String systemName = scan.nextLine ( );


        String description = "";
        while(true)
        {
            String line = scan.nextLine ( );
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

            choice = scan.next();
            if ( choice.equals ( "1" ) ) {
                fuzzyLogicSystem.addVariables();
            } else if ( choice.equals ( "2" ) ) {
                fuzzyLogicSystem.addFuzzySets();
            } else if ( choice.equals ( "3" ) ) {
                fuzzyLogicSystem.addRules();
            } else if ( choice.equals ( "4" ) ) {
                fuzzyLogicSystem.runSimulationOnCrisp();
            } else if ( choice.equalsIgnoreCase ( "close" )) {
                break;
            } else {
                System.out.println ( "Error => no such choice exists!\n");
            }

        }

    }


    static void fuzzyLogicToolBox ( )
    {
        Scanner scan = new Scanner ( System.in );

        int choice;
        while(true)
        {
            System.out.println ("Fuzzy Logic Toolbox\n"
                    +"===================\n"
                    +"1- Create a new fuzzy system\n"
                    +"2- Quit");

            choice = scan.nextInt ( );
            if(choice == 1)
                mainMenu();
            else
                break;
        }
    }


    public static void main(String[] args)
    {

        fuzzyLogicToolBox();

    }


}
