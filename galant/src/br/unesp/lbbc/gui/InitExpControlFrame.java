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
import javax.swing.SwingConstants;
import java.awt.Color;

public class InitExpControlFrame extends JFrame {

	/**
	 * 
	 */
	private JToolBar toolBar;
	private JButton btnExport;
	private JSplitPane splitPaneMain;
	private JSplitPane splitPaneLeft; 
	private JSplitPane splitPanelLeftTop;
	private JPanel panelFunctions;
	private JRadioButton rdbtnCustom;
	private JLabel lblResolution;
	private JRadioButton rdbtnGaussian;
	private JButton btnDraw;
	private JFormattedTextField frmtdtxtfldLbbcLaboratrio;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfCustom;
	private JTextField tfGaussian;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JListAtt jListAttExp;
	private JListAtt jListAttControl;
	private JCheckBox checkBox2D;
	
	private JPanel panelSurface;
	private JLabel lblSelect;
	private JLabel lblSelectTheExperiment;
	private JCheckBox chckbxLog;
	private JLabel lblSmoothness;
	private JTextField tfSmooth;


	/**
	 * Create the frame.
	 */
	public InitExpControlFrame() {
		setMinimumSize(new Dimension(800, 600));
		setLookAndFeel();
		//setIconImage(Toolkit.getDefaultToolkit().getImage(InitExpControlFrame.class.getResource("/br/unesp/lbbc/main/icone.jpg")));
		setTitle("Galant - Experiment X Control");
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
		
		splitPanelLeftTop = new JSplitPane();
		splitPanelLeftTop.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPanelLeftTop.setResizeWeight(0.5);
		splitPaneLeft.setTopComponent(splitPanelLeftTop);
		
		jListAttExp = new JListAtt();
		JScrollPane scrollPane1 = new JScrollPane(jListAttExp);
		splitPanelLeftTop.setTopComponent(scrollPane1);
		
		jListAttControl = new JListAtt();
		JScrollPane scrollPane2 = new JScrollPane(jListAttControl);
		splitPanelLeftTop.setBottomComponent(scrollPane2);
		
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
				ColumnSpec.decode("min:grow"),
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
		
		
		rdbtnCustom = new JRadioButton("Custom");
		rdbtnCustom.setSelected(true);
		rdbtnCustom.setActionCommand("Custom");
		buttonGroup.add(rdbtnCustom);
		panelFunctions.add(rdbtnCustom, "2, 2");
		
		lblResolution = new JLabel("Resolution");
		panelFunctions.add(lblResolution, "4, 2, right, default");
		
		tfCustom = new JTextField();
		tfCustom.setMinimumSize(new Dimension(50, 22));
		tfCustom.setText("100");
		panelFunctions.add(tfCustom, "6, 2, fill, default");
		tfCustom.setColumns(10);
		
		lblSmoothness = new JLabel("Smoothness");
		panelFunctions.add(lblSmoothness, "4, 4, right, default");
		
		tfSmooth = new JTextField();
		tfSmooth.setToolTipText("Only integer");
		tfSmooth.setText("1.5");
		panelFunctions.add(tfSmooth, "6, 4, fill, default");
		tfSmooth.setColumns(10);
		
		rdbtnGaussian = new JRadioButton("Gaussian");
		rdbtnGaussian.setActionCommand("Gaussian");
		buttonGroup.add(rdbtnGaussian);
		panelFunctions.add(rdbtnGaussian, "2, 6");
		
		JLabel lblSigma = new JLabel("Sigma");
		panelFunctions.add(lblSigma, "4, 6, right, default");
		
		tfGaussian = new JTextField();
		tfGaussian.setMinimumSize(new Dimension(50, 22));
		tfGaussian.setText("0.05");
		tfGaussian.setEnabled(false);
		
		panelFunctions.add(tfGaussian, "6, 6, fill, default");
		tfGaussian.setColumns(10);
		
		checkBox2D = new JCheckBox("2D");
		checkBox2D.setSelected(true);
		//panelFunctions.add(checkBox2D, "2, 8");
		
		chckbxLog = new JCheckBox("log");
		panelFunctions.add(chckbxLog, "4, 8");
		
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
		btnExport.setToolTipText("Export Image JPG");
		
		btnExport.setIcon(new ImageIcon(InitExpControlFrame.class.getResource("/br/unesp/lbbc/main/export.png")));
		toolBar.add(btnExport);
		btnExport.setEnabled(false);
		
		lblSelect = new JLabel("                             ");
		lblSelect.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelect.setFont(new Font("Tahoma", Font.PLAIN, 16));
		toolBar.add(lblSelect);
		
		lblSelectTheExperiment = new JLabel("Select the experiment and the control respectively");
		lblSelectTheExperiment.setForeground(Color.BLUE);
		lblSelectTheExperiment.setFont(new Font("Tahoma", Font.BOLD, 16));
		toolBar.add(lblSelectTheExperiment);
	}
	
	private void initListeners() {
		btnDraw.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SurfacePanel sp = new SurfacePanel();
				String function = buttonGroup.getSelection().getActionCommand();
				String at1 = jListAttExp.getAtt();
				String at2 = jListAttControl.getAtt();
				
				
				if (function=="Gaussian"){
										
					try {
						double sigma = Double.parseDouble(tfGaussian.getText());
						int te = Integer.parseInt(tfCustom.getText());
						
						setSurfacePanel(sp.drawGaussian(at1,at2,te,sigma,chckbxLog.isSelected()));
						btnExport.setEnabled(true);
						
					} catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(null,"Select attribute and draw again ");
						//e1.printStackTrace();
					}	
				}
				else if (function=="Custom"){
					double smooth = Double.parseDouble(tfSmooth.getText());
					
					try {
						setSurfacePanel(sp.drawCustom(at1,at2, Integer.parseInt(tfCustom.getText()),smooth,chckbxLog.isSelected()));
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
		
		
		rdbtnCustom.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tfSmooth.setEnabled(true);
				tfGaussian.setEnabled(false);			
				
			}
		});
		
		rdbtnGaussian.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tfSmooth.setEnabled(false);
				tfGaussian.setEnabled(true);
				
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

	public void setSurfacePanel(JPanel surfNew){
		
		panelSurface.removeAll();
		panelSurface.add(surfNew);
		panelSurface.revalidate();
		
	}
	
	public static void setLookAndFeel() {
		
		WindowsLookAndFeel look = new WindowsLookAndFeel();
		try {
			UIManager.setLookAndFeel(look);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getFunctionString() {
		String function = buttonGroup.getSelection().getActionCommand();
		return function;
	}
}
