package ru.electrictower.orf.mvc;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.Getter;
import ru.electrictower.orf.beans.Article;
import ru.electrictower.orf.http.HttpCore;

import java.net.URL;

/**
 * @author Aliaksei Boole
 */
public class Model
{
        private volatile boolean rssRun = true;
        private final HttpCore httpCore = new HttpCore();
        @Getter
        private Article lastArticle;
        private boolean isNewArticle;


        public boolean isNewArticle()
        {
                return isNewArticle;
        }

        public void startRssThread()
        {
                new Thread()
                {
                        @Override
                        public void run()
                        {
                                while (rssRun)
                                {
                                        try
                                        {
                                                URL feedUrl = new URL("http://www.onliner.by/feed");
                                                SyndFeedInput input = new SyndFeedInput();
                                                SyndFeed feed = input.build(new XmlReader(feedUrl));
                                                System.out.println(feed);
                                                SyndEntryImpl lastArticle = (SyndEntryImpl) feed.getEntries().get(0);
                                                Thread.sleep(10000);
                                        }
                                        catch (Exception e)
                                        {
                                                e.printStackTrace();
                                                rssRun = false;
                                        }
                                }
                        }
                }.start();
        }

        public void stopRssThread()
        {
                rssRun = false;
        }
}
