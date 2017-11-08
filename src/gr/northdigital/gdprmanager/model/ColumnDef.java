package gr.northdigital.gdprmanager.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class ColumnDef {
  public ColumnDef(String columnName, boolean isSecure) {
    this.columnName = new SimpleStringProperty(columnName);
    this.isSecure = new SimpleBooleanProperty(isSecure);
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

  private SimpleStringProperty columnName;
  private SimpleBooleanProperty isSecure;
}
