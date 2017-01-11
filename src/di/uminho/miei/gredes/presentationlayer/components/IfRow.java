package di.uminho.miei.gredes.presentationlayer.components;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class IfRow extends JPanel {

	private JLabel labelOutOctects;
	private JLabel labelInOctects;
	private JLabel labelStatus;
	private JLabel labelName;
	private JLabel labelIndex;

	/**
	 * Create the panel.
	 */
	public IfRow() {
		setLayout(new GridLayout(1, 0, 0, 0));

		labelIndex = new JLabel("Index");
		labelIndex.setHorizontalAlignment(SwingConstants.CENTER);
		add(labelIndex);

		labelName = new JLabel("Name");
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		add(labelName);

		labelStatus = new JLabel("Status");
		labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
		add(labelStatus);

		labelInOctects = new JLabel("InOctects");
		labelInOctects.setHorizontalAlignment(SwingConstants.CENTER);
		add(labelInOctects);

		labelOutOctects = new JLabel("OutOctects");
		labelOutOctects.setHorizontalAlignment(SwingConstants.CENTER);
		add(labelOutOctects);

	}

	public void updateLabels(String index, String name, String status, String inOctects, String outOctects) {
		labelOutOctects.setText(outOctects);
		labelInOctects.setText(inOctects);
		labelStatus.setText(status);
		labelName.setText(name);
		labelIndex.setText(index);

	}

}
