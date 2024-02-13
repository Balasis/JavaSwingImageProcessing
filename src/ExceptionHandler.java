import java.util.logging.Logger;
public class ExceptionHandler {
    private static final Logger logger=Logger.getLogger(ExceptionHandler.class.getName());
    public static void handleException(Exception e){
        logger.severe("Heres an exception fella : "+ e.getMessage());
    }
//some comment
}
