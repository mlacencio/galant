package br.unesp.lbbc.main;

import giny.view.NodeView;

import java.util.List;

import javax.swing.JPanel;

import cytoscape.CyNetwork;
import cytoscape.ding.DingNetworkView;
import cytoscape.layout.CyLayoutAlgorithm;
import cytoscape.layout.LayoutProperties;
import cytoscape.layout.Tunable;
import cytoscape.task.TaskMonitor;
import cytoscape.view.CyNetworkView;

public class GalantView extends DingNetworkView {
LayoutProperties layoutProperties;
	
	
	public GalantView(CyNetwork arg0, String arg1) {
		super(arg0, arg1);
		MyLayout layout = new MyLayout();
		layoutProperties = new LayoutProperties(layout.getName());
		layoutProperties.add(new Tunable("groupcount", "Number of random groups ",
				Tunable.INTEGER, new Integer(2)));
		

	}
	
	

	class MyLayout implements CyLayoutAlgorithm{

		@Override
		public void doLayout() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void doLayout(CyNetworkView arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void doLayout(CyNetworkView arg0, TaskMonitor arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<String> getInitialAttributeList() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			return "MyLayout";
		}

		@Override
		public LayoutProperties getSettings() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public JPanel getSettingsPanel() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void halt() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void lockNode(NodeView arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void lockNodes(NodeView[] arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void revertSettings() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setLayoutAttribute(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setSelectedOnly(boolean arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public byte[] supportsEdgeAttributes() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public byte[] supportsNodeAttributes() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean supportsSelectedOnly() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void unlockAllNodes() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unlockNode(NodeView arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updateSettings() {
			// TODO Auto-generated method stub
			
		}
		
		
	}

}
