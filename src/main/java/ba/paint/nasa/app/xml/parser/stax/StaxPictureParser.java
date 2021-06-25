package ba.paint.nasa.app.xml.parser.stax;

import ba.paint.nasa.app.xml.Refreshable;
import ba.paint.nasa.app.xml.parser.XMLPictureParser;
import ba.paint.nasa.app.xml.shape.Ellipse;
import ba.paint.nasa.app.xml.shape.PaintShape;
import ba.paint.nasa.app.xml.shape.Rectangle;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class StaxPictureParser implements XMLPictureParser {
    private Refreshable refreshable;

    public StaxPictureParser() {
        this(Refreshable.NO_REFRESH);
    }

    public StaxPictureParser(Refreshable refreshable) {
        this.refreshable = refreshable;
    }
    
    

    @Override
    public List<PaintShape> readPicture(String filename) {
        try {
            List<PaintShape> paintShapes = new ArrayList<>();
            XMLInputFactory xMLInputFactory = XMLInputFactory.newFactory();
            XMLStreamReader xMLStreamReader = xMLInputFactory.createXMLStreamReader(new FileReader(filename));
            boolean xOpen = false;
            String x = null;
            boolean yOpen = false;
            String y = null;
            String color = null;
            boolean colorOpen = false;
            String typeOfShape = null;
            boolean typeOfShapeOpen = false;
            while (xMLStreamReader.hasNext()) {
                int typeOfElement = xMLStreamReader.next();
                switch (typeOfElement) {
                    case XMLStreamReader.START_ELEMENT:
                        String name = xMLStreamReader.getName().toString();
                        if ("x".equals(name)) {
                            xOpen = true;
                        } else if ("y".equals(name)) {
                            yOpen = true;
                        } else if ("color".equals(name)) {
                            colorOpen = true;
                        } else if ("typeOfShape".equals(name)) {
                            typeOfShapeOpen = true;
                        }
                        break;
                    case XMLStreamReader.CHARACTERS:
                        String content = xMLStreamReader.getText();
                        if (xOpen) {
                            x = content;
                        } else if (yOpen) {
                            y = content;
                        } else if (colorOpen) {
                            color = content;
                        } else if (typeOfShapeOpen) {
                            typeOfShape = content;
                        }
                        break;
                    case XMLStreamReader.END_ELEMENT:
                        String endName = xMLStreamReader.getName().toString();
                        if ("x".equals(endName)) {
                            xOpen = false;
                        } else if ("y".equals(endName)) {
                            yOpen = false;
                        } else if ("color".equals(endName)) {
                            colorOpen = false;
                        } else if ("typeOfShape".equals(endName)) {
                            typeOfShapeOpen = false;
                        } else if ("shape".equals(endName)) {
                            Color colorAwt = "PLAVA".equals(color) ? Color.BLUE : Color.RED;
                            PaintShape paintShape;
                            if ("KRUG".equals(typeOfShape)) {
                                paintShape = new Ellipse(
                                        Integer.parseInt(x),
                                        Integer.parseInt(y), 
                                        colorAwt);
                            } else {
                                paintShape = new Rectangle(
                                        Integer.parseInt(x),
                                        Integer.parseInt(y), 
                                        colorAwt);
                            }
                            paintShapes.add(paintShape);
                        }
                        break;
                }
            }
            return paintShapes;
        } catch (FileNotFoundException | NumberFormatException | XMLStreamException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void savePicture(List<PaintShape> paintShapes, String filename) {
        try{
            XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newFactory();
            XMLStreamWriter streamWriter = xMLOutputFactory.createXMLStreamWriter(new FileWriter(filename));
            streamWriter.writeStartDocument();
            streamWriter.writeStartElement("shapes");
            for(PaintShape paintShape : paintShapes){
                streamWriter.writeStartElement("shape");
                //x
                streamWriter.writeStartElement("x");
                streamWriter.writeCharacters(paintShape.getX()+"");
                streamWriter.writeEndElement();
                //y
                streamWriter.writeStartElement("y");
                streamWriter.writeCharacters(paintShape.getY()+"");
                streamWriter.writeEndElement();
                //color
                streamWriter.writeStartElement("color");
                streamWriter.writeCharacters(paintShape.getColor().equals(Color.BLUE)?"PLAVA":"CRVENA");
                streamWriter.writeEndElement();
                //typeOfShape
                streamWriter.writeStartElement("typeOfShape");
                streamWriter.writeCharacters((paintShape instanceof Ellipse)? "KRUG":"KVADRAT");
                streamWriter.writeEndElement();
                //
                streamWriter.writeEndElement();
            }
            streamWriter.writeEndElement();
            streamWriter.writeEndDocument();
            streamWriter.flush();
            JOptionPane.showMessageDialog(null, "Slika je sačuvana");
            refreshable.refresh();
        }catch(IOException | XMLStreamException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
