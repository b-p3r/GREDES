package di.uminho.miei.gredes.businesslayer.threads;

import java.io.IOException;

import org.snmp4j.smi.OID;

import di.uminho.miei.gredes.businesslayer.snmp.Monitor;

public class RequestThread extends Thread {

	private Monitor monitor;

	private int ifNumberInt;

	public RequestThread(Monitor monitor, int ifNumberInt) {
		super();
		this.monitor = monitor;
		this.ifNumberInt = ifNumberInt;
	}

	@Override
	public void run() {

		OID sysUptime = new OID(".1.3.6.1.2.1.1.3");

		OID ifIndex = new OID(".1.3.6.1.2.1.2.2.1.1");
		OID ifDescr = new OID(".1.3.6.1.2.1.2.2.1.2");
		OID ifOpStatus = new OID(".1.3.6.1.2.1.2.2.1.8");
		OID ifInOctets = new OID(".1.3.6.1.2.1.2.2.1.10");
		OID ifOutOctets = new OID(".1.3.6.1.2.1.2.2.1.16");

		OID query[] = { sysUptime, ifIndex, ifDescr, ifOpStatus, ifInOctets, ifOutOctets };

		while (true) {

			try {
				monitor.sendBulkAssynchronous(query, 1, ifNumberInt, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
