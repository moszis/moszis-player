package com.moszis.moszisplayer.service;

import java.awt.*;

public class KeyboardService {


    public boolean pressKey(int keyCode){

        //TODO: write random logic; for now just click center

        try{
            Robot bot = new Robot();
            bot.keyPress(keyCode);
            bot.keyRelease(keyCode);

            return true;
        }catch (Exception e){
            System.err.println(e);

            return false;
        }


    }

}
