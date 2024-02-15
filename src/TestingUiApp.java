import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class TestingUiApp {
    public static void main(String[] args) {
        //Display UI using swing. So far we just have a frame and a button and trying to understand how to form the ui and add functionality .
        SwingUtilities.invokeLater(TestingUiApp::createAndShowGUI);
    }




    private static void createAndShowGUI() {
        //all of the buttons are constructed into convertImageButtons
        // and classes extend Into extends JPanel (JPanel exists in Swing library so you have them access through that here)


        // Create the window
        JFrame frame = new JFrame("Image-Processing");

        //it closes the application when window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //redesign layout so it will accept both buttons otherwise it will just center them.
        frame.setLayout(new FlowLayout());

        //set Dimensions
        frame.setSize(700, 400);

        //disable resize
       frame.setResizable(false);

        // Add components
        //goes to the ConvertImageButtonsClass and create an instance of
        SimpleConsoleTextButton button=new SimpleConsoleTextButton("ButtonTest","SomeText");
        //browses the image file(
        BrowseButton browseButton=new BrowseButton(frame);

        JButton greyScale=new JButton("GreyScale");
        greyScale.addActionListener(e->{
            convertIntoGreyScale(browseButton.getImageFile());
            //Turn it to grayscale
            // Create new bufferedImage from the current processed 2d Array
            BufferedImage outputBufferedImage = createBufferedImageObjFrom2dArray(browseButton.getImageFile());
            //Set path for the image to be saved as well as the name
            File outputFile = new File("./theImageOutput.jpg");
            //Export the img
            exportImg(outputBufferedImage,outputFile);
        });



/*

                // Turn the first "x":30 in our case rows rgb into black (0,0,0) (if rows exist)
                // turnFirstTenRowsIntoBlack(currentImg2dArray, 130);

                //Turn it to grayscale
                convertIntoGreyScale(currentImg2dArray);


                // Create new bufferedImage from the current processed 2d Array
                BufferedImage outputBufferedImage = createBufferedImageObjFrom2dArray(currentImg2dArray);

                //Set path for the image to be saved as well as the name
                File outputFile = new File("./theImageOutput.jpg");

                //Export the img
                exportImg(outputBufferedImage,outputFile);



        });*/

        frame.getContentPane().add(button);
        frame.getContentPane().add(browseButton);
        frame.getContentPane().add(greyScale);

        // I disable the button if there is null to browse Image
        //OK after a bit of search here's the CATCH ON THIS
        greyScale.setEnabled(browseButton.getImageFile() != null); // Disable the button

        // Display the window
    //  frame.pack();//seems like pack was affecting the size
        frame.setVisible(true);
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
