package ru.electrictower.orf.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;
import java.util.Date;

import static lombok.AccessLevel.*;

/**
 * Created by Wizard on 22.02.14.
 */
@AllArgsConstructor(access = PUBLIC)
public class Article
{
        @Getter
        private String title;
        @Getter
        private String description;
        @Getter
        private String section;
        @Getter
        private InputStream image;
        @Getter
        private Date pubDate;
}
