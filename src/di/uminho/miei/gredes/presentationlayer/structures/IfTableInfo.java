package di.uminho.miei.gredes.presentationlayer.structures;

import java.util.ArrayList;
import java.util.List;

public class IfTableInfo implements Comparable<IfTableInfo> {

	private long sysUptime;
	private List<IfRowInfo> ifList;

	public IfTableInfo() {
		super();
		this.ifList = new ArrayList<>();
		this.sysUptime = 0;
	}

	public IfTableInfo(long sysUpTime, List<IfRowInfo> ifList) {
		super();
		this.sysUptime = sysUpTime;
		this.ifList = ifList;

	}

	public IfTableInfo(IfTableInfo ifTableInfo) {
		super();
		this.ifList = ifTableInfo.getIfList();
		this.sysUptime = ifTableInfo.getSysUptime();
	}

	public synchronized List<IfRowInfo> getIfList() {
		ArrayList<IfRowInfo> tmp = new ArrayList<>();

		for (IfRowInfo ifRowInfo : this.ifList) {
			tmp.add(ifRowInfo.clone());
		}
		return tmp;
	}

	public synchronized void setIfList(List<IfRowInfo> ifList) {
		this.ifList.clear();
		for (IfRowInfo ifRowInfo : ifList) {
			this.ifList.add(ifRowInfo.clone());
		}

	}

	public synchronized long getSysUptime() {
		return sysUptime;
	}

	public synchronized void setSysUptime(long sysUptime) {
		this.sysUptime = sysUptime;
	}

	public synchronized long getIfInOctetsFrom(int iface) {

		return this.getIfList().get(iface).getIfInOctets();

	}

	public synchronized long getIfOutOctetsFrom(int iface) {

		return this.getIfList().get(iface).getIfOutOctets();

	}

	@Override
	public synchronized int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ifList == null) ? 0 : ifList.hashCode());
		result = prime * result + (int) (sysUptime ^ (sysUptime >>> 32));
		return result;
	}
	
	@Override
	public synchronized int compareTo(IfTableInfo o) {
		if (this.getSysUptime() > o.getSysUptime())
			return 1;
		else if (this.getSysUptime() < o.getSysUptime())
			return -1;
		else
			return 0;
	}

	@Override
	public synchronized boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IfTableInfo other = (IfTableInfo) obj;
		if (ifList == null) {
			if (other.ifList != null)
				return false;
		} else if (!ifList.equals(other.getIfList()))
			return false;
		if (sysUptime != other.getSysUptime())
			return false;
		return true;
	}

	@Override
	public IfTableInfo clone() {

		return new IfTableInfo(this);
	}

	@Override
	public synchronized String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("" + this.getSysUptime()).append("\n");
		builder.append("..........................\n");

		for (IfRowInfo ifRowInfo : this.ifList) {
			builder.append(ifRowInfo.toString());
		}
		builder.append("..........................\n");

		return builder.toString();
	}

	

}
