import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AuswertungsController {

    private final AuswertungsView view;

    public AuswertungsController() {
        view = new AuswertungsView();
        view.setAuswertenAction(e -> aktualisiereView());
        view.setSaveAction(e -> speichern());
    }

    public void aktualisiereView() {
        view.setDurchschnitt(view.getDurchschnitt());
        view.setAnzahlAnzeige(view.getAnzahl());
    }

    /**
     * Speichert den Notenspiegel als CSV-Datei.
     */
    public void speichern() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                for (int i = 0; i < view.getAnzahlSpinner().length; i++) {
                    int count = (int) view.getAnzahlSpinner()[i].getValue();
                    writer.write((i + 1) + "," + count);
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(null, "Notenspiegel wurde erfolgreich gespeichert.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Speichern der Datei: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuswertungsController controller = new AuswertungsController();
            controller.view.setVisible(true);
        });
    }
}
