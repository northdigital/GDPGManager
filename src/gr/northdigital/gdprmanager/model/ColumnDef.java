package gr.northdigital.gdprmanager.model;

import gr.logismos.orasqlworker.utils.DbField;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class ColumnDef {
  public ColumnDef() {
    this.columnName = new SimpleStringProperty();
    this.isSecure = new SimpleBooleanProperty();
  }

  public String getColumnName() {
    return columnName.get();
  }

  public SimpleStringProperty columnNameProperty() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName.set(columnName);
  }

  public boolean isIsSecure() {
    return isSecure.get();
  }

  public SimpleBooleanProperty isSecureProperty() {
    return isSecure;
  }

  public void setIsSecure(boolean isSecure) {
    this.isSecure.set(isSecure);
  }

  @DbField(fieldName = "owner")
  public String owner;
  @DbField(fieldName = "table_name")
  public String tableName;
  @DbField(fieldName = "is_secured")
  public Boolean originalIsSecured;

  @DbField(fieldName = "column_name")
  public SimpleStringProperty columnName;
  @DbField(fieldName = "is_secured")
  public SimpleBooleanProperty isSecure;
}
