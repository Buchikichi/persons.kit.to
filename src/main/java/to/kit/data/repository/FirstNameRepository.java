package to.kit.data.repository;

import org.springframework.stereotype.Repository;

/**
 * FirstName repository
 * @author H.Sasai
 */
@Repository
public class FirstNameRepository extends KanjiNameRepository {
	private static final String TARGET_DATA = "firstName";

	public FirstNameRepository() {
		super(TARGET_DATA);
	}
}
