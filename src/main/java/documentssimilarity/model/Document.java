package documentssimilarity.model;

import java.util.List;

import lombok.Getter;

@Getter
public class Document {

	private final String name;
	private final List<String> words;

	public Document(final String name, final List<String> words) {
		this.name = name;
		this.words = words;
	}

}
