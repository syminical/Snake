
//theworldisquiethere

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Witchcraft extends JPanel {

	private final int max = 400, boxes = 20, origin = 10;
	private boolean gameRunning = false, firstClick = false;
	private int fps = 0, tick = 20, direction = -1, keyTracker = -1;
	private Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 32);
	private int[] borderGrid = {0xff0000, 0xff5700, 0xff8600, 0xffb300, 0xffd400, 0xffe700, 0xebff00, 0xbcff00, 0x1bff00, 0x00ff83, 0x00fff1, 0x00d4ff, 0x008cff, 0x0035ff, 0x5400ff, 0x9400ff, 0xd100ff, 0xff00f6, 0xff00c9,  0xff0091};
	private int[][] grid = new int[20][20];
	private BlockList snake = new BlockList();
	private BlockList randoms = new BlockList();
	private Block head = new Block();

	public Witchcraft() {

		setBackground(Color.BLACK);

		adapters();

	}

	private void adapters() {

		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (!firstClick) {

					requestFocus();
					firstClick = true;

				}

				if (!gameRunning && e.getX() >= 136 && e.getX() <= 288 && e.getY() >= 176 && e.getY() <= 230) {

					gameRunning = true;

					randoms.add(new Block(10));

				}

				//JOptionPane.showMessageDialog(null,"x: [" + e.getX() + "] | y: [" + e.getY() + "]" , null, JOptionPane.PLAIN_MESSAGE);

			}

		});

		this.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {

				if (gameRunning && keyTracker != e.getKeyCode()) {

					keyTracker = e.getKeyCode();

					switch (e.getKeyCode()) {

						case KeyEvent.VK_UP:

							if (direction != 2) direction = 0;
							return;

						case KeyEvent.VK_RIGHT:

							if (direction != 3) direction = 1;
							return;

						case KeyEvent.VK_DOWN:

							if (direction != 0) direction = 2;
							return;

						case KeyEvent.VK_LEFT:

							if (direction != 1) direction = 3;
							return;

						case KeyEvent.VK_SPACE:

							snake.add(new Block());
							snake.shade();
							snake.glow();
System.out.println("[" + head.getColour() + "]");
					}

				}
System.out.println("[" + head.getColour() + "]");
			}

		});

	}

	public void start() {

		double start = 0, end = start, totalTime = 0, totalFrames = 0, tracker = 0, sleepTime = 0, fUpdate = 0, tUpdate = 0, gUpdate = 0, sUpdate = 0, holder;

		snake.add(head);

		while (true) {

			start = System.currentTimeMillis();

			if (start - sUpdate >= ((gameRunning)? 500 : 1500) / snake.size()) {

				snake.shade();
				snake.glow();
				sUpdate = System.currentTimeMillis();
				
			}

			if (start - gUpdate >= ((gameRunning)? 1500 : 3000) / tick) {

				move();
				gUpdate = System.currentTimeMillis();

			}

			if (start - tUpdate >= 1000 / tick) {

				updateBorder();
				tUpdate = System.currentTimeMillis();

			}

			if (start - fUpdate >= ((gameRunning)? 1000/60 : 1000/10)) {

				repaint();
				totalFrames++;
				fUpdate = System.currentTimeMillis();

			}

			end = System.currentTimeMillis();

			totalTime += (end - start);

			sleepTime = (1000/60 - (end - start));

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

					System.out.println("[sleep fail]");

				};

			}

			totalTime += (System.currentTimeMillis() - end);

			if (totalTime >= 1000) {

				fps = (int)totalFrames;

				totalFrames = 0;
				totalTime = 0;
				end = 0;
				tracker = 0;

			}

		}

	}

	private void move() {

		if (gameRunning) {

			snake.shift();

			switch (direction) {

				case 0: head.up(); break;

				case 1: head.right(); break;

				case 2: head.down(); break;

				case 3: head.left(); break;

			}

			if (snake.collision()) {

				gameRunning = false;
				direction = -1;

			}
	
		} else {

			if (snake.size() > 1) {

				snake.shift();
				snake.remove();

			}

		}

	}

	private void drawGrid(Graphics g) {

		g.setColor(new Color(0x212121));

		for (int i = 0; i <= boxes; i++) {

			g.drawLine((i * boxes) + origin, origin, (i * 20) + origin, max + origin);
			g.drawLine(origin, (i * boxes) + origin, max + origin, (i * 20) + origin);

		}

		g.fillRect(0, 0, 10, 10);
		g.fillRect(410, 0, 10, 10);
		g.fillRect(410, 410, 10, 10);
		g.fillRect(0, 410, 10, 10);

	}

	private void drawFps(Graphics g) {

		g.setColor(Color.WHITE);

		g.drawString("fps: [" + fps + "]", 15, max + origin + 10);
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

	private void updateBorder() {

		int temp = borderGrid[19];

		for (int i = 0; i < 19; i++)

			borderGrid[19 - i] = borderGrid[18 - i];

		borderGrid[0] = temp;

	}

	private void drawBorder(Graphics g) {

		for (int i = 0; i < 20; i++) {

			g.setColor(new Color(borderGrid[i]));

			g.fillRect((10 + (20 * i)), 0, 20, 10);
			g.fillRect(410, (10 + (20 * i)), 10, 20);
			g.fillRect((390 - (20 * i)), 410, 20, 10);
			g.fillRect(0, (390 - (20 * i)), 10, 20);

		}

	}

	public void drawBlocks(Graphics g) {

		randoms.draw(g);
		snake.draw(g);

	}

	public void paint(Graphics g) {

		super.paint(g);

		drawBlocks(g);
		drawBorder(g);
		drawGrid(g);
		drawFps(g);

		if (!gameRunning)

			drawMenu(g);

	}

}

