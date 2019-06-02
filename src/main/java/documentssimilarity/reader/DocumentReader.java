package documentssimilarity.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import documentssimilarity.model.Document;

public class DocumentReader {

	public List<Document> read(final List<File> documentsToRead) throws IOException{
		final List<Document> documents = new ArrayList<>();
		for (final File document : documentsToRead) {
			final Document doc = new Document();
			doc.setName(document.getName());

			final List<String> allLines = Files.readAllLines(Paths.get(document.getAbsolutePath()));
			doc.setWords(this.removePunctuation(allLines));

			documents.add(doc);
		}
		return documents;
	}

	private List<String> removePunctuation(final List<String> lines) {
		final List<String> newLines = new ArrayList<>();
		for (final String line : lines) {
			newLines.add(line.replaceAll("\\p{Punct}", ""));
		}

		return newLines;
	}

}
