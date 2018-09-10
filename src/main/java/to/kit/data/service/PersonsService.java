package to.kit.data.service;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;

import to.kit.data.repository.Chooser;
import to.kit.data.web.form.ChooserOption;

/**
 * 情報作成サービス.
 * @author H.Sasai
 */
@Service
public class PersonsService {
	@Autowired
	private DefaultListableBeanFactory factory;

	private Object choose(Map<String, Object> objectMap, String chooserName, ChooserOption option) {
		Object obj = objectMap.get(chooserName);
		String depends = option.getDepends();

		if (depends != null && !depends.isEmpty()) {
			ChooserOption opt = new ChooserOption();

			BeanUtils.copyProperties(option, opt);
			opt.setDepends(null);
			option.setDependObject(choose(objectMap, depends, opt));
		}
		if (obj != null) {
			return obj;
		}
		String repositoryName = chooserName + "Repository";

		try {
			Chooser chooser = this.factory.getBean(repositoryName, Chooser.class);

			obj = chooser.choose(option);
			objectMap.put(chooserName, obj);
		} catch (BeansException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return obj;
	}

	private String makeRecord(PersonsCriteria criteria) {
		List<String> csv = new ArrayList<>();
		Map<String, Object> objectMap = new HashMap<>();

		for (ChooserOption option : criteria.getChoosers()) {
			String[] name = option.getName().split("[.]");
			String chooserName = name[0];
			String propertyName = name[1];
			Object obj = choose(objectMap, chooserName, option);

			if (obj == null) {
				continue;
			}
			PropertyDescriptor desc = BeanUtils.getPropertyDescriptor(obj.getClass(), propertyName);
			Method method = desc.getReadMethod();
			try {
				Object value = method.invoke(obj);

				csv.add(String.valueOf(value));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return String.join(",", csv);
	}

	/**
	 * 情報作成.
	 * @param out 出力
	 * @throws IOException 入出力例外
	 */
	public void createPersons(OutputStream out, PersonsCriteria criteria) throws IOException {
		for (int cnt = 0; cnt < criteria.getNumberOfPersons(); cnt++) {
			String line = makeRecord(criteria);

			out.write(line.getBytes());
			out.write(0x0a);
		}
	}
}
