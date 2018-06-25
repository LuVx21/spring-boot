package org.luvx.pattern.Composite;

abstract class FileType {
	public void add(FileType c) {
	};

	public void remove(FileType c) {
	};

	public FileType getChild(int i) {
		return null;
	};

	public abstract void operation();
}
