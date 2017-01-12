package di.uminho.miei.gredes.main;

import java.io.IOException;
import java.util.Vector;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import di.uminho.miei.gredes.businesslayer.snmp.Monitor;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;

public class Main {

	public static void main(String[] args) throws IOException {
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
		System.out.println(queryIfNumVar.get(0).getVariable().toLong());
		int ifNumberInt = queryIfNumVar.get(1).getVariable().toInt();

		IfTableInfo queryS = client.getAsStringBulk(query, 1, ifNumberInt);
		System.out.println(queryS.toString());

	}

}
