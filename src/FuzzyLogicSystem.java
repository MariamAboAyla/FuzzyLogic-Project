import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
    Vector<Double> fuzzySetValues=new Vector<>();


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

    Double crispVal;

    Vector<FuzzySetData> fuzzysets=new Vector<>();

    Map<String,Double> membership=new HashMap<>();

    Variable (){}
    Variable(String name, Double start, Double end)
    {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    void init(){
        for(FuzzySetData set:fuzzysets){
            membership.put(set.setName,0.0);
        }
    }
    void calcMembership(){
        for(int i=0;i<fuzzysets.size();i++){
            FuzzySetData group=fuzzysets.get(i);
            double val=0.0;
            if(group.type.equals("TRAP")){
                Double arr[]={0.0,1.0,1.0,0.0};
                for(int j=0;j<3;j++){
                    if(crispVal>=group.fuzzySetValues.get(j)&&crispVal<group.fuzzySetValues.get(j+1)){
                        double slope=(arr[j+1]-arr[j])/(group.fuzzySetValues.get(j+1)-group.fuzzySetValues.get(j));
                        double intercept=arr[j+1]-(slope*group.fuzzySetValues.get(j+1));
                        val=slope*crispVal+intercept;
                        break;
                    }
                }
                membership.put(group.setName,val);
            }
            else if(group.type.equals("TRI")){
                Double arr[]={0.0,1.0,0.0};
                for(int j=0;j<2;j++){
                    if(crispVal>=group.fuzzySetValues.get(j)&&crispVal<group.fuzzySetValues.get(j+1)){
                        double slope=(arr[j+1]-arr[j])/(group.fuzzySetValues.get(j+1)-group.fuzzySetValues.get(j));
                        double intercept=arr[j+1]-(slope*group.fuzzySetValues.get(j+1));
                        val=slope*crispVal+intercept;
                        break;
                    }
                }
                membership.put(group.setName,val);
            }
        }
    }
}


// class fuzzyLogicSystem => name, description, variable names, fuzzy sets, and rules
public class FuzzyLogicSystem {

    String name;
    String description;
    Map<String, Integer> variablesIndx=new HashMap<>(); // variable name => Maps to its type: IN or OUT

    Vector<Variable> inVariables=new Vector<Variable>();// contains the variables' data , and to check its type? from map above
    Vector<Variable> outVariables=new Vector<Variable>();
    //Map<String, Vector<FuzzySetData>> fuzzySet=new HashMap<>(); // maps each variable_name to its fuzzy set's data
    Vector<Rules> ruleList=new Vector<>(); // contains the rules



    /*
     * This function is used to add variables in the database
     * 2 types of variables each in a different array for its type:
     *    ( IN and OUT )
     */
    void addVariables(int[] cnt) throws IOException {

        System.out.println ("Enter the variable's name, type (IN/OUT) and range ([lower, upper]):\n"
            +"(Press x to finish)\n--------------------------------------------------------------------");

        while(true) {
            String line = Files.readAllLines(Paths.get("input.txt")).get(cnt[0]++);
            String[] lineData = line.split ( " " );

            if(lineData[0].equals ( "x" ))
                break;

            String variableName = lineData[0];
            String type = lineData[1];

            String tmp1 = lineData[2].substring ( 1 , lineData[2].length ( ) - 1 );
            String tmp2 = lineData[3].substring ( 0 , lineData[3].length ( ) - 1 );

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

            Variable var = new Variable ( variableName , start , end );
            if(type.equals("IN")) {
                inVariables.add(var);
                variablesIndx.put(variableName,inVariables.size()-1);
            }
            else {
                outVariables.add(var);
                variablesIndx.put(variableName,outVariables.size()-1);
            }
        }

    }



    /*
     * This function is used for adding fuzzy sets to existing
     * variables
     */
    void  addFuzzySets(int[] cnt) throws IOException {
        System.out.println ( "Enter the variable's name:\n"
        +"--------------------------");

        String variableName = Files.readAllLines(Paths.get("input.txt")).get(cnt[0]++);

        // if there is no variable name
        if (!variablesIndx.containsKey ( variableName ))
        {
            System.out.println ("Error: No such a variable name exists!");
            return;
        }

        System.out.println ("Enter the fuzzy set name, type (TRI/TRAP) and values: (Press x to finish)\n"
            +"-----------------------------------------------------");


        Vector<FuzzySetData> fuzzySetDataVector = new Vector<> (  );
        while(true) 
        {
            String line = Files.readAllLines(Paths.get("input.txt")).get(cnt[0]++);
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
        int indx=variablesIndx.get(variableName);
        if(inVariables.size()>indx&&inVariables.get(indx).name.equals(variableName)){
            inVariables.get(indx).fuzzysets=fuzzySetDataVector;
        }
        else{
            outVariables.get(indx).fuzzysets=fuzzySetDataVector;
        }
    }


    /*
     * function that checks whether the rules are written
     * in a correct way or not
     */
    Boolean isValidRule(Vector<stringPair>  input, Vector<stringPair>  output, Vector<String> operation)
    {
        if (input.isEmpty () || output.isEmpty() || (input.size()>1 && operation.isEmpty ())) {
            System.out.println ( "Error: invalid rule! check input, output and a valid operator!\n" );
            return false;
        }

        return true;
    }



    
    /*
     * Function that add the rules to the database/ inference system
     */
    void addRules(int[] cnt) throws IOException {

        System.out.println ( "Enter the rules in this format: (Press x to finish)\n"
            +"IN_variable set operator IN_variable set => OUT_variable set\n"
            +"------------------------------------------------------------");

        while(true)
        {

            String line = Files.readAllLines(Paths.get("input.txt")).get(cnt[0]++);
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
            for (int i = idx; i<lineData.length; i++)
            {
                if(i+1< lineData.length)
                {
                    output.add ( new stringPair ( lineData[i], lineData[i+1] ) );
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
            if(!variablesIndx.containsKey ( variableName ))
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
    void runSimulationOnCrisp(int[] cnt) throws IOException {
        if(ruleList.isEmpty()) {
            System.out.println("CAN’T START THE SIMULATION! Please add the fuzzy sets and rules first.");
            return;
        }

        for (Variable var : inVariables) {
            if(var.fuzzysets.isEmpty()){
                System.out.println("CAN’T START THE SIMULATION! Please add the fuzzy sets and rules first.");
                return;
            }
        }

        System.out.println("Enter the crisp values:\n" +
                "-----------------------");

        for (Variable var:inVariables){
            System.out.print(var.name+": ");
            String crispval=Files.readAllLines(Paths.get("input.txt")).get(cnt[0]++);
            var.crispVal=Double.parseDouble(crispval);
        }
        Simulation simulator=new Simulation();
        simulator.doSimulation(this);
    }
}
