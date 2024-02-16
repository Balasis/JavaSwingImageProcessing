import javax.swing.*;

class ExportPathButton extends JPanel{
    private boolean checkIfJFileChooseNull=false;
    public ExportPathButton(JFrame frame,boolean browserNotNull,boolean exportNotNull,JButton[] processButtons){
        JButton  exportPathButton=new JButton("Export to");

        exportPathButton.addActionListener(e->{
            JFileChooser  fileChooserObj=new JFileChooser();
            //we set a title to the window
            fileChooserObj.setDialogTitle("Choose ExportPath");
            fileChooserObj.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result=fileChooserObj.showOpenDialog(frame);
            if (result != JFileChooser.APPROVE_OPTION) {
                checkIfJFileChooseNull=false;
                fileChooserObj.cancelSelection();
                TestingUiApp.enableOrDisableProcessButtons(browserNotNull,exportNotNull,processButtons);
                return;
            }
            checkIfJFileChooseNull=true;

            TestingUiApp.enableOrDisableProcessButtons(browserNotNull,exportNotNull,processButtons);

        });

        add(exportPathButton);
    }

    public boolean isExportPathSelected(){
        return (checkIfJFileChooseNull);
    }



}
