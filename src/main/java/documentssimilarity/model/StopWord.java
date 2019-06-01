package documentssimilarity.model;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StopWord {
	PREPOSICOES(getPreposicoes()),
	ARTIGOS(getArtigos()),
	OUTRAS(getOthers());
	
	List<String> words;
	
	// FIXME renomear to English
	private static List<String> getPreposicoes(){
		// TODO criar lista de palavras
		return Collections.emptyList();
	}
	
	// FIXME renomear to English
	private static List<String> getArtigos(){
		// TODO criar lista de palavras
		return Collections.emptyList();
	}

	private static List<String> getOthers(){
		// TODO criar lista de palavras
		return Collections.emptyList();
	}

}
