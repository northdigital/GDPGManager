package gr.northdigital.gdprmanager.model;

import gr.logismos.orasqlworker.utils.DbField;
import javafx.beans.property.SimpleStringProperty;

public class TableDef {

  public TableDef() {
    this.owner = new SimpleStringProperty();
    this.tableName = new SimpleStringProperty();
  }

  public TableDef(String owner, String tableName) {
    super();
    this.owner.set(owner);
    this.tableName.set(tableName);
  }

  public String getOwner() {
    return owner.get();
  }

  public SimpleStringProperty ownerProperty() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner.set(owner);
  }

  public String getTableName() {
    return tableName.get();
  }

  public SimpleStringProperty tableNameProperty() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName.set(tableName);
  }

  @Override
  public String toString() {
    return getTableName();
  }

  @DbField(fieldName = "owner")
  public SimpleStringProperty owner;
  @DbField(fieldName = "table_name")
  public SimpleStringProperty tableName;
}
