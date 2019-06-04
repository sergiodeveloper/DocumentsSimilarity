package documentssimilarity.mainwindow;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow {

	private static final String TITLE = "Documents Similarity";

	private final Stage stage;

	public MainWindow(final Stage stage) {
		this.stage = stage;
	}

	public void start() throws IOException {
		URL resource = getClass().getResource("/main_window.fxml");
		FXMLLoader loader = new FXMLLoader(resource);

		MainWindowController controller = new MainWindowController(stage);
		loader.setController(controller);

		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add("/theme.css");
		stage.setScene(scene);

		controller.init();

		stage.setTitle(TITLE);
		stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));

		stage.setWidth(650);
		stage.setHeight(450);

		stage.show();

		stage.centerOnScreen();
	}

}
