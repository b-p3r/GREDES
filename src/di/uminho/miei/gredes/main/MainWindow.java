package di.uminho.miei.gredes.main;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import di.uminho.miei.gredes.businesslayer.snmp.Monitor;
import di.uminho.miei.gredes.presentationlayer.components.IfTablePanel;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;

public class MainWindow {

	private JFrame frame;
	private long start;
	private TreeSet<IfTableInfo> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initGUI();

		initMonitor();

	}

	private void initGUI() {
		frame = new JFrame();
		frame.setBounds(100, 100, 664, 434);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

	}

	private void initMonitor() {
		this.start = 0;

		Monitor client = new Monitor("udp:127.0.0.1/161");

		OID ifNumberScalar = new OID(".1.3.6.1.2.1.2.1.0");
		OID sysUptimeScalar = new OID(".1.3.6.1.2.1.1.3.0");

		OID sysUptime = new OID(".1.3.6.1.2.1.1.3");

		OID ifIndex = new OID(".1.3.6.1.2.1.2.2.1.1");
		OID ifDescr = new OID(".1.3.6.1.2.1.2.2.1.2");
		OID ifOpStatus = new OID(".1.3.6.1.2.1.2.2.1.8");
		OID ifInOctets = new OID(".1.3.6.1.2.1.2.2.1.10");
		OID ifOutOctets = new OID(".1.3.6.1.2.1.2.2.1.16");
		OID query[] = { sysUptime, ifIndex, ifDescr, ifOpStatus, ifInOctets, ifOutOctets };
		OID queryIfNum[] = { sysUptimeScalar, ifNumberScalar };
		Vector<? extends VariableBinding> queryIfNumVar;
		list = new TreeSet<>();
		list.add(new IfTableInfo());
		try {

			queryIfNumVar = client.getAsVar(queryIfNum);

			this.start = queryIfNumVar.get(0).getVariable().toLong();
			System.out.println(this.start);
			int ifNumberInt = queryIfNumVar.get(1).getVariable().toInt();

			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			frame.getContentPane().add(scrollPane, gbc_scrollPane);
			IfTablePanel ifTablePanel = new IfTablePanel(50, ifNumberInt);
			scrollPane.setViewportView(ifTablePanel);
			GridBagLayout gridBagLayout_1 = (GridBagLayout) ifTablePanel.getLayout();
			gridBagLayout_1.rowWeights = new double[] { 1.0, 0.0 };
			gridBagLayout_1.columnWeights = new double[] { 1.0 };

			Timer timer = new Timer(true);
			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					IfTableInfo queryS;
					try {
						queryS = client.getAsStringBulk(query, 1, ifNumberInt);
						// System.out.println(queryS.toString());
						//System.out.println(MainWindow.this.list.first().getIfList().size());
						ifTablePanel.setIfTableInfo(queryS, MainWindow.this.start, MainWindow.this.list.pollFirst().getIfList());
						list.add(queryS);

					} catch (IOException e) {
						// TODO Auto-generated catch
						// block
						e.printStackTrace();
					}

				}

			};
			// Every 20 milliseconds a new value is collected.
			timer.schedule(task, 1000, 1000);

		} catch (IOException e) {
			// TODO Auto-generated catch
			// block
			System.out.println(e.getMessage());

		}

	}

}
