package snakeludu;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class AddFrame {
	JFrame addFrame(int width, int height, String title) {
		JFrame f = new JFrame(title);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		f.setBounds((screen.width / 2) - (width / 2), (screen.height / 2)
				- (height / 2), width, height);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		return f;

	}

	
	

}