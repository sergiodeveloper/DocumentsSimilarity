package documentssimilarity.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import documentssimilarity.exception.MetadataDiferentSizeException;
import documentssimilarity.model.Document;
import documentssimilarity.model.DocumentMetadata;
import documentssimilarity.model.Similarity;
import documentssimilarity.model.StopWord;

public class SimilarityProcessor {

	private final List<Document> documents;

	private final Map<Document, DocumentMetadata> documentMetadata = new HashMap<>();

	public SimilarityProcessor(final List<Document> documents) {
		this.documents = documents;

		for (final Document document : documents) {
			this.documentMetadata.put(document, new DocumentMetadata());
		}
	}

	/**
	 * Declare each word in every document metadata to ensure same sized vectors.
	 */
	private void fillDocumentsMetadata() {
		for (final Document document : this.documents) {
			for (final String word : document.getWords()) {
				if (!word.isEmpty() && !StopWord.getStopWords().contains(word)) {
					this.documentMetadata.get(document).addWord(word);
					for (final Document otherDocument : this.documents) {
						this.documentMetadata.get(otherDocument).declareWord(word);
					}
				}
			}
		}
	}

	public void process() {
		this.fillDocumentsMetadata();
		this.calculateDocumentsDistance();

		for (final Document document : this.documents) {
			final double[] frequencies = this.documentMetadata.get(document).getFrequenciesVector();

			System.out.println(Arrays.toString(frequencies));
			System.out.println(frequencies.length);
		}
	}

	private List<Similarity> calculateDocumentsDistance() {
		final List<Similarity> similarities = new ArrayList<>();

		for (final Document document : this.documents) {

			for (final Document otherDocument : this.documents) {
				if(document == otherDocument) {
					continue;
				}

				final double distance = this.calculateDistance(
						this.documentMetadata.get(document),
						this.documentMetadata.get(otherDocument));

				similarities.add(new Similarity(document.getName(), otherDocument.getName(), distance));
			}
		}

		return similarities;
	}

	private double calculateDistance(final DocumentMetadata metadata, final DocumentMetadata otherMetadata) {

		if(metadata.size() != otherMetadata.size()) {
			throw new MetadataDiferentSizeException();
		}

		double acumulated = 0d;

		final Map<String, Integer> map = metadata.getWordCount();
		for (final Map.Entry<String, Integer> wordCount : map.entrySet()) {

			final Map<String, Integer> otherMap = otherMetadata.getWordCount();
			acumulated += Math.pow(wordCount.getValue() - otherMap.get(wordCount.getKey()),2);
		}


		return Math.sqrt(acumulated);
	}

}
