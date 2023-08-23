import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NotenverwaltungController {

    private final NotenverwaltungView view;

    public NotenverwaltungController() {
        view = new NotenverwaltungView();
        view.setAuswertenAction(e -> aktualisiereView());
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

            for (int i = 0; i < view.getNotenEingabe().size(); i++) {
                int count = (int) view.getNotenEingabe().get(i).getValue();
                writer.write("Note " + (i + 1) + "," + count);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Notenspiegel wurde erfolgreich gespeichert.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Speichern der Datei: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NotenverwaltungController controller = new NotenverwaltungController();
            controller.view.setVisible(true);
        });
    }
}
