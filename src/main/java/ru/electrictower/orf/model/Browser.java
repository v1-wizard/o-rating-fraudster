/*
 * Copyright (C) 2005-2014 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package ru.electrictower.orf.model;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.electrictower.orf.beans.Article;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Aliaksei Boole
 */
public class Browser {
    public WebDriver webDriver;
    public boolean isLogin;

    public Browser() {
        initWebDriver();
    }

    private void initWebDriver() {
        webDriver = getWebDriver();
        webDriver.manage().timeouts().implicitlyWait(20, SECONDS);
    }

    public void login(String userName, String password) {
        webDriver.get("https://profile.onliner.by/");
        WebElement userNameField = webDriver.findElement(By.xpath("//input[@type='text']"));
        userNameField.clear();
        userNameField.sendKeys(userName);
        WebElement passwordField = webDriver.findElement(By.xpath("//input[@type='password']"));
        passwordField.clear();
        passwordField.sendKeys(password);
        WebElement loginButton = webDriver.findElement(By.xpath("//button[contains(@class,'auth-box__auth-submit')]"));
        loginButton.click();
        try {
            WebElement accountInformation = webDriver.findElement(By.xpath("//a[@class='exit']"));
            isLogin = accountInformation.isDisplayed();
        } catch (NoSuchElementException e) {
            isLogin = false;
        }
    }

    public void sendComment(String text, Article article) {
        webDriver.get(article.getCommentUrl());
        webDriver.findElement(By.xpath("//textarea[@name='message']")).sendKeys(text);
        webDriver.findElement(By.xpath("//button[@class='btn-2-green btn-2-green-send']")).click();
    }

    public String getLoginError() {
        WebElement errorElem = webDriver.findElement(By.xpath("//div[@class='error']/p"));
        return errorElem.getText();
    }

    public boolean isLogin() {
        return isLogin;
    }


    private static WebDriver getWebDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "path_to_phantom_js_driver"
        );
        return new PhantomJSDriver(caps);
    }

}
