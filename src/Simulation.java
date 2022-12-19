import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Simulation {

    File file = new File("output.txt");
    FileWriter fr = new FileWriter(file, true);
    public Simulation() throws IOException {
    }
    void doSimulation(FuzzyLogicSystem system) throws IOException {
        Fuzzification(system);
        Inference(system);
        Defuzzification(system);
        fr.close();
    }
    void Fuzzification(FuzzyLogicSystem system) throws IOException {
        for(Variable var:system.inVariables){
            var.calcMembership();
        }
        for(Variable var:system.outVariables){
            var.init();
        }
        fr.write("Fuzzification => done\n");
    }
    void Inference(FuzzyLogicSystem system) throws IOException {
        for(Rules rule:system.ruleList){
            Variable in1=getVar(system,rule.input.get(0).first);
            Variable in2=getVar(system,rule.input.get(1).first);
            Variable out=getVar(system,rule.output.get(0).first);
            double val=0.0;
            if(rule.operation.get(0).equals("or")){
                val=Math.max(in1.membership.get(rule.input.get(0).second),in2.membership.get(rule.input.get(1).second));
            }
            if(rule.operation.get(0).equals("and")){
                val=Math.min(in1.membership.get(rule.input.get(0).second),in2.membership.get(rule.input.get(1).second));
            }
            if(rule.operation.get(0).equals("and_not")){
                val=Math.min(in1.membership.get(rule.input.get(0).second),1-in2.membership.get(rule.input.get(1).second));
            }
            out.membership.put(rule.output.get(0).second,Math.max(out.membership.get(rule.output.get(0).second),val));
        }
        fr.write("Inference => done\n");
    }
    void Defuzzification(FuzzyLogicSystem system) throws IOException {
        fr.write("Defuzzification => done\n");
        for(Variable var:system.outVariables){
            Vector<Double> weights=getWeightedAvg(var);
            double val=0.0,sum=0.0;
            for(int i=0;i<var.fuzzysets.size();i++){
                val+=var.membership.get(var.fuzzysets.get(i).setName)*weights.get(i);
                sum+=var.membership.get(var.fuzzysets.get(i).setName);
            }
            val=val/sum;
            String ans="";
            for(int i=0;i<weights.size();i++){
                if(i==weights.size()-1){
                    ans=var.fuzzysets.get(i).setName;
                    break;
                }
                if(val>=weights.get(i)&&val<weights.get(i+1)){
                    if(val-weights.get(i)<weights.get(i+1)-val)
                        ans=var.fuzzysets.get(i).setName;
                    else
                        ans=var.fuzzysets.get(i+1).setName;
                    break;
                }
            }
            fr.write("The predicted "+var.name+" is "+ans+" ("+val+")\n");
        }
    }
    Vector<Double> getWeightedAvg(Variable var){
        Vector<Double> weights=new Vector<>();
        for(FuzzySetData set:var.fuzzysets){
            int cnt=0;
            double sum=0;
            for(int i=0;i<set.fuzzySetValues.size();i++){
                cnt++;
                sum+=set.fuzzySetValues.get(i);
            }
            weights.add(sum/(double) cnt);
        }
        return weights;
    }
    Variable getVar(FuzzyLogicSystem system,String varName){
        int indx=system.variablesIndx.get(varName);
        if(system.inVariables.size()>indx&&system.inVariables.get(indx).name.equals(varName)){
            return system.inVariables.get(indx);
        }
        else
            return system.outVariables.get(indx);
    }
}
