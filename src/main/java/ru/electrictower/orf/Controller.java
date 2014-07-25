package ru.electrictower.orf;

import ru.electrictower.orf.beans.Article;
import ru.electrictower.orf.model.Browser;
import ru.electrictower.orf.model.Rss;
import ru.electrictower.orf.model.Sound;
import ru.electrictower.orf.view.View;

/**
 * @author Aliaksei Boole
 */
public class Controller
{
    private final Browser browser;
    private final Sound sound;
    private final Rss rss;
    private final View view;

    public Controller()
    {
        view = new View(this);
        rss = new Rss(this);
        sound = new Sound();
        browser = new Browser();
    }

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

    public void wakeUpUI()
    {
        view.wake();
    }

    public void start()
    {
        view.start();
    }

}
