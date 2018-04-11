# Lambda
## Java

*普通实现*

	File dir = new File("/an/dir/");
	   FileFilter directoryFilter = new FileFilter() {
	      public boolean accept(File file) {
	         return file.isDirectory();
	      }
	};
	
*Lambda实现*

	File dir = new File("/an/dir/");  
	
	File[] dirs = dir.listFiles((File f) -> f.isDirectory());