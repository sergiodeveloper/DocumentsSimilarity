package documentssimilarity.model;

import java.util.Arrays;
import java.util.List;

public class StopWord {

	List<String> words;

	public static List<String> getStopWords() {
		return Arrays.asList(
				// artigos
				"o", "a", "os", "as",
				"um", "uma", "uns", "umas",

				// artigos + preposições
				"ao", "à", "aos", "às",
				"do","da","dos","das",
				"no", "na", "nos", "nas",
				"pelo", "pela", "pelos", "pelas",
				"num","numa","nuns","numas",
				"dum","duma","duns","dumas",

				// preposições
				"ante", "até", "após", "com", "contra", "de",
				"desde", "em", "entre", "para", "por", "perante",
				"ser", "sem", "sob", "sobre", "traz",

				// palavras descobertas conforme execução do processamento
				"e", "i","ou",
				"bíblia", "capítulo", "equiv.",
				"httpsitesgooglecomsitebiblialivre",
				

				// INGLES
				"the", "it", "this", "those", "that", "these",
				"a", "an", "in", "on", "onto", "for", "of", "to",
				"under", "over", "onto", "since", "until", "behind",
				"without", "with", "chapter"
		);
	}

}
