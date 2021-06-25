package ba.paint.nasa.app.xml.parser.dom;

import ba.paint.nasa.app.xml.Refreshable;
import ba.paint.nasa.app.xml.parser.XMLPictureParser;
import ba.paint.nasa.app.xml.shape.Ellipse;
import ba.paint.nasa.app.xml.shape.PaintShape;
import ba.paint.nasa.app.xml.shape.Rectangle;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMPictureParser implements XMLPictureParser {

    private Refreshable refreshable;

    public DOMPictureParser() {
        this(Refreshable.NO_REFRESH);
    }

    public DOMPictureParser(Refreshable refreshable) {
        this.refreshable = refreshable;
    }

    @Override
    public List<PaintShape> readPicture(String filename) {
        try {
            List<PaintShape> paintShapes = new ArrayList<>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File(filename));
            //shapes 
            Element shapesElement = document.getDocumentElement();
            NodeList nodeList = shapesElement.getElementsByTagName("shape");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element shapeElement = (Element) node;
                    Element xElement = (Element) shapeElement.getElementsByTagName("x").item(0);
                    Element yElement = (Element) shapeElement.getElementsByTagName("y").item(0);
                    Element colorElement = (Element) shapeElement.getElementsByTagName("color").item(0);
                    Element typeOfShapeElement = (Element) shapeElement.getElementsByTagName("typeOfShape").item(0);
                    PaintShape paintShape;
                    if (typeOfShapeElement.getTextContent().equals("KRUG")) {
                        paintShape = new Ellipse(
                                Integer.parseInt(xElement.getTextContent()),
                                Integer.parseInt(yElement.getTextContent()),
                                colorElement.getTextContent().equals("PLAVA") ? Color.BLUE : Color.RED);
                    } else {
                        paintShape = new Rectangle(
                                Integer.parseInt(xElement.getTextContent()),
                                Integer.parseInt(yElement.getTextContent()),
                                colorElement.getTextContent().equals("PLAVA") ? Color.BLUE : Color.RED);
                    }
                    paintShapes.add(paintShape);
                }
            }
            return paintShapes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void savePicture(List<PaintShape> paintShapes, String filename) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            // document.getElementById("svomParagrafu").innnerHtml = "dsmdksmdk";
            Document document = documentBuilder.newDocument();
            Element shapesElement = document.createElement("shapes");
            document.appendChild(shapesElement);
            for (PaintShape paintShape : paintShapes) {
                Element shapeElement = document.createElement("shape");
                //x
                Element xElement = document.createElement("x");
                xElement.setTextContent(paintShape.getX() + "");
                shapeElement.appendChild(xElement);
                //y
                Element yElement = document.createElement("y");
                yElement.setTextContent(paintShape.getY() + "");
                shapeElement.appendChild(yElement);
                //color
                Element colorElement = document.createElement("color");
                colorElement.setTextContent(paintShape.getColor().equals(Color.BLUE) ? "PLAVA" : "CRVENA");
                shapeElement.appendChild(colorElement);
                //typeOfShape
                Element typeOfShapeElement = document.createElement("typeOfShape");
                typeOfShapeElement.setTextContent((paintShape instanceof Ellipse) ? "KRUG" : "KVADRAT");
                shapeElement.appendChild(typeOfShapeElement);

                shapesElement.appendChild(shapeElement);
            }
            //SNIMANJE -> TRANSFORMACIJA Java Objekat -> document
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filename));
            transformer.transform(source, streamResult);
            refreshable.refresh();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
