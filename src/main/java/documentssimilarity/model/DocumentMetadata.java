package documentssimilarity.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DocumentMetadata {

	private int numberOfWords = 0;

	private final Map<String, Integer> wordCount = new LinkedHashMap<>();

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

	public int[] getWordCountVector() {
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

	public int count(final String word) {
		return !this.wordCount.containsKey(word) ? 0 : this.wordCount.get(word);
	}

	public Map<String, Integer> getWordCount(){
		return Collections.unmodifiableMap(this.wordCount);
	}

}
