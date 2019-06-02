package documentssimilarity.processor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import documentssimilarity.model.Document;
import documentssimilarity.model.StopWord;

public class SimilarityProcessor {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

	public Object process(final List<Document> documents) {
		for (final Document document : documents) {
			this.LOGGER.log(Level.INFO, document.getName());
			this.LOGGER.log(Level.INFO, "Texto original:" + document.getAllText());

			this.removeCustomizedStopWords(document);

			this.LOGGER.log(Level.INFO, "Texto modificado:" + document.getAllText());

			this.calculateQuantityWords();

			// TODO - realizar processamento
		}
		return new Object();
	}

	private void calculateQuantityWords() {
		// TODO calcular quantidade de vezes que cada palavra aparece no documento

	}

	private void removeCustomizedStopWords(final Document document) {
		for (final String stopWord : StopWord.getStopWords()) {
			document.getWords().removeIf(stopWord::equals);
		}
	}

}
