import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Main {
    //These two define if user has browsed an image and has chosen an export directory.
    //Once both become true the processing buttons become available to use.
    static boolean browserNotNull=false;
    static boolean exportNotNull=false;

    public static void main(String[] args) {
        //Builds the ui by adding a frame and inside of it buttons with addActionListeners to add functionality.
        //invokeLater is used to ensure that a piece of code is executed on  a special thread responsible for
        // handling Swing components and events
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }




    private static void createAndShowGUI() {

        //BrowseButton and ExportPathButton classes are subclasses of JPanel (by using extend JPanel)
        //JPanel already exist in Swing.


        // Create the window(frame)
        JFrame frame = new JFrame("Image-Processing");

        //We set to close the application when window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //a layout so we can view more than 1 button.
        frame.setLayout(new BorderLayout());

        //set Dimensions to the frame (main window)
       frame.setSize(800, 300);

        //disable resize of the window
       frame.setResizable(false);

        // Add components(buttons) into the window

        //First I add all the processing buttons into an array. Reason is that we will use later the array to
        //activate these buttons once the browse and export path are chosen...(browserNotNull and exportNotNull turn true)
        JButton[] processButtons={
                new JButton("GreyScale"),
                new JButton("Negative"),
                new JButton("Rotate Right"),
                new JButton("Rotate Left"),
                new JButton("Invert")
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
            String pathChosenToExport=  exportPathButton.exportPathSelected() + "/";
            String browseringFileName=browseButton.getTheNameOfBrowseFileExtensionIncluded();

            File outputFile = new File(pathChosenToExport+browseringFileName);
            //Export the img
            exportImg(outputBufferedImage,outputFile);
            browseButton.reInsertFileFromBrowse();
        });

        //Negative Listener
        processButtons[1].addActionListener(e->{
            convertIntoNegative(browseButton.getImageFile());
            //Turn it to grayscale
            // Create new bufferedImage from the current processed 2d Array
            BufferedImage outputBufferedImage = createBufferedImageObjFrom2dArray(browseButton.getImageFile());
            //Set path for the image to be saved as well as the name
            String pathChosenToExport=  exportPathButton.exportPathSelected() + "/";
            String browseringFileName=browseButton.getTheNameOfBrowseFileExtensionIncluded();

            File outputFile = new File(pathChosenToExport+browseringFileName);
            //Export the img
            exportImg(outputBufferedImage,outputFile);
            browseButton.reInsertFileFromBrowse();
        });
        processButtons[2].addActionListener(e->{
            BufferedImage outputBufferedImage = createBufferedImageObjFrom2dArray(rotateRight(browseButton.getImageFile()));
            String pathChosenToExport=  exportPathButton.exportPathSelected() + "/";
            String browseringFileName=browseButton.getTheNameOfBrowseFileExtensionIncluded();
            File outputFile = new File(pathChosenToExport+browseringFileName);
            exportImg(outputBufferedImage,outputFile);
            browseButton.reInsertFileFromBrowse();
        });
        processButtons[3].addActionListener(e->{
            BufferedImage outputBufferedImage = createBufferedImageObjFrom2dArray(rotateLeft(browseButton.getImageFile()));
            String pathChosenToExport=  exportPathButton.exportPathSelected() + "/";
            String browseringFileName=browseButton.getTheNameOfBrowseFileExtensionIncluded();
            File outputFile = new File(pathChosenToExport+browseringFileName);
            exportImg(outputBufferedImage,outputFile);
            browseButton.reInsertFileFromBrowse();
        });
        processButtons[4].addActionListener(e->{
            invertImage(browseButton.getImageFile());
            BufferedImage outputBufferedImage = createBufferedImageObjFrom2dArray(browseButton.getImageFile());
            String pathChosenToExport=  exportPathButton.exportPathSelected() + "/";
            String browseringFileName=browseButton.getTheNameOfBrowseFileExtensionIncluded();
            File outputFile = new File(pathChosenToExport+browseringFileName);
            exportImg(outputBufferedImage,outputFile);
            browseButton.reInsertFileFromBrowse();
        });






                        //SETTING UP THE UI STRUCTURE (SPLITTED INTO NORTH AND SOUTH border layouts)

        //Note 1:nested JPanels in order to form the UI

        //Note 2: (Sizes) it seems like that the sizes inside a JPanel are controlled my layoutManager in swing
        //therefore instead of setSize won't work because it doesn't notify the LayoutManager about the size
        //it would like to have...for this reason we use setPreferredSize which notifies the LayoutManager of Swing(upper JPanel)

        //Note 3:Each position in BorderLayout (NORTH,CENTER,SOUTH,EAST E.T.C) can accept only one element(therefore we nest them)


        //=========================================NORTH SIDE OF MAIN FRAME (WelcomeText && browse-export panels) ======================================//

                //Creating the Welcome Text
                JPanel welcomeText = new JPanel();
                        welcomeText.setLayout(new BorderLayout());



                        //Creating text and setting the properties of it
                        JTextArea textArea = new JTextArea("Browse an image and select export path.\n Then choose among the filters to export it." +
                                " Choose the same browse-export path to process the same image multiple times");

                        textArea.setEditable(false);
                        textArea.setBackground(frame.getBackground());
                        textArea.setFocusable(false);

                        //creating Space for North position
                        JPanel topSpacerText = new JPanel();
                        topSpacerText.setPreferredSize(new Dimension(10, 10));

                        //adding textArea into Container in order to center it
                        JPanel textAreaContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
                        textAreaContainer.add(textArea);

                        //Finally adding both in the welcome text panel
                        welcomeText.add(topSpacerText,BorderLayout.NORTH);
                        welcomeText.add(textAreaContainer,BorderLayout.CENTER);



                //Creating the browseAndExportPanels
                JPanel browseAndExportPanels = new JPanel();
                        browseAndExportPanels.setLayout(new BorderLayout());
                            //creating a space for north position
                            JPanel topSpacer = new JPanel();
                            topSpacer.setPreferredSize(new Dimension(10, 10));
                        browseAndExportPanels.add(topSpacer,BorderLayout.NORTH);
                        browseAndExportPanels.add(browseButton,BorderLayout.CENTER);
                        browseAndExportPanels.add(exportPathButton,BorderLayout.SOUTH);


        //Merge them into one :NORTH Border layout
        JPanel welcomeTextAndBrowseAndExportPanels = new JPanel();
        welcomeTextAndBrowseAndExportPanels.setLayout(new BorderLayout());
        welcomeTextAndBrowseAndExportPanels.add(welcomeText,BorderLayout.NORTH);
        welcomeTextAndBrowseAndExportPanels.add(browseAndExportPanels,BorderLayout.SOUTH);






        //=========================================SOUTH SIDE OF MAIN FRAME ======================================//

        //Creating the processButtons Panel (border layout..north will be a space Panel and South will be another
        //Container Panel in flow Layout which will hold all the processing buttons, one next to other(flow))
                JPanel processButtonsAndBottomSpacerPanel = new JPanel();

                //adding for spacing
                JPanel bottomSpacer = new JPanel();
                bottomSpacer.setPreferredSize(new Dimension(10, 100));

                //Nest the buttons into a container which has flow layout so buttons will appear next to each other.
                JPanel processButtonsAndBottomSpacerPanelFlowed = new JPanel(new FlowLayout());
                //an enhanced for in order to add each processButton into the (Flowed) container
                for (JButton processButton : processButtons) {
                    processButtonsAndBottomSpacerPanelFlowed.add(processButton);
                }

                processButtonsAndBottomSpacerPanel.add(processButtonsAndBottomSpacerPanelFlowed,BorderLayout.NORTH);
                processButtonsAndBottomSpacerPanel.add(bottomSpacer,BorderLayout.SOUTH);



        //====================MERGING THE NORTH AND THE SOUTH Border layouts OF MAIN FRAME ===================//
        frame.getContentPane().add(welcomeTextAndBrowseAndExportPanels,BorderLayout.NORTH);
        frame.getContentPane().add(processButtonsAndBottomSpacerPanel,BorderLayout.SOUTH);

        // Initially we disable the process buttons
        enableOrDisableProcessButtons(browserNotNull,exportNotNull,processButtons);

        // Display the window
        frame.setVisible(true);
    }












    //I use this inside the listeners of  the browse and export buttons so each time I check if both are not null
    //the validation of them is done inside their listeners so if they are not null they are valid paths/files.
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







    //=============common Methods that all proccessing methods require, FROM RGBA to int and back=======//
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














        //======Following 2 methods are to convert the 2d array into BufferedImage obg and then Export.All processing buttons have these two===//

        public static BufferedImage  createBufferedImageObjFrom2dArray(int[][] the2dArray){
            BufferedImage outputBufferedImage = new BufferedImage(the2dArray.length, the2dArray[0].length, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < the2dArray.length; x++) {
                for (int y = 0; y < the2dArray[0].length; y++) {
                    outputBufferedImage.setRGB(x, y, the2dArray[x][y]);
                }
            }
            return outputBufferedImage;
        }


    public static void exportImg(BufferedImage outputBufferedImage,File outputFile){
        //adding the declare inside the try named try-width-resources as I read it autoclose at the end of the block
        //previously that I had it inside I couldn't delete the image that I output because it was held by jdk
        try ( OutputStream outputStream = new FileOutputStream(outputFile)){

            ImageIO.write(outputBufferedImage, "jpg", outputStream);
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }










        //=============All the following are proccessing methods.(Change the 2d array to form the desired result)================//




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
    public static int[][] rotateLeft(int[][] mat) {
        final int width = mat.length;
        final int height = mat[0].length;
        int[][] ret = new int[height][width];
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                ret[c][width-1-r] = mat[r][c];
            }
        }
        return ret;
    }
    public static int[][] rotateRight(int[][] mat) {
        final int width = mat.length;
        final int height = mat[0].length;
        int[][] ret = new int[height][width];
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                ret[height-1-c][r] = mat[r][c];
            }
        }
        return ret;
    }
    public static void invertImage(int[][] mat){
        final int width = mat.length;
        final int height = mat[0].length;
        int[][] flipped = new int[width][height];
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                flipped[r][c] = mat[width - 1 - r][height - 1 - c];
            }
        }
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                mat[i][j] = flipped[i][j];
            }
        }
    }
}
