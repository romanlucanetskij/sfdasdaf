import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

public class WorkDialog extends JDialog {
  private JTextField cityField;
  private JTextField addressField;
  private JTextField workTypeField;
  private JTextField costField;
  private JTextField startDateField;
  private JTextField endDateField;
  private boolean succeeded;
  private Work work;

  public WorkDialog(Frame parent) {
    super(parent, "Добавить работу", true);
    JPanel panel = new JPanel(new GridLayout(7, 2));
    cityField = new JTextField();
    addressField = new JTextField();
    workTypeField = new JTextField();
    costField = new JTextField();
    startDateField = new JTextField();
    endDateField = new JTextField();

    panel.add(new JLabel("Город:"));
    panel.add(cityField);
    panel.add(new JLabel("Адрес:"));
    panel.add(addressField);
    panel.add(new JLabel("Тип работ:"));
    panel.add(workTypeField);
    panel.add(new JLabel("Стоимость:"));
    panel.add(costField);
    panel.add(new JLabel("Дата начала (ДД.ММ.ГГГГ):"));
    panel.add(startDateField);
    panel.add(new JLabel("Дата окончания (ДД.ММ.ГГГГ):"));
    panel.add(endDateField);

    JButton addButton = new JButton("Добавить");
    JButton cancelButton = new JButton("Отмена");

    addButton.addActionListener(e -> {
      try {
        work = new Work(
            cityField.getText(),
            addressField.getText(),
            workTypeField.getText(),
            Double.parseDouble(costField.getText()),
            startDateField.getText(),
            endDateField.getText());
        succeeded = true;
        dispose();
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(WorkDialog.this, "Стоимость должна быть числом.");
      } catch (ParseException ex) {
        JOptionPane.showMessageDialog(WorkDialog.this, "Неверный формат даты.");
      } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(WorkDialog.this, ex.getMessage());
      }
    });

    cancelButton.addActionListener(e -> dispose());

    JPanel buttons = new JPanel();
    buttons.add(addButton);
    buttons.add(cancelButton);

    getContentPane().add(panel, BorderLayout.CENTER);
    getContentPane().add(buttons, BorderLayout.PAGE_END);

    pack();
    setResizable(false);
    setLocationRelativeTo(parent);
  }

  public Work getWork() {
    return work;
  }

  public boolean isSucceeded() {
    return succeeded;
  }
}
