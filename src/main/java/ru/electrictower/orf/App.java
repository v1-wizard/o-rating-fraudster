package ru.electrictower.orf;

import ru.electrictower.orf.mvc.Model;
import ru.electrictower.orf.view.View;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
            Model model = new Model();
            model.startRssThread();
            View view = new View(model);
            view.start();
    }
}
