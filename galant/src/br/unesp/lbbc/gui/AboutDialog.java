package br.unesp.lbbc.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {

	public AboutDialog(){
		
		
		setTitle("About");
		setIconImage(Toolkit.getDefaultToolkit().getImage(InitAttributeFrame.class.getResource("/br/unesp/lbbc/main/gravata.png")));
		JLabel label = new JLabel(new ImageIcon(AboutDialog.class.getResource("/br/unesp/lbbc/main/about.jpg"))); 
		label.setPreferredSize(new Dimension(800,500));
		
		getContentPane().add(label );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	 
}
