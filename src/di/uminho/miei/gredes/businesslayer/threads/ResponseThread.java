package di.uminho.miei.gredes.businesslayer.threads;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;

public class ResponseThread extends Thread implements ResponseListener {

	/**
	 * 
	 */
	private ManagerHelper manageHelper;
	private Timer timer;
	private TimerTask task;

	/**
	 * 
	 * @param managerHelper
	 */
	public ResponseThread(ManagerHelper managerHelper) {
		super();
		this.manageHelper = managerHelper;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

		 timer = new Timer(true);
		
		 task = new TimerTask() {
		
		 @Override
		 public void run() {
		 try {
		 ResponseThread.this.manageHelper.bulkOctectsPollingRequest(ResponseThread.this);
		 } catch (Exception e) {
		 // //TODO:
		 }
		 }
		
		 };
		
		 timer.schedule(task, 0, 1000);
		
		// TODO: Adicionar timer cancel
//		try {
//
//			while (true) {
//
//				Thread.sleep(1000);
//
//				System.out.println("2 . ###################");
//
//				this.manageHelper.bulkOctectsPollingRequest(this);
//
//				System.out.println("3 . ###################");
//
//			}
//
//		} catch (Exception e) {
//			// TODO:
//		}
//		;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.snmp4j.event.ResponseListener#onResponse(org.snmp4j.event.
	 * ResponseEvent)
	 */
	@Override
	public void onResponse(ResponseEvent event) {

		((Snmp) event.getSource()).cancel(event.getRequest(), this);
		try {
			manageHelper.bulkOctectsPollingResponse(event);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block

		}

	}

}
