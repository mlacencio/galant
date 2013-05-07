package br.unesp.lbbc.gui;

import javax.swing.*;

import br.unesp.lbbc.choks.Gradient;
import br.unesp.lbbc.choks.HeatMap;

import java.awt.*;
import java.awt.event.*;

//class HeatMapDemo extends JFrame implements ItemListener
class HeatMapDemo extends JPanel implements ItemListener
{
    HeatMap panel;
    JPanel listPane;
    
    JComboBox gradientComboBox;
    
    String[] names = {"GRADIENT_BLACK to WHITE",
                      "GRADIENT_BLUE to RED",
                      "GRADIENT_GREEN YELLOW ORANGE RED",
                      "GRADIENT_HEAT",
                      "GRADIENT_HOT",
                      "GRADIENT_MAROON to GOLD",
                      "GRADIENT_RAINBOW",
                      "GRADIENT_RAINBOW2",
                      "GRADIENT_RED to GREEN",
                      "GRADIENT_ROY"};
    Color[][] gradients = {Gradient.GRADIENT_BLACK_TO_WHITE,
                           Gradient.GRADIENT_BLUE_TO_RED,
                           Gradient.GRADIENT_GREEN_YELLOW_ORANGE_RED,
                           Gradient.GRADIENT_HEAT,
                           Gradient.GRADIENT_HOT,
                           Gradient.GRADIENT_MAROON_TO_GOLD,
                           Gradient.GRADIENT_RAINBOW,
                           Gradient.GRADIENT_RAINBOW2,
                           Gradient.GRADIENT_RED_TO_GREEN,
                           Gradient.GRADIENT_ROY};

    public HeatMapDemo(HeatMap HM) throws Exception
    {
        
        listPane = new JPanel();
        listPane.setLayout(new GridBagLayout());
        //listPane.setBorder(BorderFactory.createTitledBorder("Gradient"));// teste1
       
        listPane.setBackground(Color.WHITE);

        GridBagConstraints gbc;        
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        //gbc.anchor = GridBagConstraints.WEST; teste1
        //gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(2, 2, 10, 2);
        //gbc.fill = GridBagConstraints.BOTH;  teste1
        
      
        //JLabel label = new JLabel("Gradient:");
        //listPane.add(label, gbc);
        
        Integer[] intArray = new Integer[names.length];
        for (int i = 0; i < names.length; i++)
        {
            intArray[i] = new Integer(i);
          
        }
        
        gradientComboBox = new JComboBox(intArray);
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        gradientComboBox.setRenderer(renderer);
        gradientComboBox.addItemListener(this);
        
        
        //gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
        listPane.add(gradientComboBox, gbc);
        
         
        // you can use a pre-defined gradient:
        panel = HM;
        gradientComboBox.setSelectedIndex(7);
        
        // set miscelaneous settings
    }
    
    public JPanel testJPanel(){
    	return listPane;
    }
        
    public void itemStateChanged(ItemEvent e)
    {
    	Object source = e.getItemSelectable();

            Integer ix = (Integer) e.getItem();
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                panel.updateGradient(gradients[ix]);
            }
       }
    
    // this function will be run from the EDT
   
   
    
        
    class ComboBoxRenderer extends JLabel implements ListCellRenderer
    {
        public ComboBoxRenderer()
        {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
        }
        
        public Component getListCellRendererComponent(
                                                JList list,
                                                Object value,
                                                int index,
                                                boolean isSelected,
                                                boolean cellHasFocus)
        {
            int selectedIndex = ((Integer)value).intValue();
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setText(names[selectedIndex].substring(9));
            return this;
        }
    }
}
