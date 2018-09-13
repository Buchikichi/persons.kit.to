package to.kit.data.repository;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

import to.kit.data.entity.KenAll;
import to.kit.data.entity.Street;
import to.kit.data.service.PersonsCriteria;
import to.kit.data.util.NameUtils;
import to.kit.data.web.form.ChooserOption;

/**
 * KenAll repository.
 * @author H.Sasai
 */
@Repository
@ConfigurationProperties(prefix = "ken-all")
public class KenAllRepository extends ZipRepository implements Chooser {
	private static final Logger LOG = LoggerFactory.getLogger(KenAllRepository.class);
	private static final String KEN_ALL = "ken_all";
	private static final String CHOME = "丁目";
	private String charsetName;
	private Map<String, List<String>> map = new HashMap<>();
	private Map<String, String[]> prefMap = new HashMap<>();
	private boolean loaded;
	@Autowired
	private NameUtils nameUtils;

	private KenAll createRecord(String line) {
		String[] elements = line.split("\t");
		KenAll rec = new KenAll();
		String x0402 = elements[0];
		String x0401 = x0402.substring(0, 2);
		String[] pref = this.prefMap.get(x0401);
		String prefKana = pref[0];
		String municipalityKana = elements[4];
		String cityKana = elements[5];

		rec.setX0401(x0401);
		rec.setX0402(x0402);
		rec.setZip5(elements[1]);
		rec.setZip7(elements[2]);
		rec.setPrefKana(prefKana);
		rec.setMunicKana(municipalityKana);
		rec.setCityKana(cityKana);
		rec.setPref(pref[1]);
		rec.setMunic(elements[7]);
		rec.setCity(elements[8]);
		rec.setSomeCity("1".equals(elements[9]));
		rec.setSomeNumber("1".equals(elements[10]));
		rec.setSomeChome("1".equals(elements[11]));
		rec.setSomeZip("1".equals(elements[12]));
		rec.setUpdate(elements[13]);
		rec.setReason(elements[14]);
		return rec;
	}

	private void createStreet(KenAll rec) {
		String city = rec.getCity();
		String street = "";

		if (rec.isSomeChome() && !city.contains(CHOME)) {
			String num = String.valueOf((int) (Math.random() * 8) + 1);

			street += this.nameUtils.toKansuuji.apply(num) + CHOME;
		}
		street += new Street().toString();
		rec.setStreet(street);
	}

	private String choosePrefecture(String[] prefectures) {
		String pref = null;

		if (prefectures != null) {
			int ix = (int) (Math.random() * prefectures.length);

			pref = prefectures[ix];
		}
		if (prefectures == null || !this.map.containsKey(pref)) {
			Object[] keys = this.map.keySet().toArray();
			int num = (int) (Math.random() * keys.length);

			pref = String.valueOf(keys[num]);
		}
		return pref;
	}

	private void load() {
		if (this.loaded) {
			return;
		}
		this.loaded = true;
		load(KEN_ALL, Charset.forName(this.charsetName), line -> {
			String[] elements = line.split(",");
			String cityKana = elements[5];
			String city = elements[8];

			if (cityKana.startsWith("\"ｲｶﾆ") || cityKana.contains("(") || cityKana.contains(")") || city.contains("（")
					|| city.contains("）") || city.contains("、")) {
				return;
			}
			String key = elements[0].substring(0, 2);
			List<String> list = this.map.get(key);

			for (int ix = 0; ix < elements.length; ix++) {
				String str = elements[ix];

				if (str.charAt(0) != '"') {
					continue;
				}
				elements[ix] = str.substring(1, str.length() - 1);
			}
			if (list == null) {
				list = new ArrayList<String>();
				this.map.put(key, list);
				this.prefMap.put(key, new String[] { elements[3], elements[6] });
				LOG.info("{}:{}", key, elements[3]);
			}
			elements[3] = null;
			elements[6] = null;
			elements[12] = null;
			elements[13] = null;
			elements[14] = null;
			list.add(String.join("\t", elements));
		});
	}

	@Override
	public KenAll choose(PersonsCriteria criteria, ChooserOption option) {
		load();
		List<String> list = this.map.get(choosePrefecture(criteria.getPrefectures()));
		int ix = (int)(Math.random() * list.size());
		KenAll rec = createRecord(list.get(ix));

		createStreet(rec);
		return rec;
	}

	/**
	 * @return the charsetName
	 */
	public String getCharsetName() {
		return this.charsetName;
	}

	/**
	 * @param charsetName the charsetName to set
	 */
	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}
}
