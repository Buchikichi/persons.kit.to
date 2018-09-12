package to.kit.data.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import to.kit.data.entity.OrdinaryText;
import to.kit.data.service.PersonsCriteria;
import to.kit.data.web.form.ChooserOption;

@Repository
public class UUIDRepository implements Chooser {
	@Override
	public Object choose(PersonsCriteria criteria, ChooserOption option) {
		return new OrdinaryText(UUID.randomUUID().toString());
	}
}
