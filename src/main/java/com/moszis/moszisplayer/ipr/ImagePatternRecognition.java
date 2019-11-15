package com.moszis.moszisplayer.ipr;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.moszis.moszisplayer.dto.Area;
import org.springframework.stereotype.Service;

import java.awt.AWTException;
import java.text.DecimalFormat;

@Service
public class ImagePatternRecognition {

    private int matchingAlgorithm = Imgproc.TM_CCOEFF_NORMED;
    private String defaultScanAreaFile   = "Desktop.jpg";
    private String defaultResultFile     = "result.jpg";
    private int defaultMatchPercent = 70;


    public Area getMatchArea(String templateFile){

        return getMatchArea(defaultScanAreaFile, templateFile, matchingAlgorithm, defaultMatchPercent);

    }


    public Area getMatchArea(String templateFile, int matchPercent){

        return getMatchArea(defaultScanAreaFile, templateFile, matchingAlgorithm, matchPercent);

    }


    public Area getMatchArea(String scanAreaFile, String templateFile){

        return getMatchArea(scanAreaFile, templateFile, matchingAlgorithm, defaultMatchPercent);

    }


    public Area getMatchArea(String scanAreaFile, String templateFile, int match_method, int matchPercent){

        if(match_method < 0 || match_method > 5){
            match_method = matchingAlgorithm;
        }

        MinMaxLocResult mmr;
        try {
            mmr = runMatching(scanAreaFile, templateFile, match_method);
        } catch (AWTException e) {
            return null;
        }

        Point matchLoc;
        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }


        Area area = buildArea(matchLoc, templateFile);

        if(calculateMatchPercentage(match_method, mmr) > matchPercent){
            area.setMatch(true);
        }else{
            area.setMatch(false);
        }

        return area;

    }



    public boolean isMatch(String templateFile, double matchPercent){

        return isMatch(defaultScanAreaFile, templateFile, matchingAlgorithm, matchPercent);

    }


    public boolean isMatch(String scanAreaFile, String templateFile, int match_method, double matchPercent){

        if(match_method < 0 || match_method > 5){
            match_method = matchingAlgorithm;
        }

        MinMaxLocResult mmr;
        try {
            mmr = runMatching(scanAreaFile, templateFile, match_method);
        } catch (AWTException e) {
            return false;
        }

        if(calculateMatchPercentage(match_method, mmr) > matchPercent){
            //System.out.println("Success");
            return true;
        }else{
            //System.out.println("NO MATCH---");
            return false;
        }

    }


    public void generateMatchOutputFile(String templateFile){

        try {
            generateMatchOutputFile(defaultScanAreaFile, templateFile, defaultResultFile, matchingAlgorithm);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    public void generateMatchOutputFile(String scanAreaFile, String templateFile, String outFile,
                                        int match_method) throws AWTException {

        if(match_method < 0 || match_method > 5){
            match_method = matchingAlgorithm;
        }

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat img   = Imgcodecs.imread(scanAreaFile);
        Mat templ = Imgcodecs.imread(templateFile);

        MinMaxLocResult mmr;
        try {
            mmr = runMatching(scanAreaFile, templateFile, match_method);
        } catch (AWTException e) {
            return;
        }

        Point matchLoc;
        if (match_method == Imgproc.TM_SQDIFF
                || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }

        // / Show me what you got
        Imgproc.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(),
                matchLoc.y + templ.rows()), new Scalar(0, 255, 0));

        matchLoc.x = matchLoc.x + templ.cols()/2 - 10;
        matchLoc.y = matchLoc.y + templ.rows()/2 - 10;

        Imgproc.rectangle(img, matchLoc, new Point(matchLoc.x + 20,
                matchLoc.y + 20), new Scalar(0, 255, 0));

        // Save the visualized detection.
        Imgcodecs.imwrite(outFile, img);
    }



    private MinMaxLocResult runMatching(String scanAreaFile, String templateFile, int match_method) throws AWTException {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat img   = Imgcodecs.imread(scanAreaFile);
        Mat templ = Imgcodecs.imread(templateFile);

        // / Create the result matrix
        int result_cols = img.cols() - templ.cols() + 1;
        int result_rows = img.rows() - templ.rows() + 1;
        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

        // / Do the Matching and Normalize
        Imgproc.matchTemplate(img, templ, result, match_method);

        //Additional Normalization seem to screw up the result
        // Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        // / Localizing the best match with minMaxLoc
        MinMaxLocResult mmr = Core.minMaxLoc(result);

        return mmr;

    }

    //TODO: Match area may not work properly if template different size
    private Area buildArea(Point matchLoc, String templateFile){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat templ = Imgcodecs.imread(templateFile);

        Area area = new Area();
        area.x = matchLoc.x;
        area.y = matchLoc.y;
        area.width = templ.cols();
        area.height = templ.rows();
        area.generateCenterX();
        area.generateCenterY();

        return area;
    }


    private double calculateMatchPercentage(int match_method, MinMaxLocResult mmr){

        Double matchPercent = 0.0;

        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchPercent = 100 - (mmr.minVal * 100);
        } else {
            matchPercent = mmr.maxVal * 100;
        }

        DecimalFormat df = new DecimalFormat("####0.00");
        System.out.println("Match Percent: " + df.format(matchPercent)+"%");

        return matchPercent;
    }
}

