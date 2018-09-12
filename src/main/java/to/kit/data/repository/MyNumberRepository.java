package to.kit.data.repository;

import java.util.Arrays;

import org.springframework.stereotype.Repository;

import to.kit.data.entity.OrdinaryText;
import to.kit.data.service.PersonsCriteria;
import to.kit.data.web.form.ChooserOption;

/**
 * マイナンバー生成.
 * @author Hidetaka Sasai
 */
@Repository
public class MyNumberRepository implements Chooser {
	private static final int MAX = 9999;
	private static final int RETRY = 10;

	private int rack(long ... excludes) {
		int num = 1357;

		for (int ix = 0; ix < RETRY; ix++) {
			num = (int) (Math.random() * MAX);
			if (num % 1111 == 0) {
				continue;
			}
			if (Arrays.binarySearch(excludes, num) != -1) {
				continue;
			}
			break;
		}
		return num;
	}

	private String calculateCheckDigit(final String code) {
		int val = 0;
		int ix = 1;

		for (char c : code.toCharArray()) {
			int p = c - '0';
			int q = 7 - ix % 6;

			val += p * q;
			ix++;
		}
		return String.valueOf((11 - val % 11) % 11 % 10);
	}

	@Override
	public OrdinaryText choose(final PersonsCriteria criteria, final ChooserOption option) {
		long hi = rack();
		long mid = rack(hi);
		long lo = rack(hi, mid) / 10;
		long full = hi * 10000000L + mid * 1000 + lo;
		String code = String.format("%011d", Long.valueOf(full));
		String cd = calculateCheckDigit(code);
		String text = String.format("%04d %04d %03d%s", Long.valueOf(hi), Long.valueOf(mid), Long.valueOf(lo), cd);

		return new OrdinaryText(text);
	}
}
