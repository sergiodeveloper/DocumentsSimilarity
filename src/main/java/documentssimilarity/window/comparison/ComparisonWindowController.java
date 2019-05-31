package documentssimilarity.window.comparison;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import documentssimilarity.model.Document;
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
	private List<File> documentsToRead;
	private Object someObjectNotCreatedYetForResultOfProcess;

	public ComparisonWindowController(final Stage stage, List<File> documents) {
		this.stage = stage;
		this.documentsToRead = documents;
	}

	public void init() {
		canvasController = new CanvasController(canvas);
		configureStage();
		configureCanvas();
	}

	public void process() {
		try {
			List<Document> documents = readDocuments();
			someObjectNotCreatedYetForResultOfProcess = SimilarityProcessor.process(documents);
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
	
	private List<Document> readDocuments() throws IOException {
		List<Document> documents = new ArrayList<>();
		for (File document : documentsToRead) {
			Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "Lendo arquivo: " + document.getName());
			
			Document doc = new Document();
			doc.setName(document.getName());
			doc.setLines(Files.readAllLines(Paths.get(document.getAbsolutePath())));
			
			documents.add(doc);
		}
		
		return documents;
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
		if(SHOW_NAMES.equals(namesButton.getText())) {
			namesButton.setText(HIDE_NAMES);
		}else {
			namesButton.setText(SHOW_NAMES);
		}
	}
	
	private void changeTextGalaxyButton() {
		if(SHOW_GALAXY.equals(galaxyButton.getText())) {
			galaxyButton.setText(HIDE_GALAXY);
		}else {
			galaxyButton.setText(SHOW_GALAXY);
		}
	}

}
