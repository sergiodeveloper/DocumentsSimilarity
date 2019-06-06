package documentssimilarity.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import documentssimilarity.ArrayUtils;
import documentssimilarity.model.Document;
import documentssimilarity.model.DocumentMetadata;
import documentssimilarity.model.Point;
import documentssimilarity.model.StopWord;
import mdsj.MDSJ;

public class SimilarityProcessor {

	private static final int DIMENSION_RANK = 2;

	private final List<Document> documents;

	private final Map<Document, DocumentMetadata> documentMetadata = new HashMap<>();

	public SimilarityProcessor(final List<Document> documents) {
		this.documents = documents;

		for (final Document document : documents) {
			this.documentMetadata.put(document, new DocumentMetadata(document));
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

	public List<Point> process(final boolean useTfIdf) {
		this.fillDocumentsMetadata();

		double[][] distanceMatrix;

		if (useTfIdf) {
			distanceMatrix = generateTfIdfDistanceMatrix();
		} else {
			distanceMatrix = generateFrequenciesDistanceMatrix();
		}

		double[][] classicalScaling = MDSJ.classicalScaling(distanceMatrix, DIMENSION_RANK);

		List<Point> points = new ArrayList<>();
		for (int i = 0; i < classicalScaling[0].length; i++) {
			String label = documents.get(i).getName();
			points.add(new Point(label, classicalScaling[0][i], classicalScaling[1][i]));
		}
		return points;
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

	private double[][] generateTfIdfDistanceMatrix() {
		double[][] distanceMatrix = new double[documents.size()][documents.size()];

		for (int i = 0; i < documents.size(); i++) {
			for (int j = 0; j < documents.size(); j++) {
				Document first = documents.get(i);
				Document second = documents.get(j);

				double distance = ArrayUtils.euclidianDistance(documentMetadata.get(first).getTfIdfVector(documents),
						documentMetadata.get(second).getTfIdfVector(documents));
				distanceMatrix[i][j] = distance;
			}
		}

		return distanceMatrix;
	}

}
