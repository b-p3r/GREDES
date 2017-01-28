package di.uminho.miei.gredes.presentationlayer.structures;

import java.util.ArrayList;
import java.util.List;

public class IfTableInfo implements Comparable<IfTableInfo> {

	private long sysUptime;
	private List<IfRowInfo> ifList;

	public IfTableInfo() {
		super();
		this.setIfList(new ArrayList<>());
		this.setSysUptime(0);
	}

	public IfTableInfo(long sysUpTime, List<IfRowInfo> ifList) {
		super();
		this.setSysUptime(sysUpTime);
		this.setIfList(ifList);

	}

	public IfTableInfo(IfTableInfo ifTableInfo) {
		super();
		synchronized (ifList) {
			this.ifList = ifTableInfo.getIfList();
			this.sysUptime = ifTableInfo.getSysUptime();
		}

	}

	public List<IfRowInfo> getIfList() {
		ArrayList<IfRowInfo> tmp = new ArrayList<>();

		synchronized (this.ifList) {
			for (IfRowInfo ifRowInfo : this.ifList) {
				tmp.add(ifRowInfo.clone());
			}
		}

		return tmp;
	}

	public void setIfList(List<IfRowInfo> ifList) {
		synchronized (this.ifList) {
			this.ifList.clear();
			for (IfRowInfo ifRowInfo : ifList) {
				this.ifList.add(ifRowInfo.clone());
			}
		}

	}

	public synchronized long getSysUptime() {
		return sysUptime;
	}

	public synchronized void setSysUptime(long sysUptime) {
		this.sysUptime = sysUptime;
	}

	public long getIfInOctetsFrom(int iface) {

		return this.getIfList().get(iface).getIfInOctets();

	}

	public long getIfOutOctetsFrom(int iface) {

		return this.getIfList().get(iface).getIfOutOctets();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		synchronized (this.ifList) {
			result = prime * result + ((ifList == null) ? 0 : ifList.hashCode());
			result = prime * result + (int) (sysUptime ^ (sysUptime >>> 32));
		}

		return result;
	}

	@Override
	public int compareTo(IfTableInfo o) {
		if (this.getSysUptime() > o.getSysUptime())
			return 1;
		else if (this.getSysUptime() < o.getSysUptime())
			return -1;
		else
			return 0;
	}

	@Override
	public synchronized boolean equals(Object obj) {
		boolean eq = true;
		synchronized (this.ifList) {
			if (this == obj)
				eq = true;
			if (obj == null)
				eq = false;
			if (getClass() != obj.getClass())
				eq = false;
			IfTableInfo other = (IfTableInfo) obj;
			if (ifList == null) {
				if (other.ifList != null)
					eq = false;
			} else if (!ifList.equals(other.getIfList()))
				eq = false;
			if (sysUptime != other.getSysUptime())
				eq = false;
		}

		return eq;
	}

	@Override
	public IfTableInfo clone() {

		return new IfTableInfo(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		synchronized (this.ifList) {

			builder.append("" + this.getSysUptime()).append("\n");
			builder.append("..........................\n");

			for (IfRowInfo ifRowInfo : this.ifList) {
				builder.append(ifRowInfo.toString());
			}
			builder.append("..........................\n");

		}

		return builder.toString();
	}

}
