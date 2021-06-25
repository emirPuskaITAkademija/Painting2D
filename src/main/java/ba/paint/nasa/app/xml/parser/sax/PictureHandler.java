package ba.paint.nasa.app.xml.parser.sax;

import ba.paint.nasa.app.layout.PaintWindow;
import ba.paint.nasa.app.xml.shape.Ellipse;
import ba.paint.nasa.app.xml.shape.PaintShape;
import ba.paint.nasa.app.xml.shape.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PictureHandler extends DefaultHandler {

    private int x;
    private boolean xOpen = false;

    private int y;
    private boolean yOpen = false;

    private String color;
    private boolean colorOpen = false;

    private String typeOfShape;
    private boolean typeOfShapeOpen = false;

    private List<PaintShape> paintShapes = new ArrayList<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if ("x".equals(qName)) {
            xOpen = true;
        } else if ("y".equals(qName)) {
            yOpen = true;
        } else if ("color".equals(qName)) {
            colorOpen = true;
        } else if ("typeOfShape".equals(qName)) {
            typeOfShapeOpen = true;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length);
        if (xOpen) {
            x = Integer.parseInt(content);
            xOpen = false;
        } else if (yOpen) {
            y = Integer.parseInt(content);
            yOpen = false;
        } else if (colorOpen) {
            color = content;
            colorOpen = false;
        } else if (typeOfShapeOpen) {
            typeOfShape = content;
            typeOfShapeOpen = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("shape".equals(qName)) {
            Color colorAwt = "CRVENA".equals(color) ? Color.RED : Color.BLUE;
            PaintShape paintShape;
            if (typeOfShape.equals("KRUG")) {
                paintShape = new Ellipse(x, y, colorAwt);
            } else {
                paintShape = new Rectangle(x, y, colorAwt);
            }
            paintShapes.add(paintShape);
        }
    }

    public List<PaintShape> getPaintShapes() {
        return paintShapes;
    }

}
