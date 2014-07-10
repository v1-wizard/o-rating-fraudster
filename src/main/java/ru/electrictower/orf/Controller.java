package ru.electrictower.orf;

import lombok.extern.log4j.Log4j;
import ru.electrictower.orf.beans.Article;
import ru.electrictower.orf.model.Browser;
import ru.electrictower.orf.model.Rss;
import ru.electrictower.orf.model.Sound;

/**
 * @author Aliaksei Boole
 */
@Log4j
public class Controller
{
    Rss rss = new Rss();
    Browser browser = new Browser();
    Sound sound = new Sound();

    public Article getHotArticle()
    {
        return rss.getHotArticle();
    }

    public void startRssThread()
    {
        rss.startRssThread();
    }

    public void stopRssThread()
    {
        rss.stopRssThread();
    }

    public boolean canShowArticle()
    {
        return browser.isLogin() && rss.hasHotArticle();
    }

    public void sendComment(String comment, Article article)
    {
        browser.sendComment(comment, article);
    }

    public void login(String userName, String password)
    {
        browser.login(userName, password);
    }

    public String getLoginError()
    {
        return browser.getLoginError();
    }

    public boolean isLogin()
    {
        return browser.isLogin();
    }

    public void beep()
    {
        sound.beep();
    }

}
