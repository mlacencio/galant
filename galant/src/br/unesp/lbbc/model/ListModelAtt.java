package br.unesp.lbbc.model;

import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import cytoscape.view.CyNetworkView;

public class ListModelAtt implements ListModel {

	/**
	 * Preciso permitir somente a entrada de dados possível (corrigir a lista Atribute) 
	 * */
	
	private CyAttributes cyNodeAttrs=Cytoscape.getNodeAttributes();	
	private CyNetworkView net = Cytoscape.getCurrentNetworkView();
	private List<CyNode> nodeList = Cytoscape.getCyNodesList();
	public static int TYPE_BOOLEAN = 1;
	public static int TYPE_FLOATING = 2;
	public static int TYPE_INTEGER = 3;
	
	@Override
	public void addListDataListener(ListDataListener arg0) {
		

	}

	@Override
	public Object getElementAt(int arg0) {
		String elem = cyNodeAttrs.getAttributeNames()[arg0];
		return elem;
	}

	@Override
	public int getSize() {
		String size[] = cyNodeAttrs.getAttributeNames();
		return size.length;
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

}
