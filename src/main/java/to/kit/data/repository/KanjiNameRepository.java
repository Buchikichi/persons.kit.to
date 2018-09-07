package to.kit.data.repository;

import java.util.ArrayList;
import java.util.List;

import to.kit.data.entity.KanjiName;

/**
 * KanjiName repository.
 * @author H.Sasai
 */
public abstract class KanjiNameRepository extends NameRepository {
	private List<KanjiName> list = new ArrayList<>();

	/**
	 * Choose a record.
	 * @return KanjiName
	 */
	public KanjiName choose() {
		int ix = (int) (Math.random() * Math.random() * this.list.size());

		return this.list.get(ix);
	}

	protected KanjiNameRepository(String targetname) {
		load(targetname, line -> {
			String[] element = line.split(",");
			KanjiName rec = new KanjiName();

			rec.setKana(element[0]);
			rec.setKanji(element[1]);
			this.list.add(rec);
		});
	}
}
