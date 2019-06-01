package documentssimilarity.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import documentssimilarity.model.Document;

public class DocumentReader {

	public static List<Document> read(List<File> documentsToRead) throws IOException{
		List<Document> documents = new ArrayList<>();
		for (File document : documentsToRead) {
			
			Document doc = new Document();
			doc.setName(document.getName());
			doc.setLines(Files.readAllLines(Paths.get(document.getAbsolutePath())));
			
			documents.add(doc);
		}
		return documents;
	}
	
}
