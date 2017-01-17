package di.uminho.miei.gredes.businesslayer.threads;

import di.uminho.miei.gredes.presentationlayer.components.IfTablePanel;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableRegistry;

public class UIUpdateThread extends Thread {

	private IfTableRegistry ifTableRegistry;
	private IfTablePanel ifTablePanel;
	private long start;

	public UIUpdateThread(IfTableRegistry ifTableRegistry, IfTablePanel ifTablePanel, long start) {
		super();
		this.ifTableRegistry = ifTableRegistry;
		this.ifTablePanel = ifTablePanel;
		this.start = start;

	}

	@Override
	public void run() {

		try {
			while (true) {
				sleep(1000);

				System.out.println("REFRESH");
				IfTableInfo ifTableInfo = ifTableRegistry.pollFirst();
				ifTablePanel.setIfTableInfo(ifTableInfo, this.start, ifTableRegistry.first().getIfList());

			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
