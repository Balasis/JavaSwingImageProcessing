import java.util.logging.Logger;
import java.util.Scanner;
public class ExceptionHandler {

    private static final Logger logger=Logger.getLogger(ExceptionHandler.class.getName());
    public static void handleException(Exception e){
        logger.severe("Heres an exception fella : "+ e.getMessage());
    }
    public static void handleExceptionCantReadPhoto(Exception e){
        Scanner myScanObj=new Scanner((System.in));
        logger.info("Could not load the image. Please ensure that the path is correct");
        myScanObj.nextLine();
    }

}
