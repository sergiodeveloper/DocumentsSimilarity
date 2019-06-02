package documentssimilarity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Document {

	private String name;
	private final Map<String, Double> words = new HashMap<>();

	public void setWords(final List<String> lines) {
		final List<String> arrayWord = new ArrayList<>();
		for (final String word : lines) {
			arrayWord.addAll(Arrays.asList(word.split(" ")));
		}

		for (final String string : arrayWord) {
			if(StringUtils.isBlank(string) || StopWord.getStopWords().contains(string)) {
				continue;
			}

			final Double d;
			if(this.words.containsKey(string)) {
				d = this.words.get(string);
			}else {
				d = new Double(0);
			}

			this.words.put(string, d + 1);
		}
	}

}
