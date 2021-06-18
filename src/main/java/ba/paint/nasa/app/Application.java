package ba.paint.nasa.app;

import ba.paint.nasa.app.layout.PaintWindow;
import javax.swing.JFrame;

public class Application {

    public static void main(String[] args) {
        PaintWindow instance =  PaintWindow.getInstance();
        instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instance.setVisible(true);
    }
}
