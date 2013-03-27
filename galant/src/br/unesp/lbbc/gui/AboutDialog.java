package br.unesp.lbbc.gui;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {

	public AboutDialog(){
		
		
		setTitle("About");
		JLabel label = new JLabel(new ImageIcon(AboutDialog.class.getResource("/br/unesp/lbbc/main/about.png"))); 
		label.setPreferredSize(new Dimension(520,250));
		
		getContentPane().add(label );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	 
}
