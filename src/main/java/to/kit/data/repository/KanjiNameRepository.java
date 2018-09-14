package to.kit.data.repository;

import java.util.ArrayList;
import java.util.List;

import to.kit.data.entity.KanjiName;
import to.kit.data.service.PersonsCriteria;
import to.kit.data.web.form.ChooserOption;

/**
 * KanjiName repository.
 * @author H.Sasai
 */
public abstract class KanjiNameRepository extends TextRepository implements Chooser {
	private List<String> list = new ArrayList<>();

	/**
	 * Choose a record.
	 * @return KanjiName
	 */
	@Override
	public KanjiName choose(PersonsCriteria criteria, ChooserOption option) {
		int ix = (int) (Math.random() * Math.random() * Math.random() * this.list.size());
		String[] element = this.list.get(ix).split(",");
		KanjiName rec = new KanjiName(element);

		return rec;
	}

	protected KanjiNameRepository(String targetname) {
		load(targetname, line -> {
			this.list.add(line);
		});
	}
}
