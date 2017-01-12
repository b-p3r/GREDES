package di.uminho.miei.gredes.businesslayer.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import di.uminho.miei.gredes.presentationlayer.structures.IfRowInfo;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;

public class Monitor {

	private static final int totalColumns = 5;

	private static final int sysUpTime = 0;

	private static final int ifIndex = 1;

	private static final int ifDescr = 2;

	private static final int ifOpStatus = 3;

	private static final int ifInOctets = 4;

	private static final int ifOutOctets = 5;

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

	public Vector<? extends VariableBinding> getAsVar(OID oid[]) throws IOException {
		ResponseEvent event = get(getPDUGet(oid));
		return event.getResponse().getVariableBindings();
	}

	public IfTableInfo getAsStringBulk(OID[] oid, int nonRepeaters, int maxRepetitions) throws IOException {
		ResponseEvent event = get(getPDUGetBulk(oid, nonRepeaters, maxRepetitions));

		IfTableInfo ifTableInfo = new IfTableInfo();
		ArrayList<IfRowInfo> tmp = new ArrayList<>();

		Vector<? extends VariableBinding> var = event.getResponse().getVariableBindings();
		ifTableInfo.setSysUptime(var.get(sysUpTime).getVariable().toLong());

		for (int i = 1, j=0; j < (maxRepetitions * totalColumns);i++, j+=totalColumns) {

			
			int index = var.get(ifIndex+j).getVariable().toInt();
			String descr = var.get(ifDescr+j).getVariable().toString();
			int status = var.get(ifOpStatus+j).getVariable().toInt();
			//int status = 0;
			long inOctets = var.get(ifInOctets+j).getVariable().toLong();
			long outOctets = var.get(ifOutOctets+j).getVariable().toLong();
			IfRowInfo tmprow = new IfRowInfo(index, descr, status, inOctets, outOctets);
			//System.out.println(tmprow.toString());
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
