
//theworldisquiethere

public class BlockNode {

	private Block data;
	private BlockNode next = null;
	private BlockNode previous = null;

	public BlockNode(Block container) {

		data = container;

	}

	public Block getData() {

		return data;

	}

	public BlockNode getNext() {

		return next;

	}


	public BlockNode getPrevious() {

		return previous;

	}

	public void setNext(BlockNode container) {

		next = container;

	}

	public void setPrevious(BlockNode container) {

		previous = container;

	}

}
