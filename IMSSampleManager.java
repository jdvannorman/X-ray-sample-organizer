import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.File;

public class IMSSampleManager extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();

        // Persist localStorage in the user's AppData so data survives restarts.
        // setUserDataDirectory() must be called before any content is loaded.
        File dataDir = new File(System.getProperty("user.home"),
                "AppData\\Roaming\\IMS-Sample-Manager");
        dataDir.mkdirs();
        engine.setUserDataDirectory(dataDir);

        // Load calculator.html from the same folder as the JAR
        File htmlFile = new File(getAppDir(), "calculator.html");
        if (!htmlFile.exists()) {
            htmlFile = new File("calculator.html");   // fallback: working directory
        }
        engine.load(htmlFile.toURI().toURL().toString());

        Scene scene = new Scene(webView, 800, 700);
        stage.setTitle("IMS Sample Manager");
        stage.setMinWidth(640);
        stage.setMinHeight(520);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.show();
    }

    private static File getAppDir() {
        try {
            File jar = new File(
                    IMSSampleManager.class
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI());
            return jar.getParentFile();
        } catch (Exception e) {
            return new File(".");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
