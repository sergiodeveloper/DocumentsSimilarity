package documentssimilarity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DocumentMetadata {

	private int numberOfWords = 0;

	private final Map<String, Integer> wordCount = new LinkedHashMap<>();

	public void addWord(final String word) {
		numberOfWords++;
		if (!wordCount.containsKey(word)) {
			wordCount.put(word, 0);
		}
		wordCount.put(word, wordCount.get(word) + 1);
	}

	/**
	 * Declares word in map but does not increment it.
	 */
	public void declareWord(final String word) {
		if (!wordCount.containsKey(word)) {
			wordCount.put(word, 0);
		}
	}

	public int size() {
		return wordCount.size();
	}

	public int[] getWordCountVector() {
		int[] vector = new int[wordCount.size()];
		int index = 0;
		for (Entry<String, Integer> entry : wordCount.entrySet()) {
			vector[index] = entry.getValue();
			index++;
		}
		return vector;
	}

	public double[] getFrequenciesVector() {
		int[] wordCountVector = getWordCountVector();
		double[] frequencies = new double[wordCountVector.length];

		for (int i = 0; i < wordCountVector.length; i++) {
			frequencies[i] = wordCountVector[i] / (double) numberOfWords;
		}
		return frequencies;
	}

	public int count(final String word) {
		return !wordCount.containsKey(word) ? 0 : wordCount.get(word);
	}

}
