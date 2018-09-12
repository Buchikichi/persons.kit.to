package to.kit.data.repository;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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
	private static final String KEN_ALL = "ken_all";
	private static final int NUM_OF_PREFECTURES = 47;
	private static final String CHOME = "丁目";
	private String charsetName;
	private Map<String, List<KenAll>> map = new HashMap<>();
//	private UnaryOperator<String> op = NameUtils.toHiragana;
	@Autowired
	private ApartmentRepository apartmentRepository;
	@Autowired
	private NameUtils nameUtils;

	private KenAll createRecord(String[] elements) {
		KenAll rec = new KenAll();
		String x0402 = elements[0];
		String x0401 = x0402.substring(0, 2);
		String prefKana = elements[3];
		String municipalityKana = elements[4];
		String cityKana = elements[5];

		rec.setX0401(x0401);
		rec.setX0402(x0402);
		rec.setZip5(elements[1]);
		rec.setZip7(elements[2]);
		rec.setPrefKana(prefKana);
		rec.setMunicKana(municipalityKana);
		rec.setCityKana(cityKana);
		rec.setPref(elements[6]);
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

	private void load() {
		if (0 < this.map.size()) {
			return;
		}
		load(KEN_ALL, Charset.forName(this.charsetName), line -> {
			String[] elements = line.replaceAll("\"", "").split(",");
			String cityKana = elements[5];
			String city = elements[8];
			if (cityKana.startsWith("ｲｶﾆ") || cityKana.contains("(") || cityKana.contains(")") || city.contains("（")
					|| city.contains("）")) {
				return;
			}
			KenAll rec = createRecord(elements);
			String key = rec.getX0401();
			List<KenAll> list = this.map.get(key);

			if (list == null) {
				list = new ArrayList<KenAll>();
				this.map.put(key, list);
			}
			list.add(rec);
		});
	}

	private void createStreet(KenAll rec) {
		String city = rec.getCity();
		Street street = new Street();

		if (rec.isSomeChome() && !city.contains(CHOME)) {
			String num = String.valueOf((int) (Math.random() * 5) + 1);
			String chomeStr = this.nameUtils.toKansuuji.apply(num) + CHOME;

			city += chomeStr;
		}
		city += street.toString();
		if ((int) (Math.random() * 8) == 0) {
			String apartment= this.apartmentRepository.choose(rec);
			int floor = ((int) (Math.random() * 9) + 1) * 100;
			int roomNum = floor + (int) (Math.random() * 30) + 1;

			city += apartment + roomNum;
		}
		rec.setCity(city);
	}

	private String choosePrefecture(String prefectures) {
		if (prefectures == null || prefectures.isEmpty()) {
			int num = (int)(Math.random() * NUM_OF_PREFECTURES) + 1;

			return String.format("%02d", Integer.valueOf(num));
		}
		String[] elements = prefectures.split("[,]");
		int ix = (int)(Math.random() * elements.length);

		return elements[ix];
	}

	@Override
	public KenAll choose(PersonsCriteria criteria, ChooserOption option) {
		load();
		List<KenAll> list = this.map.get(choosePrefecture(criteria.getPrefectures()));
		int ix = (int)(Math.random() * list.size());
		KenAll source = list.get(ix);
		KenAll rec = new KenAll();

		BeanUtils.copyProperties(source, rec);
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
