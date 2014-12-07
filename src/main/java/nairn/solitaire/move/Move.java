package nairn.solitaire.move;

public class Move {
	private int x;
	private int y;
	private Direction direction;

	public Move(final int x, final int y, final Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("(");
		sb.append(x+1);
		sb.append(", ").append(y+1);
		sb.append(", ").append(direction);
		sb.append(')');
		return sb.toString();
	}
}
