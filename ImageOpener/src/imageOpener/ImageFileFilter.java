package imageOpener;

import java.io.File;

import javax.swing.filechooser.FileFilter;


//default file filter for images
public class ImageFileFilter extends FileFilter {

	public boolean accept(File pathname) {
		
		//get the file name, and converts to lower case
		String filename = pathname.getName().toLowerCase();
		
		boolean accepted = false;

		//if the file is a: gif, png, jpeg, bmp, wbmp, or jpeg accepted is true
		if(	filename.endsWith(".gif") | 
			filename.endsWith(".png") |
			filename.endsWith(".jpeg")|
			filename.endsWith(".bmp") |
			filename.endsWith(".wbmp")|
			filename.endsWith(".jpg"))
			accepted = true;
		
		return accepted;
	}

	//don't will be necessary
	public String getDescription() {
		return null;
	}

}
