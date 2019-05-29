package documentssimilarity;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MainWindowController {

	private static final String EMPTY_FOLDER_TEXT = "Escolha uma pasta";

	@FXML
	private Label folderPathLabel;
	@FXML
	private VBox filesContainer;
	private final Stage stage;

	private File folder = null;

	private final List<File> documents = new ArrayList<>();

	private final Map<File, CheckBox> checkBoxes = new HashMap<>();

	public MainWindowController(final Stage stage) {
		this.stage = stage;
	}

	public void init() {
		folderPathLabel.setText(EMPTY_FOLDER_TEXT);
		filesContainer.getChildren().clear();
	}

	@FXML
	public void castSimilarities() {
		List<File> selectedDocuments = new ArrayList<>();
		for (Entry<File, CheckBox> entry : checkBoxes.entrySet()) {
			if (entry.getValue().isSelected()) {
				selectedDocuments.add(entry.getKey());
			}
		}

		if(selectedDocuments.size() < 2) {
			Alert errorAlert = new Alert(AlertType.INFORMATION);
			errorAlert.setHeaderText("Por favor, selecione mais documentos");
			errorAlert.showAndWait();
		} else {
			ComparisonWindow comparisonWindow = new ComparisonWindow(selectedDocuments);
			comparisonWindow.start();
		}
	}

	@FXML
	public void chooseFolder() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedFolder = directoryChooser.showDialog(stage);

		if (selectedFolder == null) {
			folderPathLabel.setText(EMPTY_FOLDER_TEXT);
			folder = null;
		} else {
			folderPathLabel.setText(selectedFolder.getName());
			folder = selectedFolder;
		}

		try {
			updateDocumentsList();
		} catch (IOException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Falha ao tentar ler pasta");
			errorAlert.setContentText("Tente escolher outra pasta");
			errorAlert.showAndWait();
		}
	}

	private void updateDocumentsList() throws IOException {
		filesContainer.getChildren().clear();

		if (folder != null) {
			Files.walkFileTree(folder.toPath(), new FileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs)
						throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
					documents.add(file.toFile());
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}

			});

			for (File document : documents) {
				CheckBox item = new CheckBox(document.getName());
				checkBoxes.put(document, item);
				item.setSelected(true);
				filesContainer.getChildren().add(item);
			}
		}
	}

}
