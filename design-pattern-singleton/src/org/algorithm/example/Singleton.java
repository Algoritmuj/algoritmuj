package org.algorithm.example;

class Singleton {
	private static Singleton instance;

	// soukrom� konstruktor
	private Singleton() {
	}

	// metoda vytv��enj�c� pouze jedin� objekt (thread-safe)
	public static Singleton getInstance() {

		if (instance == null) {
			// blok k�du, kde je t�eba zajistit synchronizaci mezi v�ce vl�kny
			synchronized (Singleton.class) {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}

		return instance;
	}
}