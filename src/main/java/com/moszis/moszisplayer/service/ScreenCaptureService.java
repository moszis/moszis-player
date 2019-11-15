package com.moszis.moszisplayer.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ScreenCaptureService {

    public boolean createDesktopScreenshot(String format, String outputFile){

        try{
            Robot bot = new Robot();

            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = bot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, format, new File(outputFile));

            return true;

        } catch (AWTException | IOException ex) {
            System.err.println(ex);

            return false;
        }

    }

    public boolean createAllScreensScreenshot(String format, String outputFile){

        try{
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] screens = ge.getScreenDevices();

            Rectangle allScreenBounds = new Rectangle();

            for (GraphicsDevice screen : screens) {
                Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
                allScreenBounds.width += screenBounds.width;
                allScreenBounds.height = Math.max(allScreenBounds.height, screenBounds.height);
            }

            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(allScreenBounds);

            ImageIO.write(screenShot, format, new File(outputFile));

        }catch(Exception e){
            return false;
        }

        return true;
    }
}
