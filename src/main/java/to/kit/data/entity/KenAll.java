package to.kit.data.entity;

public class KenAll {
	private String[] elements;
	/** prefecture. */
	private String prefKana;
	private String pref;
	// ext
	private String street;

	public String getX0401() {
		return getX0402().substring(0, 2);
	}
	public String getX0402() {
		return this.elements[0];
	}
	public String getZip5() {
		return this.elements[1];
	}
	public String getZip7() {
		return this.elements[2];
	}
	public String getPrefKana() {
		return this.prefKana;
	}
	public void setPrefKana(String prefKana) {
		this.prefKana = prefKana;
	}
	public String getMunicKana() {
		return this.elements[4];
	}
	public String getCityKana() {
		return this.elements[5];
	}
	public String getPref() {
		return this.pref;
	}
	public void setPref(String pref) {
		this.pref = pref;
	}
	public String getMunic() {
		return this.elements[7];
	}
	public String getCity() {
		return this.elements[8];
	}
	public boolean isSomeCity() {
		return "1".equals(this.elements[9]);
	}
	public boolean isSomeNumber() {
		return "1".equals(this.elements[10]);
	}
	public boolean isSomeChome() {
		return "1".equals(this.elements[11]);
	}
	public boolean isSomeZip() {
		return "1".equals(this.elements[12]);
	}
	public String getUpdate() {
		return this.elements[13];
	}
	public String getReason() {
		return this.elements[14];
	}
	public String getStreet() {
		return this.street;
	}
	public void setStreet(String value) {
		this.street = value;
	}

	public KenAll(String[] elements) {
		this.elements = elements;
	}
}
