package di.uminho.miei.gredes.presentationlayer.structures;

public class IfRowInfo {

	private int ifIndex;
	private String ifDescr;
	private int ifOpStatus;
	private long ifInOctets;
	private long ifOutOctets;

	public IfRowInfo() {
		super();

		this.ifIndex = 0;
		this.ifDescr = "";
		this.ifOpStatus = 0;
		this.ifInOctets = 0;
		this.ifOutOctets = 0;
	}

	public IfRowInfo(int ifIndex, String ifDescr, int ifOpStatus, long ifInOctets, long ifOutOctets) {
		super();

		this.ifIndex = ifIndex;
		this.ifDescr = ifDescr;
		this.ifOpStatus = ifOpStatus;
		this.ifInOctets = ifInOctets;
		this.ifOutOctets = ifOutOctets;
	}

	public IfRowInfo(IfRowInfo ifRowInfo) {
		super();

		this.ifIndex = ifRowInfo.getIfIndex();
		this.ifDescr = ifRowInfo.getIfDescr();
		this.ifOpStatus = ifRowInfo.getIfOpStatus();
		this.ifInOctets = ifRowInfo.getIfInOctets();
		this.ifOutOctets = ifRowInfo.getIfOutOctets();
	}

	public synchronized int getIfIndex() {
		return ifIndex;
	}

	public synchronized void setIfIndex(int ifIndex) {
		this.ifIndex = ifIndex;
	}

	public synchronized String getIfDescr() {
		return ifDescr;
	}

	public synchronized void setIfDescr(String ifDescr) {
		this.ifDescr = ifDescr;
	}

	public synchronized int getIfOpStatus() {
		return ifOpStatus;
	}

	public synchronized void setIfOpStatus(int ifOpStatus) {
		this.ifOpStatus = ifOpStatus;
	}

	public synchronized long getIfInOctets() {
		return ifInOctets;
	}

	public synchronized void setIfInOctets(long ifInOctets) {
		this.ifInOctets = ifInOctets;
	}

	public synchronized long getIfOutOctets() {
		return ifOutOctets;
	}

	public synchronized void setIfOutOctets(long ifOutOctets) {
		this.ifOutOctets = ifOutOctets;
	}

	@Override
	public synchronized int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ifDescr == null) ? 0 : ifDescr.hashCode());
		result = prime * result + (int) (ifInOctets ^ (ifInOctets >>> 32));
		result = prime * result + ifIndex;
		result = prime * result + ifOpStatus;
		result = prime * result + (int) (ifOutOctets ^ (ifOutOctets >>> 32));
		return result;
	}

	@Override
	public synchronized boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IfRowInfo other = (IfRowInfo) obj;
		if (ifDescr == null) {
			if (other.getIfDescr() != null)
				return false;
		} else if (!ifDescr.equals(other.getIfDescr()))
			return false;
		if (ifInOctets != other.getIfInOctets())
			return false;
		if (ifIndex != other.getIfIndex())
			return false;
		if (ifOpStatus != other.getIfOpStatus())
			return false;
		if (ifOutOctets != other.getIfOutOctets())
			return false;
		return true;
	}

	@Override
	public IfRowInfo clone() {

		return new IfRowInfo(this);
	}

	@Override
	public synchronized String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Index:     \t").append("" + this.getIfIndex()).append("\n");
		sb.append("Descr:     \t").append(this.getIfDescr()).append("\n");
		sb.append("OpStatus:  \t").append("" + this.getIfOpStatus()).append("\n");
		sb.append("InOctets:  \t").append("" + this.getIfInOctets()).append("\n");
		sb.append("OutOctets: \t").append("" + this.getIfOutOctets()).append("\n");
		sb.append("--------------------------\n");
		return sb.toString();
	}

}
