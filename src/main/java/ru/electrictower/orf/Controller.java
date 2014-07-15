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
    private final Rss rss = new Rss();
    private final Browser browser = new Browser();
    private final Sound sound = new Sound();

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

    public void sendComment(final String comment, final Article article)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                browser.sendComment(comment, article);
            }
        }.start();
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
