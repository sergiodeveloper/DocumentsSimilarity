package documentssimilarity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Document {

	private String name;
	private List<String> words;

	public String getAllText() {
		final StringBuilder sb = new StringBuilder();
		for (final String word : this.words) {
			sb.append(word);
		}

		return sb.toString();
	}

	public void setWords(final List<String> lines) {
		this.words = new ArrayList<>();
		for (final String word : lines) {
			this.words.addAll(Arrays.asList(word.split(" ")));
		}
	}

}
