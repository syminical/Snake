
//theworldisquiethere

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Witchcraft extends JPanel {

	private final int max = 400, boxes = 20, origin = 10;
	public static int tick = 15;
	private boolean gameRunning = false, firstClick = false, fpsToggle = false, f = false, p = false, rHeadOn = false, a = false, l = false, e = false;
	private int fps = 0, direction = -1, keyTracker = -1, directionTracker = -1, milestone = 0, rHead = 0;
	private double fpsTimer = 0, clock = 0, alexTimer = 0;
	private Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 30);
	private Font manaFont = new Font("Comic Sans MS", Font.BOLD, 20);
	private int[] borderGrid = {0xff0000, 0xff5700, 0xff8600, 0xffb300, 0xffd400, 0xffe700, 0xebff00, 0xbcff00, 0x1bff00, 0x00ff83, 0x00fff1, 0x00d4ff, 0x008cff, 0x0035ff, 0x5400ff, 0x9400ff, 0xd100ff, 0xff00f6, 0xff00c9,  0xff0091};
	private int[][] grid = new int[20][20];
	private Block head = new Block();
	private BlockList snake = new BlockList();
	private BlockDispenser dispenserCore = new BlockDispenser();
	private Thread dispenser = new Thread(dispenserCore);

	public Witchcraft() {

		setBackground(Color.BLACK);

		adapters();

	}

	private void adapters() {

		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (!gameRunning && e.getX() >= 136 && e.getX() <= 288 && e.getY() >= 176 && e.getY() <= 230) {

					if (!firstClick) { requestFocus(); firstClick = true; begin(); }

					else beginAgain();

				}

				//JOptionPane.showMessageDialog(null,"x: [" + e.getX() + "] | y: [" + e.getY() + "]" , null, JOptionPane.PLAIN_MESSAGE);

			}

		});

		this.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent ee) {

				if (gameRunning && keyTracker != ee.getKeyCode()) {

					keyTracker = ee.getKeyCode();

					switch (ee.getKeyCode()) {

						case KeyEvent.VK_UP:

							if (directionTracker != 2) direction = 0;
							break;

						case KeyEvent.VK_RIGHT:

							if (directionTracker != 3) direction = 1;
							break;

						case KeyEvent.VK_DOWN:

							if (directionTracker != 0) direction = 2;
							break;

						case KeyEvent.VK_LEFT:

							if (directionTracker != 1) direction = 3;
							break;

						case KeyEvent.VK_F:

							fpsTimer = System.currentTimeMillis();
							f = true; p = false; fpsToggle = false;
							break;

						case KeyEvent.VK_P:

							if (f && System.currentTimeMillis() - fpsTimer < 1000) p = true;
							break;

						case KeyEvent.VK_S:

							if (f && p && System.currentTimeMillis() - fpsTimer < 1000) fpsToggle = true;
							break;

						case KeyEvent.VK_A:

							alexTimer = System.currentTimeMillis();
							a = true; l = false; e = false; rHeadOn = false;
							break;

						case KeyEvent.VK_L:

							if (a && System.currentTimeMillis() - alexTimer < 1000) l = true;
							break;

						case KeyEvent.VK_E:

							if (a && l && System.currentTimeMillis() - alexTimer < 1000) e = true;
							break;

						case KeyEvent.VK_X:

							if (a && l && e && System.currentTimeMillis() - alexTimer < 1000) rHeadOn = true;

					}

				}

			}

		});

	}

	private void begin() {

		clock = System.currentTimeMillis();
		gameRunning = true;
		dispenser.start();

	}

	private void beginAgain() {

		snake.clear();
		tick = 15;
		keyTracker = -1;
		milestone = 0;
		snake.add(head);
		clock = System.currentTimeMillis();
		gameRunning = true;

	}

	public void start() {

		double start = 0, end = start, totalTime = 0, totalFrames = 0, tracker = 0, sleepTime = 0, fUpdate = 0, tUpdate = 0, sUpdate = 0, holder;

		snake.add(head);
		clock = System.currentTimeMillis();

		while (true) {

			start = System.currentTimeMillis();

			if (start - sUpdate >= 250 / snake.size()) {

				snake.glow();
				sUpdate = System.currentTimeMillis();
				
			}

			if (start - tUpdate >= 1000 / tick) {

				updateBorder();
				move();
				tUpdate = System.currentTimeMillis();

			}

			if (start - fUpdate >= ((gameRunning)? 1000/30 : 1000/10)) {

				repaint();
				totalFrames++;
				fUpdate = System.currentTimeMillis();

			}

			end = System.currentTimeMillis();

			totalTime += (end - start);

			sleepTime = (1000 / ((gameRunning)? 30 : 10) - (end - start));

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

			directionTracker = direction;

			if (!rHeadOn && snake.collision()) {

				gameRunning = false;
				direction = -1;
				clock = (int)((System.currentTimeMillis() - clock) / 1000);

				if (head.getX() >= 9 && head.getX() <= 11 && head.getY() == 7) head.down();

				else if (head.getX() >= 6 && head.getX() <= 13 && head.getY() == 12) head.up();

			} else if (dispenserCore.collision(head.getX(), head.getY())) {

				snake.add(new Block());
				snake.shade();

				if (snake.size() - milestone > 3) {

					milestone = snake.size();
					tick += 1;

				}

			}				
	
		} else snake.shift();

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

		g.drawString("fps: [" + fps + "] | tick: [" + tick + "]", 15, max + origin + 10);
	}

	private void drawButton(Graphics g) {

		g.setColor(new Color(200, 200, 200));
		g.fillRoundRect((135), (182), (155), (57), 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect( (138), (185), (150), (50), 40, 40 ); 	
		g.setColor(new Color(150, 150, 150));
		g.drawRoundRect( (141), (188), (145), (43), 40, 40);
		g.setColor(Color.WHITE);
		g.setFont(buttonFont);
		g.drawString("Click Me", (153), (220));

	}

	private void drawMenu(Graphics g) {

		drawButton(g);

	}

	private void drawExtendedMenu(Graphics g) {

		//g.setColor(new Color(200, 200, 200));
		g.setColor(Color.WHITE);
		g.setFont(manaFont);
		drawScore(g);
		drawTime(g);

	}

	private void drawScore(Graphics g) {

		String answer = "" + (snake.size() - 1);

		g.drawString(answer, (216 - (answer.length() * 12 / 2)), 170);

	}

	private void drawTime(Graphics g) {

		int min = ((int)(clock / 60)), sec = ((int)(clock % 60));
		String answer = ("" + ((min > 0)? ("" + min + "m ") : "") + sec + "s");

		g.drawString(answer, (216 - (answer.length() * 12 / 2)), 270);

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

		dispenserCore.draw(g);

		if (!rHeadOn)

			snake.draw(g);

		else {

			if (rHead == 19) rHead = 0;

			else rHead++;

			snake.draw(g, borderGrid[rHead]);
	
		}

	}

	public void paint(Graphics g) {

		super.paint(g);

		drawBlocks(g);
		drawBorder(g);
		drawGrid(g);
		if (fpsToggle) drawFps(g);

		if (!gameRunning) {

			drawMenu(g);
			if (firstClick) drawExtendedMenu(g);

		}

	}

}

