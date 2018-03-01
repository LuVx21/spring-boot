package org.luvx.api.callback;

/**
 * 回调函数示例 某个程序S(Student.main) 调用 服务程序A(Arrays) 中的某个方法(sort),
 * 服务程序A的sort方法在某个时候反过来调用S的某个方法(compareTo),这种情况下,compareTo叫做S的回调方法。
 */
public class Callback1 implements Comparable<Object> {
	private int id;
	private String name;
	private int age;
	private int score;

	public Callback1(int id, String name, int age, int score) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.score = score;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", score=" + score + "]";
	}

	// 回调方法
	public int compareTo(Object obj) {
		System.out.println("Callback");
		return this.id - ((Callback1) obj).id;
	}
}
