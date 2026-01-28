package app;

public class Utils {

    public static int hhmmssToSeconds(String time) {
        String[] parts = time.split(":");
        if (parts.length != 3)
            throw new IllegalArgumentException();

        int hour = Integer.parseInt(parts[0]) * 3600;
        int minutes = Integer.parseInt(parts[1]) * 60;
        int seconds = Integer.parseInt(parts[2]);

        return hour + minutes + seconds;
    }

    public static String secondsToHHMMSS(int seconds) {
        return String.format("%02d:%02d:%02d",
                seconds / 3600,
                (seconds % 3600) / 60,
                seconds % 60);
    }
}
