import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
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

    private void aktualisiereView() {
        view.setDurchschnitt(view.getDurchschnitt());
        view.setAnzahlAnzeige(view.getAnzahl());
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
            writer.write("Datum" + ", " + view.getDatum());
            writer.newLine();
            writer.newLine();

            for (int i = 0; i < view.getNotenAnzahlen().size(); i++) {
                int count = (int) view.getNotenAnzahlen().get(i);
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

            while (line != null)
            {
                if (line.startsWith("Klasse, ")) {
                    String klasse = line.substring("Klasse, ".length()).trim();
                    view.setKlasse(klasse);
                }
                else if (line.startsWith("Fach, ")) {
                    String fach = line.substring("Fach, ".length()).trim();
                    view.setFach(fach);
                }
                else if (line.startsWith("Datum, ")) {
                    String datum = line.substring("Datum, ".length()).trim();
                    view.setDatum(datum);
                }
                else if (line.startsWith("Note")) {
                    List<Integer> notenAnzahlen = new ArrayList<>();
                    while(line != null && line.startsWith("Note"))
                    {
                        String anzahl = line.split(",")[1].trim();
                        notenAnzahlen.add(Integer.valueOf(anzahl));
                        line = reader.readLine();
                    }
                    view.setNotenAnzahlen(notenAnzahlen);
                }
                line = reader.readLine();
            }
            JOptionPane.showMessageDialog(null, "Notenspiegel wurde erfolgreich geladen.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Laden der Datei: " + e.getMessage());
        }
    }

    private void resetView() {
        view.setKlasse("");
        view.setFach("");
        view.setDatum("");
        view.resetNotenEingabe();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NotenverwaltungController controller = new NotenverwaltungController();
            controller.view.setVisible(true);
        });
    }
}
