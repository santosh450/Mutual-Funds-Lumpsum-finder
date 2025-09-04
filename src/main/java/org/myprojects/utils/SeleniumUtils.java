package org.myprojects.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v133.emulation.Emulation;
import org.openqa.selenium.devtools.v133.page.Page;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Optional;

public final class SeleniumUtils {

    private SeleniumUtils() {
        // Private constructor to prevent instantiation
    }

    public static File captureReport(String htmlFilePath) throws Exception {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Run headless
        ChromeDriver driver = new ChromeDriver(options);

        // 1. Open local HTML report
        driver.get(new File(htmlFilePath).toURI().toString());

        // 2. Create DevTools session
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        // Get full page dimensions using JS
        Long width = (Long) driver.executeScript("return document.body.scrollWidth");
        Long height = (Long) driver.executeScript(
                "return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);"
        );

        // ell Chrome to use that size
        devTools.send(Emulation.setDeviceMetricsOverride(
                width.intValue(),
                height.intValue(),
                1.0,
                false,               // mobile
                Optional.empty(),    // scale
                Optional.empty(),    // screenWidth
                Optional.empty(),    // screenHeight
                Optional.empty(),    // positionX
                Optional.empty(),    // positionY
                Optional.empty(),    // dontSetVisibleSize
                Optional.empty(),    // screenOrientation
                Optional.empty(),    // viewport
                Optional.empty(),    // displayFeature
                Optional.empty()
        ));

        // 3. Capture FULL PAGE screenshot
        String screenshotBase64 = devTools.send(Page.captureScreenshot(
                Optional.of(Page.CaptureScreenshotFormat.PNG), // format
                Optional.empty(),   // quality (only for JPEG)
                Optional.empty(),   // clip
                Optional.of(true),  // fromSurface
                Optional.empty(),   // captureBeyondViewport
                Optional.empty()    // optimizeForSpeed
        ));

        // 4. Save to file
        byte[] data = Base64.getDecoder().decode(screenshotBase64);
        File screenshotFile = new File("full_report.png");
        try (FileOutputStream fos = new FileOutputStream(screenshotFile)) {
            fos.write(data);
        }
        driver.quit();
        return screenshotFile;
    }
}
