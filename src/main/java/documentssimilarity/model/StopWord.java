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
				" ", "Capítulo", "capítulo",
				"httpsitesgooglecomsitebiblialivre",
				"1","2","3","4","5","6","7","8","9","10",
				"14","2012");
	}

}
