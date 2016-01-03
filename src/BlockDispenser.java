
//theworldisquiethere

import java.util.ArrayList;
import java.awt.*;

public class BlockDispenser implements Runnable {

	private ArrayList<Block> holder;	

	public BlockDispenser() {

		holder = new ArrayList<Block>();

	}

	public void run() {

		try {

			while (true) {

				holder.add(new Block(((int)(Math.random() * 20)), ((int)(Math.random() * 20))));
				Thread.sleep((500 / Witchcraft.tick) * 128);
				clearExpired();

			}

		} catch (Exception e) {
e.printStackTrace();
		}

	}

	public void draw(Graphics g) {

		if (holder.size() == 0) return;

		for (int i = 0; i < holder.size(); i++)

			holder.get(i).draw(g);

	}

	public void clearExpired() {

		if (holder.size() == 0) return;

		for (int i = 0; i < holder.size(); i++)

			if (holder.get(i).expired()) holder.remove(i);

	}

	public boolean collision(int container, int container2) {

		if (holder.size() == 0) return false;

		for (int i = 0; i < holder.size(); i++)

			if (holder.size() > 0 && holder.get(i).getX() == container && holder.get(i).getY() == container2) {

				holder.remove(i);
				return true;

			}

		return false;

	}

}
