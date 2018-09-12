package to.kit.data.repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

public abstract class ZipRepository extends TextRepository {
	private static final Logger LOG = LoggerFactory.getLogger(ZipRepository.class);

	protected void load(String targetName, Charset cs, Consumer<String> process) {
		String resourceName = "/data/" + targetName + ".zip";

		LOG.info("Load:{}", targetName);
		try (InputStream in = getClass().getResourceAsStream(resourceName);
				ZipInputStream zip = new ZipInputStream(in)) {
			for (;;) {
				ZipEntry entry = zip.getNextEntry();

				if (entry == null) {
					break;
				}
				byte[] bytes = StreamUtils.copyToByteArray(zip);

				try (InputStream stream = new ByteArrayInputStream(bytes)){
					load(stream, cs, process);
				}
			}
			LOG.info("Loaded.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
