package br.ufal.ic.p2.bikeshare.core;

import br.ufal.ic.p2.bikeshare.exceptions.DataInvalidaException;
import br.ufal.ic.p2.bikeshare.exceptions.DataNaoPodeSerNulaException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ValidadorData {

    public static LocalDateTime validarEConverter(String dataStr) throws DataNaoPodeSerNulaException, DataInvalidaException {
        if (dataStr == null || dataStr.trim().isEmpty()) {
            throw new DataNaoPodeSerNulaException();
        }
        String trimmed = dataStr.trim();
        String[] parts = trimmed.split(" ");
        if (parts.length != 2) {
            throw new DataInvalidaException();
        }
        String[] dateParts = parts[0].split("/");
        String[] timeParts = parts[1].split(":");
        if (dateParts.length != 3 || timeParts.length != 2) {
            throw new DataInvalidaException();
        }
        try {
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            if (year < 1000 || year > 9999) throw new DataInvalidaException();
            if (month < 1 || month > 12) throw new DataInvalidaException();
            if (hour < 0 || hour > 23) throw new DataInvalidaException();
            if (minute < 0 || minute > 59) throw new DataInvalidaException();

            LocalDate ld = LocalDate.of(year, month, day);
            LocalTime lt = LocalTime.of(hour, minute);
            return LocalDateTime.of(ld, lt);
        } catch (DateTimeException | NumberFormatException e) {
            throw new DataInvalidaException();
        }
    }

    public static LocalDate validarEConverterDataApenas(String dataStr) throws DataNaoPodeSerNulaException, DataInvalidaException {
        if (dataStr == null || dataStr.trim().isEmpty()) {
            throw new DataNaoPodeSerNulaException();
        }
        String trimmed = dataStr.trim();
        if (trimmed.contains(" ") || trimmed.contains(":")) {
            throw new DataInvalidaException();
        }
        String[] dateParts = trimmed.split("/");
        if (dateParts.length != 3) {
            throw new DataInvalidaException();
        }
        try {
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);

            if (year < 1000 || year > 9999) throw new DataInvalidaException();
            if (month < 1 || month > 12) throw new DataInvalidaException();

            return LocalDate.of(year, month, day);
        } catch (DateTimeException | NumberFormatException e) {
            throw new DataInvalidaException();
        }
    }
}
