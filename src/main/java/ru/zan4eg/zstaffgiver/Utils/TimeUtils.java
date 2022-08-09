package ru.zan4eg.zstaffgiver.Utils;

public class TimeUtils {
    public TimeUtils() {
    }

    public static String getTime(long sec) {
        if (sec < 1L) {
            return "1 секунда";
        } else {
            long m = sec / 60L;
            sec %= 60L;
            long h = m / 60L;
            m %= 60L;
            long d = h / 24L;
            h %= 24L;
            long y = d / 365L;
            d %= 365L;
            String time = "";
            if (y > 0L) {
                time = time + y + " " + formatTime(y, "год", "лет", "лет");
                if (d > 0L || h > 0L || m > 0L || sec > 0L) {
                    time = time + " ";
                }
            }

            if (d > 0L) {
                time = time + d + " " + formatTime(d, "день", "дня", "дней");
                if (h > 0L || m > 0L || sec > 0L) {
                    time = time + " ";
                }
            }

            if (h > 0L) {
                time = time + h + " " + formatTime(h, "час", "часа", "часов");
                if (m > 0L || sec > 0L) {
                    time = time + " ";
                }
            }

            if (m > 0L) {
                time = time + m + " " + formatTime(m, "минута", "минуты", "минут");
                if (sec > 0L) {
                    time = time + " ";
                }
            }

            if (sec > 0L) {
                time = time + sec + " " + formatTime(sec, "секунда", "секунды", "секунд");
            }

            return time;
        }
    }

    public static long longTime(String in) {
        String name = in.replaceAll("\\d", "");

        int time;
        try {
            time = Integer.parseInt(in.replaceAll("\\D", ""));
        } catch (NumberFormatException var4) {
            return 0L;
        }

        if (!name.equalsIgnoreCase("years") && !name.equalsIgnoreCase("year") && !name.equalsIgnoreCase("y")) {
            if (!name.equalsIgnoreCase("days") && !name.equalsIgnoreCase("day") && !name.equalsIgnoreCase("d")) {
                if (!name.equalsIgnoreCase("hours") && !name.equalsIgnoreCase("hour") && !name.equalsIgnoreCase("h")) {
                    if (!name.equalsIgnoreCase("minutes") && !name.equalsIgnoreCase("minute") && !name.equalsIgnoreCase("min") && !name.equalsIgnoreCase("m")) {
                        return !name.equalsIgnoreCase("seconds") && !name.equalsIgnoreCase("second") && !name.equalsIgnoreCase("sec") && !name.equalsIgnoreCase("s") ? 0L : (long) time;
                    } else {
                        return (long) (time * 60);
                    }
                } else {
                    return (long) (time * 60 * 60);
                }
            } else {
                return (long) (time * 24 * 60 * 60);
            }
        } else {
            return (long) (time * 24 * 60 * 60 * 365);
        }
    }

    private static String formatTime(long num, String single, String lessfive, String others) {
        if (num % 100L > 10L && num % 100L < 15L) {
            return others;
        } else {
            switch ((int) (num % 10L)) {
                case 1:
                    return single;
                case 2:
                case 3:
                case 4:
                    return lessfive;
                default:
                    return others;
            }
        }
    }
}
