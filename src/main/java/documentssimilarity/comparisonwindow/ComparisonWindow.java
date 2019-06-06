package documentssimilarity.comparisonwindow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ComparisonWindow {

	private static final String TITLE = "Similaridade entre documentos";

	List<File> documents = new ArrayList<>();

	private final boolean useTfIdf;

	public ComparisonWindow(final List<File> documents, final boolean useTfIdf) {
		this.documents = documents;
		this.useTfIdf = useTfIdf;
	}

	public void start() {
		Stage stage = new Stage();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/comparison_window.fxml"));

		ComparisonWindowController controller = new ComparisonWindowController(stage, documents, useTfIdf);
		loader.setController(controller);

		Scene scene;
		try {
			scene = new Scene(loader.load());
		} catch (IOException e) {
			Logger.getLogger(getClass().getSimpleName()).log(Level.SEVERE, e.toString(), e);
			return;
		}

		scene.getStylesheets().add("/theme.css");
		stage.setScene(scene);

		stage.setTitle(TITLE);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

		stage.setWidth(700);
		stage.setHeight(500);

		stage.show();
		stage.centerOnScreen();

		new Thread(controller::init).start();
	}

}
