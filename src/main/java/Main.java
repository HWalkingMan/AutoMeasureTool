import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/28 1:13
 */
public class Main extends Application {

  public static void main(String[] args) throws IOException {
    launch(args);

  }


  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("fxml/Main.fxml"));
    Scene scene = new Scene(root, 600, 500);
    scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());

    primaryStage.setTitle("Auto Metrics");
    primaryStage.setScene(scene);
    primaryStage.show();

  }
}
