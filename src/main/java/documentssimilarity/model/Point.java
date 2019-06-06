package documentssimilarity.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Point {
	private double x;
	private double y;
	private String label;

	public Point(final String label, final double x, final double y) {
		this.label = label;
		this.x = x;
		this.y = y;
	}

}
