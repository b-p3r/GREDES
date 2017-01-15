package di.uminho.miei.gredes.presentationlayer.structures;

import java.util.Set;
import java.util.TreeSet;

public class IfTableRegistry {

	private Set<IfTableInfo> registry;

	public IfTableRegistry() {
		super();
		this.registry = new TreeSet<>();
	}

	public IfTableRegistry(Set<IfTableInfo> registry) {
		super();
		this.registry = new TreeSet<>();
		this.setRegistry(registry);
	}

	public IfTableRegistry(IfTableRegistry ifRegistry) {
		super();
		this.registry = ifRegistry.getRegistry();
	}

	public synchronized Set<IfTableInfo> getRegistry() {
		TreeSet<IfTableInfo> tmp = new TreeSet<>();

		for (IfTableInfo ifTableInfo : this.registry) {
			tmp.add(ifTableInfo.clone());

		}

		return tmp;
	}
	
	
	

	public synchronized int size() {
		return registry.size();
	}

	public synchronized boolean isEmpty() {
		return registry.isEmpty();
	}

	public synchronized boolean contains(Object o) {
		return registry.contains(o);
	}

	public synchronized void add(IfTableInfo e) {
		registry.add(e);
	}

	public synchronized void remove(Object o) {
		registry.remove(o);
	}

	public synchronized void clear() {
		registry.clear();
	}

	public synchronized void setRegistry(Set<IfTableInfo> registry) {

		this.registry.clear();
		for (IfTableInfo ifTableInfo : registry) {
			this.registry.add(ifTableInfo.clone());

		}

	}

	@Override
	public synchronized int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registry == null) ? 0 : registry.hashCode());
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
		IfTableRegistry other = (IfTableRegistry) obj;
		if (registry == null) {
			if (other.getRegistry() != null)
				return false;
		} else if (!registry.equals(other.getRegistry()))
			return false;
		return true;
	}

	@Override
	public synchronized IfTableRegistry clone() {

		return new IfTableRegistry(this);
	}

	@Override
	public synchronized String toString() {
		StringBuilder builder = new StringBuilder();

		for (IfTableInfo ifTableInfo : this.registry) {
			builder.append(ifTableInfo.toString());
		}

		return builder.toString();
	}

}
