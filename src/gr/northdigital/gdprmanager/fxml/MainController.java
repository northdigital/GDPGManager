package gr.northdigital.gdprmanager.fxml;

import gr.logismos.orasqlworker.SqlWorker;
import gr.logismos.orasqlworker.utils.JdbcConBuilder;
import gr.northdigital.gdprmanager.model.ColumnDef;
import gr.northdigital.gdprmanager.model.TableDef;
import gr.northdigital.gdprmanager.utils.OraHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
  private JdbcConBuilder jdbcConBuilder;
  private SqlWorker sqlWorker;
  private ObservableList<String> users;
  private ObservableList<TableDef> tables;
  private FilteredList<TableDef> filteredTables;
  private ObservableList<ColumnDef> columns;

  @FXML
  public HBox hBoxMain;

  @FXML
  public VBox vBoxUsersTables;

  @FXML
  public ComboBox<String> cbUsers;

  @FXML
  public ListView<TableDef> lstTables;

  @FXML
  public TableView<ColumnDef> tblColumns;

  @FXML
  public TableColumn<ColumnDef, String> columnName;

  @FXML
  public TableColumn<ColumnDef, Boolean> isSecure;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      users = FXCollections.observableArrayList();
      tables = FXCollections.observableArrayList();
      filteredTables = new FilteredList<>(tables, t -> true);

      columns = FXCollections.observableArrayList();

      columnName.setCellValueFactory(new PropertyValueFactory<>("columnName"));
//      columnName.setCellFactory(TextFieldTableCell.forTableColumn());
//      columnName.setOnEditCommit(e -> {
//        TableColumn.CellEditEvent<ColumnDef, String> cellEditEventEventHandler = (TableColumn.CellEditEvent<ColumnDef, String>)e;
//        ColumnDef columnDef = e.getRowValue();
//        columnDef.setColumnName(cellEditEventEventHandler.getNewValue());
//      });

      isSecure.setCellValueFactory(param -> param.getValue().isSecureProperty());
      isSecure.setCellFactory(CheckBoxTableCell.forTableColumn(isSecure));

      jdbcConBuilder = new JdbcConBuilder("192.168.1.201", "casino", "system", "sporades");
      //jdbcConBuilder = new JdbcConBuilder("192.168.1.202", "casino", "system", "sporades");
      //jdbcConBuilder = new JdbcConBuilder("localhost", "casino","system", "sporades");

      sqlWorker = new SqlWorker(jdbcConBuilder);

      cbUsers.setItems(users);
      lstTables.setItems(filteredTables);
      tblColumns.setItems(columns);

      sqlWorker.run(connection -> {
        users.addAll(OraHelper.getOraUsers(connection));
        tables.addAll(OraHelper.getOraTables(connection));
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void onShown() throws Exception {
    cbUsers.getSelectionModel().selectFirst();
  }

  @FXML
  public void onAction(ActionEvent actionEvent) throws Exception {
    if (actionEvent.getTarget() == cbUsers) {
      String selectedUser = cbUsers.getValue();

      filteredTables.setPredicate(table -> {
        return table.getOwner().equals(selectedUser);
      });

//      tables.clear();
//      sqlWorker.run(connection -> {
//        tables.addAll(OraHelper.getUserTables(connection, selectedUser));
//      });
    }
  }
}
