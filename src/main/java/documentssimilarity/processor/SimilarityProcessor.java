package documentssimilarity.processor;

import java.util.List;
import java.util.Map;

import documentssimilarity.model.Document;

public class SimilarityProcessor {

	public Object process(final List<Document> documents) {
		for (final Document document : documents) {
			this.calculateFrequency(document);
			System.out.println("Teste");
		}
		return new Object();
	}

	private void calculateFrequency(final Document document) {
		for (final Map.Entry<String, Double> mapWord : document.getWords().entrySet()) {
			final Double qtd = mapWord.getValue();
			final Double frequency = qtd / document.getWords().size();

			mapWord.setValue(frequency);
		}
	}

}
