package ru.electrictower.orf.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PUBLIC;

/**
 * Created by Wizard on 22.02.14.
 */
@AllArgsConstructor(access = PUBLIC)
public class User
{
    @Getter
    private String name;
    @Getter
    private String password;
}
