package gr.northdigital.gdprmanager;

import gr.northdigital.gdprmanager.fxml.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

  public Main() throws SQLException {
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      // we need the loader in order to get the controller, so we are loading in 2 steps
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./fxml/Main.fxml"));
      Parent root = fxmlLoader.load();
      MainController mainController = fxmlLoader.getController();

      primaryStage.setOnShown(e -> {
        try {
          mainController.onShown();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      });

      Scene scene = new Scene(root, 600, 800);
      scene.getStylesheets().add(getClass().getResource("./fxml/standard.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle("GDPR Manager");
      primaryStage.show();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) throws SQLException {
    launch(args);
  }
}
