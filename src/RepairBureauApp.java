import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RepairBureauApp extends JFrame {
  private WorkStack workStack;
  private JTable workTable;
  private WorkTableModel tableModel;
  private AnimatedPanel animatedPanel;

  public RepairBureauApp() {
    setTitle("Бюро ремонта");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    workStack = new WorkStack();

    try {
      workStack.loadFromFile("works.txt");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Ошибка при загрузке данных: " + e.getMessage());
    }

    initUI();
  }

  private void initUI() {
    animatedPanel = new AnimatedPanel();
    setContentPane(animatedPanel);
    animatedPanel.setLayout(new BorderLayout());

    createMenu();

    tableModel = new WorkTableModel(workStack.getWorks());
    workTable = new JTable(tableModel);
    workTable.setFillsViewportHeight(true);
    workTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

    workTable.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));

    tableModel.addTableModelListener(e -> workStack.updateWorks(tableModel.getWorks()));

    JScrollPane scrollPane = new JScrollPane(workTable);
    animatedPanel.add(scrollPane, BorderLayout.CENTER);
  }

  private void createMenu() {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("Файл");
    JMenuItem loadItem = new JMenuItem("Загрузить");
    JMenuItem saveItem = new JMenuItem("Сохранить");
    JMenuItem exitItem = new JMenuItem("Выход");

    loadItem.addActionListener(e -> loadWorks());
    saveItem.addActionListener(e -> saveWorks());
    exitItem.addActionListener(e -> exitApp());

    fileMenu.add(loadItem);
    fileMenu.add(saveItem);
    fileMenu.addSeparator();
    fileMenu.add(exitItem);

    JMenu workMenu = new JMenu("Работы");
    JMenuItem addItem = new JMenuItem("Добавить");
    JMenuItem removeItem = new JMenuItem("Удалить");
    JMenuItem calculateItem = new JMenuItem("Рассчитать стоимость по адресу");
    JMenuItem longestItem = new JMenuItem("Самая длительная работа");
    JMenuItem sortItem = new JMenuItem("Сортировать по стоимости");
    JMenuItem filterItem = new JMenuItem("Фильтр по городу и дате");

    addItem.addActionListener(e -> addWork());
    removeItem.addActionListener(e -> removeWork());
    calculateItem.addActionListener(e -> calculateTotalCost());
    longestItem.addActionListener(e -> showLongestWork());
    sortItem.addActionListener(e -> sortWorks());
    filterItem.addActionListener(e -> filterWorks());

    workMenu.add(addItem);
    workMenu.add(removeItem);
    workMenu.addSeparator();
    workMenu.add(calculateItem);
    workMenu.add(longestItem);
    workMenu.add(sortItem);
    workMenu.add(filterItem);

    menuBar.add(fileMenu);
    menuBar.add(workMenu);
    setJMenuBar(menuBar);
  }

  private void loadWorks() {
    try {
      workStack.loadFromFile("works.txt");
      tableModel.setWorks(workStack.getWorks());
      JOptionPane.showMessageDialog(this, "Данные успешно загружены.");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Ошибка при загрузке данных: " + e.getMessage());
    }
  }

  private void saveWorks() {
    try {
      workStack.saveToFile("works.txt");
      JOptionPane.showMessageDialog(this, "Данные успешно сохранены.");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Ошибка при сохранении данных: " + e.getMessage());
    }
  }

  private void exitApp() {
    int result = JOptionPane.showConfirmDialog(this, "Вы действительно хотите выйти?", "Выход", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      saveWorks();
      System.exit(0);
    }
  }

  private void addWork() {
    WorkDialog dialog = new WorkDialog(this);
    dialog.setVisible(true);
    if (dialog.isSucceeded()) {
      Work work = dialog.getWork();
      workStack.push(work);
      tableModel.setWorks(workStack.getWorks());
    }
  }

  private void removeWork() {
    int selectedRow = workTable.getSelectedRow();
    if (selectedRow >= 0) {
      int result = JOptionPane.showConfirmDialog(this, "Вы действительно хотите удалить выбранную работу?", "Удаление", JOptionPane.YES_NO_OPTION);
      if (result == JOptionPane.YES_OPTION) {
        workStack.removeAt(selectedRow);
        tableModel.setWorks(workStack.getWorks());
      }
    } else {
      JOptionPane.showMessageDialog(this, "Пожалуйста, выберите работу для удаления.");
    }
  }

  private void calculateTotalCost() {
    String address = JOptionPane.showInputDialog(this, "Введите адрес:");
    if (address != null && !address.isEmpty()) {
      double totalCost = workStack.calculateTotalCost(address);
      JOptionPane.showMessageDialog(this, "Суммарная стоимость работ по адресу " + address + ": " + totalCost);
    }
  }

  private void showLongestWork() {
    Work longestWork = workStack.getLongestWork();
    if (longestWork != null) {
      JOptionPane.showMessageDialog(this, "Самая длительная работа:\n" + longestWork);
    } else {
      JOptionPane.showMessageDialog(this, "Нет доступных работ.");
    }
  }

  private void sortWorks() {
    workStack.sortByCost();
    tableModel.setWorks(workStack.getWorks());
  }

  private void filterWorks() {
    String city = JOptionPane.showInputDialog(this, "Введите город:");
    if (city != null && !city.isEmpty()) {
      java.util.List<Work> filteredWorks = workStack.getWorksByCityLastYear(city);
      if (!filteredWorks.isEmpty()) {
        new FilteredWorksDialog(this, filteredWorks).setVisible(true);
      } else {
        JOptionPane.showMessageDialog(this, "Работы в городе " + city + " за прошлый год не найдены.");
      }
    }
  }
}
