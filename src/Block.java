
//theworldisquiethere

import java.awt.*;

public class Block {

	private int colour = 0xffffff, x = 10, y = 9;
	private boolean expired = false, glow = false;
	private Thread keeper;

	public Block() {

		keeper = null;

	}

	public Block(int container, int container2) {

		x = container;
		y = container2;

		keeper = new Thread(new BlockTimer(this));

		keeper.start();

	}

	public void draw(Graphics g) {

		if (glow) g.setColor((new Color(colour)).brighter());

		else g.setColor(new Color(colour));

		g.fillRect(x * 20 + 10, y * 20 + 10, 20, 20);

	}

	public void draw(Graphics g, int container) {

		if (glow) g.setColor((new Color(container)).brighter());

		else g.setColor((new Color(container)));

		g.fillRect(x * 20 + 10, y * 20 + 10, 20, 20);

	}

	public void up() {

		if (y > 0) y--;

		else y = 19;

	}

	public void right() {

		if (x < 19) x++; 

		else x = 0;

	}

	public void down() {

		if (y < 19) y++;

		else y = 0;

	}

	public void left() {

		if (x > 0) x--;

		else x = 19;

	}

	public int getColour() {

		return colour;

	}

	public int getX() {

		return x;

	}

	public int getY() {

		return y;

	}

	public boolean expired() {

		return expired;

	}

	public void setColour(int container) {

		colour = container;

	}

	public void setLocation(int container, int container2) {

		x = container;
		y = container2;

	}

	public void expire() {

		expired = true;

	}

	public void glow() {

		glow = !glow;

	}

}
