package helper;

import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Helper abstract class contains functions relating to time conversions.
 *
 * @author Jonathan Congmon
 */
public abstract class Time {
    /**
     * Converts a time to the UTC time.
     *
     * @param time Time in the local timezone
     * @return Timestamp in UTC time
     */
    public static Timestamp localToUTC(LocalDateTime time) {
        ZonedDateTime ZDT = time.atZone(ZoneId.systemDefault());
        ZonedDateTime convertedUTC = ZDT.withZoneSameInstant(ZoneId.of("UTC"));
        return Timestamp.valueOf(convertedUTC.toLocalDateTime());
    }

    /**
     * Converts a UTC time to the local system time.
     *
     * @param time Time in UTC
     * @return ZonedDateTime in the local timezone
     */
    public static ZonedDateTime UTCtoLocal(Timestamp time) {
        LocalDateTime LDT = time.toLocalDateTime();
        ZonedDateTime ZDT = LDT.atZone(ZoneId.of("UTC"));
        ZonedDateTime convertedZDT = ZDT.withZoneSameInstant(ZoneId.systemDefault());
        return convertedZDT;
    }

    /**
     * Converts a UTC time to EST time.
     *
     * @param time Time in the UTC timezone
     * @return Timestamp in EST time
     */
    public static Timestamp UTCtoEST(Timestamp time) {
        LocalDateTime LDT = time.toLocalDateTime();
        ZonedDateTime ZDT = LDT.atZone(ZoneId.of("UTC"));
        ZonedDateTime convertedZDT = ZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        return Timestamp.valueOf(convertedZDT.toLocalDateTime());
    }

    /**
     * Checks if a time is between start and end business hours, and if the time is from Monday to Friday.
     *
     * @param time Time in UTC
     * @return Boolean if the time is during business hours.
     */
    public static boolean isNotDuringBusinessHours(Timestamp time) {
        //time is in UTC
        LocalTime startTime = LocalTime.of(8, 0, 0);
        LocalTime endTime = LocalTime.of(22, 0, 0);
        LocalTime queryEST = UTCtoEST(time).toLocalDateTime().toLocalTime();
        LocalDateTime LDT = time.toLocalDateTime();
        int dayOfWeek = LDT.getDayOfWeek().getValue();
        return queryEST.isBefore(startTime) || queryEST.isAfter(endTime) || dayOfWeek == 6 || dayOfWeek == 7;
    }

    /**
     * Checks if there are overlapping appointments from the appointmentList and time.
     *
     * @param time            Time to be checked
     * @param appointmentList Appointment List containing Appointment objects to be checked against time
     * @return Boolean if there are overlapping appointments
     */
    public static boolean isOverlappingAppointment(Timestamp time, ObservableList<Appointments> appointmentList) {
        LocalDateTime timeLDT = time.toLocalDateTime();
        for (Appointments appointments : appointmentList) {
            LocalDateTime appStartLDT = appointments.getStart().toLocalDateTime();
            LocalDateTime appEndLDT = appointments.getEnd().toLocalDateTime();
            if (timeLDT.isAfter(appStartLDT) && timeLDT.isBefore(appEndLDT)) {
                return true;
            }
            if (timeLDT.isEqual(appStartLDT)) {
                return true;
            }
        }
        return false;
    }
}
