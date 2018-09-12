package to.kit.data.repository;

import to.kit.data.service.PersonsCriteria;
import to.kit.data.web.form.ChooserOption;

public interface Chooser {
	Object choose(PersonsCriteria criteria, ChooserOption option);
}
