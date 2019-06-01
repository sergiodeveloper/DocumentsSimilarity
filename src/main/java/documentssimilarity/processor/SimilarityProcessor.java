package documentssimilarity.processor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import documentssimilarity.model.Document;
import documentssimilarity.model.StopWord;

public class SimilarityProcessor {

	private Logger LOGGER = Logger.getLogger(getClass().getSimpleName());

	public Object process(List<Document> documents) {
		for (Document document : documents) {
			LOGGER.log(Level.INFO, document.getName());
			LOGGER.log(Level.INFO, "Texto original:" + document.getAllText());

			this.removePunctuation(document);
			this.removeCustomizedStopWords(document);

			LOGGER.log(Level.INFO, "Texto modificado:" + document.getAllText());
			
			calculateQuantityWords();

			// TODO - realizar processamento
		}
		return new Object();
	}

	private void calculateQuantityWords() {
		// TODO calcular quantidade de vezes que cada palavra aparece no documento
		
	}

	// FIXME It's doesn't work
	private void removePunctuation(Document document) {
		for (String line : document.getLines()) {
			line = line.replaceAll("\\p{Punct}", "");
		}
	}

	// FIXME It's doesn't work
	private void removeCustomizedStopWords(Document document) {
		for (String stopWord : StopWord.getStopWords()) {
			document.removeFromAllLines(stopWord);
		}
	}

}
