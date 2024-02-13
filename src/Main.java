import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        String imgPath = "resources/theImagee.jpg";
        BufferedImage currentImg;

        do {
            currentImg = fetchImage(imgPath);
        }while(currentImg==null);

        //Creating a 2d Array using BufferedImage dimensions
        int[][] currentImg2dArray = create2dArrayUsingBufferedImage(currentImg);

        //Populate the currentImg2dArray with the corresponding RGB value of each pixel of currentImg
        populate2dArrWithRGBFromTheBufferedImg(currentImg2dArray, currentImg);

        // Turn the first "x":30 in our case rows rgb into black (0,0,0) (if rows exist)
       // turnFirstTenRowsIntoBlack(currentImg2dArray, 130);

        //Turn it to grayscale
        convertIntoGreyScale(currentImg2dArray);


        // Create new bufferedImage from the current processed 2d Array
        BufferedImage outputBufferedImage = createBufferedImageObjFrom2dArray(currentImg2dArray);

        //Set path for the image to be saved as well as the name
        File outputFile = new File("resources/theImageOutput.jpg");

        //Export the img
        exportImg(outputBufferedImage,outputFile);

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





    //BufferedImage(return type) is the canvas you work with once the image data is in memory,
    // and ImageIO.read() is the tool you use to load the image data into that canvas
    // from an external source, like a file(myImgObj). Throws exception informs the compiler
    //that this method might return an exception(error) so it gets handled by the caller of the method
    public static BufferedImage fetchImage(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            ExceptionHandler.handleExceptionCantReadPhoto(e);
            return null;
        }
    }





    // Create a 2d Array | In our case the height(px) is the columns  and the rows are the  width(px).
    public static int[][] create2dArrayUsingBufferedImage(BufferedImage bufferedImg) {
        return new int[bufferedImg.getWidth()][bufferedImg.getHeight()];
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
            ImageIO.write(outputBufferedImage, "jpg", outputFile);
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


}
