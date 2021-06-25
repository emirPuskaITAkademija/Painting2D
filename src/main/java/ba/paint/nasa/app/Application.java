package ba.paint.nasa.app;

import ba.paint.nasa.app.layout.PaintWindow;
import javax.swing.JFrame;
/**
 * Nasa aplikacija sadrži GUI ili UI za crtanje slika.
 * <p>
 * Naša aplikacija ima 2 panela:
 * <li>1. PaintSettingsPanel
 * <li>2. PaintPanel
 * <p>
 * Oba panela se nalaze u našem prozoru za slikanje.
 * 
 * @author Grupa1
 */
public class Application {

    public static void main(String[] args) {
        PaintWindow instance =  PaintWindow.getInstance();
        instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instance.setVisible(true);
    }
}
