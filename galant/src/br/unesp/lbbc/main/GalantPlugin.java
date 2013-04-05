package br.unesp.lbbc.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import br.unesp.lbbc.gui.AboutDialog;
import br.unesp.lbbc.gui.HelpWindow;
import br.unesp.lbbc.gui.InitAttributeFrame;
import br.unesp.lbbc.gui.InitExpControlFrame;
import br.unesp.lbbc.util.Loadlibs;
import cytoscape.Cytoscape;
import cytoscape.plugin.CytoscapePlugin;

/**
 * @author Esther Camilo
 *
 */
public class GalantPlugin extends CytoscapePlugin {

	JMenu jMenuViaComplex;
	JMenuItem jmiExpControl;
	JMenuItem jmiAttribute;
	JMenuItem jmiHelp;
	JMenuItem jmiAbout;


	public GalantPlugin() {
		
		//Loadlibs.copyJar();
		
		Loadlibs.loadMyLibs();
		// Initializing variables
		
		jMenuViaComplex = new JMenu("Galant");
		
		jmiExpControl = new JMenuItem("Exp X Control");
		jmiAttribute = new JMenuItem("Attributes");
		jmiHelp = new JMenuItem("Help");
		jmiAbout = new JMenuItem("About");

		// Adding menus
		Cytoscape.getDesktop().getCyMenus().getMenuBar().getMenu("Plugins")
				.add(jMenuViaComplex);
		jMenuViaComplex.add(jmiExpControl);
		jMenuViaComplex.add(jmiAttribute);
		//jMenuViaComplex.add(jmiHelp);
		jMenuViaComplex.add(jmiAbout);
		
	/*	//Adding button to toolbar
		
		ImageIcon icon;
		icon = new ImageIcon(getClass().getResource("icone.jpg"));
		VCToolBarAction actionVC = new VCToolBarAction(icon,this);
		Cytoscape.getDesktop().getCyMenus().addCytoscapeAction((CytoscapeAction) actionVC);
	*/	
		
		// Adding actions
		
		jmiExpControl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InitExpControlFrame exp = new InitExpControlFrame();
				
			}
		});

		jmiAttribute.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InitAttributeFrame att = new InitAttributeFrame();
				
			}
		});
		
		jmiHelp.addActionListener(new ActionListener() {

			@Override
		public void actionPerformed(ActionEvent e) {
				URL index = ClassLoader.getSystemResource("webpages/index.html");
				   new HelpWindow("Help", index);
			}
		});

		jmiAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AboutDialog about = new AboutDialog();
			}
		});

	}

}
