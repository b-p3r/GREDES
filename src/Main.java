import java.io.IOException;
import java.util.List;

import org.snmp4j.smi.OID;

public class Main {

	public static void main(String[] args) throws IOException {
		Monitor client = new Monitor("udp:127.0.0.1/161");

		OID ifNumberScalar = new OID(".1.3.6.1.2.1.2.1.0");

		OID sysUptime = new OID(".1.3.6.1.2.1.1.3");
		// OID ifNumber = new OID(".1.3.6.1.2.1.2.1");

		OID ifIndex = new OID(".1.3.6.1.2.1.2.2.1.1");
		OID ifDescr = new OID(".1.3.6.1.2.1.2.2.1.2");
		OID ifOpStatus = new OID(".1.3.6.1.2.1.2.2.1.8");
		OID ifInOctets = new OID(".1.3.6.1.2.1.2.2.1.10");
		OID ifOutOctets = new OID(".1.3.6.1.2.1.2.2.1.16");
		OID query[] = { sysUptime, ifIndex, ifDescr, ifOpStatus, ifInOctets, ifOutOctets };

		int ifNumberInt = Integer.parseInt(client.getAsString(ifNumberScalar));
		// String ifNumberS = client.getAsString(ifNumber);
		// String ifIndexS = client.getAsString(ifIndex);
		// String ifDescrS = client.getAsString(ifDescr);
		// String ifOpStatusS = client.getAsString(ifDescr);
		// String ifInOctetsS = client.getAsString(ifInOctets);
		// String ifOutOctetsS = client.getAsString(ifOutOctets);

		List<List<String>> queryS = client.getAsStringBulk(query, 1, ifNumberInt);
		System.out.println(queryS.get(0).get(0));

		for (int i = 1; i < queryS.size(); i++) {

			System.out.print("Linha :");

			for (int j = 0; j < queryS.get(i).size(); j++) {

				String string = queryS.get(i).get(j);
				System.out.print(string + "\t");
			}

			System.out.println();

		}

		// System.out.println(ifNumberS);
		// System.out.println(ifIndexS);
		// System.out.println(ifDescrS);
		// System.out.println(ifOpStatusS);
		// System.out.println(ifInOctetsS);
		// System.out.println(ifOutOctetsS);
		System.out.println("########################");
		// System.out.println(client.toString());
		// List<List<String>> lis= client.getTableAsStrings(query);
		//
		// for (List<String> list : lis) {
		// for (String string : list) {
		// System.out.println(string);
		// }
		// }

		// for (List<String> list : queryS) {
		// System.out.println("OBJ "+list.toString());
		// for (String string : list) {
		// System.out.println(string);
		//
		// }
		//
	}

}
