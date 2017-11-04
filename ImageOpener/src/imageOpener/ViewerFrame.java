package imageOpener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/* this frame will show the chosen images
 * and change the current image for the next or the previous image
 */
@SuppressWarnings("serial")
public class ViewerFrame extends JFrame{

	private int currentImage;							//the current index image to be showing
	private ImagePanel viewerPanel = new ImagePanel();	//the component that show image itself
	private ImageIcon[] selectedImages;					//the images to be shown
	private final Dimension screenSize;					//size of the screen for calculations

	//the constructor defines the content of the viewer
	//from the selected files in a File array
	public ViewerFrame(File[] files) throws IOException{
		//JFrame settings
		super("Window viewer");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		
		//getting the screen's size and assigning to the property
		this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// If the files arrays is null or empty
		if(files != null && files.length < 1) {
			//there are at least one image to show then:
			this.selectedImages = createImages(files);			//create the images from the files
			this.setSizeFrame(currentImage);					//define the size of the frame
			this.viewerPanel.setIcon(this.selectedImages[0]);	//and show the first image
		}else {
			//there is no image to show
			this.setSize(400, 400);									//set the width and height for 400 px
			this.viewerPanel.setText("Nothing to see here...");		//defines a message for the user
			this.viewerPanel.setAlignmentX(JLabel.CENTER_ALIGNMENT);//defines the alignment
			this.viewerPanel.setOpaque(true);						
			this.viewerPanel.setBackground(Color.black);
		}
		
		//add the viewer panel to a JScrollPane
		//and the JScrollPane to frame
		this.add(new JScrollPane(this.viewerPanel));

		//finally show the frame
		this.setVisible(true);
	}

	//show the next image on the selected images array
	public void nextImage() {
		//if the there is no images to show, stop the processing
		if(this.selectedImages == null) return;

		//increment the image index
		++this.currentImage;
		
		//if the current image index is bigger than the last index array
		//show the last image
		if (this.currentImage > this.selectedImages.length-1)
			this.currentImage = this.selectedImages.length-1;

		//set the size frame
		this.setSizeFrame(this.currentImage);
		
		//and finally select and show the image
		this.viewerPanel.setIcon(this.selectedImages[this.currentImage]);
	}

	//show the previous image on the selected images array
	public void previousImage() {
		//if the there is no images to show, stop the processing
		if(this.selectedImages == null) return;

		//decrement the image index
		--this.currentImage;
		
		//if the current image index is bigger than the last index array
		//show the last image
		if (this.currentImage < 0)
			this.currentImage = 0;

		//set the size of the frame
		this.setSizeFrame(this.currentImage);
		
		//and finally select and show the image
		this.viewerPanel.setIcon(this.selectedImages[this.currentImage]);
	}

	//create a image icon the each selected file, and stores in array
	private ImageIcon[] createImages(File[] file) {
		//create a ImageIcon array for store the images from the files array
		ImageIcon[] imgs = new ImageIcon[file.length];

		//create a ImageIcon for each File,and saves in the imgs array
		for (int i = 0; i < imgs.length; i++) {
			try {
				imgs[i] = new ImageIcon(ImageIO.read(file[i]));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		}

		return imgs;
	}

	//set the files array to be shown
	public void setFiles(File[] files) {
		//if there are files in files array
		if((files != null) | files.length > 0 ) {
			this.viewerPanel.setText("");				//Erase the user's message
			this.selectedImages = createImages(files);	//creates the images from the files
			this.currentImage = 0;						//and set the index to the first image

			//defines the frame size
			this.setSizeFrame(this.currentImage);

			//show the first selected image 
			this.viewerPanel.setIcon(this.selectedImages[this.currentImage]);
		}else {	//if there is no files to be shown
			//show a user's message
			this.viewerPanel.setText("Nothing to see here...");
		}
	}

	//defines the size for the frame from the image actually shown
	private void setSizeFrame(int index) {

		//get the width and height of the image
		int imageWidth = this.selectedImages[index].getIconWidth();
		int imageHeight = this.selectedImages[index].getIconHeight();

		//if the image is bigger than screen so:
		if((imageHeight > this.screenSize.getHeight()) | (imageWidth > this.screenSize.getWidth()))
			this.setSize(this.screenSize);					//set the frame's size to the screen's size
		//else if the image is smaller than default image size
		else if((imageHeight <= 400) | (imageWidth <= 400))
			this.setSize(400, 400);							//set the frame's size to default
		//else the image is bigger than default, and smaller than screen
		else
			this.setSize(imageWidth, imageHeight);			//set the frame's size to size's image
	}
}
