package com.insider.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ElementHelper {
    private WebDriver driver;

    public ElementHelper(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findSelectElement(String... selectors) {
        for (String selector : selectors) {
            try {
                List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    return elements.get(0);
                }
            } catch (Exception e) {
                // Continue to next selector
            }
        }
        // Try XPath as fallback
        for (String selector : selectors) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(selector));
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    return elements.get(0);
                }
            } catch (Exception e) {
                // Continue
            }
        }
        return null;
    }

    public void selectByPartialText(WebElement selectElement, String partialText) {
        Select select = new Select(selectElement);
        List<WebElement> options = select.getOptions();
        for (WebElement option : options) {
            String optionText = option.getText().trim();
            if (optionText.contains(partialText) || partialText.contains(optionText)) {
                select.selectByVisibleText(optionText);
                return;
            }
        }
        throw new RuntimeException("Could not find option containing: " + partialText);
    }
}
