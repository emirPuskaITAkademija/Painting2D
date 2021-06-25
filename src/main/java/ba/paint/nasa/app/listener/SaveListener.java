package ba.paint.nasa.app.listener;

import ba.paint.nasa.app.xml.parser.XMLPictureParser;
import ba.paint.nasa.app.xml.shape.PaintShape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.JOptionPane;
/**
 * Rezultat slikanja na PainPanelu je šta ????
 * 
 * <li> List<PaintShape> paintShapes....
 * <li> XMLPictureParser -> read, write   -> XML
 * 
 * 
 * @author Grupa1
 */
public class SaveListener implements ActionListener {
    
    //NFS
    public static final String PICTURE_EXTENSION = ".nfs";

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
