import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;








class BrowseButton extends JPanel{
    private   int[][] currentImg2dArray;
    public BrowseButton(JFrame frame,JButton activateOne){
        JButton browseButton=new JButton("Browse Image");


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
                activateOne.setEnabled(getImageFile() != null);
                return;
            }
            File selectedFile = fileChooserObj.getSelectedFile();
            File inputFile = new File(selectedFile.getAbsolutePath());
            currentImg = fetchImage(inputFile);
            //Creating a 2d Array using BufferedImage dimensions
            assert currentImg != null;
            currentImg2dArray = create2dArrayUsingBufferedImage(currentImg);

            //Populate the currentImg2dArray with the corresponding RGB value of each pixel of currentImg
            populate2dArrWithRGBFromTheBufferedImg(currentImg2dArray, currentImg);
            activateOne.setEnabled(getImageFile() != null);
        });


        add(browseButton);//add it to the panel
    }


    public  int[][] getImageFile(){
        return currentImg2dArray;
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







/*

//class is package-private visibility due to not declaring them public or protected or private...
class SimpleConsoleTextButton extends JPanel {
    public SimpleConsoleTextButton(String buttonName,String textInside){
        JButton button = new JButton(buttonName);
        //Ok as far as I understand the actionPerformed is a standard method included in actionListener
        //that's why Override is used here ; in order to override the method of the superclass that it derives
        button.addActionListener(e -> System.out.println(textInside));
        add(button);//it adds it to SimpleConsoleTextButton
    }

}*/
