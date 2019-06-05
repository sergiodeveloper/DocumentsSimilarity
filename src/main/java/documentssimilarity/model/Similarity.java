package documentssimilarity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Similarity {

	String from;
	String to;
	double distance;

}
