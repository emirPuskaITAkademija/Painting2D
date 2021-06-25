package ba.paint.nasa.app.listener;

import static ba.paint.nasa.app.listener.SaveListener.PICTURE_EXTENSION;
import ba.paint.nasa.app.xml.parser.XMLPictureParser;
import ba.paint.nasa.app.xml.shape.PaintShape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.swing.JOptionPane;


public class OpenListener implements ActionListener{

    private final Supplier<XMLPictureParser> pictureParserSupplier;
    private final Consumer<List<PaintShape>> shapesConsumer;

    public OpenListener(Supplier<XMLPictureParser> pictureParser,Consumer<List<PaintShape>> shapesConsumer) {
        this.pictureParserSupplier = pictureParser;
        this.shapesConsumer = shapesConsumer;
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String pictureName = JOptionPane.showInputDialog("Unesi ime slike");
          if(pictureName == null || pictureName.isEmpty()){
            return;
        }
        if(!pictureName.endsWith(PICTURE_EXTENSION)){
            pictureName+=PICTURE_EXTENSION;
        }
        List<PaintShape> paintShapes = pictureParserSupplier.get().readPicture(pictureName);
        shapesConsumer.accept(paintShapes);
    }
    
}
