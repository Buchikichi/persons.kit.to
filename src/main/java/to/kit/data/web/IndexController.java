package to.kit.data.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Indexコントローラー.
 * @author H.Sasai
 */
@Controller
@RequestMapping("/")
public class IndexController {
	/**
	 * Index.
	 * @return 画面
	 */
	@RequestMapping
	public String index() {
		return "index";
	}
}
