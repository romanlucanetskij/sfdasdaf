import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class WorkStack {
  private LinkedList<Work> works;

  public WorkStack() {
    works = new LinkedList<>();
  }

  public void push(Work work) {
    works.push(work);
  }

  public Work pop() {
    return works.pop();
  }

  public void removeAt(int index) {
    works.remove(index);
  }

  public List<Work> getWorks() {
    return works;
  }

  public void updateWorks(List<Work> updatedWorks) {
    this.works = new LinkedList<>(updatedWorks);
  }

  public void loadFromFile(String filename) {
    File file = new File(filename);
    if (!file.exists()) {
      try {
        // Если файл не существует, создаем его
        file.createNewFile();
        System.out.println("Файл " + filename + " не найден. Создан новый файл.");
      } catch (IOException e) {
        System.out.println("Ошибка при создании файла: " + e.getMessage());
      }
      return;
    }

    works.clear();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(";");
        if (parts.length == 6) {
          Work work = new Work(parts[0], parts[1], parts[2],
              Double.parseDouble(parts[3]), parts[4], parts[5]);
          works.add(work);
        }
      }
    } catch (IOException | ParseException e) {
      System.out.println("Ошибка при загрузке данных: " + e.getMessage());
    }
  }

  public void saveToFile(String filename) {
    File file = new File(filename);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      for (Work work : works) {
        writer.write(String.join(";",
            work.getCity(),
            work.getAddress(),
            work.getWorkType(),
            String.valueOf(work.getCost()),
            work.getStartDateStr(),
            work.getEndDateStr()));
        writer.newLine();
      }
      System.out.println("Данные успешно сохранены в файл " + filename);
    } catch (IOException e) {
      System.out.println("Ошибка при сохранении данных: " + e.getMessage());
    }
  }

  // Остальные методы остаются без изменений

  public double calculateTotalCost(String address) {
    return works.stream()
        .filter(work -> work.getAddress().equalsIgnoreCase(address))
        .mapToDouble(Work::getCost)
        .sum();
  }

  public Work getLongestWork() {
    return works.stream()
        .max(Comparator.comparingLong(Work::getDuration))
        .orElse(null);
  }

  public void sortByCost() {
    works.sort(Comparator.comparingDouble(Work::getCost));
  }

  public List<Work> getWorksByCityLastYear(String city) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.YEAR, -1);
    Date lastYear = calendar.getTime();
    return works.stream()
        .filter(work -> work.getCity().equalsIgnoreCase(city) && work.getEndDate() != null)
        .filter(work -> work.getEndDate().after(lastYear))
        .collect(Collectors.toList());
  }
}
