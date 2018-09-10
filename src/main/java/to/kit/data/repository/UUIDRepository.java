package to.kit.data.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import to.kit.data.entity.OrdinaryText;
import to.kit.data.web.form.ChooserOption;

@Repository
public class UUIDRepository implements Chooser {
	@Override
	public Object choose(ChooserOption option) {
		return new OrdinaryText(UUID.randomUUID().toString());
	}
}
