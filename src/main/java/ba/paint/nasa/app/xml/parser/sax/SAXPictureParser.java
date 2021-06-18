package ba.paint.nasa.app.xml.parser.sax;

import ba.paint.nasa.app.layout.PaintWindow;
import ba.paint.nasa.app.xml.parser.XMLPictureParser;
import ba.paint.nasa.app.xml.shape.Ellipse;
import ba.paint.nasa.app.xml.shape.PaintShape;
import java.awt.Color;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class SAXPictureParser implements XMLPictureParser{

    @Override
    public List<PaintShape> readPicture(String filename) {
        List<PaintShape> paintShapes = new ArrayList<>();
        return paintShapes;
    }

    @Override
    public void savePicture(List<PaintShape> paintShapes, String filename) {
         try(PrintWriter out = new PrintWriter(new FileWriter(filename))){
            out.println("<?xml version=\"1.0\"?>");
            out.println("<shapes>");
            for(PaintShape paintShape: paintShapes){
                out.println("<shape>");
                out.println("<x>" + paintShape.getX()+"</x>");
                out.println("<y>" + paintShape.getY()+"</y>");
                out.println("<color>" + (paintShape.getColor()== Color.BLUE?"PLAVA":"CRVENA")+"</color>");
                out.println("<typeOfShape>");
                if(paintShape instanceof Ellipse){
                    out.println("KRUG");
                }else{
                    out.println("KVADRAT");
                }
                out.println("</typeOfShape>");
                out.println("</shape>");
            }
            out.println("</shapes>");
             JOptionPane.showMessageDialog(null, "SLIKA JE SAČUVANA");
             PaintWindow.getInstance().getPaintPanel().getPaintShapes().clear();
            PaintWindow.getInstance().getPaintPanel().repaint();
        }catch (Exception exception){
            System.err.println(exception.getMessage());
            JOptionPane.showMessageDialog(null, "Greška: " + exception.getMessage());
        }
    }
    
}
