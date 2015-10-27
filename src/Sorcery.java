
//theworldisquiethere

import javax.swing.*;
import java.awt.*;

public class Sorcery {

	public static JFrame box = new JFrame("Snake");
	private JPanel atlas = new JPanel();
	private Witchcraft game = new Witchcraft();

	public Sorcery() {
		
		prepareAtlas();

		buildAbox();

	}

	private void prepareAtlas() {

		atlas.setPreferredSize(new Dimension(400, 400));
		atlas.setLayout(new BoxLayout(atlas, BoxLayout.Y_AXIS));
		atlas.add(game);

	}

	private void buildAbox() {

		box.setContentPane(atlas);
		box.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		box.setResizable(false);
		box.pack();
		box.setLocationRelativeTo(null);
		box.setVisible(true);

	}

	public static void main(String args[]) {

		Sorcery magic = new Sorcery();

	}

}

