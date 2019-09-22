package org.algorithm.example;

class Singleton {
	private static Singleton instance;

	// soukromý konstruktor
	private Singleton() {
	}

	// metoda vytváøenjící pouze jediný objekt (thread-safe)
	public static Singleton getInstance() {

		if (instance == null) {
			// blok kódu, kde je tøeba zajistit synchronizaci mezi více vlákny
			synchronized (Singleton.class) {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}

		return instance;
	}
}