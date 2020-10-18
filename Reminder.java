import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Reminder {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz uuuu");
    private Date nextDate;
    Period period;
    String reminderTitle;
    String reminderMessage;

    //This constructor is for parsing user input
    public Reminder(String nextDate, int num, int timeInterval, String reminderTitle, String reminderMessage) {
        // timeInterval represents {day, week, month, year}
        switch (timeInterval) {
            case 2:
                this.period = Period.ofWeeks(num);
                break;
            case 3:
                this.period = Period.ofMonths(num);
                break;
            case 4:
                this.period = Period.ofYears(num);
                break;
            default:
                this.period = Period.ofDays(num);
        }

        SimpleDateFormat toDate = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            this.nextDate = toDate.parse(nextDate);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(0);
        }

        this.reminderTitle = reminderTitle;
        this.reminderMessage = reminderMessage;
    }

    //This constructor is for parsing from file
    public Reminder(String nextDate, String period, String reminderTitle, String reminderMessage) {
        SimpleDateFormat toDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        try {
            this.nextDate = toDate.parse(nextDate);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(0);
        }
        this.period = Period.parse(period);
        this.reminderTitle = reminderTitle;
        this.reminderMessage = reminderMessage;
    }

    //Calculates what the next date should be
    public Date calculateNextDate() {
        ZonedDateTime dateTime = LocalDateTime.parse(nextDate.toString(), formatter)
                .atZone(ZoneId.systemDefault());
        Instant newTime = dateTime.plus(period).toInstant();
        return Date.from(newTime);
    }

    //Sets new dates after push notification is sent
    public void alarm() {
        nextDate = calculateNextDate();
    }

    //used to store inside file
    public String toString() {
        return nextDate + "," + period + "," + reminderTitle + "," + reminderMessage;
    }

    public boolean isSameDay() {
        ZonedDateTime currentDate = LocalDateTime.parse(new Date().toString(), formatter).atZone(ZoneId.systemDefault());
        ZonedDateTime alarmDate = LocalDateTime.parse(getNextDate().toString(), formatter)
                .atZone(ZoneId.systemDefault());
        ZonedDateTime week = alarmDate.minusDays(1);
        return currentDate.isAfter(week) && currentDate.isBefore(alarmDate);
    }

    public long getMillisBetween() {
        if (isSameDay()) {
            return getNextDate().getTime() - new Date().getTime();
        }
        return -1;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public Period getPeriod() {
        return period;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
