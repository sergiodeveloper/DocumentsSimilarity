package documentssimilarity.comparisonwindow;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CanvasController {

	private final GraphicsContext g;
	private final Canvas canvas;

	public CanvasController(final Canvas canvas) {
		this.canvas = canvas;
		g = canvas.getGraphicsContext2D();
	}

	public void clear() {
		g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public void fillCircle(final double radius, final Color color, final double x, final double y) {
		g.setFill(color);
		g.fillOval(x - radius, y - radius, radius, radius);
	}

}
