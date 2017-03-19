package cz.exler.screenhost;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * ScreenShotter is simple purpose class that provides method
 * for taking image of the current screen upon request.
 * The minimum delay between 2 screen snaps is defined
 * in milliseconds by SCR_DELAY. When next request is sent
 * before SCR_DELAY passes, previous screen is return.
 * (This delay mechanism is done due to optimisation.)
 * <p>
 * TODO Suggested improvement: Add handling of parallel access
 * TODO Suggested improvement: Try other formats (PNG,...)
 */
public class ScreenShotter {
    /**
     * Constructor takes an initial screen shot.
     */
    ScreenShotter(){
        m_lastScreen = takeScreenshot();
    }

    /**
     * Checks if the stored screen shot needs to be updated
     * based on:
     * <ul>
     * <li>No screen shot taken so far (m_lastScreen is null or contains 0 bytes)
     * <li>Since taking last screen shot passed more time than is defined in SCR_DELAY
     * </ul>
     * If update of the screen shot image is required screen shot
     * is taken and stored in internal buffer for future use
     * and returned.
     *
     * @return binary represantation of last taken screenshot
     *         (for format of the binary data see <a href="#takeScreenshot">takeScreenshot</a>)
     */
    public byte[] getScreen(){
        long now = System.currentTimeMillis();
        if(m_lastScreen == null || !(m_lastScreen.length > 0) || (now - m_lastScreenTaken) > SCR_DELAY){
            m_lastScreen = takeScreenshot();
            m_lastScreenTaken = now;
        }
        return m_lastScreen;
    }

    // Private
    /**
     * Minimum delay between screenshots in milliseconds
     */
    private final long SCR_DELAY = 500;
    /**
     * Binary representation of last screen taken
     */
    private  byte[] m_lastScreen = {};
    /**
     * Timestamp of last taken screen
     */
    private long m_lastScreenTaken = 0;

    /**
     * Takes a current image of thee screen using createScreenCapture
     * from Robot class. Then converts the image to binary using jpeg
     * compression.
     * In case the process encounters an error, empty data is returned.
     *
     * @return binary represantation of taken screenshot in jpeg format
     */
    private byte[] takeScreenshot(){
        byte[] takenScreen = {};
        try {
            Robot robot = new Robot();
            BufferedImage screen = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ByteArrayOutputStream screenBytes = new ByteArrayOutputStream();
            ImageIO.write(screen, "JPG", screenBytes);
            screenBytes.flush();
            takenScreen = screenBytes.toByteArray();
        }
        catch(java.awt.AWTException e){
            System.out.print("ERROR taking screenshot: " + e.getMessage());
        }
        catch(java.io.IOException e){
            System.out.print("ERROR converting image: " + e.getMessage());
        }
        return takenScreen;
    }
}
