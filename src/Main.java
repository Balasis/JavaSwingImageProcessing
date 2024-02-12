import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
public class Main {
        public static void main (String[]args){
            try{
                //path to the img, We will change this later on how to choose the path
                String imgPath="resources/theImage.png";

                //we fetch the image from the path in order to create our BufferedImage obj
                // (more info over fetchImage method bellow)
                BufferedImage currentImg=fetchImage(imgPath);

                 int[][] currentImg2dArray= create2dArrayUsingBufferedImage(currentImg);



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
                e.printStackTrace();
            }

        }







        //BufferedImage(return type) is the canvas you work with once the image data is in memory,
        // and ImageIO.read() is the tool you use to load the image data into that canvas
        // from an external source, like a file(myImgObj).
        public static BufferedImage fetchImage(String path) throws IOException{
            //File Object :It is used to create a path to the file, that's the only job of the FILE.
            return   ImageIO.read(new File(path));
        }


        // Create a 2d Array | In our case the height(px) is the columns  and the rows are the  width(px).
        public static int[][] create2dArrayUsingBufferedImage(BufferedImage bufferedImg){
            //Get the dimensions of the Image in order to create a 2d array which will hold the info for each pixel.
            return  new int[bufferedImg.getWidth()][bufferedImg.getHeight()];
        }





}