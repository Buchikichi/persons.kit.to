package to.kit.data.entity;

/**
 * 漢字名とかなのセット.
 * @author Hidetaka Sasai
 */
public class KanjiName {
	private String kanji;
	private String kana;

	/**
	 * @return the kanji
	 */
	public String getKanji() {
		return this.kanji;
	}
	/**
	 * @param kanji the kanji to set
	 */
	public void setKanji(String kanji) {
		this.kanji = kanji;
	}
	/**
	 * @return the kana
	 */
	public String getKana() {
		return this.kana;
	}
	/**
	 * @param kana the kana to set
	 */
	public void setKana(String kana) {
		this.kana = kana;
	}
}
