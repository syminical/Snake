
//theworldisquiethere

import java.awt.*;

public class BlockList {

	private BlockNode head = null, glowy = head;
	private int size = 0;

	public void add(Block container) {

		if (head == null) {

			head = new BlockNode(container);
			head.setNext(head);
			head.setPrevious(head);
			glowy = head;

		} else {

			BlockNode temp = new BlockNode(container);

			temp.setPrevious(head.getPrevious());
			temp.getPrevious().setNext(temp);
			temp.setNext(head);
			temp.getNext().setPrevious(temp);

			temp.getData().setLocation(temp.getPrevious().getData().getX(), temp.getPrevious().getData().getY());

		}

		size++;

	}

	private void remove(BlockNode container) {

		if (size == 0) return;

		BlockNode temp = head;

		for (int i = 0; i < size; i++) {

			if (temp.equals(container)) {

				if (size == 1) head = null;

				else {

					temp.getPrevious().setNext(temp.getNext());
					temp.getNext().setPrevious(temp.getPrevious());

				}

				size--;

				break;

			}

			temp = temp.getNext();

		}

	}

	public int size() {

		return size;

	}

	public void draw(Graphics g) {

		if (size == 0) return;

		BlockNode temp = head.getPrevious();

		for (int i = 0; i < size; i++) {

			temp.getData().draw(g);

			temp = temp.getPrevious();

		}

	}

	public void shift() {

		if (size == 0) return;

		BlockNode temp = head.getPrevious();

		for (int i = 0; i < size - 1; i++) {

			temp.getData().setLocation(temp.getPrevious().getData().getX(), temp.getPrevious().getData().getY());

			temp = temp.getPrevious();

		}

	}

	public void shade() {

		if (size < 1) return;

		int holder = 50;
		BlockNode temp = head.getPrevious();

		for (int i = 0; i < (size - 1); i++) {

			temp.getData().setColour((new Color(holder, holder, holder)).getRGB());

			if (holder < 240) holder += (int)(160 / (size - 1));

			temp = temp.getPrevious();

		}

	}

	public void glow() {

		if (size == 0) return;

		glowy.getData().glow();
		glowy = glowy.getNext();
		glowy.getData().glow();

	}

	public boolean collision() {

		int x = head.getData().getX(), y = head.getData().getY();
		BlockNode temp = head.getNext();

		for (int i = 0; i < size - 1; i++) {

			if (temp.getData().getX() == x && temp.getData().getY() == y)

				return true;

			temp = temp.getNext();

		}

		return false;

	}

	public void clear() {

		head = null;
		glowy = head;
		size = 0;

	}

}
