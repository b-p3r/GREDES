package di.uminho.miei.gredes.businesslayer.threads;

import java.io.IOException;
import java.util.Vector;

import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import di.uminho.miei.gredes.businesslayer.snmp.Monitor;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableRegistry;

public class ManagerHelper {

	/**
	 * 
	 */
	private Monitor monitor;
	private long start;
	private int ifNumberInt;
	private OID bulkOIDs[];
	private OID[] ifNumOIDs;
	private IfTableRegistry registry;

	/**
	 * 
	 * @param address
	 */
	public ManagerHelper(String address) {
		super();
		this.monitor = new Monitor(address);
		this.start = 0;
		this.ifNumberInt = 0;
		this.bulkOIDs = new OID[6];
		this.ifNumOIDs = new OID[2];
		registry = new IfTableRegistry();
	}

	public void preparePolling() throws IOException {

		/**
		 * Criar array de OIDS para saber número de interfaces e sysuptime
		 * inicial
		 */
		OID ifNumberScalar = new OID(".1.3.6.1.2.1.2.1.0");
		OID sysUptimeScalar = new OID(".1.3.6.1.2.1.1.3.0");

		OID tmpIfNum[] = { sysUptimeScalar, ifNumberScalar };

		System.arraycopy(tmpIfNum, 0, ifNumOIDs, 0, tmpIfNum.length);

		/**
		 * Criar um array para o polling dos dados para monitorização
		 */

		OID ifIndex = new OID(".1.3.6.1.2.1.2.2.1.1");
		OID ifDescr = new OID(".1.3.6.1.2.1.2.2.1.2");
		OID ifOpStatus = new OID(".1.3.6.1.2.1.2.2.1.8");
		OID ifInOctets = new OID(".1.3.6.1.2.1.2.2.1.10");
		OID ifOutOctets = new OID(".1.3.6.1.2.1.2.2.1.16");
		OID sysUptime = new OID(".1.3.6.1.2.1.1.3");

		OID tmpOIDS[] = { sysUptime, ifIndex, ifDescr, ifOpStatus, ifInOctets, ifOutOctets };

		System.arraycopy(tmpOIDS, 0, bulkOIDs, 0, tmpOIDS.length);


		

	}

	/**
	 * 
	 * @throws IOException
	 */
	public void numberInterfacesPolling() throws IOException {
		
		System.out.println("ifNumOIDs\n" + ifNumOIDs[0] +" "+ifNumOIDs[1]);
		
		Vector<? extends VariableBinding> queryIfNumVar = monitor.getAsVarSynchronous(ifNumOIDs);

		setStart(queryIfNumVar.get(0).getVariable().toLong());

		setIfNumberInt(queryIfNumVar.get(1).getVariable().toInt());

	}

	/**
	 * 
	 * @param responseListener
	 * @throws IOException
	 */
	public void bulkOctectsPollingRequest(ResponseListener responseListener) throws IOException {

		monitor.sendBulkAssynchronous(bulkOIDs, 1, ifNumberInt, responseListener);

	}

	/**
	 * 
	 * @param event
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void bulkOctectsPollingResponse(ResponseEvent event) throws IOException, InterruptedException {

		IfTableInfo table = monitor.getAsTableBulkAssynchronous(1, 5, event);
		System.out.println("Received response PDU is: " + table.toString());

		registry.add(table.clone());

	}
	
	

	/**
	 * 
	 * @return
	 */
	public Monitor getMonitor() {
		return monitor;
	}

	/**
	 * 
	 * @param monitor
	 */
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	/**
	 * 
	 * @return
	 */
	public IfTableRegistry getRegistry() {
		return registry;
	}

	/**
	 * 
	 * @param registry
	 */
	public void setRegistry(IfTableRegistry registry) {
		this.registry = registry;
	}

	/**
	 * 
	 * @return
	 */
	public int getIfNumberInt() {
		return ifNumberInt;
	}

	/**
	 * 
	 * @param ifNumberInt
	 */
	public void setIfNumberInt(int ifNumberInt) {
		this.ifNumberInt = ifNumberInt;
	}

	/**
	 * 
	 * @return
	 */
	public long getStart() {
		return start;
	}

	/**
	 * 
	 */
	public void setStart(long start) {
		this.start = start;
	}

}
