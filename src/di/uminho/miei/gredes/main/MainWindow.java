package di.uminho.miei.gredes.main;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import di.uminho.miei.gredes.businesslayer.threads.ManagerHelper;
import di.uminho.miei.gredes.businesslayer.threads.ResponseRequestThread;
import di.uminho.miei.gredes.businesslayer.threads.UIUpdateWorker;
import di.uminho.miei.gredes.presentationlayer.components.IfTablePanel;

/**
 * 
 * @author bpereira
 *
 */
public class MainWindow {

	private JFrame frame;

	private static ResponseRequestThread pollthread;

	private static ManagerHelper managerHelper;

	private static IfTablePanel ifTablePanel;

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 */
	public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException {

		/** Janela de aviso de carregamento **/
		JFrame frame = new JFrame();
		JTextField textField = new JTextField(40);
		textField.setEditable(false);

		frame.getContentPane().add(textField, "North");

		frame.pack();

		frame.setVisible(true);
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		textField.setText("Loading...");
		
		managerHelper = new ManagerHelper("udp:127.0.0.1/161");
		managerHelper.preparePolling();

		pollthread = new ResponseRequestThread(managerHelper);

		/**
		 * 
		 */
		SwingUtilities.invokeAndWait(new Runnable() {

			

			@Override
			public void run() {
				try {

					managerHelper.numberInterfacesPolling();
				   				
					managerHelper.setPolltime(3010);


				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

		/**
		 * 
		 */
		EventQueue.invokeAndWait(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow(100);
					window.frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
					    @Override
					    public void windowClosing(WindowEvent windowEvent) {
					    	MainWindow.pollthread.stopPolling();
					    	try {
								MainWindow.managerHelper.stop();
							} catch (IOException e) {
								
							}
					       
					    }
					});
					MainWindow.pollthread.start();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		
		

		UIUpdateWorker uiUpdateWorker = new UIUpdateWorker(managerHelper, ifTablePanel);
		uiUpdateWorker.execute();
	}
	
	

	/**
	 * Create the application.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public MainWindow(int numberPointsShowings) throws IOException, InterruptedException {
		initGUI();

		fillGUI(numberPointsShowings);

	}

	/**
	 * 
	 */
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

	/**
	 * 
	 * @param numberShowingsPoints
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void fillGUI(int numberShowingsPoints) throws IOException, InterruptedException {

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		ifTablePanel = new IfTablePanel(numberShowingsPoints, managerHelper.getInterfacesTotalNumber());

		scrollPane.setViewportView(ifTablePanel);
		GridBagLayout gridBagLayout_1 = (GridBagLayout) ifTablePanel.getLayout();
		gridBagLayout_1.rowWeights = new double[] { 1.0, 0.0 };
		gridBagLayout_1.columnWeights = new double[] { 1.0 };

	}

}
