package ru.electrictower.orf.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.Date;

import static lombok.AccessLevel.*;

/**
 * Created by Wizard on 22.02.14.
 */
@ToString
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
        private Date pubDate;
}
