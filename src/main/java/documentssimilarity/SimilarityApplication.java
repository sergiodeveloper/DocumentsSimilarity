package documentssimilarity;

import documentssimilarity.window.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class SimilarityApplication extends Application {

	public static void main(final String[] args) {
		SimilarityApplication.launch(SimilarityApplication.class, args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		MainWindow main = new MainWindow(stage);
		main.start();
	}

}
