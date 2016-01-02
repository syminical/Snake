
//theworldisquiethere

import java.awt.*;

public class BlockTimer implements Runnable {

	private int colour = 255;
	private Block target;

	public BlockTimer(Block container) {

		target = container;

	}

	public void run() {

		try {

			Thread.sleep(1000);

			while (colour > 0) {

				target.setColour((new Color(colour, colour, colour)).getRGB());
				Thread.sleep(1000 / Witchcraft.tick);

				colour -= (2);

			}

			target.expire();

		} catch (InterruptedException e) {

		}

	}

}
