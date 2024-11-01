import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Work {
  private String city;
  private String address;
  private String workType;
  private double cost;
  private Date startDate;
  private Date endDate;

  private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

  public Work(String city, String address, String workType, double cost, String startDateStr, String endDateStr) throws ParseException {
    if (cost < 0) {
      throw new IllegalArgumentException("Стоимость не может быть отрицательной");
    }
    this.city = city;
    this.address = address;
    this.workType = workType;
    this.cost = cost;
    this.startDate = sdf.parse(startDateStr);
    this.endDate = sdf.parse(endDateStr);
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getWorkType() {
    return workType;
  }

  public void setWorkType(String workType) {
    this.workType = workType;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    if (cost < 0) {
      throw new IllegalArgumentException("Стоимость не может быть отрицательной");
    }
    this.cost = cost;
  }

  public String getStartDateStr() {
    return sdf.format(startDate);
  }

  public void setStartDateStr(String dateStr) throws ParseException {
    this.startDate = sdf.parse(dateStr);
  }

  public String getEndDateStr() {
    return sdf.format(endDate);
  }

  public void setEndDateStr(String dateStr) throws ParseException {
    this.endDate = sdf.parse(dateStr);
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public long getDuration() {
    return endDate.getTime() - startDate.getTime();
  }

  @Override
  public String toString() {
    return "Город: " + city + "\nАдрес: " + address + "\nТип работ: " + workType +
        "\nСтоимость: " + cost + "\nНачало: " + getStartDateStr() + "\nОкончание: " + getEndDateStr();
  }
}
