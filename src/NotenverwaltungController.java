import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotenverwaltungController {

    private final NotenverwaltungView view;

    public NotenverwaltungController() {
        view = new NotenverwaltungView();
        view.setAuswertenAction(e -> aktualisiereView());
        view.setLoadAction(e -> laden());
        view.setSaveAction(e -> speichern());
        aktualisiereView();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NotenverwaltungController controller = new NotenverwaltungController();
            controller.view.setVisible(true);
        });
    }

    private void aktualisiereView() {
        view.setDurchschnitt(calculateAverage());
        view.setAnzahlAnzeige(view.getAnzahl());
    }

    /**
     * Berechnet den Notendurchschnitt einer Klassenarbeit auf eine Nachkommastelle genau.
     * Berechnung: [Durchschnitt] = [Summe der Einzelnoten] / [Anzahl]
     * @return Durchschnitt auf eine Nachkommastelle gerundet.
     */
    private Double calculateAverage() {
        int anzahl = view.getAnzahl();
        List<Integer> notenAnzahlen = view.getNotenAnzahlen();

        if (anzahl == 0) {
            return Double.NaN;
        } else {
            int summe = 0;

            for (int i = 0; i < notenAnzahlen.size(); i++) {
                int anzahlNote = notenAnzahlen.get(i);
                anzahl += anzahlNote;
                summe += (i + 1) * anzahlNote;
            }
            return Math.round(10.0 * summe / anzahl) / 10.0;
        }
    }

    /**
     * Speichert den Notenspiegel als CSV-Datei.
     */
    private void speichern() {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("notenverwaltung.csv"));

            writer.write("Klasse" + ", " + view.getKlasse());
            writer.newLine();
            writer.write("Fach" + ", " + view.getFach());
            writer.newLine();
            writer.write("Datum" + ", " + SimpleDateFormat.getDateInstance().format(view.getDatum()));
            writer.newLine();
            writer.newLine();

            for (int i = 0; i < view.getNotenAnzahlen().size(); i++) {
                int count = view.getNotenAnzahlen().get(i);
                writer.write("Note " + (i + 1) + "," + count);
                writer.newLine();
            }

            writer.close();
            resetView();
            JOptionPane.showMessageDialog(null, "Notenspiegel wurde erfolgreich gespeichert.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Speichern der Datei: " + e.getMessage());
        }
    }

    /**
     * Lädt die CSV-Datei und schreibt die Daten in die View.
     */
    private void laden() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("notenverwaltung.csv"));

            String line = reader.readLine();

            while (line != null) {
                if (line.startsWith("Klasse, ")) {
                    String klasse = line.substring("Klasse, ".length()).trim();
                    view.setKlasse(klasse);
                } else if (line.startsWith("Fach, ")) {
                    String fach = line.substring("Fach, ".length()).trim();
                    view.setFach(fach);
                } else if (line.startsWith("Datum, ")) {
                    String dateString = line.substring("Datum, ".length()).trim();

                    Date date = SimpleDateFormat.getDateInstance().parse(dateString); // Für den Moment einfach ignorieren!

                    view.setDatum(date);
                } else if (line.startsWith("Note")) {
                    List<Integer> notenAnzahlen = new ArrayList<>();
                    while (line != null && line.startsWith("Note")) {
                        String anzahl = line.split(",")[1].trim();
                        notenAnzahlen.add(Integer.valueOf(anzahl));
                        line = reader.readLine();
                    }
                    view.setNotenAnzahlen(notenAnzahlen);
                }
                line = reader.readLine();
            }
            JOptionPane.showMessageDialog(null, "Notenspiegel wurde erfolgreich geladen.");
        } catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Laden der Datei: " + e.getMessage());
        }
    }

    private void resetView() {
        view.setKlasse("");
        view.setFach("");
        view.setDatum(new Date());
        view.resetNotenEingabe();
    }
}
