import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class WorkTableModel extends AbstractTableModel {
  private List<Work> works;
  private final String[] columnNames = {"Город", "Адрес", "Тип работ", "Стоимость", "Начало", "Окончание"};

  public WorkTableModel(List<Work> works) {
    this.works = works;
  }

  public void setWorks(List<Work> works) {
    this.works = works;
    fireTableDataChanged();
  }

  public List<Work> getWorks() {
    return works;
  }

  @Override
  public int getRowCount() {
    return works.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Work work = works.get(rowIndex);
    switch (columnIndex) {
      case 0:
        return work.getCity();
      case 1:
        return work.getAddress();
      case 2:
        return work.getWorkType();
      case 3:
        return work.getCost();
      case 4:
        return work.getStartDateStr();
      case 5:
        return work.getEndDateStr();
      default:
        return null;
    }
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    Work work = works.get(rowIndex);
    try {
      switch (columnIndex) {
        case 0:
          work.setCity((String) aValue);
          break;
        case 1:
          work.setAddress((String) aValue);
          break;
        case 2:
          work.setWorkType((String) aValue);
          break;
        case 3:
          work.setCost(Double.parseDouble(aValue.toString()));
          break;
        case 4:
          work.setStartDateStr((String) aValue);
          break;
        case 5:
          work.setEndDateStr((String) aValue);
          break;
      }
      fireTableCellUpdated(rowIndex, columnIndex);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Ошибка при вводе данных: " + e.getMessage());
    }
  }
}
