package ba.paint.nasa.app.layout;

import ba.paint.nasa.app.listener.ExitListener;
import ba.paint.nasa.app.listener.OpenListener;
import ba.paint.nasa.app.listener.SaveListener;
import ba.paint.nasa.app.xml.parser.dom.DOMPictureParser;
import ba.paint.nasa.app.xml.parser.sax.SAXPictureParser;
import ba.paint.nasa.app.xml.parser.stax.StaxPictureParser;
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
        JMenu saveMenu = new JMenu("Snimi sa");
        saveMenu.add(createMenuItem("SAX parserom", 'S', new SaveListener(new SAXPictureParser(this::refreshAfterSave), paintPanel::getPaintShapes)));
        saveMenu.add(createMenuItem("DOM parserom", 'D', new SaveListener(new DOMPictureParser(this::refreshAfterSave), paintPanel::getPaintShapes)));
        saveMenu.add(createMenuItem("STAX parserom", 'T', new SaveListener(new StaxPictureParser(this::refreshAfterSave), paintPanel::getPaintShapes)));
        fileMenu.add(saveMenu);

        JMenu openMenu = new JMenu("Otvori sa");
        openMenu.add(createMenuItem("SAX parserom", 'S', new OpenListener(SAXPictureParser::new, paintPanel::acceptPaintShapes)));
        openMenu.add(createMenuItem("DOM parserom", 'D', new OpenListener(DOMPictureParser::new, paintPanel::acceptPaintShapes)));
        openMenu.add(createMenuItem("STAX parserom", 'T', new OpenListener(StaxPictureParser::new, paintPanel::acceptPaintShapes)));
        fileMenu.add(openMenu);

        fileMenu.add(createMenuItem("Izlaz", 'I', new ExitListener()));
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void refreshAfterSave() {
        paintPanel.getPaintShapes().clear();
        paintPanel.repaint();
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
