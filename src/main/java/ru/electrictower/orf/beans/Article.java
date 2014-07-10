package ru.electrictower.orf.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

import static lombok.AccessLevel.PUBLIC;

/**
 * Created by Wizard on 22.02.14.
 */
@ToString
@AllArgsConstructor(access = PUBLIC)
@EqualsAndHashCode
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
    @Getter
    private String commentUrl;
    @Getter
    private String ImageUrl;

}
