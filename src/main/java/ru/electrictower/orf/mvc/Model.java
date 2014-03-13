package ru.electrictower.orf.mvc;

import lombok.Getter;
import ru.electrictower.orf.beans.Article;
import ru.electrictower.orf.http.HttpCore;
import ru.electrictower.orf.http.Response;

import java.net.URI;

/**
 * @author Aliaksei Boole
 */
public class Model
{
        private boolean rssRun;
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
                rssRun = true;
                new Runnable()
                {
                        @Override
                        public void run()
                        {
                                while (rssRun)
                                {
                                        try
                                        {
                                                Response response = httpCore.executeGetRequest(new URI("http://www.onliner.by/feed"));
                                                Article actualArticle = getActualArticle(response);
                                                if (!lastArticle.equals(actualArticle))
                                                {
                                                        lastArticle = actualArticle;
                                                }
                                        }
                                        catch (Exception e)
                                        {
                                                e.printStackTrace();
                                                rssRun = false;
                                        }
                                }
                        }
                }.run();
        }

        private Article getActualArticle(Response response)
        {
                return null;
        }

        public void stopRssThread()
        {
                rssRun = false;
        }
}
