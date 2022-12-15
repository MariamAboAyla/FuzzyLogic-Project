import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

// to make pair of strings
class stringPair
{
    String first;
    String second;
    stringPair(String f, String s)
    {
        first = f;
        second= s;
    }
    
}

// struct for making rules : consists of input, output and operation
class Rules
{

    // Input
    Vector<stringPair> input;

    // Output
    Vector<stringPair> output;

    // operator
    Vector<String> operation;

    Rules() {}
    Rules(Vector<stringPair> i, Vector<stringPair> o, Vector<String> op) {
        input  =  i;
        output =  o ;
        operation = op ;
    }

}

// struct for making the fuzzy set data => type and fuzzy set values
class FuzzySetData
{

    String setName;
    String type;
    Vector<Double> fuzzySetValues;

    FuzzySetData( String sName,  String  vType, Vector<Double>  fuzzSet){
        setName = sName;
        type = vType;
        fuzzySetValues = fuzzSet;
    }

}


// struct variable: to hold the variable info => name and start of universal discourse and end of universal discourse
class Variable
{
    String name;
    Double start;
    Double end;
    Variable(String name, Double start, Double end)
    {
        this.name = name;
        this.start = start;
        this.end = end;
    }

}


// class fuzzyLogicSystem => name, description, variable names, fuzzy sets, and rules
public class FuzzyLogicSystem {

    String name;
    String description;
    Map<String, String> variablesTypes; // variable name => Maps to its type: IN or OUT
    Vector<Variable> variables; // contains the variables' data , and to check its type? from map above
    Map<String, Vector<FuzzySetData>> fuzzySet; // maps each variable_name to its fuzzy set's data
    Vector<Rules> ruleList; // contains the rules



    /*
     * This function is used to add variables in the database
     * 2 types of variables each in a different array for its type:
     *    ( IN and OUT )
     */
    void addVariables()
    {

        System.out.println ("Enter the variable's name, type (IN/OUT) and range ([lower, upper]):\n"
            +"(Press x to finish)\n--------------------------------------------------------------------");


        Scanner scan = new Scanner ( System.in );

        while(true) {
            String line = scan.nextLine ( );
            String[] lineData = line.split ( " " );

            if(lineData[0].equals ( "x" ))
                break;

            String variableName = lineData[0];
            String type = lineData[1];

            System.err.println ( line.length ( ) + " \n" + lineData.length );

            String tmp1 = lineData[2].substring ( 1 , lineData[2].length ( ) - 1 );
            String tmp2 = lineData[2].substring ( 0 , lineData[3].length ( ) - 1 );
            Double start = Double.parseDouble ( tmp1 );
            Double end = Double.parseDouble ( tmp2 );

            if ( end < start ) {

                System.out.println ( "Error: invalid input! Check the boundaries" );
                return;

            }

            if ( type == null ) {
                System.out.println ( "Error: each variable should be IN(input) or OUT(output)!" );
                return;
            }

            if ( variableName == null ) {
                System.out.println ( "Error: Must enter a variable name!" );
                return;
            }

            variables.add ( new Variable ( variableName , start , end ) );
            variablesTypes.put ( variableName , type );

        }


    }



    /*
     * This function is used for adding fuzzy sets to existing
     * variables
     */
    void  addFuzzySets()
    {
        System.out.println ( "Enter the variable's name:"
        +"--------------------------");

        Scanner scan = new Scanner ( System.in );

        String variableName = scan.nextLine ( );

        // if there is no variable name
        if (!variablesTypes.containsKey ( variableName ))
        {
            System.out.println ("Error: No such a variable name exists!");
            return;
        }

        System.out.println ("Enter the fuzzy set name, type (TRI/TRAP) and values: (Press x to finish)\n"
            +"-----------------------------------------------------");


        Vector<FuzzySetData> fuzzySetDataVector = new Vector<> (  );
        while(true) 
        {
            String line = scan.nextLine ( );
            String[] lineData = line.split ( " " );
            String setName = lineData[0];

            if ( setName.equals ( "x" ) ) {
                break;
            }

            String setType = lineData[1];
            Vector<Double> fuzzValues = new Vector<>();
            
            if ( setType.equals ( "TRI" ) ) 
            {
                fuzzValues.add ( Double.parseDouble ( lineData[2] ));
                fuzzValues.add ( Double.parseDouble ( lineData[3] ));
                fuzzValues.add ( Double.parseDouble ( lineData[4] ));
                
            } else if ( setType.equals ( "TRAP" ) ) 
            {
                fuzzValues.add ( Double.parseDouble ( lineData[2] ));
                fuzzValues.add ( Double.parseDouble ( lineData[3] ));
                fuzzValues.add ( Double.parseDouble ( lineData[4] ));
                fuzzValues.add ( Double.parseDouble ( lineData[5] ));
                
                
            } else {
                System.out.println ( "Error: Enter valid type!" );
                return;
            }

            fuzzySetDataVector.add ( new FuzzySetData ( setName, setType, fuzzValues ) );
            
        }
        
        fuzzySet.put ( variableName, fuzzySetDataVector );

    }



    /*
     * function that checks whether the rules are written
     * in a correct way or not
     */
    Boolean isValidRule(Vector<stringPair>  input, Vector<stringPair>  output, Vector<String> operation)
    {
        if (input.isEmpty () || output.isEmpty() || (input.size ()>1 && operation.isEmpty ())) {
            System.out.println ( "Error: invalid rule! check input, output and a valid operator!\n" );
            return false;
        }

        return true;
    }



    
    /*
     * Function that add the rules to the database/ inference system
     */
    void addRules()
    {

        System.out.println ( "Enter the rules in this format: (Press x to finish)\n"
            +"IN_variable set operator IN_variable set => OUT_variable set\n"
            +"------------------------------------------------------------");


        Scanner scan = new Scanner ( System.in );

        while(true)
        {

            String line = scan.nextLine ( );
            String[] lineData = line.split ( " " );

            if ( lineData[0].equals ( "x" ) )
                break;

            if(lineData.length <= 1)
            {
                System.out.println ("Error: wrong rule syntax!" );
                break;
            }

            Rules rules = new Rules (  );
            String variableName = lineData[0];
            String value = lineData[1];

            Vector<stringPair> input = new Vector<> (  );
            Vector<stringPair> output = new Vector<> (  );
            Vector<String> operators = new Vector<> (  );

            input.add ( new stringPair ( variableName, value ) );

            int idx = -1; // to know the start index of output of the rule

            // get the input variables and values
            for(int i = 2; i< lineData.length; i++)
            {

                if(lineData[i].equals ( "or" )  || lineData[i].equals ( "and" ) || lineData[i].equals ( "and_not" ))
                {
                    operators.add ( lineData[i] );
                }else if (lineData[i].equals ( "=>" ))
                {
                    idx = i+1;
                    break;
                }else
                {
                    input.add ( new stringPair ( lineData[i], lineData[i+1] ) );
                    i++;
                }

            }

            // now to get the output variables and values
            for (int i = idx; i< lineData.length; i++)
            {
                if(i+1< lineData.length)
                {
                    input.add ( new stringPair ( lineData[i], lineData[i+1] ) );
                    i++;
                }else{
                    System.out.println ("Error: wrong rule! must write variable name and value!" );
                    return;
                }

            }


            // check on possible errors
            if(!isValidRule(input, output, operators))
            {
                System.out.println ("Error: Invalid rule syntax!" );
                continue;
            }

            // check if this variableName is in the database
            if(!variablesTypes.containsKey ( variableName ))
            {
                System.out.println ("warning: this variable name doesn't exists!\n");
            }


            rules.input = input;
            rules.output = output;
            rules.operation = operators;

            ruleList.add ( rules );

        }



    }



    /*
     * To call the function runSimulation from the simulation class
     * to make the steps of fuzzification till get the required result
     */
    void runSimulationOnCrisp()
    {
//        Simulation simulation = new Simulation ();
//        simulation.runSimulation();
    }



}
