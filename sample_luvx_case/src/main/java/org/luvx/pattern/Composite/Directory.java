package org.luvx.pattern.Composite;

import java.util.ArrayList;

class Directory extends FileType {
	private String name;

	private ArrayList<FileType> list = new ArrayList<FileType>();

	public Directory(String name) {
		this.name = name;
	}

	public void add(FileType c) {
		list.add(c);
	}

	public void remove(FileType c) {
		list.remove(c);
	}

	public FileType getChild(int i) {
		return (FileType) list.get(i);
	}

	public void operation() {
		System.out.println("  对文件夹'" + name + "'进行读取");
		for (Object obj : list) {
			((FileType) obj).operation();
		}
	}
}