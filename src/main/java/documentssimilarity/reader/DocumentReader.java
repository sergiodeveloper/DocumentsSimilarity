package documentssimilarity.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import documentssimilarity.model.Document;

public class DocumentReader {

	public List<Document> read(List<File> documentsToRead) throws IOException{
		List<Document> documents = new ArrayList<>();
		for (File document : documentsToRead) {
			Document doc = new Document();
			doc.setName(document.getName());

			List<String> allLines = Files.readAllLines(Paths.get(document.getAbsolutePath()));
			doc.setWords(this.removePunctuation(allLines));

			documents.add(doc);
		}
		return documents;
	}
	
	private List<String> removePunctuation(List<String> lines) {
		List<String> newLines = new ArrayList<>(); 
		for (String line : lines) {
			newLines.add(line.replaceAll("\\p{Punct}", ""));
		}
		
		return newLines;
	}
	
}
