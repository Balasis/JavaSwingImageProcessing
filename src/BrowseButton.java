import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;




class BrowseButton extends JPanel{
    private   int[][] currentImg2dArray;
    private   String getTheNameOfBrowseringFile;
    public BrowseButton(JFrame frame,JButton[] processButtons){
        JButton browseButton=new JButton("...");

        //setting characters...I tried to change just the width at the start but wouldn't make much sense anyway...(didn't work)
        JTextField pathTextField = new JTextField(50);
        pathTextField.setEditable(false);

        JLabel browseLabel = new JLabel("Browse:");



        browseButton.addActionListener(e -> {
            //we create a JfileChooser
            JFileChooser fileChooserObj=new JFileChooser();
            //we set a title to the window
            fileChooserObj.setDialogTitle("Choose an image");
            //showOpenDialog is to open the option for the user to choose a file... the parameter it takes
            //is in which parent window is to be displayed (in our case in the frame)
            //the result of showOpenDialog(frame) is an int number... which represents
            //if action was approved , cancel , or error
            BufferedImage currentImg=null;

            int result=fileChooserObj.showOpenDialog(frame);


            if (result != JFileChooser.APPROVE_OPTION) {
                fileChooserObj.cancelSelection();
                TestingUiApp.browserNotNull=false;
                pathTextField.setText(null);
                TestingUiApp.enableOrDisableProcessButtons(TestingUiApp.browserNotNull,TestingUiApp.exportNotNull,processButtons);
                return;
            }




            File selectedFile = fileChooserObj.getSelectedFile();
            getTheNameOfBrowseringFile=fileChooserObj.getSelectedFile().getName();
            File inputFile = new File(selectedFile.getAbsolutePath());

            currentImg = fetchImage(inputFile);
            if (currentImg==null){
                fileChooserObj.cancelSelection();
                TestingUiApp.browserNotNull=false;
                pathTextField.setText(null);
                TestingUiApp.enableOrDisableProcessButtons(TestingUiApp.browserNotNull,TestingUiApp.exportNotNull,processButtons);
                JOptionPane.showMessageDialog(null, "Please enter an Image File", "Invalid File", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            pathTextField.setText(selectedFile.getAbsolutePath());

            //Creating a 2d Array using BufferedImage dimensions
            assert currentImg != null;
            currentImg2dArray = create2dArrayUsingBufferedImage(currentImg);

            //Populate the currentImg2dArray with the corresponding RGB value of each pixel of currentImg
            populate2dArrWithRGBFromTheBufferedImg(currentImg2dArray, currentImg);
            TestingUiApp.browserNotNull=true;
            TestingUiApp.enableOrDisableProcessButtons(true,TestingUiApp.exportNotNull,processButtons);
        });


        // Create a panel to hold label and text field horizontally
        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new BorderLayout());
        pathPanel.add(browseLabel, BorderLayout.NORTH);
        pathPanel.add(pathTextField, BorderLayout.CENTER);
        pathPanel.add(browseButton, BorderLayout.EAST);

        add(pathPanel);



    }


    public  int[][] getImageFile(){
        return currentImg2dArray;
    }

    public String getTheNameOfBrowseFileExtensionIncluded(){
        return getTheNameOfBrowseringFile;
    }


    //By Using getRGB(x,y) we get the rgb value of its pixel (which is an int number) and we place it inside each
    //the corresponding array element that represents this pixel in the2dArray.
    //Furthmore, there is no dimension/indexDifferences check between the2dArray and the bufferedImg
    //Since the2dArray was made out of the dimensions of theBufferedImage.
    public static void populate2dArrWithRGBFromTheBufferedImg(int[][] the2dArray, BufferedImage theBufferedImage) {
        for (int x = 0; x < the2dArray.length; x++) {
            for (int y = 0; y < the2dArray[0].length; y++) {
                the2dArray[x][y] = theBufferedImage.getRGB(x, y);
            }
        }
    }


    //BufferedImage(return type) is the canvas you work with once the image data is in memory,
    // and ImageIO.read() is the tool you use to load the image data into that canvas
    // from an external source, like a file(myImgObj). Throws exception informs the compiler
    //that this method might return an exception(error) so it gets handled by the caller of the method
    public static BufferedImage fetchImage(File path) {
        try {
            InputStream inputStream = new FileInputStream(path);
            //seems like ImageIO.read(inputStream) doesn't throw any error...just a null if it cant read it
            //therefore I cant catch an exception for it to send it to handler
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            ExceptionHandler.handleExceptionCantReadPhoto(e);
            return null;
        }
    }


    // Create a 2d Array | In our case the height(px) is the columns  and the rows are the  width(px).
    public static int[][] create2dArrayUsingBufferedImage(BufferedImage bufferedImg) {
        return new int[bufferedImg.getWidth()][bufferedImg.getHeight()];
    }


}