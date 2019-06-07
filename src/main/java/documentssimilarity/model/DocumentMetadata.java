package documentssimilarity.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DocumentMetadata {

	private int numberOfWords = 0;
	private final Map<String, Integer> wordCount = new LinkedHashMap<>();
	private final Document document;

	private double[] tfIdfVector;
	private int[] wordCountVector;

	public DocumentMetadata(final Document document) {
		this.document = document;
	}

	public void addWord(final String word) {
		this.numberOfWords++;
		if (!this.wordCount.containsKey(word)) {
			this.wordCount.put(word, 0);
		}
		this.wordCount.put(word, this.wordCount.get(word) + 1);
	}

	/**
	 * Declares word in map but does not increment it.
	 */
	public void declareWord(final String word) {
		if (!this.wordCount.containsKey(word)) {
			this.wordCount.put(word, 0);
		}
	}

	public int size() {
		return this.wordCount.size();
	}

	private int[] getWordCountVector() {
		if(this.wordCountVector != null) {
			return this.wordCountVector;
		}

		this.wordCountVector = new int[this.wordCount.size()];
		int index = 0;
		for (final Entry<String, Integer> entry : this.wordCount.entrySet()) {
			this.wordCountVector[index] = entry.getValue();
			index++;
		}
		return this.wordCountVector;
	}

	public double[] getFrequenciesVector() {
		final int[] wordCountVector = this.getWordCountVector();
		final double[] frequencies = new double[wordCountVector.length];

		for (int i = 0; i < wordCountVector.length; i++) {
			frequencies[i] = wordCountVector[i] / (double) this.numberOfWords;
		}
		return frequencies;
	}

	public double[] getTfIdfVector(final Collection<DocumentMetadata> allMetadata) {
		if(this.tfIdfVector != null) {
			return this.tfIdfVector;
		}

		final int[] wordCountVector = this.getWordCountVector();
		this.tfIdfVector = new double[this.wordCount.size()];

		final int docsCount = allMetadata.size();

		for (int i = 0; i < wordCountVector.length; i++) {
			final double tf = wordCountVector[i];

			int documentsContainingWord = 1;
			for (final DocumentMetadata otherMetadata : allMetadata) {
				if (otherMetadata != this && otherMetadata.getWordCountVector()[i] > 0) {
					++documentsContainingWord;
				}
			}
			final double idf = Math.log((double) docsCount / documentsContainingWord);

			this.tfIdfVector[i] = tf * idf;
		}
		return this.tfIdfVector;
	}

	public int count(final String word) {
		return !this.wordCount.containsKey(word) ? 0 : this.wordCount.get(word);
	}

	public Map<String, Integer> getWordCount() {
		return Collections.unmodifiableMap(this.wordCount);
	}

}
