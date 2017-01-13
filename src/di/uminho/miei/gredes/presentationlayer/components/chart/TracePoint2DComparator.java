package di.uminho.miei.gredes.presentationlayer.components.chart;

import java.util.Comparator;

import info.monitorenter.gui.chart.TracePoint2D;

public class TracePoint2DComparator implements Comparator<TracePoint2D> {

	@Override
	public int compare(TracePoint2D pt1, TracePoint2D pt2) {
		if (pt1.getX() < pt2.getX())
			return -1;
		if (pt1.getX() > pt2.getX())
			return 1;
		else
			return 0;

	}

}
