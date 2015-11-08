package thin.blog.salary;

/**
 * Created by jmprathab on 23/10/15.
 */
public class Data {
    int id;
    private String day, month, year, inTime, outTime, pay;

    public Data(int id, String year, String month, String day, String inTime, String outTime, String pay) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.inTime = inTime;
        this.outTime = outTime;
        this.pay = pay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}
