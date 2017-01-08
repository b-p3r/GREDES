import java.util.ArrayList;
import java.util.TreeMap;

import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;

public class ResponseThread extends Thread {
	
	@SuppressWarnings("unused")
	private Monitor monitor;
	@SuppressWarnings("unused")
	private ResponseListener responseListener;
	
	ArrayList<TreeMap<Integer, Integer>> receivedList = new ArrayList<>();

	public ResponseThread(Monitor monitor) {
		super();
		this.monitor = monitor;
	}

	@Override
	public void run() {
		
		
		while (true) {
			
			responseListener = new ResponseListener() {
				
				@Override
				public void onResponse(ResponseEvent event) {
					((Snmp)event.getSource()).cancel(event.getRequest(), this);
				       System.out.println("Received response PDU is: "+event.getResponse());
					
				}
			};
			
			
			
			
			
		}
	}
	
	

}
