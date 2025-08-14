package Projekt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Student extends Person {

    private final List<Note> notenListe = new ArrayList<>();
    private final List<Kurs> kursListe  = new ArrayList<>();
    private String studentId;
    private final int jahr = LocalDate.now().getYear();
    private final int geburtsJahr;

    public Student(String name, int alter, String studentId) {
        super(name, alter);
        this.geburtsJahr = jahr - alter;    // اول سال تولد محاسبه شود
        setStudentId(studentId);            // بعد ID چک شود
    }

    /** مثال ساده از قانون: دو حرف اول نام + شامل سال تولد */
    public void setStudentId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Studenten-ID darf nicht null sein.");
        }
        String upper = id.trim().toUpperCase(Locale.ROOT);

        String name = getName();
        String nameUpper = name.toUpperCase(Locale.ROOT);
        if (nameUpper.length() < 2 || upper.length() < 2) {
            throw new IllegalArgumentException("Name/ID ist zu kurz.");
        }

        String initials = nameUpper.substring(0, 2);
        boolean ok = upper.startsWith(initials)
                && upper.contains(String.valueOf(geburtsJahr));

        if (!ok) {
            throw new IllegalArgumentException(
                "Ungültige Studenten-ID: Muss mit den Initialen beginnen und das Geburtsjahr enthalten."
            );
        }
        this.studentId = upper;
    }

    public String getStudentId() {
        return studentId;
    }

    public void noteHinzufuegen(Note note) {
        if (note == null) throw new IllegalArgumentException("Note darf nicht null sein.");
        notenListe.add(note);
    }

    public void kursHinzufuegen(Kurs kurs) {
        if (kurs == null) throw new IllegalArgumentException("Kurs darf nicht null sein.");
        kursListe.add(kurs);
    }

    public double getDurch() {
        if (notenListe.isEmpty()) return 0.0;
        double sum = 0.0;
        for (Note n : notenListe) sum += n.getNote();
        return sum / notenListe.size();
    }

    /** خواندن فقط‌-خواندنی لیست‌ها برای امنیت API */
    public List<Kurs> getKurse() { return Collections.unmodifiableList(kursListe); }
    public List<Note> getNoten() { return Collections.unmodifiableList(notenListe); }

    public List<String> getKursNamen() {
        List<String> namen = new ArrayList<>();
        for (Kurs k : kursListe) {
            try {
                var m = k.getClass().getMethod("getKursName");
                Object val = m.invoke(k);
                if (val instanceof String s) namen.add(s);
            } catch (Exception ignore) {
            }
        }
        return namen;
    }

    public double[] getStudentNotes() {
        double[] arr = new double[notenListe.size()];
        for (int i = 0; i < notenListe.size(); i++) {
            arr[i] = notenListe.get(i).getNote();
        }
        return arr;
    }

    @Override
    public String toString() {
        return String.format(
            Locale.ROOT,
            "Name: %s | Alter: %d | ID: %s | Noten: %d | Kurse: %d | Ø: %.2f",
            getName(), getAlter(), studentId, notenListe.size(), kursListe.size(), getDurch()
        );
    }
    @Override
    public void zeigeDetails() {
        System.out.printf(
            "Name: %s%nAlter: %d%nStudenten-ID: %s%n",
            getName(), getAlter(), getStudentId()
        );

        // اگر خواستی، کورس‌ها و نمره‌ها را هم چاپ کن:
        for (Kurs k : getKurse()) {
            k.zeigeKurs();
        }
        for (Note n : getNoten()) {
            System.out.println("Note: " + String.format("%.2f", n.getNote()));
        }
        System.out.println("------------------------------");
    }


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student s)) return false;
        return Objects.equals(studentId, s.studentId);
    }
    @Override public int hashCode() {
        return Objects.hash(studentId);
    }
}
