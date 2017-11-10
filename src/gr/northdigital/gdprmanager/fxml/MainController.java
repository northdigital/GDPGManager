package gr.northdigital.gdprmanager.fxml;

import gr.logismos.orasqlworker.SqlWorker;
import gr.logismos.orasqlworker.utils.JdbcConBuilder;
import gr.northdigital.gdprmanager.model.ColumnDef;
import gr.northdigital.gdprmanager.model.TableDef;
import gr.northdigital.gdprmanager.utils.OraHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
  private FilteredList<ColumnDef> filteredColumns;

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
      // initialize collections
      users = FXCollections.observableArrayList();
      tables = FXCollections.observableArrayList();
      filteredTables = new FilteredList<>(tables, t -> true);
      columns = FXCollections.observableArrayList();
      filteredColumns = new FilteredList<>(columns, t -> false);

      // apply column factories
      columnName.setCellValueFactory(new PropertyValueFactory<>("columnName"));
      isSecure.setCellValueFactory(param -> param.getValue().isSecureProperty());
      isSecure.setCellFactory(CheckBoxTableCell.forTableColumn(isSecure));

      // apply datasets
      cbUsers.setItems(users);
      lstTables.setItems(filteredTables);
      tblColumns.setItems(filteredColumns);

      // set listener for table->selection change event
      lstTables.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        // when the new selection is null ...
        if(newValue == null) {
          filteredColumns.setPredicate(column -> false);
          return;
        }

        // when the new selection is not null ...
        String selectedOwner = newValue.getOwner();
        String selectedTable = newValue.getTableName();

        filteredColumns.setPredicate(column -> {
          return column.owner.equals(selectedOwner) && column.tableName.equals(selectedTable);
        });
      });

      // read data from db
      jdbcConBuilder = new JdbcConBuilder("192.168.1.201", "casino", "system", "sporades");
      sqlWorker = new SqlWorker(jdbcConBuilder);

      sqlWorker.run(connection -> {
        users.addAll(OraHelper.getOraUsers(connection));
        tables.addAll(OraHelper.getOraTables(connection));
        columns.addAll(OraHelper.getOraColumns(connection));
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
    }
  }
}
