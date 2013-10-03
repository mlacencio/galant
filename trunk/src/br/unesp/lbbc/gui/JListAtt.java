
package br.unesp.lbbc.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;

import br.unesp.lbbc.model.ListModelAtt;
//import br.unesp.lbbc.main.InitApplicationFrame;

/**
 * @author esther
 * 
 */
public class JListAtt extends JList {

	
	private ListModelAtt model;
	private String att;

	public JListAtt() {
		
		model = new ListModelAtt();
		
		this.setModel(model);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
				JList newList = (JList) arg0.getComponent();
				String s = (String) newList.getSelectedValue();
				setAttribute(s);
				
			}
		});
		
	}

	public ListModelAtt getModel() {
		return model;
	}

	public void setModel(ListModelAtt model) {
		this.model = model;
	}
	
	public void setAttribute(String newAtt){
		att = newAtt;
	}
	
	public String getAtt (){
		return att;
	}
	

}
