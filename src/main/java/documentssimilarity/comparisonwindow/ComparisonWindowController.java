package documentssimilarity.comparisonwindow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import documentssimilarity.model.Point;
import documentssimilarity.processor.DocumentReader;
import documentssimilarity.processor.SimilarityProcessor;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ComparisonWindowController {

	private static final String SHOW_NAMES = "Exibir nomes";
	private static final String HIDE_NAMES = "Ocultar nomes";
	private static final String SHOW_GALAXY = "Exibir Galaxy";
	private static final String HIDE_GALAXY = "Ocultar Galaxy";

	private static final int GLOW_RADIUS = 150;
	private static final int POINT_RADIUS = 15;

	@FXML
	private ScrollPane canvasParent;
	@FXML
	private Canvas canvas;
	@FXML
	private Button namesButton;
	@FXML
	private Button galaxyButton;

	private boolean showingNames = true;
	private boolean showingGalaxy = true;

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
		updateCanvasSize();
		process();
		draw();
	}

	private void process() {
		DocumentReader reader = new DocumentReader();

		List<IOException> exceptions = new ArrayList<>();
		SimilarityProcessor processor = new SimilarityProcessor(reader.read(documents, exceptions));
		points = processor.process(useTfIdf);
		draw();

		if (!exceptions.isEmpty()) {
			Platform.runLater(() -> {
				Alert warningAlert = new Alert(AlertType.WARNING);
				warningAlert.setHeaderText("Ignorando " + exceptions.size() + " arquivo(s)");
				warningAlert.setContentText("Verifique se estão acessíveis e se tratam de arquivos de texto");
				warningAlert.show();
			});
		}
	}

	private void updateCanvasSize() {
		Platform.runLater(() -> {
			canvas.setWidth(canvasParent.getWidth());
			canvas.setHeight(canvasParent.getHeight());
		});
	}

	private void configureStage() {
		stage.widthProperty().addListener((o, old, value) -> {
			canvas.setWidth(0);
			PauseTransition delay = new PauseTransition(Duration.millis(50));
			delay.setOnFinished(event -> {
				canvas.setWidth(canvasParent.getWidth());
				draw();
			});
			delay.play();
		});
		stage.heightProperty().addListener((o, old, value) -> {
			canvas.setHeight(0);
			PauseTransition delay = new PauseTransition(Duration.millis(50));
			delay.setOnFinished(event -> {
				canvas.setHeight(canvasParent.getHeight());
				draw();
			});
			delay.play();
		});
	}

	private void draw() {
		Platform.runLater(() -> {
			canvasController.clear();
			updateCanvasSize();
			if (points == null) {
				Font font = new Font(20);
				canvasController.fillText("Por favor aguarde...", Color.BLACK, font, 100, 100);
				return;
			}
			normalize(canvas.getWidth(), canvas.getHeight());

			if (showingGalaxy) {
				for (Point point : points) {
					double x = point.getX();
					double y = point.getY();
					RadialGradient paint = new RadialGradient(0, 0, x, y, GLOW_RADIUS, false, CycleMethod.NO_CYCLE,
							new Stop(0, new Color(0.5, 0.6, 1, 0.3)), new Stop(1, Color.TRANSPARENT));
					canvasController.fillCircle(GLOW_RADIUS, paint, x, y);
				}
			}
			for (Point point : points) {
				double x = point.getX();
				double y = point.getY();
				canvasController.fillCircle(POINT_RADIUS, new Color(0.5, 0.5, 0, 0.5), x, y);
				canvasController.strokeCircle(POINT_RADIUS, 1, Color.BLACK, x, y);
			}
			if (showingNames) {
				for (Point point : points) {
					double x = point.getX();
					double y = point.getY();
					String label = point.getLabel();
					Font font = new Font(12);
					canvasController.fillText(label, Color.BLACK, font, x + POINT_RADIUS * 1.4, y + POINT_RADIUS / 3.0);
				}
			}
		});
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
		showingNames = !showingNames;
		namesButton.setText(showingNames ? HIDE_NAMES : SHOW_NAMES);
		draw();
	}

	@FXML
	public void galaxyButtonAction() {
		showingGalaxy = !showingGalaxy;
		galaxyButton.setText(showingGalaxy ? HIDE_GALAXY : SHOW_GALAXY);
		draw();
	}

}
