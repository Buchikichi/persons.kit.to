package to.kit.data.web.form;

import java.util.List;

import to.kit.data.service.PersonsCriteria;

public class PersonsForm {
	private static final int MAX_PERSONS = 1000000;
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

	public PersonsCriteria createCriteria() {
		PersonsCriteria criteria = new PersonsCriteria();
		int num = this.numberOfPersons;

		criteria.setChoosers(this.choosers);
		if (num < 1) {
			num = 1;
		} else if (MAX_PERSONS < num) {
			num = MAX_PERSONS;
		}
		criteria.setNumberOfPersons(num);
		return criteria;
	}
}
