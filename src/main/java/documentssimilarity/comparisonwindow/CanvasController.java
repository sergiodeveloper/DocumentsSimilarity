package documentssimilarity.comparisonwindow;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

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

	public void fillCircle(final double radius, final Paint paint, final double x, final double y) {
		g.setFill(paint);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	public void strokeCircle(final double radius, final double border, final Paint paint, final double x,
			final double y) {
		g.setStroke(paint);
		g.setLineWidth(border);
		g.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	public void fillText(final String text, final Paint paint, final Font font, final double x, final double y) {
		g.setFill(paint);
		g.setFont(font);
		g.fillText(text, x, y);
	}

}
