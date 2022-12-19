import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class GUI extends JDialog {
    private JPanel contentPane;
    private JButton sendButton;
    private JButton exit;
    private JTextArea inFilePath;
    private JTextArea outFilePath;
    private JLabel guiLabel;

    public GUI ( ) {

        setContentPane ( contentPane );
        setModal ( true );
        getRootPane ( ).setDefaultButton ( sendButton );



        sendButton.addActionListener ( new ActionListener ( ) {
            public void actionPerformed ( ActionEvent e ) {
                onOK ( );
            }
        } );

        exit.addActionListener ( new ActionListener ( ) {
            public void actionPerformed ( ActionEvent e ) {
                onCancel ( );
            }
        } );

        // call onCancel() when cross is clicked
        setDefaultCloseOperation ( DO_NOTHING_ON_CLOSE );
        addWindowListener ( new WindowAdapter ( ) {
            public void windowClosing ( WindowEvent e ) {
                onCancel ( );
            }
        } );

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction ( new ActionListener ( ) {
            public void actionPerformed ( ActionEvent e ) {
                onCancel ( );
            }
        } , KeyStroke.getKeyStroke ( KeyEvent.VK_ESCAPE , 0 ) , JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );
    }



    private void onOK ( ) {


//        JOptionPane.showMessageDialog(contentPane, "Please enter valid input file path first!");

        // the output file path
        String writeFilePath = outFilePath.getText ( );
        String readFilePath = inFilePath.getText (  );

        // call the function that: writes the output to this file's path
        gui_files guiFiles = new gui_files(readFilePath, writeFilePath);


    }

    private void onCancel ( ) {
        // add your code here if necessary
        dispose ( );
    }
    public JTextArea getInFilePath ( ) {
        return inFilePath;
    }

    public void setInFilePath ( JTextArea inFilePath ) {
        this.inFilePath = inFilePath;
    }

    public JTextArea getOutFilePath ( ) {
        return outFilePath;
    }

    public void setOutFilePath ( JTextArea outFilePath ) {
        this.outFilePath = outFilePath;
    }




    public static void main ( String[] args ) {
        GUI dialog = new GUI ( );
        dialog.pack ( );
        dialog.setVisible ( true );
        System.exit ( 0 );
    }


}
