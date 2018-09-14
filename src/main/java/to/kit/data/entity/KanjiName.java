package to.kit.data.entity;

/**
 * 漢字名とかなのセット.
 * @author Hidetaka Sasai
 */
public class KanjiName {
	private final String[] elements;

	/**
	 * @return the kana
	 */
	public String getKana() {
		return this.elements[0];
	}
	/**
	 * @return the kanji
	 */
	public String getKanji() {
		return this.elements[1];
	}

	public KanjiName(final String[] elements) {
		this.elements = elements;
	}
}
