package to.kit.data.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Text repository.
 * @author H.Sasai
 */
public abstract class TextRepository {
	private static final Logger LOG = LoggerFactory.getLogger(TextRepository.class);

	protected void load(InputStream stream, Charset cs, Consumer<String> process) {
		try (Reader reader = new InputStreamReader(stream, cs); BufferedReader in = new BufferedReader(reader)) {
			in.lines().forEach(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void load(String targetName, Consumer<String> process) {
		String resourceName = "/data/" + targetName + ".txt";

		LOG.info("Load:{}", targetName);
		try (InputStream stream = getClass().getResourceAsStream(resourceName)) {
			load(stream, Charset.defaultCharset(), process);
			LOG.info("Loaded.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
