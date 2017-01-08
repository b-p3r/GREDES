import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class IfTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6862255045319901747L;

	/**
	 * Create the panel.
	 */
	public IfTablePanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel border = new JPanel();
		border.setBorder(new TitledBorder(null, "Table", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_border = new GridBagConstraints();
		gbc_border.fill = GridBagConstraints.BOTH;
		gbc_border.insets = new Insets(0, 0, 5, 0);
		gbc_border.gridx = 0;
		gbc_border.gridy = 0;
		add(border, gbc_border);
		GridBagLayout gbl_border = new GridBagLayout();
		gbl_border.columnWidths = new int[]{0, 0};
		gbl_border.rowHeights = new int[]{0, 0};
		gbl_border.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_border.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		border.setLayout(gbl_border);
		
		JPanel panelColumnId = new JPanel();
		GridBagConstraints gbc_panelColumnId = new GridBagConstraints();
		gbc_panelColumnId.fill = GridBagConstraints.BOTH;
		gbc_panelColumnId.gridx = 0;
		gbc_panelColumnId.gridy = 0;
		border.add(panelColumnId, gbc_panelColumnId);
		panelColumnId.setLayout(new GridLayout(0, 5, 0, 0));
		
		JLabel lblIfIndex = new JLabel("Index");
		lblIfIndex.setHorizontalAlignment(SwingConstants.CENTER);
		panelColumnId.add(lblIfIndex);
		
		JLabel lblIfDescr = new JLabel("Name");
		lblIfDescr.setHorizontalAlignment(SwingConstants.CENTER);
		panelColumnId.add(lblIfDescr);
		
		JLabel lblIfStatus = new JLabel("Status");
		lblIfStatus.setHorizontalAlignment(SwingConstants.CENTER);
		panelColumnId.add(lblIfStatus);
		
		JLabel lblIfInOctects = new JLabel("InOctects");
		lblIfInOctects.setHorizontalAlignment(SwingConstants.CENTER);
		panelColumnId.add(lblIfInOctects);
		
		JLabel lblIfOutOctects = new JLabel("OutOctects");
		lblIfOutOctects.setHorizontalAlignment(SwingConstants.CENTER);
		panelColumnId.add(lblIfOutOctects);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridLayout grid = new GridLayout(0, 1, 0, 0);
		panel.setLayout(grid);
		
		for (int i = 0; i < 5; i++) {
			panel.add(new IfRow());
		}
		
		IfRow ifRow = getIfRow(4);
		
		
		
		ifRow.updateLabels("1", "1", "1", "1", "1");


	}

	public IfRow getIfRow(int row) {

		return (IfRow)((JPanel)this.getComponent(1)).getComponent(row);
	}

}
