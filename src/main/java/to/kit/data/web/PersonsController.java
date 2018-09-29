package to.kit.data.web;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import to.kit.data.service.PersonsCriteria;
import to.kit.data.service.PersonsService;
import to.kit.data.web.form.PersonsForm;

/**
 * PersonsController.
 * @author H.Sasai
 */
@Controller
@RequestMapping("/persons")
public class PersonsController {
	@Autowired
	private PersonsService personsService;

	/**
	 * 情報作成.
	 * @param form フォーム
	 * @param response HTTP response
	 * @throws IOException 入出力例外
	 */
	@RequestMapping("/create")
	public void create(@RequestBody PersonsForm form, HttpServletResponse response) throws IOException {
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(DateTimeFormatter.ofPattern("yyMMdd-HHmmss"));
		String filename = String.format("persons%s.zip", dateTime);
		String attachment = String.format("attachment;filename=\"%s\"", filename);
		String uuid = UUID.randomUUID().toString().replace("-", "");
		PersonsCriteria criteria = form.createCriteria();

		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Disposition", attachment);
		try (OutputStream outputStream = response.getOutputStream();
				ZipOutputStream out = new ZipOutputStream(outputStream);) {
			out.setComment(uuid);
			out.putNextEntry(new ZipEntry(String.format("persons%s.csv", dateTime)));
			this.personsService.createPersons(out, criteria);
			out.closeEntry();
		}
	}
}
