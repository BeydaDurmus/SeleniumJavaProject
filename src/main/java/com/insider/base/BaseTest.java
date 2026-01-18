package com.insider.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional String browser) {
        String selectedBrowser = getBrowser(browser);
        boolean headless = isHeadless();
        String gridUrl = getGridUrl();

        System.out.println("Browser: " + selectedBrowser.toUpperCase());
        System.out.println("Headless: " + headless);
        System.out.println("Grid URL: " + (gridUrl != null ? gridUrl : "Local"));

        driver = createDriver(selectedBrowser, headless, gridUrl);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    private String getBrowser(String testngParam) {
        if (testngParam != null && !testngParam.isEmpty()) {
            return testngParam.toLowerCase();
        }
        String sysProp = System.getProperty("browser");
        if (sysProp != null && !sysProp.isEmpty()) {
            return sysProp.toLowerCase();
        }
        String envVar = System.getenv("BROWSER");
        if (envVar != null && !envVar.isEmpty()) {
            return envVar.toLowerCase();
        }
        return "chrome";
    }

    private boolean isHeadless() {
        String headless = System.getProperty("headless");
        if (headless == null) {
            headless = System.getenv("HEADLESS");
        }
        return "true".equalsIgnoreCase(headless);
    }

    private String getGridUrl() {
        String gridUrl = System.getProperty("grid.url");
        if (gridUrl == null) {
            gridUrl = System.getenv("SELENIUM_GRID_URL");
        }
        return gridUrl;
    }

    private WebDriver createDriver(String browser, boolean headless, String gridUrl) {
        if (gridUrl != null && !gridUrl.isEmpty()) {
            return createRemoteDriver(browser, headless, gridUrl);
        }
        return createLocalDriver(browser, headless);
    }

    private WebDriver createRemoteDriver(String browser, boolean headless, String gridUrl) {
        try {
            URL url = new URL(gridUrl);
            switch (browser) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) firefoxOptions.addArguments("--headless");
                    return new RemoteWebDriver(url, firefoxOptions);

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) edgeOptions.addArguments("--headless");
                    return new RemoteWebDriver(url, edgeOptions);

                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless");
                        chromeOptions.addArguments("--no-sandbox");
                        chromeOptions.addArguments("--disable-dev-shm-usage");
                    }
                    return new RemoteWebDriver(url, chromeOptions);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium Grid URL: " + gridUrl, e);
        }
    }

    private WebDriver createLocalDriver(String browser, boolean headless) {
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                if (headless) firefoxOptions.addArguments("--headless");
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                edgeOptions.addArguments("--disable-notifications");
                if (headless) edgeOptions.addArguments("--headless");
                return new EdgeDriver(edgeOptions);

            case "safari":
                return new SafariDriver();

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                if (headless) {
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                return new ChromeDriver(chromeOptions);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
