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


                //Turn the first  "x":30 in our case   rows rgb into black (0,0,0) (if rows exist)
                turnFirstTenRowsIntoBlack(currentImg2dArray,130);















                //Let's recreate an image out of the current processed 2d Array
                BufferedImage outputBufferedImage = new BufferedImage(currentImg2dArray.length, currentImg2dArray[0].length, BufferedImage.TYPE_INT_RGB);

                for (int x = 0; x < currentImg2dArray.length; x++) {
                    for (int y = 0; y < currentImg2dArray[0].length; y++) {
                        outputBufferedImage.setRGB(x, y, currentImg2dArray[x][y]);
                    }
                }






                File outputFile = new File("C:/Users/Oathkeeper/Desktop/output.png");
                try {
                    ImageIO.write(outputBufferedImage, "png", outputFile);
                    System.out.println("Image saved successfully.");
                } catch (IOException e) {
                    System.err.println("Error saving image: " + e.getMessage());
                }

/*
                                        //Testing functionality(Delete later):

                //in order to see the real value from the int number that represent the rgb we need to do some
                //shifting ( >> 24 ) and some masking  (0xFF)<-taking the last 8 LeastSignificantBits (LSB)
                //The followings will be removed from this method its only for now for testing purposes
                int alpha = (currentImg2dArray[0][0] >> 24) & 0xFF;
                int red = (currentImg2dArray[0][0] >> 16) & 0xFF;
                int green = (currentImg2dArray[0][0] >> 8) & 0xFF;
                int blue = currentImg2dArray[0][0] & 0xFF;
                System.out.println("Alpha: " + alpha);
                System.out.println("Red: " + red);
                System.out.println("Green: " + green);
                System.out.println("Blue: " + blue);
*/



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




        //By Using getRGB(x,y) we get the rgb value of its pixel (which is an int number) and we place it inside each
        //the corresponding array element that represents this pixel in the2dArray.
        //Furthmore, there is no dimension/indexDifferences check between the2dArray and the bufferedImg
        //Since the2dArray was made out of the dimensions of theBufferedImage.
        public static void populate2dArrWithRGBFromTheBufferedImg(int[][] the2dArray,BufferedImage theBufferedImage){
            for (int x = 0; x < the2dArray.length; x++) {
                for (int y = 0; y < the2dArray[0].length; y++) {
                    the2dArray[x][y]=theBufferedImage.getRGB(x,y);
                }
            }
        }



        public static void turnFirstTenRowsIntoBlack(int[][] the2dArray,int rowsToBlacken){


            //find the minimum number of rows that exist till 10
            //when it finds the number that is less or equal to length it breaks out the loop
            int minNumberOfRows=1;
            for (int i = rowsToBlacken; i >=0; i--) {
                if (i<= the2dArray[0].length){
                    minNumberOfRows=i;
                    break;
                }
            }

            //loop through each pixel and put the int number that represents rgb(0,0,0,255)..which is -16777216
            for (int x = 0; x < the2dArray.length; x++) {
                for (int y = 0; y < minNumberOfRows; y++) {
                    the2dArray[x][y]=-16777216;
                }
            }


        }

}