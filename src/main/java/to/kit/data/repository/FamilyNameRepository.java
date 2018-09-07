package to.kit.data.repository;

import org.springframework.stereotype.Repository;

/**
 * FamilyName repository
 * @author H.Sasai
 */
@Repository
public class FamilyNameRepository extends KanjiNameRepository {
	private static final String TARGET_DATA = "familyName";

	private FamilyNameRepository() {
		super(TARGET_DATA);
	}
}
