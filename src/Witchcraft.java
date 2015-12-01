
//theworldisquiethere

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Witchcraft extends JPanel {

	private final int max = 400, boxes = 20, origin = 10;
	private boolean gameRunning = false, menuRunning = true;
	private int fps = 0;
	private Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 32);

	public Witchcraft() {

		setBackground(Color.WHITE);

		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (menuRunning && e.getX() >= 136 && e.getX() <= 288 && e.getY() >= 176 && e.getY() <= 230) {

					gameRunning = true;
					menuRunning = false;

				}

				//JOptionPane.showMessageDialog(null,"x: [" + e.getX() + "] | y: [" + e.getY() + "]" , null, JOptionPane.PLAIN_MESSAGE);

			}

		});

	}

	public void start() {

		fps();

	}

	private void fps() {

		double start = 0, end = start, totalTime = 0, totalFrames = 0, gameTarget = 1000/60, menuTarget = 1000/10, tracker = 0, sleepTime = 0;

		while (gameRunning || menuRunning) {

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

				if (tracker > 0) {

					if (sleepTime <= tracker) {

						tracker -= sleepTime;

						sleepTime = 0;

					} else {

						sleepTime -= tracker;

						tracker = 0;

					}

				}

				try {

					Thread.sleep((int)sleepTime);
			
				} catch(InterruptedException e) {

					System.out.println("Thread fail.");

				};

			}

			totalFrames++;

			totalTime += (System.currentTimeMillis() - end);

			if (totalTime >= 1000) {

				fps = (int)totalFrames;

				totalFrames = 0;
				totalTime = 0;
				end = 0;
				tracker = 0;

				System.gc();

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

		//g.setColor(Color.WHITE);

		g.drawString("fps: [" + fps + "]", 0, max + origin + 10);
	}

	private void drawButton(Graphics g) {

		g.setColor(Color.YELLOW);
		g.fillRoundRect((135), (175), (155), (57), 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect( (138), (178), (150), (50), 40, 40 ); 	
		g.setColor(Color.YELLOW);
		g.drawRoundRect( (141), (181), (145), (43), 40, 40);
		g.setFont(buttonFont);
		g.drawString("BEGIN", (165 - 1), (214));

	}

	private void drawMenu(Graphics g) {

		drawButton(g);

	}

	public void paint(Graphics g) {

		super.paint(g);
		drawGrid(g);
		drawFps(g);

		if (menuRunning)

			drawMenu(g);

	}

}

