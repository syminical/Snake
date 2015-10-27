
//theworldisquiethere

import javax.swing.*;
import java.awt.*;

public class Witchcraft extends JPanel {

	private final int max = 400, boxes = 20;

	public Witchcraft() {

		setBackground(Color.WHITE);

		repaint();

	}

	private void drawGrid(Graphics g) {

		g.setColor(Color.BLACK);

		for (int i = 0; i <= boxes; i++) {

			g.drawLine((i * boxes), 0, (i * 20), max);
			g.drawLine(0, (i * boxes), max, (i * 20));

		}

	}

	public void paint(Graphics g) {

		super.paint(g);

		drawGrid(g);

	}

}

