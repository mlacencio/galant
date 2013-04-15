package br.unesp.lbbc.controller;

import giny.view.NodeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import br.unesp.lbbc.util.Util;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import cytoscape.view.CyNetworkView;

public class Mapping {

	private CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();
	private CyNetworkView net = Cytoscape.getCurrentNetworkView();
	private List<CyNode> nodeList = Cytoscape.getCyNodesList();
	public static int TYPE_BOOLEAN = 1;
	public static int TYPE_FLOATING = 2;
	public static int TYPE_INTEGER = 3;

	/**
	 * Class responsible for converting the type of attributes in double and
	 * generate one map of key-String and value-double[], where double[] is
	 * x,y,z coordinates
	 */

	public Mapping() {
	}

	/**
	 * Given one attribute, this method gives you one map (nameNode,valueAtt) in
	 * double type.
	 */

	private HashMap<String, Double> getTypeFloat(String attName) {

		HashMap<String, Double> parcialMapAtt = new HashMap<String, Double>();

		for (CyNode node : nodeList) {

			Double value = 0.0;

			try {

				value = cyNodeAttrs.getDoubleAttribute(node.getIdentifier(),
						attName);

			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null,
						"Missing Attribute for Node: " + node
								+ "\n it will be considered zero");
				e.printStackTrace();
			}

			parcialMapAtt.put(node.getIdentifier(), value);

		}

		return parcialMapAtt;

	}

	private HashMap<String, Double> convertIntegerToDouble(String attName) {
		HashMap<String, Double> map = new HashMap<String, Double>();
		ArrayList<String> lista = new ArrayList<String>();
		for (CyNode node : nodeList) {

			Integer value = 0;
			try {
				value = cyNodeAttrs.getIntegerAttribute(node.getIdentifier(),
						attName);
				map.put(node.getIdentifier(), (double) value);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				value = 0;
				map.put(node.getIdentifier(), (double) value);
				lista.add(node.toString());
			}

		}

		return map;
	}

	private void saveMissingNodes(ArrayList<String> lista) {
		for (String elem : lista) {
			System.out.println(elem);
		}
		JOptionPane.showMessageDialog(null, "saveMissingNodes");

	}

	private HashMap<String, Double> convertBooleanToDouble(String attName) {
		HashMap<String, Double> map = new HashMap<String, Double>();
		Double x = 0.0;
		for (CyNode node : nodeList) {
			Boolean value = cyNodeAttrs.getBooleanAttribute(
					node.getIdentifier(), attName);
			if (value == true) {
				x = 1.0;
			} else {
				x = 0.0;
			}
			map.put(node.getIdentifier(), x);
		}
		return map;
	}

	private HashMap<String, double[]> getCurrentCoordinates() {

		HashMap<String, double[]> coordMap = new HashMap<String, double[]>();

		List<CyNode> nodeList = Cytoscape.getCyNodesList();

		for (CyNode cynode : nodeList) {
			String gene = cynode.getIdentifier();

			try {
				NodeView nodeView = net.getNodeView(cynode);
				double x = nodeView.getXPosition();
				double y = nodeView.getYPosition();
				// estava saindo de ponta cabeca, entao coloquei um menos no y
				double coordinates[] = { x, -y };
				coordMap.put(gene, coordinates);
			} catch (Exception e) {
				// JOptionPane.showMessageDialog(null,"The network has changed. You need to load it again.","Network error",JOptionPane.ERROR_MESSAGE
				// );
				// e.printStackTrace();

			}

		}

		return coordMap;
	}

	// Get complete hash map for one attribute
	public HashMap<String, double[]> getCompleteHash(String attribute,
			Boolean isLog) {

		HashMap<String, Double> att = new HashMap<String, Double>();

		int type = cyNodeAttrs.getType(attribute);

		if (type == TYPE_BOOLEAN) {
			att = convertBooleanToDouble(attribute);
		} else if (type == TYPE_FLOATING) {
			att = getTypeFloat(attribute);
		} else if (type == TYPE_INTEGER) {
			att = convertIntegerToDouble(attribute);
		} else {
			JOptionPane.showMessageDialog(null,
					"It is not possible to draw attributes of this type");
		}

		HashMap<String, double[]> coor = getCurrentCoordinates();

		HashMap<String, double[]> completHash = new HashMap<String, double[]>();

		for (String key : coor.keySet()) {

			Double terceiraColuna = att.get(key);
			if (terceiraColuna != null) {
				double xyz[] = { coor.get(key)[0], coor.get(key)[1],
						terceiraColuna };
				completHash.put(key, xyz);
			} else {

				if (!isLog) {
					double xyz[] = { coor.get(key)[0], coor.get(key)[1], 0 };   //if is NOT log, the missing are zero
					completHash.put(key, xyz);
				} else {
					double xyz[] = { coor.get(key)[0], coor.get(key)[1], 1 };   //if is log, the missing value 1
					completHash.put(key, xyz);
				}

			}
		}

		HashMap<String, double[]> completHashNormalized = Util.Normalize(completHash);

		return completHashNormalized;

	}

}
