package ba.paint.nasa.app.layout;

import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

public class PaintSettingsPanel extends JPanel {

    //u prvoj grupi
    private JRadioButton circleRadioButton = new JRadioButton("Krug");
    private JRadioButton squareRadioButton = new JRadioButton("Kvadrat");
    //u drugoj grupi
    private JRadioButton blueRadioButton = new JRadioButton("Plava");
    private JRadioButton redRadioButton = new JRadioButton("Red");

    public PaintSettingsPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel shapePanel = new JPanel();
        shapePanel.add(circleRadioButton);
        circleRadioButton.setSelected(true);
        shapePanel.add(squareRadioButton);
        TitledBorder shapeTitledBorder = new TitledBorder("Oblik");
        shapePanel.setBorder(shapeTitledBorder);
        ButtonGroup shapeButtonGroup = new ButtonGroup();
        shapeButtonGroup.add(circleRadioButton);
        shapeButtonGroup.add(squareRadioButton);

        JPanel colorPanel = new JPanel();
        colorPanel.add(blueRadioButton);
        blueRadioButton.setSelected(true);
        colorPanel.add(redRadioButton);
        TitledBorder colorTitledBorder = new TitledBorder("Boja");
        colorPanel.setBorder(colorTitledBorder);
        ButtonGroup colorButtonGroup = new ButtonGroup();
        colorButtonGroup.add(blueRadioButton);
        colorButtonGroup.add(redRadioButton);
        
        add(shapePanel);
        add(colorPanel);
    }
    
    public boolean isBlueRadioButtonSelected(){
        return blueRadioButton.isSelected();
    }
    
    public boolean isCircleSelected(){
        return circleRadioButton.isSelected();
    }
}
