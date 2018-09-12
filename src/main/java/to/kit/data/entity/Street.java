package to.kit.data.entity;

/**
 * 番地・番号.
 * @author Hidetaka Sasai
 */
public class Street {
	private final String hi;
	private final String lo;

	public String getHi() {
		return this.hi;
	}
	public String getLo() {
		return this.lo;
	}

	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append(this.hi);
		if (this.lo != null) {
			buff.append('-');
			buff.append(this.lo);
		}
		return buff.toString();
	}

	public Street() {
		this.hi = String.valueOf((int) (Math.random() * 15) + 1);
		if (0 < (int) (Math.random() * 5)) {
			this.lo = String.valueOf((int) (Math.random() * 30) + 1);
		} else {
			this.lo = null;
		}
	}
}
