package documentssimilarity.processor;

import java.util.Arrays;
import java.util.List;

import documentssimilarity.model.Document;
import documentssimilarity.model.StopWord;

public class SimilarityProcessor {
	
	public static Object process(List<Document> documents){
		for (Document document : documents) {
			removeStopWords(document);
			removePunctuation(document);
			// TODO - realizar processamento
		}
		return new Object();
	}

	private static void removePunctuation(Document document) {
		document.getLines().stream().forEach(l -> l.replaceAll("[^a-zA-Z ]", ""));
	}

	private static void removeStopWords(Document document) {
		Arrays.stream(StopWord.values())
			.map(s -> s.getWords())
			.flatMap(List<String>::stream)
			.forEach(w -> document.removeFromAllLines(w));
	}

}
