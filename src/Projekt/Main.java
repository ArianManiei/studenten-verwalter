package Projekt;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner Rian = new Scanner(System.in);
        boolean checker = false;
        ArrayList<Student> StudentenListeMain = new ArrayList<>();

        StudentenVerwaltung SV = new StudentenVerwaltung(StudentenListeMain);

        for (int i = 0; i <= 28; i++) System.out.print("=");
        System.out.println();
        System.out.println();
        System.out.println(" Studenten-Verwaltungssystem");
        System.out.println();
        for (int i = 0; i <= 28; i++) System.out.print("=");
        System.out.println();

        int wahl = -1;
        do {
            System.out.printf(
                "1. Neuen Studenten hinzufügen%n" +
                "2. Note für einen Studenten eingeben%n" +
                "3. Alle Studenten anzeigen%n" +
                "4. Durchschnitt eines Studenten berechnen%n" +
                "5. Daten speichern%n" +
                "6. Daten laden%n" +
                "0. Beenden%n%n"
            );

            System.out.println("Bitte wählen Sie eine Option:");
            try {
                wahl = Rian.nextInt();
                Rian.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Bitte eine gültige Zahl eingeben.");
                Rian.nextLine();
                continue;
            }

            switch (wahl) {

                case 1:
                    checker = false;
                    System.out.println("--- Neuen Studenten hinzufügen ---");
                    do {
                        try {
                            System.out.println("Name: ");
                            String studentName = Rian.nextLine();

                            System.out.println("Alter: ");
                            int studentAlter = Rian.nextInt();
                            Rian.nextLine();

                            System.out.println("Studenten-ID: ");
                            String STudentenID = Rian.nextLine();

                            Student Snew = new Student(studentName, studentAlter, STudentenID);
                            StudentenListeMain.add(Snew);
                            System.out.println("Student erfolgreich hinzugefügt");
                            checker = true;

                        } catch (IllegalArgumentException e) {
                            System.out.println("Fehler: " + e.getMessage());
                        } catch (InputMismatchException e) {
                            System.out.println("Bitte nur Zahlen für das Alter eingeben.");
                            Rian.nextLine();
                        }
                    } while (!checker);
                    break;

                case 2:
                    checker = false;
                    System.out.println("--- Note hinzufügen ---");

                    System.out.println("Studenten-ID: ");
                    String studentenIDSuchen = Rian.nextLine().toUpperCase();

                    System.out.println("Kursname: ");
                    String KursName = Rian.nextLine();

                    System.out.println("KursCode: ");
                    String KursCode = Rian.nextLine();

                    Note NoteStudenten = null;
                    do {
                        try {
                            System.out.println("Note (1.0 - 5.0): ");
                            double NoteStudent = Rian.nextDouble();
                            Rian.nextLine();
                            NoteStudenten = new Note(NoteStudent);
                            checker = true;

                        } catch (InputMismatchException e) {
                            System.out.println("Bitte nur Zahl eingeben.");
                            Rian.nextLine();
                        } catch (IllegalArgumentException e) {
                            System.out.println("Fehler: " + e.getMessage());
                        }
                    } while (!checker);

                    Kurs Kursstudent = new Kurs(KursName, KursCode);

                    Student ST = SV.nachIdSuchen(studentenIDSuchen);
                    if (ST == null) {
                        System.out.println("Kein Student mit dieser ID gefunden!");
                        break;
                    }
                    ST.noteHinzufuegen(NoteStudenten);
                    ST.kursHinzufuegen(Kursstudent);
                    System.out.println("Note/Kurs hinzugefügt.");
                    break;

                case 3:
                    if (StudentenListeMain.isEmpty()) {
                        System.out.println("Keine Studenten vorhanden.");
                    } else {
                        for (Student s : StudentenListeMain) {
                            s.zeigeDetails();
                        }
                    }
                    break;

                case 4:
                    System.out.println("Studenten-ID: ");
                    String id4 = Rian.nextLine();
                    Student ST2 = SV.nachIdSuchen(id4);
                    if (ST2 == null) {
                        System.out.println("Kein Student mit dieser ID gefunden!");
                        break;
                    }
                    System.out.printf("Durchschnittsnote: %.2f%n", ST2.getDurch());
                    break;

                case 5:
                    try {
                        java.nio.file.Path dir = java.nio.file.Paths.get("out");
                        java.nio.file.Files.createDirectories(dir);
                        java.nio.file.Path fileP = dir.resolve("students.csv");
                        boolean existed = java.nio.file.Files.exists(fileP);
                        try (var w = java.nio.file.Files.newBufferedWriter(
                                fileP,
                                java.nio.charset.StandardCharsets.UTF_8,
                                java.nio.file.StandardOpenOption.CREATE,
                                java.nio.file.StandardOpenOption.APPEND)) {
                            if (!existed) { w.write("Name,Alter,ID"); w.newLine(); }
                            for (Student s : StudentenListeMain) {
                                w.write(String.format("%s,%d,%s", s.getName(), s.getAlter(), s.getStudentId()));
                                w.newLine();
                            }
                        }
                        System.out.println("Gespeichert: " + fileP.toAbsolutePath());
                    } catch (Exception e) {
                        System.out.println("Speicherfehler: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("(Laden) Noch nicht implementiert.");
                    break;

                case 0:
                    System.out.println("Auf Wiedersehen!");
                    break;

                default:
                    System.out.println("Ungültige Auswahl.");
            }

        } while (wahl != 0);

        Rian.close();
    }
}
