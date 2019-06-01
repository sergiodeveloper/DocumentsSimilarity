package documentssimilarity.model;

import java.util.Arrays;
import java.util.List;

public class StopWord {

	List<String> words;

	public static List<String> getStopWords() {
		return Arrays.asList("a", "e", "i", "o", "u", 
				"ante", "até", "após", "com", "contra", "de", 
				"desde", "em", "entre", "para", "por", "perante", 
				"ser", "sem", "sob", "sobre", "traz");
	}

}
