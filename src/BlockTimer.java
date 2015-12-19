
//theworldisquiethere

import java.awt.*;

public class BlockTimer implements Runnable {

	private int limit, colour = 255;
	private Block target;

	public BlockTimer(int container, Block container2) {

		limit = container;
		target = container2;

	}

	public void run() {

		try {

			while (colour > 0) {

				target.setColour((new Color(colour, colour, colour)).getRGB());
				Thread.sleep((int)((limit / 16.0) * 500));

				colour -= 16;

			}

			target.expire();

		} catch (InterruptedException e) {

		}

	}

}
