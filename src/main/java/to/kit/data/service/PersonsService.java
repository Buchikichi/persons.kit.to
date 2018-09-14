package to.kit.data.service;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOG = LoggerFactory.getLogger(PersonsService.class);

	@Autowired
	private DefaultListableBeanFactory factory;

	private Object choose(Map<String, Object> objectMap, String chooserName, PersonsCriteria criteria,
			ChooserOption option) {
		Object obj = objectMap.get(chooserName);
		String depends = option.getDepends();

		if (depends != null && !depends.isEmpty()) {
			ChooserOption opt = new ChooserOption();

			BeanUtils.copyProperties(option, opt);
			opt.setDepends(null);
			option.setDependObject(choose(objectMap, depends, criteria, opt));
		}
		if (obj != null) {
			return obj;
		}
		String repositoryName = chooserName + "Repository";

		try {
			Chooser chooser = this.factory.getBean(repositoryName, Chooser.class);

			obj = chooser.choose(criteria, option);
			objectMap.put(chooserName, obj);
		} catch (BeansException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return obj;
	}

	private String makeRecord(PersonsCriteria criteria) {
		StringBuilder buff = new StringBuilder();
		Map<String, Object> objectMap = new HashMap<>();

		for (ChooserOption option : criteria.getChoosers()) {
			String[] name = option.getName().split("[.]");
			Object obj = choose(objectMap, name[0], criteria, option);

			if (obj instanceof String) {
				buff.append(obj);
			} else
			if (obj != null) {
				PropertyDescriptor desc = BeanUtils.getPropertyDescriptor(obj.getClass(), name[1]);
				Method method = desc.getReadMethod();

				try {
					buff.append(method.invoke(obj));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			buff.append(option.getSeparator());
		}
		return buff.toString();
	}

	/**
	 * 情報作成.
	 * @param out 出力
	 * @throws IOException 入出力例外
	 */
	public void createPersons(OutputStream out, PersonsCriteria criteria) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		Format fmt = new DecimalFormat();
		String free = fmt.format(Long.valueOf(runtime.freeMemory()));
		String total = fmt.format(Long.valueOf(runtime.totalMemory()));
		String max = fmt.format(Long.valueOf(runtime.maxMemory()));

		LOG.info("free:{}/total:{}/max:{}", free, total, max);
		LOG.info("Request:" + criteria.getNumberOfPersons());
		for (int cnt = 0; cnt < criteria.getNumberOfPersons(); cnt++) {
			String line = makeRecord(criteria);

			out.write(line.getBytes());
			out.write(0x0a);
		}
		LOG.info("Done.");
	}
}
