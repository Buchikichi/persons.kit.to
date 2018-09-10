package to.kit.data.web.form;

public class ChooserOption {
	private String name;
	private String depends;
	private Object dependObject;
	private String separator;

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepends() {
		return this.depends;
	}
	public void setDepends(String depends) {
		this.depends = depends;
	}
	public Object getDependObject() {
		return this.dependObject;
	}
	public void setDependObject(Object dependObject) {
		this.dependObject = dependObject;
	}
	public String getSeparator() {
		return this.separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
