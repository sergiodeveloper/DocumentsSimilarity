package documentssimilarity;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow extends Application {

	private static final String TITLE = "Documents Similarity";

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		stage.show();

		stage.setTitle(TITLE);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

		stage.setWidth(800);
		stage.setHeight(550);
		stage.centerOnScreen();
	}

}
