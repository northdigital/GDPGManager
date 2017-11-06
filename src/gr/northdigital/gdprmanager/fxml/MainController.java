package gr.northdigital.gdprmanager.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class MainController {
  public static MainController mainController;

  public MainController() {
    mainController = this;
  }

  @FXML
  public ComboBox<String> cbUsers;
}
