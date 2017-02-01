package di.uminho.miei.gredes.presentationlayer.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import di.uminho.miei.gredes.presentationlayer.components.chart.Chart;

/**
 * 
 * @author bpereira
 *
 */
public class IfRow extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2257989872672966093L;
	private JLabel labelStatus;
	private JLabel labelName;
	private JLabel labelIndex;
	private int numPointsShowing;
	private Chart chartInOctects;
	private Chart chartOutOctects;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;

	/**
	 * Create the panel.
	 */
	public IfRow(int numPointsShowing) {
		this.setNumPointsShowing(numPointsShowing);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 44, 90, 90, 0 };
		gridBagLayout.rowHeights = new int[] { 300, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 47, 47, 47, 0 };
		gbl_panel.rowHeights = new int[] { 396, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		labelIndex = new JLabel("Index");
		GridBagConstraints gbc_labelIndex = new GridBagConstraints();
		gbc_labelIndex.fill = GridBagConstraints.BOTH;
		gbc_labelIndex.insets = new Insets(0, 0, 0, 5);
		gbc_labelIndex.gridx = 0;
		gbc_labelIndex.gridy = 0;
		panel.add(labelIndex, gbc_labelIndex);
		labelIndex.setHorizontalAlignment(SwingConstants.CENTER);

		labelName = new JLabel("Name");
		GridBagConstraints gbc_labelName = new GridBagConstraints();
		gbc_labelName.fill = GridBagConstraints.BOTH;
		gbc_labelName.insets = new Insets(0, 0, 0, 5);
		gbc_labelName.gridx = 1;
		gbc_labelName.gridy = 0;
		panel.add(labelName, gbc_labelName);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);

		labelStatus = new JLabel("Status");
		GridBagConstraints gbc_labelStatus = new GridBagConstraints();
		gbc_labelStatus.fill = GridBagConstraints.BOTH;
		gbc_labelStatus.gridx = 2;
		gbc_labelStatus.gridy = 0;
		panel.add(labelStatus, gbc_labelStatus);
		labelStatus.setHorizontalAlignment(SwingConstants.CENTER);

		scrollPane_1 = new JScrollPane();
		
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 0;
		add(scrollPane_1, gbc_scrollPane_1);

		chartInOctects = new Chart(numPointsShowing);
		scrollPane_1.setViewportView(chartInOctects);
		chartInOctects.setLayout(new GridLayout(1, 0, 0, 0));

		scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 2;
		gbc_scrollPane_2.gridy = 0;
		add(scrollPane_2, gbc_scrollPane_2);
		chartOutOctects = new Chart(numPointsShowing);
		
		scrollPane_2.setViewportView(chartOutOctects);
		chartOutOctects.setLayout(new GridLayout(1, 0, 0, 0));

	}

	/**
	 * 
	 * @param index
	 * @param name
	 * @param status
	 * @param sysUptime
	 * @param inoctets
	 * @param outoctets
	 */
	public void updateRow(int index, String name, int status, double sysUptime, double inoctets, double outoctets) {
		
		chartOutOctects.addPoint(sysUptime, outoctets);
		chartInOctects.addPoint(sysUptime, inoctets);
		labelStatus.setText("" + status);
		labelName.setText(name);
		labelIndex.setText("" + index);

	}

	/**
	 * 
	 * @return
	 */
	public Chart getChartInOctects() {
		return chartInOctects;
	}

	/**
	 * 
	 * @param chartInOctects
	 */
	public void setChartInOctects(Chart chartInOctects) {
		this.chartInOctects = chartInOctects;
	}

	/**
	 * 
	 * @return
	 */
	public Chart getChartOutOctects() {
		return chartOutOctects;
	}

	/**
	 * 
	 * @param chartOutOctects
	 */
	public void setChartOutOctects(Chart chartOutOctects) {
		this.chartOutOctects = chartOutOctects;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumPointsShowing() {
		return numPointsShowing;
	}

	/**
	 * 
	 * @param numPointsShowing
	 */
	public void setNumPointsShowing(int numPointsShowing) {
		this.numPointsShowing = numPointsShowing;
	}

}
