import javax.swing.*;
import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.logging.Logger;
import java.util.Scanner;

//experimental state...its working if the path is wrong at the fetch image (in browseButton) but it cant really be wrong since it
//is chosen by ui selection. Moreover, fetchImage method doesn't throw an Exception error if image.io.read() cant read the file , instead it returns
//a null value. As a result I cant redirect it here to be handled.
//In other words the current ExceptionHandler handles absolutely nothing.
public class ExceptionHandler {



    private static final Logger logger=Logger.getLogger(ExceptionHandler.class.getName());
    public static void handleException(Exception e){
        logger.severe("Heres an exception fella : "+ e.getMessage());
    }
    public static void handleExceptionCantReadPhoto(Exception e){

        Scanner myScanObj=new Scanner((System.in));
      //  logger.info("Could not load the image. Please ensure that the path is correct");

        if (e instanceof FileNotFoundException) {
            // Handle file not found exception
            System.err.println("File not found: " + e.getMessage());
      JOptionPane.showMessageDialog(null, "File not found", "Invalid File", JOptionPane.INFORMATION_MESSAGE);

        } else if (e instanceof AccessDeniedException) {
            // Handle access denied exception
            System.err.println("Access denied: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Access denied:", "Invalid File", JOptionPane.INFORMATION_MESSAGE);

        } else {
            // Handle other types of IOExceptions
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Please enter a valid Image File", "Invalid File", JOptionPane.INFORMATION_MESSAGE);

        }

    }



}
