import javax.swing.*;
import java.awt.*;

class ExportPathButton extends JPanel{
    private  String exportPath;
    public ExportPathButton(JFrame frame,JButton[] processButtons){

        JButton  exportPathButton=new JButton("...");
        //setting characters... I tried to change just the width at the start but wouldn't make much sense anyway...(didn't work)
        JTextField pathTextField = new JTextField(50);

        pathTextField.setEditable(false);

        JLabel exportLabel = new JLabel("Export:");

        exportPathButton.addActionListener(e->{
            JFileChooser fileChooserObj=new JFileChooser();
            //we set a title to the window
            fileChooserObj.setDialogTitle("Choose ExportPath");
            fileChooserObj.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result=fileChooserObj.showOpenDialog(frame);
            if (result != JFileChooser.APPROVE_OPTION) {
                fileChooserObj.cancelSelection();
                TestingUiApp.exportNotNull=false;
                pathTextField.setText(null);
                TestingUiApp.enableOrDisableProcessButtons(TestingUiApp.browserNotNull,TestingUiApp.exportNotNull,processButtons);
                return;
            }



            exportPath=fileChooserObj.getSelectedFile().getAbsolutePath();
            pathTextField.setText(fileChooserObj.getSelectedFile().getAbsolutePath());
            //enable process buttons
            TestingUiApp.exportNotNull=true;
            TestingUiApp.enableOrDisableProcessButtons(TestingUiApp.browserNotNull,true,processButtons);

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
