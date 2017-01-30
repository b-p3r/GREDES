package di.uminho.miei.gredes.businesslayer.threads;

import java.util.List;

import javax.swing.SwingWorker;

import di.uminho.miei.gredes.presentationlayer.components.IfTablePanel;
import di.uminho.miei.gredes.presentationlayer.structures.IfTableInfo;

public class UIUpdateWorker extends SwingWorker<Boolean, IfTableInfo> {

	private ManagerHelper mHelper;
	private IfTablePanel ifTablePanel;

	/**
	 * 
	 * @param managerHelper
	 * @param ifTablePanel
	 */
	public UIUpdateWorker(ManagerHelper managerHelper, IfTablePanel ifTablePanel) {
		this.mHelper = managerHelper;
		this.ifTablePanel = ifTablePanel;
	}

	

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Boolean doInBackground() throws Exception {
		while (!isCancelled()) {
		IfTableInfo ifTableInfo = null;
		if (mHelper.getRegistry().size() > 1) {
			ifTableInfo = mHelper.getRegistry().pollFirst();
			publish(ifTableInfo);
		}
	}

	return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#process(java.util.List)
	 */
	@Override
	protected void process(List<IfTableInfo> chunks) {
		IfTableInfo mostRecentDataSet = chunks.get(chunks.size() - 1);
		try {
			ifTablePanel.setIfTableInfo(mHelper.getRegistry().first(), mHelper.getStart(),
					mostRecentDataSet.getIfList());

			long start = System.currentTimeMillis();
			long duration = System.currentTimeMillis() - start;

			Thread.sleep(40 - duration); // 40 ms ==> 25fps
			//Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
		} catch (InterruptedException e) {
		}
	}
	

}
