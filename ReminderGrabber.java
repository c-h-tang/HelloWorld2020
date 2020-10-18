import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class ReminderGrabber {
    List<Reminder> reminders;
    File reminderStorage;

    public ReminderGrabber() {
        reminders = new ArrayList<Reminder>();
        reminderStorage = new File("reminderStorage.txt");
        try {
            reminderStorage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        populate();
    }

    public void populate() {
        FileReader fr = null;
        try {
            fr = new FileReader(reminderStorage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        BufferedReader bfr = new BufferedReader(fr);
        while (true) {
            String line = null;
            try {
                line = bfr.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
            if (line == null) {
                break;
            }
            StringTokenizer strTok = new StringTokenizer(line, ",");
            reminders.add(new Reminder(strTok.nextToken(), strTok.nextToken(), strTok.nextToken(), strTok.nextToken(), strTok.nextToken()));
        }
        try {
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void addReminder(String initialDate, int num, int timeInterval, String reminderTitle, String reminderMessage) {
        reminders.add(new Reminder(initialDate, num, timeInterval, reminderTitle, reminderMessage));
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(reminderStorage, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        PrintWriter pw = new PrintWriter(fos);
        pw.println(reminders.get(reminders.size() - 1));
        pw.close();
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void debug() {
        for (Reminder r : reminders) {
            System.out.println(r);
        }
    }
}
