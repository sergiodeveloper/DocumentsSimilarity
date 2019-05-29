package documentssimilarity;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ComparisonWindowController {

	private static final String SHOW_NAMES = "Exibir nomes";
	private static final String HIDE_NAMES = "Ocultar nomes";
	private static final String SHOW_GALAXY = "Exibir Galaxy";
	private static final String HIDE_GALAXY = "Ocultar Galaxy";

	@FXML
	private VBox canvasParent;
	@FXML
	private Canvas canvas;
	@FXML
	private Button namesButton;
	@FXML
	private Button galaxyButton;

	private final ComparisonWindow comparisonWindow;
	private CanvasController canvasController;

	public ComparisonWindowController(final ComparisonWindow comparisonWindow) {
		this.comparisonWindow = comparisonWindow;
	}

	public void init() {
		canvasController = new CanvasController(canvas);

		canvasParent.widthProperty().addListener((o, old, value) -> {
			canvas.setWidth(value.doubleValue());
			refreshCanvas();
		});
		canvasParent.heightProperty().addListener((o, old, value) -> {
			canvas.setHeight(value.doubleValue());
			refreshCanvas();
		});

		canvas.setWidth(canvasParent.getWidth());
		canvas.setHeight(canvasParent.getHeight());
		refreshCanvas();
	}

	private void refreshCanvas() {
		canvasController.fillCircle(40, Color.GOLD, 200, 200);
	}

	@FXML
	public void namesButtonAction() {

	}

	@FXML
	public void galaxyButtonAction() {

	}

}
