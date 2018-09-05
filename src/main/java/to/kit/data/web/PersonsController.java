package to.kit.data.web;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import to.kit.data.web.form.PersonsForm;

/**
 * PersonsController.
 * @author H.Sasai
 */
@Controller
@RequestMapping("/persons")
public class PersonsController {
	@RequestMapping("/create")
	public void create(PersonsForm form, HttpServletResponse response) throws IOException, CompressorException {
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
		String filename = String.format("persons%s.csv.gz", dateTime);
		String attachment = String.format("attachment;filename=\"%s\"", filename);

		response.setHeader("Content-Type", "application/x-compress");
		response.setHeader("Content-Disposition", attachment);
		try (OutputStream outputStream = response.getOutputStream();
				CompressorOutputStream out = new CompressorStreamFactory()
						.createCompressorOutputStream(CompressorStreamFactory.GZIP, outputStream)) {
			byte[] bytes = "nyanko".getBytes();

			out.write(bytes);
		}
	}
}
