package di.uminho.miei.gredes.presentationlayer.components.chart;

import java.awt.Color;
import java.util.TreeSet;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.TracePoint2D;
import info.monitorenter.gui.chart.traces.Trace2DSorted;

/**
 * 
 * @author bpereira
 *
 */
public class Chart extends Chart2D {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8624502985053219743L;

	private int numPointsShowing;

	private ITrace2D trace;
	private TreeSet<TracePoint2D> pointTracker;

	/**
	 * 
	 * @param numPointsShowing
	 */
	public Chart(int numPointsShowing) {
		super();
		this.numPointsShowing = numPointsShowing;
		this.trace = new Trace2DSorted();
		this.pointTracker = new TreeSet<>(new TracePoint2DComparator());
		trace.setColor(Color.RED);
		this.addTrace(trace);
		

	}

	/**
	 * 
	 * @return
	 */
	public  int getNumPointsShowing() {
		return this.numPointsShowing;
	}

	/**
	 * 
	 * @param numPointsShowing
	 */
	public void setNumPointsShowing(int numPointsShowing) {
		this.numPointsShowing = numPointsShowing;
	}

	/**
	 * 
	 * @return
	 */
	public  ITrace2D getTrace() {
		return trace;
	}

	/**
	 * 
	 * @return
	 */
	public TreeSet<TracePoint2D> getPointTracker() {
		return pointTracker;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void addPoint(double x, double y) {

		
		pointTracker.add(new TracePoint2D(x, y));
		trace.addPoint(new TracePoint2D(x, y));

		if (trace.getSize() == this.numPointsShowing) {

			TracePoint2D point = pointTracker.pollFirst();
			trace.removePoint((TracePoint2D) point);

		}
	}
	
	

}
