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
	private static final int POINT_RADIUS = 10;

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
		final long tempoInicio = System.currentTimeMillis();
		this.canvasController = new CanvasController(this.canvas);
		this.configureStage();
		this.updateCanvasSize();
		this.process();
		this.draw();
		System.out.println("Tempo Total: "+(System.currentTimeMillis()-tempoInicio));
	}

	private void process() {
		final DocumentReader reader = new DocumentReader();

		final List<IOException> exceptions = new ArrayList<>();
		final SimilarityProcessor processor = new SimilarityProcessor(reader.read(this.documents, exceptions));
		this.points = processor.process(this.useTfIdf);
		this.draw();

		if (!exceptions.isEmpty()) {
			Platform.runLater(() -> {
				final Alert warningAlert = new Alert(AlertType.WARNING);
				warningAlert.setHeaderText("Ignorando " + exceptions.size() + " arquivo(s)");
				warningAlert.setContentText("Verifique se estão acessíveis e se tratam de arquivos de texto");
				warningAlert.show();
			});
		}
	}

	private void updateCanvasSize() {
		Platform.runLater(() -> {
			this.canvas.setWidth(this.canvasParent.getWidth());
			this.canvas.setHeight(this.canvasParent.getHeight());
		});
	}

	private void configureStage() {
		this.stage.widthProperty().addListener((o, old, value) -> {
			this.canvas.setWidth(0);
			final PauseTransition delay = new PauseTransition(Duration.millis(50));
			delay.setOnFinished(event -> {
				this.canvas.setWidth(this.canvasParent.getWidth());
				this.draw();
			});
			delay.play();
		});
		this.stage.heightProperty().addListener((o, old, value) -> {
			this.canvas.setHeight(0);
			final PauseTransition delay = new PauseTransition(Duration.millis(50));
			delay.setOnFinished(event -> {
				this.canvas.setHeight(this.canvasParent.getHeight());
				this.draw();
			});
			delay.play();
		});
	}

	private void draw() {
		Platform.runLater(() -> {
			this.canvasController.clear();
			this.updateCanvasSize();
			if (this.points == null) {
				final Font font = new Font(20);
				this.canvasController.fillText("Por favor aguarde...", Color.BLACK, font, 100, 100);
				return;
			}
			this.normalize(this.canvas.getWidth(), this.canvas.getHeight());

			if (this.showingGalaxy) {
				for (final Point point : this.points) {
					final double x = point.getX();
					final double y = point.getY();
					final RadialGradient paint = new RadialGradient(0, 0, x, y, GLOW_RADIUS, false, CycleMethod.NO_CYCLE,
							new Stop(0, new Color(0.5, 0.6, 1, 0.3)), new Stop(1, Color.TRANSPARENT));
					this.canvasController.fillCircle(GLOW_RADIUS, paint, x, y);
				}
			}
			for (final Point point : this.points) {
				final double x = point.getX();
				final double y = point.getY();
				this.canvasController.fillCircle(POINT_RADIUS, new Color(0.5, 0.5, 0, 0.5), x, y);
				this.canvasController.strokeCircle(POINT_RADIUS, 1, Color.BLACK, x, y);
			}
			if (this.showingNames) {
				for (final Point point : this.points) {
					final double x = point.getX();
					final double y = point.getY();
					final String label = point.getLabel();
					final Font font = new Font(12);
					this.canvasController.fillText(label, Color.BLACK, font, x + POINT_RADIUS * 1.4, y + POINT_RADIUS / 3.0);
				}
			}
		});
	}

	private synchronized void normalize(final double canvasWidth, final double canvasHeight) {
		double minX = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		for (final Point point : this.points) {
			minX = Math.min(minX, point.getX());
			maxX = Math.max(maxX, point.getX());
			minY = Math.min(minY, point.getY());
			maxY = Math.max(maxY, point.getY());
		}
		final double margin = 150;
		for (final Point point : this.points) {
			final double x = point.getX();
			final double y = point.getY();

			final double diffX = maxX - minX;
			final double diffY = maxY - minY;

			point.setX((x - minX) / Math.max(diffX, diffY) * (canvasWidth - margin * 2) + margin);
			point.setY((y - minY) / Math.max(diffX, diffY) * (canvasHeight - margin * 2) + margin);
		}
	}

	@FXML
	public void namesButtonAction() {
		this.showingNames = !this.showingNames;
		this.namesButton.setText(this.showingNames ? HIDE_NAMES : SHOW_NAMES);
		this.draw();
	}

	@FXML
	public void galaxyButtonAction() {
		this.showingGalaxy = !this.showingGalaxy;
		this.galaxyButton.setText(this.showingGalaxy ? HIDE_GALAXY : SHOW_GALAXY);
		this.draw();
	}

}
