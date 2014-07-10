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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import ru.electrictower.orf.beans.Article;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Aliaksei Boole
 */
public class Browser
{
    public WebDriver webDriver;
    public boolean isLogin;

    public Browser()
    {
        initWebDriver();
    }

    private void initWebDriver()
    {
        webDriver = new HtmlUnitDriver();
        webDriver.manage().timeouts().implicitlyWait(10, SECONDS);
    }

    public void login(String userName, String password)
    {
        webDriver.get("https://profile.onliner.by/login");
        WebElement userNameField = webDriver.findElement(By.id("username"));
        userNameField.clear();
        userNameField.sendKeys(userName);
        WebElement passwordField = webDriver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys(password);
        WebElement checkBox = webDriver.findElement(By.name("autologin"));
        checkBox.click();
        WebElement loginButton = webDriver.findElement(By.cssSelector(".btn>input"));
        loginButton.click();
        try
        {
            WebElement accountInformation = webDriver.findElement(By.xpath("//a[contains(@class,'exit')]"));
            isLogin = accountInformation.isDisplayed();
        }
        catch (NoSuchElementException e)
        {
            isLogin = false;
        }
    }

    public void sendComment(String text, Article article)
    {
        webDriver.get(article.getCommentUrl());
        webDriver.findElement(By.xpath("//textarea[@name='message']")).sendKeys(text);
        webDriver.findElement(By.xpath("//button[@class='btn-2-green btn-2-green-send']")).click();
    }

    public String getLoginError()
    {
        WebElement errorElem = webDriver.findElement(By.xpath("//div[@class='error']/p"));
        return errorElem.getText();
    }

    public boolean isLogin()
    {
        return isLogin;
    }

}
