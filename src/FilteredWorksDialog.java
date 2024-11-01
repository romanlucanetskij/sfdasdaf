import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FilteredWorksDialog extends JDialog {
  public FilteredWorksDialog(Frame parent, List<Work> works) {
    super(parent, "Результаты фильтрации", true);

    WorkTableModel model = new WorkTableModel(works);
    JTable table = new JTable(model);
    table.setFillsViewportHeight(true);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

    JScrollPane scrollPane = new JScrollPane(table);

    getContentPane().add(scrollPane, BorderLayout.CENTER);

    JButton closeButton = new JButton("Закрыть");
    closeButton.addActionListener(e -> dispose());
    JPanel panel = new JPanel();
    panel.add(closeButton);
    getContentPane().add(panel, BorderLayout.PAGE_END);

    setSize(600, 400);
    setLocationRelativeTo(parent);
  }
}
