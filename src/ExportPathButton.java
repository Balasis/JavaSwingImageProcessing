import javax.swing.*;
import java.awt.*;


//Constructor of export button. Also, a public method to extract the export path to main
class ExportPathButton extends JPanel{
    //variable accessible to main through exportPathSelected below
    private  String exportPath;
    public ExportPathButton(JFrame frame,JButton[] processButtons){
        //Propably we should have renamed the constructor into exportPanel. Anyway we create a button and add listener
        //to pop up select window , a non-editable textfield to view the path chosen and a label over the textField
        //that says Export:


        JButton  exportPathButton=new JButton("...");
        //sets char numbers to be viewed..(biggest the number the biggest the width of the textfield)
        JTextField pathTextField = new JTextField(50);

        pathTextField.setEditable(false);

        JLabel exportLabel = new JLabel("Export:");

        exportPathButton.addActionListener(e->{
            JFileChooser fileChooserObj=new JFileChooser();
            //we set a title to the window
            fileChooserObj.setDialogTitle("Choose ExportPath");
            //we make the file chooser to select directories only since we need the path
            fileChooserObj.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //showOpenDialog accept as parameter the parent frame (the one replaced to view the select window)
            //and returns an int number depending of what the user did...
            int result=fileChooserObj.showOpenDialog(frame);
            //here we check if user canceled and we restore
            if (result != JFileChooser.APPROVE_OPTION) {
                fileChooserObj.cancelSelection();
                Main.exportNotNull=false;
                pathTextField.setText(null);
                Main.enableOrDisableProcessButtons(Main.browserNotNull, Main.exportNotNull,processButtons);
                return;
            }



            exportPath=fileChooserObj.getSelectedFile().getAbsolutePath();
            pathTextField.setText(fileChooserObj.getSelectedFile().getAbsolutePath());
            //enable process buttons
            Main.exportNotNull=true;
            Main.enableOrDisableProcessButtons(Main.browserNotNull,true,processButtons);

        });
        // Create a panel to hold label and text field horizontally
        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new BorderLayout());
        pathPanel.add(exportLabel, BorderLayout.NORTH);
        pathPanel.add(pathTextField, BorderLayout.CENTER);
        pathPanel.add(exportPathButton, BorderLayout.EAST);

        add(pathPanel);

    }

    public String exportPathSelected(){
        return (exportPath);
    }


}
