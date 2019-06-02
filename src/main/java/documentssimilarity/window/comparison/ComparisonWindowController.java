package documentssimilarity.window.comparison;

import java.io.File;
import java.io.IOException;
import java.util.List;

import documentssimilarity.processor.SimilarityProcessor;
import documentssimilarity.reader.DocumentReader;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private List<File> documents;

	public ComparisonWindowController(final Stage stage, List<File> documents) {
		this.stage = stage;
		this.documents = documents;
	}

	public void init() {
		canvasController = new CanvasController(canvas);
		configureStage();
		configureCanvas();
	}

	public void process() {
		try {
			SimilarityProcessor processor = new SimilarityProcessor();
			DocumentReader reader = new DocumentReader();
			processor.process(reader.read(documents));
		} catch (IOException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Erro ao ler os arquivos.");
			errorAlert.showAndWait();
		}
	}

	public void draw() {
		// TODO - usa objeto resultante do processamento para desenhar
		refreshCanvas();
	}

	private void configureCanvas() {
		canvas.setWidth(canvasParent.getWidth());
		canvas.setHeight(canvasParent.getHeight());
	}

	private void configureStage() {
		stage.widthProperty().addListener((o, old, value) -> {
			canvas.setWidth(0);
			PauseTransition delay = new PauseTransition(Duration.millis(10));
			delay.setOnFinished(event -> {
				canvas.setWidth(canvasParent.getWidth());
				draw();
			});
			delay.play();
		});
		stage.heightProperty().addListener((o, old, value) -> {
			canvas.setHeight(0);
			PauseTransition delay = new PauseTransition(Duration.millis(10));
			delay.setOnFinished(event -> {
				canvas.setHeight(canvasParent.getHeight());
				draw();
			});
			delay.play();
		});
	}

	private void refreshCanvas() {
		canvasController.fillCircle(40, Color.GOLD, 200, 200);
	}

	@FXML
	public void namesButtonAction() {
		changeTextNamesButton();
	}

	@FXML
	public void galaxyButtonAction() {
		changeTextGalaxyButton();
	}

	private void changeTextNamesButton() {
		if (SHOW_NAMES.equals(namesButton.getText())) {
			namesButton.setText(HIDE_NAMES);
		} else {
			namesButton.setText(SHOW_NAMES);
		}
	}

	private void changeTextGalaxyButton() {
		if (SHOW_GALAXY.equals(galaxyButton.getText())) {
			galaxyButton.setText(HIDE_GALAXY);
		} else {
			galaxyButton.setText(SHOW_GALAXY);
		}
	}

}
