package com.hwadee.bookstore.test;

public class ThreadLocalTest implements Runnable {
	String name = null;
	int i = 0;

	/*
	 * 使用ThreadLocal解决线程安全问题
	 */
	 ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	@Override
	public void run() {
		for (; i < 10; i++) {
//			synchronized (this) {
//				name = Thread.currentThread().getName();
			threadLocal.set(Thread.currentThread().getName());

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

				}
				System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
//			}
		}
	}

	public static void main(String[] args) {
		ThreadLocalTest test = new ThreadLocalTest();

		new Thread(test, "AAA").start();
		;
		new Thread(test, "BBB").start();
		;

	}

}
