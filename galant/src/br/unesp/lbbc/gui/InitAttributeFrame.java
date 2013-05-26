package br.unesp.lbbc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.jgoodies.forms.layout.Sizes;

public class InitAttributeFrame extends JFrame {

	/**
	 * 
	 */
	private JToolBar toolBar;
	private JButton btnExport;
	private JSplitPane splitPaneMain;
	private JSplitPane splitPaneLeft; 
	private JPanel panelFunctions;
	private JRadioButton rdbtnCustom1;
	private JLabel lblResolution;
	private JRadioButton rdbtnGaussian;
	private JButton btnDraw;
	private JFormattedTextField frmtdtxtfldLbbcLaboratrio;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfCustom;
	private JTextField tfGaussian;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JListAtt jListAtt;
	private JCheckBox checkBox2D;
	
	private JPanel panelSurface;
	private JLabel lblSmoothness;
	private JTextField tfSmooth;
	private JCheckBox chckbxLog;
	private JRadioButton rdbtnCustom2;
	private JLabel lblExponent;
	private JTextField tfPowerLaw;

		/**
	 * Create the frame.
	 */
	public InitAttributeFrame() {
		setMinimumSize(new Dimension(800, 600));
		setLookAndFeel();
		//setIconImage(Toolkit.getDefaultToolkit().getImage(InitAttributeFrame.class.getResource("/br/unesp/lbbc/main/icone.jpg")));
		setTitle("Galant - Single Attribute Projection");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		initComponents();
		initListeners();
		setVisible(true);
	}

	private void initComponents(){
		
		toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		splitPaneMain = new JSplitPane();
		contentPane.add(splitPaneMain, BorderLayout.CENTER);
		
		splitPaneLeft = new JSplitPane();
		splitPaneLeft.setResizeWeight(1.0);
		splitPaneLeft.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneMain.setLeftComponent(splitPaneLeft);
		
		jListAtt = new JListAtt();
		jListAtt.setSelectedIndex(0);
		JScrollPane scrollPane = new JScrollPane(jListAtt);
		splitPaneLeft.setLeftComponent(scrollPane);
		
		panelFunctions = new JPanel();
		panelFunctions.setMaximumSize(new Dimension(10, 10));
		panelFunctions.setBorder(new TitledBorder(null, "Function", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPaneLeft.setRightComponent(panelFunctions);
		panelFunctions.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.FILL, Sizes.bounded(Sizes.MINIMUM, Sizes.constant("20dlu", true), Sizes.constant("50dlu", true)), 1),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		rdbtnGaussian = new JRadioButton("Gaussian");
		rdbtnGaussian.setSelected(true);
		rdbtnGaussian.setActionCommand("Gaussian");
		buttonGroup.add(rdbtnGaussian);
		panelFunctions.add(rdbtnGaussian, "2, 2");
		
		JLabel lblSigma = new JLabel("Sigma");
		panelFunctions.add(lblSigma, "4, 2, right, default");
		
		tfGaussian = new JTextField();
		tfGaussian.setMinimumSize(new Dimension(50, 22));
		tfGaussian.setText("0.05");
		panelFunctions.add(tfGaussian, "6, 2, fill, default");
		tfGaussian.setColumns(10);
		
		
		rdbtnCustom1 = new JRadioButton("Mov.Average");
		rdbtnCustom1.setActionCommand("Custom1");
		buttonGroup.add(rdbtnCustom1);
		panelFunctions.add(rdbtnCustom1, "2, 4");
		
		checkBox2D = new JCheckBox("2D");
		checkBox2D.setSelected(true);
		
		lblSmoothness = new JLabel("Smoothness");
		panelFunctions.add(lblSmoothness, "4, 4, right, default");
		
		tfSmooth = new JTextField();
		tfSmooth.setToolTipText("Only integer");
		tfSmooth.setText("6");
		panelFunctions.add(tfSmooth, "6, 4, fill, default");
		tfSmooth.setColumns(10);
		tfSmooth.setEnabled(false);
		
		rdbtnCustom2 = new JRadioButton("Power Law");
		rdbtnCustom2.setActionCommand("Custom2");
		buttonGroup.add(rdbtnCustom2);
		panelFunctions.add(rdbtnCustom2, "2, 6");
		
		lblExponent = new JLabel("Exponent");
		panelFunctions.add(lblExponent, "4, 6, right, default");
		
		tfPowerLaw = new JTextField();
		tfPowerLaw.setText("3");
		tfPowerLaw.setEnabled(false);
		panelFunctions.add(tfPowerLaw, "6, 6, fill, default");
		tfPowerLaw.setColumns(10);
		
		chckbxLog = new JCheckBox("log");
		chckbxLog.setEnabled(false);
		chckbxLog.setToolTipText("Select if data are already in log");
		panelFunctions.add(chckbxLog, "2, 8");
		
		lblResolution = new JLabel("Resolution");
		panelFunctions.add(lblResolution, "4, 8, right, default");
		
		tfCustom = new JTextField();
		tfCustom.setMinimumSize(new Dimension(50, 22));
		tfCustom.setText("100");
		panelFunctions.add(tfCustom, "6, 8, fill, default");
		tfCustom.setColumns(10);
		//panelFunctions.add(checkBox2D, "2, 8");
		
		btnDraw = new JButton("DRAW");
		
		panelFunctions.add(btnDraw, "2, 10, 5, 1");
		
		panelSurface = new JPanel();
		splitPaneMain.setRightComponent(panelSurface);
		panelSurface.setLayout(new BorderLayout());
		
		frmtdtxtfldLbbcLaboratrio = new JFormattedTextField();
		frmtdtxtfldLbbcLaboratrio.setFont(new Font("Tahoma", Font.PLAIN, 9));
		frmtdtxtfldLbbcLaboratrio.setText("LBBC - Laborat\u00F3rio de Bioinform\u00E1tica e Biof\u00EDsica Computacional - Universidade Estadual Paulista");
		contentPane.add(frmtdtxtfldLbbcLaboratrio, BorderLayout.SOUTH);

		btnExport = new JButton("");
		btnExport.setToolTipText("Export Image");
		btnExport.setEnabled(false);
		
		btnExport.setIcon(new ImageIcon(InitAttributeFrame.class.getResource("/br/unesp/lbbc/main/export.png")));
		toolBar.add(btnExport);
		
	}
	
	private void initListeners() {
		
		btnDraw.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
			  SurfacePanel sp = new SurfacePanel();
			  String function = buttonGroup.getSelection().getActionCommand();//getFunctionString();
			  String at1 = jListAtt.getAtt();
			  int res = Integer.parseInt(tfCustom.getText());
			  boolean log = chckbxLog.isSelected();
				
				if (function=="Gaussian"){
					double sigma = Double.parseDouble(tfGaussian.getText());
					
					try {
						setSurfacePanel(sp.drawGaussian(at1, null, res, sigma, log));
						btnExport.setEnabled(true);
						
					} catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(null,"Select attribute and draw again ");
						//e1.printStackTrace();
					}	
					
				}
				else if (function=="Custom1"){ //CUSTOM DA ESTHER
					
					double smooth = Double.parseDouble(tfSmooth.getText());
					
					try {
						
						setSurfacePanel(sp.drawCustomEC(at1, null, res, smooth, log));
						btnExport.setEnabled(true);
						
					} catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(null,"Select attribute and draw again ");
						//e1.printStackTrace();
					}
				
				}
				
				else if (function=="Custom2"){ //CUSTOM DO NEY
					
					double smooth = Double.parseDouble(tfPowerLaw.getText());
					
					try {
						
						setSurfacePanel(sp.drawCustom(at1, null, res, smooth, log));
						btnExport.setEnabled(true);
						
					} catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(null,"Select attribute and draw again ");
						//e1.printStackTrace();
					}
				
				}
				else {
					JOptionPane.showMessageDialog(null,"Select Custom or Gaussian");
				}
			 
			}
		});
		
		
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					exportFile();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null,"Error");
				}
			}
		});
		
		rdbtnCustom1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tfSmooth.setEnabled(true);
				tfPowerLaw.setEnabled(false);
				tfGaussian.setEnabled(false);			
				
			}
		});
		
		rdbtnGaussian.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tfSmooth.setEnabled(false);
				tfGaussian.setEnabled(true);
				tfPowerLaw.setEnabled(false);
				
			}
		});
		rdbtnCustom2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tfPowerLaw.setEnabled(true);
				tfGaussian.setEnabled(false);
				tfSmooth.setEnabled(false);
				
			}
		});
		
	}
		
	private void exportFile() throws IOException {
		
		JFileChooser f = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("png","png");
		f.addChoosableFileFilter(filter);
		f.setFileFilter(filter);
		f.setAcceptAllFileFilterUsed(false); 
		f.showSaveDialog(null);
		File fileA = f.getSelectedFile();
		String fileInit = fileA.getAbsolutePath();
		String completNameFile = fileInit+".png";
		SurfacePanel.screenshot(completNameFile);
		
		
	}
	


	private void setSurfacePanel(JPanel surfNew){
		
		panelSurface.removeAll();
		panelSurface.add(surfNew);
		panelSurface.revalidate();
		
	}
	
	private static void setLookAndFeel() {
		
		WindowsLookAndFeel look = new WindowsLookAndFeel();
		try {
			UIManager.setLookAndFeel(look);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getFunctionString() {
		String function = buttonGroup.getSelection().getActionCommand();
		return function;
	}

	
}
