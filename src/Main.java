import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
public class Main {
        public static void main (String[]args){
            //There are extra info for each line above the method used on the line.(scroll below)
            try{
                //path to the img, We will change this later on how to choose the path
                String imgPath="resources/theImage.png";

                //we fetch the image from the path in order to create our BufferedImage obj
                BufferedImage currentImg=fetchImage(imgPath);

                //Creating a 2d Array using BufferedImage dimensions e.g (currentImg.getWidth() )
                 int[][] currentImg2dArray= create2dArrayUsingBufferedImage(currentImg);

                //Populate the currentImg2dArray with the corresponding RGB value of each pixel of currentImg
                populate2dArrWithRGBFromTheBufferedImg(currentImg2dArray,currentImg);


                //Creating a loop which will target each one of those pixels and saves the rgb color
                //the rgb color from getRGB() returns an int which contains alpha (if present), red, green, blue.


                //As we know each color(red,green,blue) is saved in 1 byte(8bit).Therefore if we would like to
                //extract the red color we should shift 16 bit (pixel >> 16) then mask(take only) with 0xFF to
                // keep only the least significant 8 bits.

                //e.g
                //int pixel = image.getRGB(x, y);  // Example pixel value
                //int redComponent = (pixel >> 16) & 0xFF; // Extract red component


            }
            catch (IOException e){
                //printStackTrace returns a detailed list of exception including the method called during the exception(error)
                e.printStackTrace();
            }

        }







        //BufferedImage(return type) is the canvas you work with once the image data is in memory,
        // and ImageIO.read() is the tool you use to load the image data into that canvas
        // from an external source, like a file(myImgObj). Throws exception informs the compiler
        //that this method might return an exception(error) so it gets handled by the caller of the method
        public static BufferedImage fetchImage(String path) throws IOException{
            //File Object :It is used to create a path to the file, that's the only job of the FILE.
            return   ImageIO.read(new File(path));
        }




        // Create a 2d Array | In our case the height(px) is the columns  and the rows are the  width(px).
        public static int[][] create2dArrayUsingBufferedImage(BufferedImage bufferedImg){
            //Get the dimensions of the Image in order to create a 2d array which will hold the info for each pixel.
            return  new int[bufferedImg.getWidth()][bufferedImg.getHeight()];
        }



        public static void populate2dArrWithRGBFromTheBufferedImg(int[][] the2dArray,BufferedImage theBufferedImage){
            for (int x = 0; x < the2dArray.length; x++) {

                for (int y = 0; y < the2dArray[0].length; y++) {
                    the2dArray[x][y]=theBufferedImage.getRGB(x,y);
                }

            }
            int alpha = (the2dArray[0][2] >> 24) & 0xFF;
            int red = (the2dArray[0][2] >> 16) & 0xFF;
            int green = (the2dArray[0][2] >> 8) & 0xFF;
            int blue = the2dArray[0][2] & 0xFF;
            System.out.println("Alpha: " + alpha);
            System.out.println("Red: " + red);
            System.out.println("Green: " + green);
            System.out.println("Blue: " + blue);

        }


}