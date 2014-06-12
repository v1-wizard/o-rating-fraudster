package ru.electrictower.orf;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
            Controller controller = new Controller();
            controller.startRssThread();
            View view = new View(controller);
            view.start();
    }
}
