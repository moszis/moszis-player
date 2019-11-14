package com.moszis.moszisplayer.service;

import java.awt.*;
import java.awt.event.InputEvent;
import com.moszis.moszisplayer.dto.Area;

public class MouseService {

    boolean defaultClickCenter = true;
    int defaultRanCenterOffset = 0;
    int defaultNumClicks = 1;

    public boolean clickTargetArea(Area area){
        return clickTargetArea(area, defaultClickCenter, defaultRanCenterOffset, defaultNumClicks);
    }

    public boolean moveMouseToTopLeftCorner(){

        try{
            Robot bot = new Robot();
            bot.mouseMove(0, 0);
            return true;
        }catch (Exception e){
            System.err.println(e);
            return false;
        }
    }


    public boolean clickTargetArea(Area area, boolean clickCenter, int ranCenterOffset, int numClicks){

        //TODO: write random logic; for now just click center

        try{
            Robot bot = new Robot();

            bot.mouseMove((int)area.centerX, (int)area.centerY);

            for(int x = 0; x < numClicks; x++){
                bot.mousePress(InputEvent.BUTTON1_MASK);
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
                Thread.sleep(50);
            }
            return true;
        }catch (Exception e){
            System.err.println(e);

            return false;
        }
    }

}
