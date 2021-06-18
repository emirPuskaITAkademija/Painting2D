package ba.paint.nasa.app.listener;

import ba.paint.nasa.app.layout.PaintWindow;
import ba.paint.nasa.app.xml.parser.XMLPictureParser;
import ba.paint.nasa.app.xml.parser.sax.SAXPictureParser;
import ba.paint.nasa.app.xml.shape.PaintShape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.JOptionPane;

public class SaveListener implements ActionListener {
    
    //NFS
    public static final String PICTURE_EXTENSION = ".nfs";

    //TIGHT COUPLED LOGIC
    //LOOSED COUPLED LOGIC 
    private final XMLPictureParser xmlPictureParser;
    private final Supplier<List<PaintShape>> shapesSupplier;

    public SaveListener(XMLPictureParser xmlPictureParser, Supplier<List<PaintShape>> shapesSupplier) {
        this.xmlPictureParser = xmlPictureParser;
        this.shapesSupplier = shapesSupplier;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pictureName = JOptionPane.showInputDialog("Unesite ime slike");
        if(pictureName == null || pictureName.isEmpty()){
            return;
        }
        if(!pictureName.endsWith(PICTURE_EXTENSION)){
            pictureName+=PICTURE_EXTENSION;
        }
        List<PaintShape> paintShapes  = shapesSupplier.get();
        xmlPictureParser.savePicture(paintShapes, pictureName);
    }
}
