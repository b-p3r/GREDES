package di.uminho.miei.gredes.businesslayer.threads;

import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;

import di.uminho.miei.gredes.businesslayer.snmp.Monitor;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableRegistry;

public class ResponseThread extends Thread {

	private Monitor monitor;
	private ResponseListener responseListener;
	private IfTableRegistry registry;
	private int ifNumberInt;

	public ResponseThread(Monitor monitor, IfTableRegistry registry, int ifNumberInt) {
		super();
		this.monitor = monitor;
		this.registry = registry;
		this.ifNumberInt = ifNumberInt;

	}

	@Override
	public void run() {

		

		while (true) {

			this.setResponseListener(new ResponseListener() {

				@Override
				public void onResponse(ResponseEvent event) {
					((Snmp) event.getSource()).cancel(event.getRequest(), this);
					IfTableInfo table = monitor.getAsTableBulkAssynchronous(null, 1, ifNumberInt, event);
					try {
						registry.add(table.clone());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

		}
	}

	public ResponseListener getResponseListener() {
		return responseListener;
	}

	public void setResponseListener(ResponseListener responseListener) {
		this.responseListener = responseListener;
	}

}
