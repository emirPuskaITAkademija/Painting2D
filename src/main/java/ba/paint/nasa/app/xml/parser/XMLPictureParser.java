package ba.paint.nasa.app.xml.parser;

import ba.paint.nasa.app.xml.shape.PaintShape;
import java.util.List;


public interface XMLPictureParser {
    public List<PaintShape> readPicture(String filename);
    
    public void savePicture(List<PaintShape> paintShapes, String filename);
}
