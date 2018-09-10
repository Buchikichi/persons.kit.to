package to.kit.data.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import to.kit.data.entity.KanjiName;
import to.kit.data.entity.KenAll;
import to.kit.data.repository.FamilyNameRepository;
import to.kit.data.repository.FirstNameRepository;
import to.kit.data.repository.KenAllRepository;

/**
 * 情報作成サービス.
 * @author H.Sasai
 */
@Service
public class PersonsService {
	@Autowired
	private FamilyNameRepository familyNameRepository;
	@Autowired
	private FirstNameRepository firstNameRepository;
	@Autowired
	private KenAllRepository kenAllRepository;

	private String makeRecord() {
		List<String> csv = new ArrayList<>();
		KanjiName familyName = this.familyNameRepository.choose();
		KanjiName firstName = this.firstNameRepository.choose();
		KenAll kenAll = this.kenAllRepository.choose();

		csv.add(UUID.randomUUID().toString().replace("-", ""));
		csv.add(familyName.getKanji());
		csv.add(firstName.getKanji());
		csv.add(familyName.getKana());
		csv.add(firstName.getKana());
		csv.add(kenAll.getZip7());
		csv.add(kenAll.getPref());
		csv.add(kenAll.getMunic());
		csv.add(kenAll.getCity());
		return String.join(",", csv);
	}

	/**
	 * 情報作成.
	 * @param out 出力
	 * @throws IOException 入出力例外
	 */
	public void createPersons(OutputStream out, PersonsCriteria criteria) throws IOException {
		for (int cnt = 0; cnt < criteria.getNumberOfPersons(); cnt++) {
			String line = makeRecord();

			out.write(line.getBytes());
			out.write(0x0a);
		}
	}
}
