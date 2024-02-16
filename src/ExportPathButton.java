import javax.swing.*;

class ExportPathButton extends JPanel{
    private  String exportPath;
    public ExportPathButton(JFrame frame,JButton[] processButtons){
        JButton  exportPathButton=new JButton("Export to");

        exportPathButton.addActionListener(e->{
            JFileChooser fileChooserObj=new JFileChooser();
            //we set a title to the window
            fileChooserObj.setDialogTitle("Choose ExportPath");
            fileChooserObj.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result=fileChooserObj.showOpenDialog(frame);
            if (result != JFileChooser.APPROVE_OPTION) {
                fileChooserObj.cancelSelection();
                TestingUiApp.exportNotNull=false;
                TestingUiApp.enableOrDisableProcessButtons(TestingUiApp.browserNotNull,TestingUiApp.exportNotNull,processButtons);
                return;
            }
            TestingUiApp.exportNotNull=true;
            exportPath=fileChooserObj.getSelectedFile().getAbsolutePath();
            TestingUiApp.enableOrDisableProcessButtons(TestingUiApp.browserNotNull,TestingUiApp.exportNotNull,processButtons);

        });

        add(exportPathButton);
    }

    public String exportPathSelected(){
        return (exportPath);
    }


}
