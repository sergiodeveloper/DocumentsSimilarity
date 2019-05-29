package documentssimilarity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow extends Application {

	private static final String TITLE = "Documents Similarity";

	public static void main(final String[] args) {
		Application.launch(MainWindow.class, args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
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
