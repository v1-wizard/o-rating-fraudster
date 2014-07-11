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

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.extern.log4j.Log4j;
import ru.electrictower.orf.beans.Article;

import java.net.URL;

/**
 * @author Aliaksei Boole
 */
@Log4j
public class Rss
{
    private volatile boolean rssRun = false;
    private volatile Article hotArticle;
    private volatile boolean isHotArticle;

    private final ArticleBuilder articleBuilder = new ArticleBuilder();

    public boolean hasHotArticle()
    {
        return isHotArticle;
    }

    public Article getHotArticle()
    {
        isHotArticle = false;
        return hotArticle;
    }

    public void startRssThread()
    {
        rssRun = true;
        Thread rssThread = new Thread()
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
                        Thread.sleep(5000);
                    }
                    catch (Exception e)
                    {
                        log.error("RssThread error.", e);
                        rssRun = false;
                    }
                }
                log.info("Rss thread closed.");
            }
        };
        rssThread.setDaemon(true);
        rssThread.start();
    }

    public void stopRssThread()
    {
        rssRun = false;
    }
}
