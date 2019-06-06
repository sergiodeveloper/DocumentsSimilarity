package documentssimilarity.comparisonwindow;

import java.io.File;
import java.io.IOException;
import java.util.List;

import documentssimilarity.model.Point;
import documentssimilarity.processor.DocumentReader;
import documentssimilarity.processor.SimilarityProcessor;
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
	private final List<File> documents;
	private final boolean useTfIdf;

	private List<Point> points;

	public ComparisonWindowController(final Stage stage, final List<File> documents, final boolean useTfIdf) {
		this.stage = stage;
		this.documents = documents;
		this.useTfIdf = useTfIdf;
	}

	public void init() {
		canvasController = new CanvasController(canvas);
		configureStage();
		udateCanvasSize();
		process();
		draw();
	}

	private void process() {
		try {
			DocumentReader reader = new DocumentReader();
			SimilarityProcessor processor = new SimilarityProcessor(reader.read(documents));
			points = processor.process(useTfIdf);
		} catch (IOException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Erro ao ler os arquivos.");
			errorAlert.showAndWait();
		}
	}

	private void udateCanvasSize() {
		canvas.setWidth(canvasParent.getWidth());
		canvas.setHeight(canvasParent.getHeight());
	}

	private void configureStage() {
		stage.widthProperty().addListener((o, old, value) -> {
			canvas.setWidth(0);
			PauseTransition delay = new PauseTransition(Duration.millis(100));
			delay.setOnFinished(event -> {
				canvas.setWidth(canvasParent.getWidth());
				draw();
			});
			delay.play();
		});
		stage.heightProperty().addListener((o, old, value) -> {
			canvas.setHeight(0);
			PauseTransition delay = new PauseTransition(Duration.millis(100));
			delay.setOnFinished(event -> {
				canvas.setHeight(canvasParent.getHeight());
				draw();
			});
			delay.play();
		});
	}

	private void draw() {
		udateCanvasSize();
		normalize(canvas.getWidth(), canvas.getHeight());
		for (Point point : points) {
			double x = point.getX();
			double y = point.getY();
			String label = point.getLabel();

			canvasController.fillCircle(20, new Color(0.5, 0.5, 0, 0.5), x, y);
			canvasController.strokeCircle(20, 1, Color.BLACK, x, y);
			canvasController.fillText(label, Color.BLACK, x + 8, y - 5);
		}
	}

	private synchronized void normalize(final double canvasWidth, final double canvasHeight) {
		double minX = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		for (Point point : points) {
			minX = Math.min(minX, point.getX());
			maxX = Math.max(maxX, point.getX());
			minY = Math.min(minY, point.getY());
			maxY = Math.max(maxY, point.getY());
		}
		double margin = 150;
		for (Point point : points) {
			double x = point.getX();
			double y = point.getY();
			point.setX((x - minX) / (maxX - minX) * (canvasWidth - margin * 2) + margin);
			point.setY((y - minY) / (maxY - minY) * (canvasHeight - margin * 2) + margin);
		}
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
