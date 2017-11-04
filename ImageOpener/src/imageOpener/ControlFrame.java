package imageOpener;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ControlFrame extends JFrame implements ActionListener{

	/* 4 buttons for the controls
	 * 1 File Chooser to get the files
	 * and 1 viewer to show images 
	 */
	private JButton	buttonOpen, buttonExit, buttonNext, buttonPrevious;
	private JFileChooser filechooser = new JFileChooser();
	private ViewerFrame viewer;

	public ControlFrame() {
		//JFrame Settings
		super("Image Opener");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(150, 50);
		
		//viewerFrame
		try {
			this.viewer = new ViewerFrame(null);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		//JFileChooser settings
		filechooser.setMultiSelectionEnabled(true);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		filechooser.setFileFilter(new ImageFileFilter());

		//create all the buttons
		this.buttonPrevious = createButton("leftArrow.png", "Previous image", KeyEvent.VK_LEFT);
		this.buttonNext = createButton("rightArrow.png", "Next image", KeyEvent.VK_RIGHT);
		this.buttonOpen = createButton("clear.png", "Open File(s)", KeyEvent.VK_ENTER);
		this.buttonExit = createButton("cancel.png", "Exit system", KeyEvent.VK_A);
		
		//the application will have two panels
		//outside panel: hold the main buttons and inside panel
		JPanel outsidePanel = new JPanel(new GridLayout(1, 3));
		//hold the directional buttons
		JPanel insidePanel = new JPanel(new GridLayout(1, 2));
		
		//add the directional buttons to inside panel
		insidePanel.add(buttonPrevious);
		insidePanel.add(buttonNext);
		
		//add the main buttons(exit and open) to outside panel
		outsidePanel.add(buttonOpen);
		outsidePanel.add(buttonExit);
		//add inside panel to outside panel
		outsidePanel.add(insidePanel);
		
		//add outside panel to the frame at the center
		this.add(outsidePanel, BorderLayout.CENTER);
		//and finally show the frame
		this.setVisible(true);
	}

	//Method to create buttons with a icon, a toolTip and a mnemonic
	private JButton createButton(String fileName, String tooltip, int mnemonic) {
		JButton jb = new JButton();							 //create a new JButton
		jb.addActionListener(this);							 //set the action listener to this
		jb.setMnemonic(mnemonic);							 //set the mnemonic
		URL filepath = this.getClass().getResource(fileName);//get the URL of the icon
		jb.setIcon(new ImageIcon(filepath));				 //and set a new ImageIcon to button
		jb.setToolTipText(tooltip);							 //set the toolTip
		return jb;
	}

	
	public void actionPerformed(ActionEvent e) {
		//get the event source
		Object source = e.getSource();

		//if the event source is equals to:

		//Button open
		if (source.equals(this.buttonOpen)) {
			
			//get the files from the user
			filechooser.showOpenDialog(this);
			File[] files = filechooser.getSelectedFiles();

			//if files is true stop the processing
			if(files.length == 0) return;

			//if files arrays is not null send to the viewer
			this.viewer.setFiles(files);
		}
		//Button next
		if (source.equals(this.buttonNext)) 
			this.viewer.nextImage();			//show the next image
		//Button Previous
		if (source.equals(this.buttonPrevious))
			this.viewer.previousImage();		//show the previous image
		//Button exit:
		if (source.equals(this.buttonExit)) 
			System.exit(0);						//exit the system
	}

	public static void main(String[] args) {
		new ControlFrame();
	}
}