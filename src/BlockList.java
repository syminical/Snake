
//theworldisquiethere

import java.awt.*;

public class BlockList {

	private BlockNode head = null;
	private int size = 0, glowSpot = 1;

	public void add(Block container) {

		if (head == null) {

			head = new BlockNode(container);
			head.setNext(head);
			head.setPrevious(head);
			head.getData().setColour(0xffffff);

		} else {

			BlockNode temp = head.getNext();

			head.setNext(new BlockNode(container));
			head.getNext().setPrevious(head);
			head = head.getNext();
			head.setNext(temp);
			head.getNext().setPrevious(head);
			head.getData().setLocation(temp.getData().getX(), temp.getData().getY());

		}

		size++;

	}

	private void remove(BlockNode container) {

		BlockNode temp = head;

		for (int i = 0; i < size; i++) {

			if (temp.getNext().equals(container)) {

				if (size == 1) head = null;

				else {

					temp.setNext(temp.getNext().getNext()); 
					temp.getNext().setPrevious(temp);

				}

				size--;

				break;

			}

			temp = temp.getNext();

		}

	}

	public void remove() {

		remove(head.getNext().getNext());

	}

	public void clearExpired() {

		BlockNode temp = head.getNext();

		for (int i = 0; i < size; i++) {

			if (temp.getData().expired()) remove(temp);

			temp = temp.getNext();

		}

	}

	public int size() {

		return size;

	}

	public void draw(Graphics g) {

		BlockNode temp = head;

		for (int i = 0; i < size; i++) {

			if (!temp.getData().expired()) temp.getData().draw(g);

			temp = temp.getPrevious();

		}

	}

	public void shift() {

		BlockNode temp = head;

		for (int i = 0; i < size - 1; i++) {

			temp.getData().setLocation(temp.getPrevious().getData().getX(), temp.getPrevious().getData().getY());

			temp = temp.getPrevious();

		}

	}

	public void shade() {

		int holder = 50;
		BlockNode temp = head;

		if (size > 1) {

			for (int i = 0; i < (size - 1); i++) {

				temp.getData().setColour((new Color(holder, holder, holder)).getRGB());

				if (holder < 240) holder += (int)(160 / (size - 1));

				temp = temp.getPrevious();

			}

		} else head.getData().setColour(0xffffff);

	}

	public void glow() {

		BlockNode temp = head.getNext();

		for (int i = 0; i < glowSpot; i++)

			temp = temp.getNext();

		temp.getData().setColour((new Color(temp.getData().getColour())).brighter().getRGB());
		glowSpot++;

		if (glowSpot > size - 1) glowSpot = 1;

	}

	public boolean collision() {

		int x = head.getNext().getData().getX(), y = head.getNext().getData().getY();
		BlockNode temp = head.getNext().getNext();

		for (int i = 0; i < size - 1; i++) {

			if (temp.getData().getX() == x && temp.getData().getY() == y)

				return true;

			temp = temp.getNext();

		}

		return false;

	}

	public String toString() {

		String answer = "| ";

		if (size > 0) {

			BlockNode temp = head.getNext();

			for (int i = 0; i < size; i++) {

				answer += "[" + temp.getData().getColour() + "] ";

				temp = temp.getNext();

			}

		}

		return answer + "|";

	}

}
