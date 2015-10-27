
//theworldisquiethere

import javax.swing.*;
import java.awt.*;

public class Witchcraft extends JPanel {

	private final int max = 400, boxes = 20, origin = 10;
	private boolean gameRunning = false, menuRunning = true;
	private int fps = 0;

	public Witchcraft() {

		setBackground(Color.WHITE);

	}

	public void start() {

		fps();

	}

	private void fps() {

		double start = 0, end = start, endEnd = start, totalTime = 0, totalFrames = 0, gameTarget = 1000/60, menuTarget = 1000/10, tracker = 0, sleepTime = 0;

		while (gameRunning || menuRunning) {

			totalTime += (endEnd - end);

			start = System.currentTimeMillis();

			repaint();

			end = System.currentTimeMillis();

			totalTime += (end - start);

			if (gameRunning)

				sleepTime = (gameTarget - (end - start));

			else

				sleepTime = (menuTarget - (end - start));

			if (sleepTime <= 0)
	
				tracker += (-1) * sleepTime;

			else {

				if (tracker > 0)

					if (sleepTime <= tracker) {

						tracker -= sleepTime;

						sleepTime = 0;

					} else {

						sleepTime -= tracker;

						tracker = 0;

					}

				try {

					Thread.sleep((int)sleepTime);
			
				} catch(InterruptedException e) {

					System.out.println("Thread fail.");

				};

			}

			if (totalTime >= 1000 || (gameRunning && totalFrames == (1/gameTarget) - 1) || (menuRunning && totalFrames == (1/menuTarget) - 1)) {

				fps = (int)totalFrames;

				totalFrames = 0;
				totalTime = 0;
				end = 0;
				endEnd = 0;
				tracker = 0;

			} else {

				endEnd = System.currentTimeMillis();

				totalFrames++;

			}

		}

	}

	private void drawGrid(Graphics g) {

		g.setColor(Color.BLACK);

		for (int i = 0; i <= boxes; i++) {

			g.drawLine((i * boxes) + origin, origin, (i * 20) + origin, max + origin);
			g.drawLine(origin, (i * boxes) + origin, max + origin, (i * 20) + origin);

		}

	}

	private void drawFps(Graphics g) {

		g.drawString("fps: [" + fps + "]", 0, max + origin + 10);
	}

	public void paint(Graphics g) {

		super.paint(g);

		drawGrid(g);

		drawFps(g);

	}

}

