import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class TestingUiApp {
    //These two define if user has browsed an image and has chosen an export directory.
    //Once both become true the processing buttons become available to use.
    static boolean browserNotNull=false;
    static boolean exportNotNull=false;

    public static void main(String[] args) {
        //Builds the ui by adding a frame and inside of it buttons with addActionListeners to add functionality.
        //invokeLater is used to ensure that a piece of code is executed on  a special thread responsible for
        // handling Swing components and events
        SwingUtilities.invokeLater(TestingUiApp::createAndShowGUI);
    }




    private static void createAndShowGUI() {

        //BrowseButton and ExportPathButton classes are subclasses of JPanel (by using extend JPanel)
        //JPanel already exist in Swing.


        // Create the window(frame)
        JFrame frame = new JFrame("Image-Processing");

        //We set to close the application when window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //a layout so we can view more than 1 button.
        frame.setLayout(new FlowLayout());

        //set Dimensions to the frame (main window)
        frame.setSize(700, 400);

        //disable resize of the window
       frame.setResizable(false);

        // Add components(buttons) into the window

        //First I add all the processing buttons into an array. Reason is that we will use later the array to
        //activate these buttons once the browse and export path are chosen...(browserNotNull and exportNotNull turn true)
        JButton[] processButtons={
                new JButton("GreyScale")
        };


        // browser is to choose path (and also creates the 2d array and populate the rgb values), while export path just get a path.
        //Both of them in the end of their listeners run enableOrDisableProcessButtons to activate or deactivate the processButtons
        BrowseButton browseButton=new BrowseButton(frame,processButtons);
        ExportPathButton exportPathButton=new ExportPathButton(frame,processButtons);




        //GreyScale Listener
        processButtons[0].addActionListener(e->{
            convertIntoGreyScale(browseButton.getImageFile());
            //Turn it to grayscale
            // Create new bufferedImage from the current processed 2d Array
            BufferedImage outputBufferedImage = createBufferedImageObjFrom2dArray(browseButton.getImageFile());
            //Set path for the image to be saved as well as the name
            File outputFile = new File("./theImageOutput.jpg");
            //Export the img
            exportImg(outputBufferedImage,outputFile);
        });



        frame.getContentPane().add(browseButton);


        frame.getContentPane().add( processButtons[0]);
        frame.getContentPane().add(exportPathButton);

        // I disable the button if there is null to browse Image
        //OK after a bit of search here's the CATCH ON THIS
        processButtons[0].setEnabled(browseButton.getImageFile() != null); // Disable the button

        // Display the window
    //  frame.pack();//seems like pack was affecting the size
        frame.setVisible(true);
    }








    public static void enableOrDisableProcessButtons(boolean browserNotNull ,boolean exportNotNull,JButton[]  processButtons){
       if (browserNotNull && exportNotNull){
           for (JButton processButton : processButtons) {
               processButton.setEnabled(true);
           }
       }else{
           for (JButton processButton : processButtons) {
               processButton.setEnabled(false);
           }
       }

    }




    public static int convertRGBAtoInt(int red, int green, int blue,int alpha){
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }


    public static int extractAlphaFromRGBAint(int intOfRGBA){
        return (intOfRGBA >> 24) & 0xFF;
    }

    public static int extractRedFromRGBAint(int intOfRGBA){
        return (intOfRGBA >> 16) & 0xFF;
    }

    public static int extractGreenFromRGBAint(int intOfRGBA){
        return (intOfRGBA >> 8) & 0xFF;
    }

    public static int extractBlueFromRGBAint(int intOfRGBA){
        return intOfRGBA & 0xFF;
    }


        public static BufferedImage  createBufferedImageObjFrom2dArray(int[][] the2dArray){
            BufferedImage outputBufferedImage = new BufferedImage(the2dArray.length, the2dArray[0].length, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < the2dArray.length; x++) {
                for (int y = 0; y < the2dArray[0].length; y++) {
                    outputBufferedImage.setRGB(x, y, the2dArray[x][y]);
                }
            }
            return outputBufferedImage;
        }






    public static void turnFirstTenRowsIntoBlack(int[][] the2dArray, int rowsToBlacken) {
        int minNumberOfRows = Math.min(rowsToBlacken, the2dArray[0].length);
        for (int x = 0; x < the2dArray.length; x++) {
            for (int y = 0; y < minNumberOfRows; y++) {
                the2dArray[x][y] = -16777216; // Black color
            }
        }
    }




    public static void exportImg(BufferedImage outputBufferedImage,File outputFile){
        try {
            OutputStream outputStream = new FileOutputStream(outputFile);
            ImageIO.write(outputBufferedImage, "jpg", outputStream);
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }












    public static void convertIntoGreyScale(int[][] the2dArray){
        int currentRedValue;
        int currentGreenValue;
        int currentBlueValue;
        int currentAlphaValue;
        for (int x = 0; x < the2dArray.length; x++) {
            for (int y = 0; y <  the2dArray[0].length; y++) {
                //Extract values from the pixel
                currentAlphaValue=extractAlphaFromRGBAint(the2dArray[x][y]);
                currentRedValue=extractRedFromRGBAint(the2dArray[x][y]);
               currentGreenValue=extractGreenFromRGBAint(the2dArray[x][y]);
               currentBlueValue=extractBlueFromRGBAint(the2dArray[x][y]);
               //Formula with some constants to create a number from 0 to 255
               int theFormula=(int) Math.round(0.2126 * currentRedValue + 0.7152 * currentGreenValue + 0.0722 * currentBlueValue);
                //then you put the same value in rgb in red green and blue(its like picking the average but the weight depend on constant)
                the2dArray[x][y]=convertRGBAtoInt(theFormula,theFormula,theFormula,currentAlphaValue);

            }
        }


    }

    public static void convertIntoNegative(int[][] the2dArray){
        int currentRedValue;
        int currentGreenValue;
        int currentBlueValue;
        int currentAlphaValue;
        for (int x = 0; x < the2dArray.length; x++) {
            for (int y = 0; y <  the2dArray[0].length; y++) {
                //Extract values from the pixel
                currentAlphaValue=extractAlphaFromRGBAint(the2dArray[x][y]);
                currentRedValue=extractRedFromRGBAint(the2dArray[x][y]);
                currentGreenValue=extractGreenFromRGBAint(the2dArray[x][y]);
                currentBlueValue=extractBlueFromRGBAint(the2dArray[x][y]);


                //To convert into negative we just do maximum value (255 ) minus the current;
                int negativeRed = 255 - currentRedValue;
                int negativeGreen = 255 - currentGreenValue;
                int negativeBlue = 255 - currentBlueValue;

                //then we put back the values into the pixel
                the2dArray[x][y]=convertRGBAtoInt(negativeRed,negativeGreen,negativeBlue,currentAlphaValue);

            }
        }


    }



}
