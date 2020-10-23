package snakeludu;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton; //import javax.swing.JFrame;
import javax.swing.JLabel;

public class ShowMessage extends SnakeLudu {

	static int w, h;
	static String msg;

	public void showWarning(int width, int height, String msg) {
		w = width;
		h = height;
		this.msg = msg;

		modal = new Dialog(f, "A modal dialog", true);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		modal.setBounds((screen.width / 2) - (width / 2), (screen.height / 2)
				- (height / 2), width, height);
		JButton ok = new JButton("OK");
		JLabel jl = new JLabel(msg);
		modal.add(ok, BorderLayout.SOUTH);
		modal.add(jl, BorderLayout.CENTER);
		ok.addActionListener(mel);
		modal.setVisible(true);
	}
}