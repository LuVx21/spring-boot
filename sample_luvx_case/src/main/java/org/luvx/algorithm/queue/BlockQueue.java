package org.luvx.algorithm.queue;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 大小为5的阻塞队列
 * 使用ReentrantLock,Condition
 */
public class BlockQueue {

	private final LinkedList<Object> list = new LinkedList<>();
	private final AtomicInteger count = new AtomicInteger(0);
	private final int MAXSIZE = 5;
	private final int MINSIZE = 0;

	private final Lock lock = new ReentrantLock();
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();

	public void put(Object obj) {
		lock.lock();
		while (count.get() == MAXSIZE) {
			try {
				notFull.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		list.add(obj);
		count.getAndIncrement();
		System.out.println(" 元素 " + obj + " 被添加 ");
		notEmpty.signal();
		lock.unlock();
	}

	public Object get() {
		lock.lock();
		Object temp;
		while (count.get() == MINSIZE) {
			try {
				notEmpty.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		count.getAndDecrement();
		temp = list.removeFirst();
		System.out.println(" 元素 " + temp + " 被移出 ");
		notFull.signal();
		lock.unlock();
		return temp;
	}

	private int size() {
		return count.get();
	}

	public static void main(String[] args) throws Exception {

		final BlockQueue myQueue = new BlockQueue();
		initMyQueue(myQueue);

		Thread t1 = new Thread(() -> {
			myQueue.put("h");
			myQueue.put("i");
		}, "t1");

		Thread t2 = new Thread(() -> {
			try {
				Thread.sleep(2000);
				myQueue.get();
				Thread.sleep(2000);
				myQueue.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "t2");

		t1.start();
		Thread.sleep(1000);
		t2.start();
	}

	private static void initMyQueue(BlockQueue myQueue) {
		myQueue.put("a");
		myQueue.put("b");
		myQueue.put("c");
		myQueue.put("d");
		myQueue.put("e");
		System.out.println("当前元素个数：" + myQueue.size());
	}
}