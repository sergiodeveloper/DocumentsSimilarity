package documentssimilarity.windows;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow {
	
	private static final String TITLE = "Documents Similarity";
	
	private Stage stage;
	
	public MainWindow(Stage stage) {
		this.stage = stage;
	}
	
	public void start() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_window.fxml"));

		MainWindowController controller = new MainWindowController(stage);
		loader.setController(controller);

		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add("/theme.css");
		stage.setScene(scene);

		controller.init();

		stage.setTitle(TITLE);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

		stage.setWidth(650);
		stage.setHeight(450);

		stage.show();

		stage.centerOnScreen();
	}

}
