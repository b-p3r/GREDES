package di.uminho.miei.gredes.main;

import java.io.IOException;
import java.util.Vector;

import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import di.uminho.miei.gredes.businesslayer.snmp.Monitor;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
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
		long ifInOctetsLoopback = queryIfNumVar.get(0).getVariable().toLong();
		int ifNumberInt = queryIfNumVar.get(1).getVariable().toInt();

		long pollTime = client.calcMaxPoll(ifInOctetsLoopback);

		System.out.println(pollTime);

		ResponseListener listener = new ResponseListener() {
			public void onResponse(ResponseEvent event) {
				// Always cancel async request when response has been received
				// otherwise a memory leak is created! Not canceling a request
				// immediately can be useful when sending a request to a
				// broadcast
				// address.
				((Snmp) event.getSource()).cancel(event.getRequest(), this);

				System.out.println("Received response PDU is: "
						+ client.getAsTableBulkAssynchronous(query, 1, ifNumberInt, event).toString());

			}
		};
		while (true) {
			Thread.sleep(pollTime);

			client.sendBulkAssynchronous(query, 1, ifNumberInt, listener);
		}

		// System.out.println(queryS.toString());

	}

}
