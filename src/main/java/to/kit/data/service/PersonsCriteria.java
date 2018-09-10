package to.kit.data.service;

import java.util.List;

import to.kit.data.web.form.ChooserOption;

public class PersonsCriteria {
	private List<ChooserOption> choosers;
	private int numberOfPersons;

	public List<ChooserOption> getChoosers() {
		return this.choosers;
	}
	public void setChoosers(List<ChooserOption> choosers) {
		this.choosers = choosers;
	}
	public int getNumberOfPersons() {
		return this.numberOfPersons;
	}
	public void setNumberOfPersons(int numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}
}
