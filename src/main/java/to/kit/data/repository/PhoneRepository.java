package to.kit.data.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import to.kit.data.entity.KenAll;
import to.kit.data.entity.OrdinaryText;
import to.kit.data.service.PersonsCriteria;
import to.kit.data.web.form.ChooserOption;

@Repository
public class PhoneRepository extends TextRepository implements Chooser {
	private static final String TARGET_DATA = "zipTel";
	private Map<String, String> map = new HashMap<>();

	private Integer getRandomNumber() {
		return Integer.valueOf((int) (Math.random() * 9999));
	}

	private String createPhone(ChooserOption option) {
		String zip7 = ((KenAll) option.getDependObject()).getZip7();
		String hi = this.map.get(zip7);

		if (hi == null) {
			hi = "090";
		}
		int len = 6 - hi.length();
		String mid = String.format("%04d", getRandomNumber());
		String lo = String.format("%04d", getRandomNumber());

		mid = mid.substring(mid.length() - len);
		return hi + '-' + mid + '-' + lo;
	}

	private String createMobilePhone() {
		String hi;
		int ptn = (int) (Math.random() * Math.random() * 4);

		if (ptn == 0) {
			hi = "090";
		} else if (ptn == 1) {
			hi = "080";
		} else if (ptn == 2) {
			hi = "070";
		} else {
			hi = "050";
		}
		String mid = String.format("%04d", getRandomNumber());
		String lo = String.format("%04d", getRandomNumber());

		return hi + '-' + mid + '-' + lo;
	}

	@Override
	public OrdinaryText choose(PersonsCriteria criteria, ChooserOption option) {
		boolean isMobile = .4 < Math.random();
		String text = isMobile ? createMobilePhone() : createPhone(option);

		return new OrdinaryText(text);
	}

	public PhoneRepository() {
		load(TARGET_DATA, line -> {
			String[] element = line.split(",");
			String zip = element[0];
			String tel = '0' + element[1];

			this.map.put(zip, tel);
		});
	}
}
