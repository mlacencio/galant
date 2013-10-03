package br.unesp.lbbc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import cytoscape.view.CyNetworkView;

public class ListModelAtt implements ListModel {

	
	private CyAttributes cyNodeAttrs=Cytoscape.getNodeAttributes();	
	private CyNetworkView net = Cytoscape.getCurrentNetworkView();
	private List<CyNode> nodeList = Cytoscape.getCyNodesList();
	public static int TYPE_BOOLEAN = 1;
	public static int TYPE_FLOATING = 2;
	public static int TYPE_INTEGER = 3;
	private List arrayAtt = new ArrayList<String>(); 
	
	public ListModelAtt (){

		String[] todosElem = cyNodeAttrs.getAttributeNames();
		
		//only add type boolean, floating and integer
		for (String elem: todosElem){
			int cynode = cyNodeAttrs.getType(elem);
			if (cynode==TYPE_BOOLEAN){
				arrayAtt.add(elem);
			}
			else if (cynode==TYPE_FLOATING){
				arrayAtt.add(elem);
			}
			else if (cynode==TYPE_INTEGER){
				arrayAtt.add(elem);
			}
		}
		
		Collections.sort(arrayAtt);
		
	}
	
	
	@Override
	public void addListDataListener(ListDataListener arg0) {
		

	}

	@Override
	public Object getElementAt(int arg0) {
		return arrayAtt.get(arg0);
	}

	@Override
	public int getSize() {
		return arrayAtt.size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

}
