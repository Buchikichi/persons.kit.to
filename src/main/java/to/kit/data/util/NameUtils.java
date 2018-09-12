package to.kit.data.util;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Component;

@Component
public class NameUtils {
	private static final String HALF_KANA = "ｧ ｱ ｨ ｲ ｩ ｳ ｪ ｴ ｫ ｵ ｶ ｶﾞｷ ｷﾞｸ ｸﾞｹ ｹﾞｺ ｺﾞｻ ｻﾞｼ ｼﾞｽ ｽﾞｾ ｾﾞｿ ｿﾞﾀ ﾀﾞﾁ ﾁﾞｯ ﾂ ﾂﾞﾃ ﾃﾞﾄ ﾄﾞﾅ ﾆ ﾇ ﾈ ﾉ ﾊ ﾊﾞﾊﾟﾋ ﾋﾞﾋﾟﾌ ﾌﾞﾌﾟﾍ ﾍﾞﾍﾟﾎ ﾎﾞﾎﾟﾏ ﾐ ﾑ ﾒ ﾓ ｬ ﾔ ｭ ﾕ ｮ ﾖ ﾗ ﾘ ﾙ ﾚ ﾛ * ﾜ ｲ ｴ ｦ ﾝ ｳﾞ";
	private static final String SUUJI = "〇一二三四五六七八九";

	private UnaryOperator<String> toKatakanaLetter = str -> {
		int ix = HALF_KANA.indexOf(str);

		if (ix == -1) {
			char ch = str.charAt(0);
			UnicodeBlock block = UnicodeBlock.of(ch);

			if (UnicodeBlock.HIRAGANA.equals(block)) {
				ix = ch - 'ぁ';
			}
		} else {
			ix /= 2;
		}
		if (ix == -1) {
			return str;
		}
		return String.valueOf((char) ('ァ' + ix));
	};

	private UnaryOperator<String> toHiraganaLetter = str -> {
		int ix = HALF_KANA.indexOf(str);

		if (ix == -1) {
			char ch = str.charAt(0);
			UnicodeBlock block = UnicodeBlock.of(ch);

			if (UnicodeBlock.KATAKANA.equals(block)) {
				ix = ch - 'ァ';
			}
		} else {
			ix /= 2;
		}
		if (ix == -1) {
			return str;
		}
		return String.valueOf((char) ('ぁ' + ix));
	};

	public UnaryOperator<String> toKansuuji = text -> {
		StringBuilder buff = new StringBuilder();

		for (final char c : text.toCharArray()) {
			if ('0' <= c && c <= '9') {
				int ix = c - '0';
				buff.append(SUUJI.charAt(ix));
				continue;
			}
			buff.append(c);
		}
		return buff.toString();
	};

	public UnaryOperator<String> toFull = text -> {
		List<String> list = new ArrayList<>();
		char[] chars = text.toCharArray();
		char punctuation = '\0';

		for (int ix = chars.length - 1; 0 <= ix; ix--) {
			char ch = chars[ix];

			if ('!' <= ch && ch <= '~') {
				list.add(String.valueOf((char) ('！' + (ch - '!'))));
				continue;
			}
			if (ch == 'ﾞ' || ch == 'ﾟ') {
				punctuation = ch;
				continue;
			}
			String letter = String.valueOf(ch);

			if (punctuation != '\0') {
				letter += String.valueOf(punctuation);
				punctuation = '\0';
			}
			int cd = HALF_KANA.indexOf(letter);

			if (cd != -1) {
				list.add(String.valueOf((char) ('ァ' + cd / 2)));
				continue;
			}
			list.add(letter);
		}
		Collections.reverse(list);
		return String.join("", list);
	};
}
