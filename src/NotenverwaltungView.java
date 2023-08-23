import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NotenverwaltungView extends JFrame {

    private final JTextField klasseTxt = new JTextField(5);
    private final JTextField fachTxt = new JTextField(8);
    private final JFormattedTextField datumTxt = new JFormattedTextField(new SimpleDateFormat("dd.MM.yy"));
    private final List<JSpinner> notenEingabe = new ArrayList<>();
    private final JLabel anzahlAnzeige = new JLabel();
    private final JLabel durchschnittAnzeige = new JLabel();
    private final JButton speichernButton = new JButton("Speichern");
    private final JButton auswertenButton = new JButton("Auswerten");

    public NotenverwaltungView() {
        setTitle("Notenverwaltung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 320);
        setLayout(new BorderLayout());

        JPanel kopfPanel = createKopfPanel();
        JPanel notenEingabePanel = createNotenEingabePanel();
        JPanel auswertenPanel = createAuswertenPanel();
        JPanel buttonPanel = createButtonPanel();

        add(kopfPanel, BorderLayout.NORTH);
        add(notenEingabePanel, BorderLayout.CENTER);
        add(auswertenPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createNotenEingabePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 6; i++) {
            JPanel zeilePanel = new JPanel();
            JLabel label = new JLabel("Note " + (i));
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 99, 1);
            notenEingabe.add(new JSpinner(spinnerModel));
            zeilePanel.add(label);
            zeilePanel.add(notenEingabe.get(i - 1));
            contentPanel.add(zeilePanel);
        }

        panel.add(contentPanel);
        return panel;
    }

    private JPanel createKopfPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel klassePanel = new JPanel();
        klassePanel.add(new JLabel("Klasse:"));
        klassePanel.add(klasseTxt);

        JPanel fachPanel = new JPanel();
        fachPanel.add(new JLabel("Fach:"));
        fachPanel.add(fachTxt);

        JPanel datumPanel = new JPanel();
        datumPanel.add(new JLabel("Datum:"));
        datumTxt.setColumns(5);
        datumPanel.add(datumTxt);

        panel.add(klassePanel);
        panel.add(fachPanel);
        panel.add(datumPanel);

        return panel;
    }

    private JPanel createAuswertenPanel() {

        JPanel layoutPanel = new JPanel(new FlowLayout());

        JPanel contentPanel = new JPanel(new GridLayout(2, 2, 5, 10));

        contentPanel.add(new JLabel("Anzahl:"));
        contentPanel.add(anzahlAnzeige);
        contentPanel.add(new JLabel("Durchschnitt:"));
        contentPanel.add(durchschnittAnzeige);

        layoutPanel.add(contentPanel);

        return layoutPanel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        panel.add(auswertenButton);
        panel.add(speichernButton);
        return panel;
    }

    public String getKlasse() {
        return klasseTxt.getText();
    }

    public String getFach() {
        return fachTxt.getText();
    }

    public String getDatum() {
        return datumTxt.getText();
    }

    public List<JSpinner> getNotenEingabe() {
        return notenEingabe;
    }

    public void setAuswertenAction(ActionListener listener) {
        auswertenButton.addActionListener(listener);
    }

    public void setSaveAction(ActionListener listener) {
        speichernButton.addActionListener(listener);
    }

    public int getAnzahl() {
        int anzahl = 0;

        for (JSpinner spinner : notenEingabe) {
            anzahl = anzahl + (int) spinner.getValue();
        }

        return anzahl;
    }

    public double getDurchschnitt() {
        int anzahl = getAnzahl();

        if (anzahl == 0) {
            return Double.NaN;
        } else {
            int summe = 0;

            for (int i = 0; i < notenEingabe.size(); i++) {
                int anzahlNote = (int) notenEingabe.get(i).getValue();
                anzahl += anzahlNote;
                summe += (i + 1) * anzahlNote;
            }
            return Math.round(10.0 * summe / anzahl) / 10.0;
        }
    }

    public void setDurchschnitt(Double durchschnitt) {
        durchschnittAnzeige.setText(String.valueOf(durchschnitt));
    }

    public void setAnzahlAnzeige(int anzahl) {
        anzahlAnzeige.setText(String.valueOf(anzahl));
    }
}
