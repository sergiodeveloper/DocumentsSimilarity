package documentssimilarity.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import documentssimilarity.ArrayUtils;
import documentssimilarity.model.Document;
import documentssimilarity.model.DocumentMetadata;
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

		double[][] frequencyDistanceMatrix = generateFrequenciesDistanceMatrix();

		for (double[] line : frequencyDistanceMatrix) {
			System.out.println(Arrays.toString(line));
		}
	}

	private double[][] generateFrequenciesDistanceMatrix() {
		double[][] distanceMatrix = new double[documents.size()][documents.size()];

		for (int i = 0; i < documents.size(); i++) {
			for (int j = 0; j < documents.size(); j++) {
				Document first = documents.get(i);
				Document second = documents.get(j);

				double distance = ArrayUtils.euclidianDistance(documentMetadata.get(first).getFrequenciesVector(),
						documentMetadata.get(second).getFrequenciesVector());
				distanceMatrix[i][j] = distance;
			}
		}

		return distanceMatrix;
	}

}
