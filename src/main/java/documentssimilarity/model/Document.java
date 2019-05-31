package documentssimilarity.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Document {

	private String name;
	private List<String> lines;
	
}
