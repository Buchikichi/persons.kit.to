package to.kit.data.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Consumer;

/**
 * Text repository.
 * @author H.Sasai
 */
public abstract class TextRepository {
	protected void load(String targetName, Consumer<String> process) {
		String resourceName = "/data/" + targetName + ".txt";

		try (InputStream stream = getClass().getResourceAsStream(resourceName);
				Reader reader = new InputStreamReader(stream);
				BufferedReader in = new BufferedReader(reader)) {
			for (;;) {
				String line = in.readLine();

				if (line == null) {
					break;
				}
				process.accept(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
