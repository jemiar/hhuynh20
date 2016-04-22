import java.awt.*;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		JFrame app = new JFrame("Test");
		PlayPanel playPanel = new PlayPanel();
		playPanel.setFocusable(true);
		app.add(playPanel, BorderLayout.CENTER);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setResizable(false);
		app.pack();
		app.setVisible(true);
	}

}
