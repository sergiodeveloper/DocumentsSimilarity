package documentssimilarity.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import documentssimilarity.model.Document;

public class DocumentReader {

	public List<Document> read(final List<File> filesToRead) throws IOException {
		final List<Document> documents = new ArrayList<>();

		for (final File file : filesToRead) {
			final List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));

			List<String> words = new ArrayList<>();
			for (final String preLine : lines) {
				String line = removePunctuation(preLine).toLowerCase();
				words.addAll(Arrays.asList(line.split("\\s+")));
			}

			documents.add(new Document(file.getName(), words));
		}
		return documents;
	}

	private String removePunctuation(final String word) {
		return word.replaceAll("\\p{Punct}", "");
	}

}