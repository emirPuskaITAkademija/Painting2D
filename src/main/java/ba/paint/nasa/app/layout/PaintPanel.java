package ba.paint.nasa.app.layout;

import ba.paint.nasa.app.xml.shape.Ellipse;
import ba.paint.nasa.app.xml.shape.PaintShape;
import ba.paint.nasa.app.xml.shape.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JPanel;

public class PaintPanel extends JPanel  {

    private final List<PaintShape> paintShapes = new ArrayList<>();

    public PaintPanel() {
        setBackground(Color.WHITE);
        addMouseListener(new DrawListener());
        addMouseMotionListener(new DrawListener());
    }

    public List<PaintShape> getPaintShapes() {
        return paintShapes;
    }


    public void acceptPaintShapes(List<PaintShape> acceptedPaintShapes) {
        paintShapes.clear();
        paintShapes.addAll(acceptedPaintShapes);
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //2D crtanje Graphics2D
        Graphics2D g2d = (Graphics2D) g;
        for (PaintShape paintShape : paintShapes) {
            g2d.setColor(paintShape.getColor());
            g2d.fill(paintShape.createShape());
        }
    }

    private class DrawListener extends MouseAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseListen(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseListen(e);
        }

        private void mouseListen(MouseEvent event) {
            int x = event.getX();
            int y = event.getY();
            PaintSettingsPanel settingsPanel = PaintWindow.getInstance().getPaintSettingsPanel();
            boolean blueSelected = settingsPanel.isBlueRadioButtonSelected();
            boolean circleSelected = settingsPanel.isCircleSelected();
            PaintShape paintShape = circleSelected
                    ? new Ellipse(x, y, blueSelected ? Color.BLUE : Color.RED)
                    : new Rectangle(x, y, blueSelected ? Color.BLUE : Color.RED);
            paintShapes.add(paintShape);
            repaint();
        }

    }
}
