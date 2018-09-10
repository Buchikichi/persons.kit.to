package to.kit.data.repository;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import to.kit.data.entity.KenAll;
import to.kit.data.web.form.ChooserOption;

/**
 * KenAll repository.
 * @author H.Sasai
 */
@Repository
@ConfigurationProperties(prefix = "ken-all")
public class KenAllRepository implements Chooser {
	private static final String KEN_ALL = "/data/ken_all.zip";
	private String charsetName;
	private Map<String, List<KenAll>> map = new HashMap<>();
//	private UnaryOperator<String> op = NameUtils.toHiragana;

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

	private void load(byte[] bytes) throws IOException {
		try (InputStream in = new ByteArrayInputStream(bytes);
				Reader isr = new InputStreamReader(in, this.charsetName);
				BufferedReader reader = new BufferedReader(isr)) {
			for (;;) {
				String line = reader.readLine();

				if (line == null) {
					break;
				}
				String[] elements = line.replaceAll("\"", "").split(",");
				String cityKana = elements[5];
				String city = elements[8];
				if (cityKana.startsWith("ｲｶﾆ") || cityKana.contains("(") || cityKana.contains(")") || city.contains("（")
						|| city.contains("）")) {
					continue;
				}
				KenAll rec = createRecord(elements);
				String key = rec.getX0401();
				List<KenAll> list = this.map.get(key);

				if (list == null) {
					list = new ArrayList<KenAll>();
					this.map.put(key, list);
				}
				list.add(rec);
			}
		}
	}

	public void load() {
		if (0 < this.map.size()) {
			return;
		}
		try (InputStream in = KenAllRepository.class.getResourceAsStream(KEN_ALL);
				ZipInputStream zip = new ZipInputStream(in)) {
			for (;;) {
				ZipEntry entry = zip.getNextEntry();

				if (entry == null) {
					break;
				}
				byte[] bytes = StreamUtils.copyToByteArray(zip);

				load(bytes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public KenAll choose(ChooserOption option) {
		load();
		List<KenAll> list = this.map.get("34");
		int ix = (int)(Math.random() * list.size());

		return list.get(ix);
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
