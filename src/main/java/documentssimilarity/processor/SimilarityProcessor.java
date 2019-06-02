package documentssimilarity.processor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import documentssimilarity.model.Document;
import documentssimilarity.model.DocumentMetadata;
import documentssimilarity.model.StopWord;

public class SimilarityProcessor {

	private final List<Document> documents;

	private final Map<Document, DocumentMetadata> documentMetadata = new HashMap<>();

	public SimilarityProcessor(final List<Document> documents) {
		this.documents = documents;

		for (Document document : documents) {
			documentMetadata.put(document, new DocumentMetadata());
		}
		fillDocumentsMetadata();
	}

	/**
	 * Declare each word in every document metadata to ensure same sized vectors.
	 */
	private void fillDocumentsMetadata() {
		for (Document document : documents) {
			for (String word : document.getWords()) {
				if (!word.isEmpty() && !StopWord.getStopWords().contains(word)) {
					documentMetadata.get(document).addWord(word);
					for (Document otherDocument : documents) {
						documentMetadata.get(otherDocument).declareWord(word);
					}
				}
			}
		}
	}

	public void process() {
		for (final Document document : documents) {
			double[] frequencies = documentMetadata.get(document).getFrequenciesVector();

			System.out.println(Arrays.toString(frequencies));
			System.out.println(frequencies.length);
		}
	}

}
