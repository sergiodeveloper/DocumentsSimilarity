package documentssimilarity.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DocumentMetadata {

	private int numberOfWords = 0;
	private final Map<String, Integer> wordCount = new LinkedHashMap<>();
	private final Document document;

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
		final int[] vector = new int[this.wordCount.size()];
		int index = 0;
		for (final Entry<String, Integer> entry : this.wordCount.entrySet()) {
			vector[index] = entry.getValue();
			index++;
		}
		return vector;
	}

	public double[] getFrequenciesVector() {
		final int[] wordCountVector = this.getWordCountVector();
		final double[] frequencies = new double[wordCountVector.length];

		for (int i = 0; i < wordCountVector.length; i++) {
			frequencies[i] = wordCountVector[i] / (double) this.numberOfWords;
		}
		return frequencies;
	}

	public double[] getTfIdfVector(final Collection<Document> allDocuments) {
		final int[] wordCountVector = this.getWordCountVector();
		final double[] tfidf = new double[wordCountVector.length];
		int n = allDocuments.size();

		for (int i = 0; i < wordCountVector.length; i++) {
			double tf = wordCountVector[i];
			String word = getWordFromIndex(i);

			int documentsContainingWord = 1;
			for (Document doc : allDocuments) {
				if (doc != this.document && doc.getWords().contains(word)) {
					documentsContainingWord++;
				}
			}
			double idf = Math.log((double) n / documentsContainingWord);

			tfidf[i] = tf * idf;
		}
		return tfidf;
	}

	public String getWordFromIndex(final int index) {
		return new ArrayList<>(this.wordCount.keySet()).get(index);
	}

	public int count(final String word) {
		return !this.wordCount.containsKey(word) ? 0 : this.wordCount.get(word);
	}

	public Map<String, Integer> getWordCount() {
		return Collections.unmodifiableMap(this.wordCount);
	}

}
