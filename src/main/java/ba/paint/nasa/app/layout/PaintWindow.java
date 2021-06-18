package ba.paint.nasa.app.layout;

import ba.paint.nasa.app.listener.ExitListener;
import ba.paint.nasa.app.listener.OpenListener;
import ba.paint.nasa.app.listener.SaveListener;
import ba.paint.nasa.app.xml.parser.sax.SAXPictureParser;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class PaintWindow extends JFrame {

    private PaintSettingsPanel paintSettingsPanel = new PaintSettingsPanel();
    private PaintPanel paintPanel = new PaintPanel();

    private PaintWindow() {
        setTitle("2D Crtanje");
        setSize(500, 300);
        //paint settings
        add(paintSettingsPanel, BorderLayout.NORTH);
        //platno za slikanje
        add(paintPanel, BorderLayout.CENTER);
        //opcije preko kojih snimamo
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fajl");
        fileMenu.setMnemonic('F');
        fileMenu.add(createMenuItem("Snimi", 'S', new SaveListener(new SAXPictureParser(), paintPanel::getPaintShapes)));
        fileMenu.add(createMenuItem("Otvori", 'O', new OpenListener(new SAXPictureParser(), paintPanel::acceptPaintShapes)));
        fileMenu.add(createMenuItem("Izlaz", 'I', new ExitListener()));
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private JMenuItem createMenuItem(String label, int mnemonic, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.setMnemonic(mnemonic);
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    public PaintSettingsPanel getPaintSettingsPanel() {
        return paintSettingsPanel;
    }

    public PaintPanel getPaintPanel() {
        return paintPanel;
    }

    private static PaintWindow INSTANCE = null;

    public static PaintWindow getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PaintWindow();
        }
        return INSTANCE;
    }

}
