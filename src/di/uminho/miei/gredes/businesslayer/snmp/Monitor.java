package di.uminho.miei.gredes.businesslayer.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import di.uminho.miei.gredes.presentationlayer.structures.IfRowInfo;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;

public class Monitor {

	private static final int TOTALCOLUMNS = 5;

	private static final int SYSUPTIME = 0;

	private static final int IFINDEX = 1;

	private static final int IFDESCR = 2;

	private static final int IFOPSTATUS = 3;

	private static final int IFINOCTETS = 4;

	private static final int IFOUTOCTETS = 5;

	private String address;

	private Snmp snmp;

	public Monitor(String address) {
		super();
		this.address = address;
		try {
			start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Since snmp4j relies on asynch req/resp we need a listener
	// for responses which should be closed
	public void stop() throws IOException {
		snmp.close();
	}

	@SuppressWarnings("unchecked")
	private void start() throws IOException {
		@SuppressWarnings("rawtypes")
		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		// Do not forget this line!
		transport.listen();
	}

	public String getAsString(OID oid) throws IOException {
		ResponseEvent event = get(getPDUGet(new OID[] { oid }));
		return event.getResponse().get(0).getVariable().toString();
	}

	public Vector<? extends VariableBinding> getAsVarSynchronous(OID oid[]) throws IOException {
		ResponseEvent event = get(getPDUGet(oid));
		return event.getResponse().getVariableBindings();
	}

	public IfTableInfo getAsStringBulk(OID[] oid, int nonRepeaters, int maxRepetitions) throws IOException {
		ResponseEvent event = get(getPDUGetBulk(oid, nonRepeaters, maxRepetitions));

		IfTableInfo ifTableInfo = new IfTableInfo();
		ArrayList<IfRowInfo> tmp = new ArrayList<>();

		Vector<? extends VariableBinding> var = event.getResponse().getVariableBindings();
		ifTableInfo.setSysUptime(var.get(SYSUPTIME).getVariable().toLong());

		for (int j = 0; j < (maxRepetitions * TOTALCOLUMNS); j += TOTALCOLUMNS) {

			int index = var.get(IFINDEX + j).getVariable().toInt();
			String descr = var.get(IFDESCR + j).getVariable().toString();
			int status = var.get(IFOPSTATUS + j).getVariable().toInt();

			long inOctets = var.get(IFINOCTETS + j).getVariable().toLong();
			long outOctets = var.get(IFOUTOCTETS + j).getVariable().toLong();
			IfRowInfo tmprow = new IfRowInfo(index, descr, status, inOctets, outOctets);
			tmp.add(tmprow);

		}

		ifTableInfo.setIfList(tmp);

		return ifTableInfo;
	}

	public void sendBulkAssynchronous(OID[] oid, int nonRepeaters, int maxRepetitions, ResponseListener listener)
			throws IOException {

		snmp.send(getPDUGetBulk(oid, nonRepeaters, maxRepetitions), getTarget(), null, listener);

	}

	

	public IfTableInfo getAsTableBulkAssynchronous(int nonRepeaters, int maxRepetitions, ResponseEvent event) {

		IfTableInfo ifTableInfo = new IfTableInfo();
		ArrayList<IfRowInfo> tmp = new ArrayList<>();
		
		

		Vector<? extends VariableBinding> var = event.getResponse().getVariableBindings();
		System.out.println("EVENT :" + event);
		ifTableInfo.setSysUptime(var.get(SYSUPTIME).getVariable().toLong());

		for (int j = 0; j < (maxRepetitions * TOTALCOLUMNS); j += TOTALCOLUMNS) {

			int index = var.get(IFINDEX + j).getVariable().toInt();
			String descr = var.get(IFDESCR + j).getVariable().toString();
			int status = var.get(IFOPSTATUS + j).getVariable().toInt();

			long inOctets = var.get(IFINOCTETS + j).getVariable().toLong();
			long outOctets = var.get(IFOUTOCTETS + j).getVariable().toLong();
			IfRowInfo tmprow = new IfRowInfo(index, descr, status, inOctets, outOctets);
			tmp.add(tmprow);

		}

		ifTableInfo.setIfList(tmp);

		return ifTableInfo;

	}

	public PDU getPDUGet(OID oids[]) {
		PDU pdu = new PDU();
		for (OID oid : oids) {
			pdu.add(new VariableBinding(oid));
		}

		pdu.setType(PDU.GET);
		return (PDU) pdu.clone();
	}

	public PDU getPDUGetBulk(OID oids[], int nonRepeaters, int maxRepetitions) {
		PDU pdu = new PDU();
		for (OID oid : oids) {
			pdu.add(new VariableBinding(oid));
		}

		pdu.setType(PDU.GETBULK);
		pdu.setNonRepeaters(nonRepeaters);
		pdu.setMaxRepetitions(maxRepetitions);
		return (PDU) pdu.clone();
	}

	public ResponseEvent get(PDU pdu) throws IOException {
		ResponseEvent event = null;

		event = snmp.send(pdu, getTarget(), null);

		if (event != null) {
			return event;
		}
		throw new RuntimeException("GET timed out");
	}

	public Target getTarget() {
		Address targetAddress = GenericAddress.parse(address);
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		return target;
	}

	public Snmp getSnmp() {
		return this.snmp;
	}

}
