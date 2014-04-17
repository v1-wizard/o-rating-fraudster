package ru.electrictower.orf.model;

import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import ru.electrictower.orf.beans.Article;

import java.util.Date;

/**
 * @author Aliaksei Boole
 */
public class ArticleBuilder
{
    private final static int DESCR_TEMP_INDEX = 1;
    private final static int IMG_TEMP_INDEX = 0;
    private final static String DESCR_TEMP_DELIMITER = "</p><p>";

    public Article buildLastArticleFrom(SyndFeed feed)
    {
        SyndEntry syndEntry = (SyndEntry) feed.getEntries().get(0);
        String title = syndEntry.getTitle();
        String description = extractDescriptionFrom(syndEntry);
        Date publicationDate = syndEntry.getPublishedDate();
        String imageUrl = extractImageUrlFrom(syndEntry);
        String section = ((SyndCategoryImpl) syndEntry.getCategories().get(0)).getName();
        return new Article(title, description, section, publicationDate, imageUrl);
    }

    private String extractDescriptionFrom(SyndEntry syndEntry)
    {
        String syndDescription = syndEntry.getDescription().getValue();
        String[] tempParts = syndDescription.split(DESCR_TEMP_DELIMITER);
        return tempParts[DESCR_TEMP_INDEX].replaceAll("<a href=.+\">", "").replace("</a>", "").replace(".", ".\n").replaceAll("<.?em>", "");
    }

    private String extractImageUrlFrom(SyndEntry syndEntry)
    {
        String syndDescription = syndEntry.getDescription().getValue();
        String[] tempParts = syndDescription.split(DESCR_TEMP_DELIMITER);
        return tempParts[IMG_TEMP_INDEX].replaceAll(".+src=\"","").replaceAll("\".+","");
    }
}
