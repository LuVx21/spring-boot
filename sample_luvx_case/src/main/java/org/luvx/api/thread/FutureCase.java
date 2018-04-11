package org.luvx.api.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Future模式
 */
public class FutureCase implements Callable<String> {

	private String result;

	public FutureCase(String result) {
		this.result = result;
	}

	@Override
	public String call() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(result);
		// 模拟耗时的构造数据过程
		Thread.sleep(5000);
		return sb.toString();
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		FutureTask<String> futureTask = new FutureTask<String>(new FutureCase("Hello"));

		ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.execute(futureTask);

		System.out.println("请求完毕......");

		try {
			Thread.sleep(2000);
			System.out.println("进行了花费2秒的操作......");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("真实数据：" + futureTask.get());
		executorService.shutdown();
	}
}