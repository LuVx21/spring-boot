package org.luvx.pattern.Composite;

class Image extends FileType {
	private String name;

	public Image(String name) {
		this.name = name;
	}

	public void operation() {
		System.out.println("    对文件'" + name + "'进行读取");
	}
}