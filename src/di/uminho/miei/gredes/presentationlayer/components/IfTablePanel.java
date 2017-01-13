package di.uminho.miei.gredes.presentationlayer.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;

public class IfTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6862255045319901747L;
	private int numPointsShowing;
	private int rowCount;

	/**
	 * Create the panel.
	 */
	public IfTablePanel(int numPointsShowing, int rowCount) {
		this.numPointsShowing = numPointsShowing;
		this.rowCount = rowCount;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel border = new JPanel();
		border.setBorder(new TitledBorder(null, "Tabela", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_border = new GridBagConstraints();
		gbc_border.fill = GridBagConstraints.BOTH;
		gbc_border.insets = new Insets(0, 0, 5, 0);
		gbc_border.gridx = 0;
		gbc_border.gridy = 0;
		add(border, gbc_border);
		GridBagLayout gbl_border = new GridBagLayout();
		gbl_border.columnWidths = new int[] { 0, 0 };
		gbl_border.rowHeights = new int[] { 0, 0 };
		gbl_border.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_border.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		border.setLayout(gbl_border);

		JPanel panelColumnId = new JPanel();
		GridBagConstraints gbc_panelColumnId = new GridBagConstraints();
		gbc_panelColumnId.fill = GridBagConstraints.BOTH;
		gbc_panelColumnId.gridx = 0;
		gbc_panelColumnId.gridy = 0;
		border.add(panelColumnId, gbc_panelColumnId);
		GridBagLayout gbl_panelColumnId = new GridBagLayout();
		gbl_panelColumnId.columnWidths = new int[] { 64, 114, 0 };
		gbl_panelColumnId.rowHeights = new int[] { 15, 0 };
		gbl_panelColumnId.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelColumnId.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelColumnId.setLayout(gbl_panelColumnId);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Estado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panelColumnId.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 47, 47, 47, 0 };
		gbl_panel_1.rowHeights = new int[] { 15, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblIfIndex = new JLabel("Index");
		GridBagConstraints gbc_lblIfIndex = new GridBagConstraints();
		gbc_lblIfIndex.fill = GridBagConstraints.BOTH;
		gbc_lblIfIndex.insets = new Insets(0, 0, 0, 5);
		gbc_lblIfIndex.gridx = 0;
		gbc_lblIfIndex.gridy = 0;
		panel_1.add(lblIfIndex, gbc_lblIfIndex);
		lblIfIndex.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblIfDescr = new JLabel("Name");
		GridBagConstraints gbc_lblIfDescr = new GridBagConstraints();
		gbc_lblIfDescr.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblIfDescr.insets = new Insets(0, 0, 0, 5);
		gbc_lblIfDescr.gridx = 1;
		gbc_lblIfDescr.gridy = 0;
		panel_1.add(lblIfDescr, gbc_lblIfDescr);
		lblIfDescr.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblIfStatus = new JLabel("Status");
		GridBagConstraints gbc_lblIfStatus = new GridBagConstraints();
		gbc_lblIfStatus.fill = GridBagConstraints.VERTICAL;
		gbc_lblIfStatus.gridx = 2;
		gbc_lblIfStatus.gridy = 0;
		panel_1.add(lblIfStatus, gbc_lblIfStatus);
		lblIfStatus.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Gráficos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 0;
		panelColumnId.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 114, 114, 0 };
		gbl_panel_2.rowHeights = new int[] { 15, 0 };
		gbl_panel_2.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JLabel lblIfInOctects = new JLabel("InOctects");
		GridBagConstraints gbc_lblIfInOctects = new GridBagConstraints();
		gbc_lblIfInOctects.fill = GridBagConstraints.BOTH;
		gbc_lblIfInOctects.insets = new Insets(0, 0, 0, 5);
		gbc_lblIfInOctects.gridx = 0;
		gbc_lblIfInOctects.gridy = 0;
		panel_2.add(lblIfInOctects, gbc_lblIfInOctects);
		lblIfInOctects.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblIfOutOctects = new JLabel("OutOctects");
		GridBagConstraints gbc_lblIfOutOctects = new GridBagConstraints();
		gbc_lblIfOutOctects.fill = GridBagConstraints.BOTH;
		gbc_lblIfOutOctects.gridx = 1;
		gbc_lblIfOutOctects.gridy = 0;
		panel_2.add(lblIfOutOctects, gbc_lblIfOutOctects);
		lblIfOutOctects.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridLayout grid = new GridLayout(0, 1, 0, 0);
		panel.setLayout(grid);

		for (int i = 0; i < this.rowCount; i++) {
			panel.add(new IfRow(this.numPointsShowing));
		}

	}

	public IfRow getIfRow(int row) {

		return (IfRow) ((JPanel) this.getComponent(1)).getComponent(row);
	}

	public void setIfTableInfo(IfTableInfo queryS, long start) {

		int size = queryS.getIfList().size();
		long sysUptime = queryS.getSysUptime();

		for (int i = 0; i < size; i++) {
			int index = queryS.getIfList().get(i).getIfIndex();
			String desc = queryS.getIfList().get(i).getIfDescr();
			int status = queryS.getIfList().get(i).getIfOpStatus();
			long inoctets = queryS.getIfList().get(i).getIfInOctets();
			long outoctets = queryS.getIfList().get(i).getIfOutOctets();

			getIfRow(i).updateRow(index, desc, status, (double) (sysUptime - start), (double) inoctets,
					(double) outoctets);
		}

	}

}
