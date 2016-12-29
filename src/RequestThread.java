import java.io.IOException;
import java.util.List;

import org.snmp4j.PDU;
import org.snmp4j.smi.OID;

public class RequestThread extends Thread {
	
	
	private Monitor monitor;

	public RequestThread(Monitor monitor) {
		super();
		this.monitor = monitor;
	}

	@Override
	public void run() {
		OID ifNumberScalar = new OID(".1.3.6.1.2.1.2.1.0");

		OID sysUptime = new OID(".1.3.6.1.2.1.1.3");
		

		OID ifIndex = new OID(".1.3.6.1.2.1.2.2.1.1");
		OID ifDescr = new OID(".1.3.6.1.2.1.2.2.1.2");
		OID ifOpStatus = new OID(".1.3.6.1.2.1.2.2.1.8");
		OID ifInOctets = new OID(".1.3.6.1.2.1.2.2.1.10");
		OID ifOutOctets = new OID(".1.3.6.1.2.1.2.2.1.16");
		OID query[] = { sysUptime, ifIndex, ifDescr, ifOpStatus, ifInOctets, ifOutOctets };

		int ifNumberInt = 0;
;		try {
			ifNumberInt = Integer.parseInt(monitor.getAsString(ifNumberScalar));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
		try {
			List<List<String>> queryS = monitor.getAsStringBulk(query, 1, ifNumberInt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (true) {
			
			PDU toSend = monitor.getPDUGetBulk(query, 1, ifNumberInt);
			try {
				monitor.getSnmp().send(toSend, monitor.getTarget());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//esperar
			
			//start
			
			//enviar PDU
			
		}
	}
	
	
	
	
	
	
	

}
