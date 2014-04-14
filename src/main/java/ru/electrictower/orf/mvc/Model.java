package ru.electrictower.orf.mvc;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j;
import ru.electrictower.orf.beans.Article;
import ru.electrictower.orf.http.HttpCore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Aliaksei Boole
 */
@Log4j
public class Model
{
    private volatile boolean rssRun = false;
    private final HttpCore httpCore = new HttpCore();
    private final ArticleBuilder articleBuilder = new ArticleBuilder();
    private volatile Article hotArticle;
    private volatile boolean isHotArticle;

    @Synchronized
    public boolean hasHotArticle()
    {
        return isHotArticle;
    }

    @Synchronized
    public Article getHotArticle()
    {
        isHotArticle = false;
        return hotArticle;
    }

    public void startRssThread()
    {
        rssRun = true;
        new Thread()
        {
            @Override
            public void run()
            {
                log.info("RssThread started.");
                while (rssRun)
                {
                    try
                    {
                        URL feedUrl = new URL("http://www.onliner.by/feed");
                        SyndFeedInput input = new SyndFeedInput();
                        SyndFeed feed = input.build(new XmlReader(feedUrl));
                        Article lastArticle = articleBuilder.buildLastArticleFrom(feed);
                        if (!lastArticle.equals(hotArticle))
                        {
                            hotArticle = lastArticle;
                            if (log.isDebugEnabled())
                            {
                                log.debug(hotArticle.toString());
                            }
                            isHotArticle = true;
                        }
                        else
                        {
                            if (log.isDebugEnabled())
                            {
                                log.debug("No new articles");
                            }
                        }
                        Thread.sleep(10000);
                    }
                    catch (Exception e)
                    {
                        log.error("RssThread error.", e);
                        rssRun = false;
                    }
                }
                log.info("Rss thread closed.");
            }
        }.start();
    }

    public void stopRssThread()
    {
        rssRun = false;
    }

    public boolean sendComment()
    {
        //TODO
        return false;
    }

    public boolean login()
    {
        //TODO
        return false;
    }
}
