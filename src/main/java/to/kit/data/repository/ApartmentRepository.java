package to.kit.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import to.kit.data.entity.KanjiName;
import to.kit.data.entity.KenAll;
import to.kit.data.entity.OrdinaryText;
import to.kit.data.service.PersonsCriteria;
import to.kit.data.util.NameUtils;
import to.kit.data.web.form.ChooserOption;

@Repository
public class ApartmentRepository extends TextRepository implements Chooser {
	private static final String CHOME = "丁目";
	private static final String[] KU_CHO = { "区", "町" };

	private List<String> list = new ArrayList<>();
	@Autowired
	private FamilyNameRepository familyNameRepository;
	@Autowired
	private FirstNameRepository firstNameRepository;
	@Autowired
	private NameUtils nameUtils;

	private String chooseName(KenAll kenAll) {
		KanjiName familyName = this.familyNameRepository.choose(null, null);
		KanjiName firstName = this.firstNameRepository.choose(null, null);
		List<String> affixList = List.of(kenAll.getCity(), kenAll.getCityKana(), familyName.getKanji(),
				familyName.getKana(), firstName.getKanji(), firstName.getKana());
		int ix = (int) (Math.random() * Math.random() * Math.random() * affixList.size());
		String affix = affixList.get(ix);

		if (affix.contains(CHOME)) {
			return "";
		}
		for (String ku : KU_CHO) {
			if (affix.contains(ku) && !affix.endsWith(ku)) {
				int start = affix.indexOf(ku);

				affix = affix.substring(start + 1);
			}
		}
		return this.nameUtils.toFull.apply(affix);
	}

	@Override
	public OrdinaryText choose(PersonsCriteria criteria, ChooserOption option) {
		if (0 < (int) (Math.random() * 8)) {
			return new OrdinaryText("");
		}
		KenAll kenAll = (KenAll) option.getDependObject();
		int ix = (int) (Math.random() * this.list.size());
		String apartment = this.list.get(ix);

		if (apartment.contains("+")) {
			String name = chooseName(kenAll);

			apartment = apartment.replace("+", name);
		} else if (apartment.contains("*")) {
			String name = "";

			if (0 < Math.random() * 3) {
				name = chooseName(kenAll);
			}
			apartment = apartment.replace("*", name);
		}
		int floor = ((int) (Math.random() * Math.random() * Math.random() * 20) + 1) * 100;
		int roomNum = floor + (int) (Math.random() * Math.random() * Math.random() * 30) + 1;

		apartment += this.nameUtils.toFull.apply(String.valueOf(roomNum));
		return new OrdinaryText(apartment);
	}

	public ApartmentRepository() {
		load("apartment", line -> {
			this.list.add(line);
		});
	}
}
