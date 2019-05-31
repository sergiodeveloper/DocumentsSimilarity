package documentssimilarity.processor;

import java.util.List;

import documentssimilarity.model.Document;

public class SimilarityProcessor {
	
	public static Object process(List<Document> documents){
		for (Document document : documents) {
			removeStopWords(document);
			
			// TODO - realizar processamento
		}
		return new Object();
	}

	private static void removeStopWords(Document document) {
		// TODO - remover stop words
	}

}
