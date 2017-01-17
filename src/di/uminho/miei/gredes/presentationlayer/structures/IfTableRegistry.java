package di.uminho.miei.gredes.presentationlayer.structures;

import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IfTableRegistry {

	private int Maxsize = 1000;
	Lock qLock;
	Condition notEmpty;
	Condition notFull;

	private TreeSet<IfTableInfo> registry;

	public IfTableRegistry() {
		super();
		this.registry = new TreeSet<>();
		qLock = new ReentrantLock();
		notEmpty = qLock.newCondition();
		notFull = qLock.newCondition();
	}

	public IfTableRegistry(TreeSet<IfTableInfo> registry) {
		super();
		this.registry = new TreeSet<>();
		this.setRegistry(registry);
	}

	public IfTableRegistry(IfTableRegistry ifRegistry) {
		super();
		this.registry = ifRegistry.getRegistry();
	}

	public TreeSet<IfTableInfo> getRegistry() {

		qLock.lock();
		TreeSet<IfTableInfo> tmp = new TreeSet<>();
		try {
			for (IfTableInfo ifTableInfo : this.registry) {
				tmp.add(ifTableInfo.clone());

			}
		} finally {
			qLock.unlock();
		}

		return tmp;
	}

	public int size() {
		qLock.lock();
		int size = 0;
		try {
			size = registry.size();
		} finally {
			qLock.unlock();
		}
		return size;
	}

	public boolean isEmpty() {
		qLock.lock();
		boolean isEmpty = false;
		try {
			isEmpty = registry.isEmpty();
		} finally {
			qLock.unlock();
		}
		return isEmpty;
	}

	public boolean contains(Object o) {
		qLock.lock();
		boolean exists = false;
		try {

			exists = registry.contains(o);
		} finally {
			qLock.unlock();
		}
		return exists;
	}

	public void add(IfTableInfo e) throws InterruptedException {
		qLock.lock();
		try {

			while (this.registry.size() == Maxsize)
				notEmpty.await();

			registry.add(e.clone());
			notEmpty.signal();
		} finally {
			qLock.unlock();
		}

	}

	public void remove(Object o) throws InterruptedException {
		qLock.lock();

		try {

			while (this.registry.isEmpty())
				notFull.await();

			registry.remove(o);

			notFull.signal();
		} finally {
			qLock.unlock();
		}

	}

	public void clear() {
		qLock.lock();
		try {
			registry.clear();
		} finally

		{
			qLock.unlock();
		}
	}

	public IfTableInfo first() throws InterruptedException {
		qLock.lock();

		IfTableInfo ifTableInfo = null;

		try {
			

				while (this.registry.isEmpty())
					notFull.await();
			ifTableInfo = registry.first();
		} finally {
			qLock.unlock();
		}
		return ifTableInfo;

	}

	public IfTableInfo last() throws InterruptedException {
		qLock.lock();

		IfTableInfo ifTableInfo = null;

		try {

			while (this.registry.isEmpty())
				notFull.await();
			ifTableInfo = registry.last();
		} finally {
			qLock.unlock();
		}

		return ifTableInfo;

	}

	public IfTableInfo pollFirst() throws InterruptedException {
		qLock.lock();

		IfTableInfo ifTableInfo = null;

		try {

			while (this.registry.isEmpty())
				notFull.await();
			ifTableInfo = registry.pollFirst();

			notFull.signal();
		} finally {
			qLock.unlock();
		}
		return ifTableInfo;
	}

	public IfTableInfo pollLast() throws InterruptedException {
		qLock.lock();

		IfTableInfo ifTableInfo = null;

		try {

			while (this.registry.isEmpty())
				notFull.await();
			ifTableInfo = registry.pollLast();

			notFull.signal();
		} finally {
			qLock.unlock();
		}
		return ifTableInfo;

	}

	public void setRegistry(TreeSet<IfTableInfo> registry) {

		qLock.lock();
		try {

			this.registry.clear();
			for (IfTableInfo ifTableInfo : registry) {
				this.registry.add(ifTableInfo.clone());

			}
		} finally {
			qLock.unlock();
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		qLock.lock();
		try {
			result = prime * result + ((registry == null) ? 0 : registry.hashCode());
		} finally {
			qLock.unlock();
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
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
	public IfTableRegistry clone() {

		return new IfTableRegistry(this);
	}

	@Override
	public String toString() {
		qLock.lock();
		StringBuilder builder = new StringBuilder();
		try {

			for (IfTableInfo ifTableInfo : this.registry) {
				builder.append(ifTableInfo.toString());
			}
		} finally {
			qLock.unlock();
		}

		return builder.toString();
	}

}
