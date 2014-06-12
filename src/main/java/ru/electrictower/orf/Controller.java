package ru.electrictower.orf;

import lombok.extern.log4j.Log4j;
import ru.electrictower.orf.beans.Article;
import ru.electrictower.orf.rss.Rss;

/**
 * @author Aliaksei Boole
 */
@Log4j
public class Controller
{

    Rss rss = new Rss();

    public boolean hasHotArticle()
    {
        return rss.hasHotArticle();
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

    public boolean sendComment(String comment)
    {
        if (comment != null && !comment.isEmpty())
        {
            System.out.println(String.format("Comment[%s] send!", comment));
        }
        return false;
    }

    public boolean login(String userName, String password)
    {
        return true;
    }

    public boolean isLogin()
    {
        return false;
    }

    public String getLoginName()
    {
        return null;
    }
}
