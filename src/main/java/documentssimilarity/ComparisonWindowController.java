package documentssimilarity;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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

	private final Stage stage;
	private CanvasController canvasController;

	public ComparisonWindowController(final Stage stage) {
		this.stage = stage;
	}

	public void init() {
		canvasController = new CanvasController(canvas);

		stage.widthProperty().addListener((o, old, value) -> {
			canvas.setWidth(0);
			PauseTransition delay = new PauseTransition(Duration.millis(10));
			delay.setOnFinished(event -> {
				canvas.setWidth(canvasParent.getWidth());
				refreshCanvas();
			});
			delay.play();
		});
		stage.heightProperty().addListener((o, old, value) -> {
			canvas.setHeight(0);
			PauseTransition delay = new PauseTransition(Duration.millis(10));
			delay.setOnFinished(event -> {
				canvas.setHeight(canvasParent.getHeight());
				refreshCanvas();
			});
			delay.play();
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
