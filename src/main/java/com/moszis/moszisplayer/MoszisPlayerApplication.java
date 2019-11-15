package com.moszis.moszisplayer;

import com.moszis.moszisplayer.dto.Area;
import com.moszis.moszisplayer.ipr.ImagePatternRecognition;
import com.moszis.moszisplayer.service.MouseService;
import com.moszis.moszisplayer.service.ScreenCaptureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class MoszisPlayerApplication {

	@Autowired
	ScreenCaptureService screenCaptureService;

	@Autowired
	ImagePatternRecognition imagePatternRecognition;

	@Autowired
	MouseService mouseService;

	public static void main(String[] args) {
		SpringApplication.run(MoszisPlayerApplication.class, args);
	}

	static {
		System.setProperty("java.awt.headless", "false");
	}

	@Scheduled(fixedRate = 5000)
	public void gameLoop(){

		System.out.println("running..."+);

		screenCaptureService.createDesktopScreenshot("jpg", "Desktop.jpg");

		Area area = imagePatternRecognition.getMatchArea("winlogo.png");

		mouseService.clickTargetArea(area);
	}
}
