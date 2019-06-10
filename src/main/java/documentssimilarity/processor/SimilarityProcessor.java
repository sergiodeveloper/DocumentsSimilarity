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

	public List<Point> process(final boolean useTfIdf) {
		this.fillDocumentsMetadata();

		double[][] distanceMatrix;

		if (useTfIdf) {
			distanceMatrix = this.generateTfIdfDistanceMatrix();
		} else {
			distanceMatrix = this.generateFrequenciesDistanceMatrix();
		}

		final double[][] classicalScaling = MDSJ.classicalScaling(distanceMatrix, DIMENSION_RANK);

		final List<Point> points = new ArrayList<>();
		for (int i = 0; i < classicalScaling[0].length; i++) {
			final String label = this.documents.get(i).getName();
			points.add(new Point(label, classicalScaling[0][i], classicalScaling[1][i]));
		}
		return points;
	}

	private double[][] generateFrequenciesDistanceMatrix() {
		final double[][] distanceMatrix = new double[this.documents.size()][this.documents.size()];

		for (int i = 0; i < this.documents.size(); i++) {
			for (int j = 0; j < this.documents.size(); j++) {
				final Document first = this.documents.get(i);
				final Document second = this.documents.get(j);

				final double distance = ArrayUtils.euclidianDistance(this.documentMetadata.get(first).getFrequenciesVector(),
						this.documentMetadata.get(second).getFrequenciesVector());
				distanceMatrix[i][j] = distance;
			}
		}

		return distanceMatrix;
	}

	private double[][] generateTfIdfDistanceMatrix() {
		final double[][] distanceMatrix = new double[this.documents.size()][this.documents.size()];

		for (int i = 0; i < this.documents.size(); i++) {
			final double[] tfIdfVector = this.documentMetadata.get(this.documents.get(i))
					.getTfIdfVector(this.documentMetadata.values());

			for (int j = 0; j < i; j++) {
				final double[] tfIdfVector2 = this.documentMetadata.get(this.documents.get(j))
						.getTfIdfVector(this.documentMetadata.values());

				final double distance = ArrayUtils.euclidianDistance(tfIdfVector,tfIdfVector2);

				distanceMatrix[i][j] = distance;
				distanceMatrix[j][i] = distance;
			}
		}

		return distanceMatrix;
	}

}
