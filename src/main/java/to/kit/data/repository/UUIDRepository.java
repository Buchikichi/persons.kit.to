package to.kit.data.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import to.kit.data.service.PersonsCriteria;
import to.kit.data.web.form.ChooserOption;

@Repository
public class UUIDRepository implements Chooser {
	@Override
	public String choose(PersonsCriteria criteria, ChooserOption option) {
		return UUID.randomUUID().toString();
	}
}
