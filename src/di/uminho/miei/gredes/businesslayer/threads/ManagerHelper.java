package di.uminho.miei.gredes.businesslayer.threads;

import java.io.IOException;
import java.util.Vector;

import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.VariableBinding;

import di.uminho.miei.gredes.businesslayer.snmp.Monitor;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableRegistry;

/**
 * 
 * @author bpereira
 *
 */
public class ManagerHelper {

	/**
	 * 
	 */
	private Monitor monitor;
	
	
	private long initialTime;
	private long polltime;

	private int interfacesTotalNumber;
	
	
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
		this.initialTime = 0;
		this.interfacesTotalNumber = 0;
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

		Vector<? extends VariableBinding> queryIfNumVar = monitor.getAsVarSynchronous(ifNumOIDs);

		setInitialTime(queryIfNumVar.get(0).getVariable().toLong());

		setInterfacesTotalNumber(queryIfNumVar.get(1).getVariable().toInt());

	}

	/**
	 * 
	 * @param responseListener
	 * @throws IOException
	 */
	public void bulkOctectsPollingRequest(ResponseListener responseListener) throws IOException {

		monitor.sendBulkAssynchronous(bulkOIDs, 1, interfacesTotalNumber, responseListener);

	}

	/**
	 * 
	 * @param event
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void bulkOctectsPollingResponse(ResponseEvent event) throws IOException, InterruptedException {

		IfTableInfo table = monitor.getAsTableBulkAssynchronous(1, this.interfacesTotalNumber, event);
		System.out.println("Received response PDU is: " + table.toString());

		registry.add(table.clone());

	}

	public void calcMaxPoll() throws IOException, InterruptedException {
		OID queryPoll[] = { new OID(".1.3.6.1.2.1.1.3.0") };

		long previouSysUpTime = this.initialTime;
		long maxInterval = 0;

		for (int i = 0; i < 5; i++) {

			Thread.sleep(3000);

			Vector<? extends VariableBinding> queryRes = monitor.getAsVarSynchronous(queryPoll);

			long sysuptime = queryRes.get(0).getVariable().toLong();
			

			if (previouSysUpTime != sysuptime) {

			if ((sysuptime - previouSysUpTime) > maxInterval) {
				maxInterval = (sysuptime - previouSysUpTime);

			}
			
			
			previouSysUpTime = sysuptime;

		}

		 }

		 setPolltime(new TimeTicks(maxInterval).toMilliseconds());
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
	public int getInterfacesTotalNumber() {
		return this.interfacesTotalNumber;
	}

	/**
	 * 
	 * @param interfacesTotalNumber
	 */
	public void setInterfacesTotalNumber(int interfacesTotalNumber) {
		this.interfacesTotalNumber = interfacesTotalNumber;
	}

	/**
	 * 
	 * @return
	 */
	public long getInitialTime() {
		return this.initialTime;
	}

	/**
	 * 
	 * @param start
	 */
	public void setInitialTime(long initialTime) {
		this.initialTime = initialTime;
	}

	/**
	 * @return the polltime
	 */
	public long getPolltime() {
		return this.polltime;
	}

	/**
	 * @param polltime the polltime to set
	 */
	public void setPolltime(long polltime) {
		this.polltime = polltime;
	}
	
	
	public void stop() throws IOException {
		this.monitor.stop();

	}
	

}
