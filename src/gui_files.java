import java.io.IOException;

// to save the files paths
public class gui_files extends Menu{

    private String outputFilePath;
    private String inputFilePath;

    public gui_files (String read, String write) {

        outputFilePath = "output.txt";
        inputFilePath = "input.txt";

        if( ! read.isEmpty ())
            inputFilePath = read;
        if( ! write.isEmpty ())
            outputFilePath = write;

        int cnt[] = {0};


        try {
            fuzzyLogicToolBox ( cnt, inputFilePath, outputFilePath );
        } catch (IOException e) {
            e.printStackTrace ( );
        }


    }


    public String getOutputFilePath ( ) {
        return outputFilePath;
    }

    public void setOutputFilePath ( String outputFilePath ) {
        this.outputFilePath = outputFilePath;
    }

    public String getInputFilePath ( ) {
        return inputFilePath;
    }

    public void setInputFilePath ( String inputFilePath ) {
        this.inputFilePath = inputFilePath;
    }


}
