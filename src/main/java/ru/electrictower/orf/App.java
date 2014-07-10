package ru.electrictower.orf;

import ru.electrictower.orf.view.View;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
            Controller controller = new Controller();
            View view = new View(controller);
            view.start();
    }
}
