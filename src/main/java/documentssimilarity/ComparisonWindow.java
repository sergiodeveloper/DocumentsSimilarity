package documentssimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ComparisonWindow {

	private static final String TITLE = "Similaridade entre documentos";

	public ComparisonWindow(final List<File> documents) {
	}

	public void start() {
		Stage stage = new Stage();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/comparison_window.fxml"));

		ComparisonWindowController controller = new ComparisonWindowController(stage);
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

		controller.init();

		stage.setTitle(TITLE);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

		stage.setWidth(700);
		stage.setHeight(500);

		stage.show();

		stage.centerOnScreen();
	}

}
