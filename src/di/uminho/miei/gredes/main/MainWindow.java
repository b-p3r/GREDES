package di.uminho.miei.gredes.main;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import di.uminho.miei.gredes.businesslayer.snmp.Monitor;
import di.uminho.miei.gredes.presentationlayer.components.IfTablePanel;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableRegistry;

public class MainWindow {

	private JFrame frame;
	private long start;
	private IfTableRegistry registry;
	private long polltime;
	private IfTablePanel ifTablePanel;

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
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public MainWindow() throws IOException, InterruptedException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void initialize() throws IOException, InterruptedException {
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

	private void initMonitor() throws IOException, InterruptedException {

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
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

		Vector<? extends VariableBinding> queryIfNumVar = client.getAsVar(queryIfNum);

		this.start = queryIfNumVar.get(0).getVariable().toLong();
		System.out.println(this.start);
		int ifNumberInt = queryIfNumVar.get(1).getVariable().toInt();
		ifTablePanel = new IfTablePanel(50, ifNumberInt);

		polltime = client.calcMaxPoll(this.start);

		scrollPane.setViewportView(ifTablePanel);
		GridBagLayout gridBagLayout_1 = (GridBagLayout) ifTablePanel.getLayout();
		gridBagLayout_1.rowWeights = new double[] { 1.0, 0.0 };
		gridBagLayout_1.columnWeights = new double[] { 1.0 };

		registry = new IfTableRegistry();

		// new UIUpdateThread(registry, ifTablePanel, this.start).start();

		ResponseListener listener = new ResponseListener() {
			public void onResponse(ResponseEvent event) {

				((Snmp) event.getSource()).cancel(event.getRequest(), this);
				IfTableInfo table = client.getAsTableBulkAssynchronous(query, 1, ifNumberInt, event);
				try {
					registry.add(table.clone());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// System.out.println("Received response PDU is: " +
				// table.toString());

			}
		};

		Timer timer = new Timer(true);
		System.out.println(this.polltime);
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				try {
					client.sendBulkAssynchronous(query, 1, ifNumberInt, listener);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		// Every 20 milliseconds a new value is collected.
		timer.schedule(task, 0, this.polltime);

		Timer timer2 = new Timer(true);

		TimerTask task2 = new TimerTask() {

			@Override
			public void run() {
				System.out.println("Received response PDU is:" + registry.size());
				if (registry.size() > 10) {
					IfTableInfo ifTableInfo = null;
					try {
						ifTableInfo = registry.pollFirst();
						System.out.println("Received response PDU is: " + ifTableInfo.toString());

						ifTablePanel.setIfTableInfo(registry.first(), MainWindow.this.start, ifTableInfo.getIfList());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		};
		// Every 20 milliseconds a new value is collected.
		timer2.schedule(task2, 4000, 1000);

	}

}
