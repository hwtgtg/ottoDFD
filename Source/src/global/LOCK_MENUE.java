package global;

import java.util.concurrent.locks.ReentrantLock;

public class LOCK_MENUE {

	private static ReentrantLock lock = null;

	public LOCK_MENUE() {
	}

	private static ReentrantLock getLock() {
		if (lock == null) {
			lock = new ReentrantLock();
		}
		return lock;
	}

	public static boolean testeLock() {

		if (getLock().tryLock()) {
			return true;
		} else {
			return false;
		}

	}

	public static void releaseLock() {
		getLock().unlock();
	}

}
