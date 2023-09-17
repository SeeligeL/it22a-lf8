import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotenverwaltungView extends JFrame {

    private final JTextField klasseTxt = new JTextField(5);
    private final JTextField fachTxt = new JTextField(8);
    private final JSpinner datumSpn = new JSpinner(new SpinnerDateModel());
    private final List<JSpinner> notenEingabe = new ArrayList<>();
    private final JLabel anzahlAnzeige = new JLabel();
    private final JLabel durchschnittAnzeige = new JLabel();
    private final JButton auswertenButton = new JButton("Auswerten");
    private final JButton speichernButton = new JButton("Speichern");
    private final JButton ladenButton = new JButton("Laden");

    public NotenverwaltungView() {
        setTitle("Notenverwaltung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(450, 320));
        setResizable(false);
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
        datumSpn.setEditor(new JSpinner.DateEditor(datumSpn, "dd.MM.yy"));
        datumPanel.add(datumSpn);

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
        panel.add(ladenButton);
        panel.add(speichernButton);
        return panel;
    }

    public String getKlasse() {
        return klasseTxt.getText();
    }

    public void setKlasse(String klasse) {
        klasseTxt.setText(klasse);
    }

    public String getFach() {
        return fachTxt.getText();
    }

    public void setFach(String fach) {
        fachTxt.setText(fach);
    }

    public Date getDatum() {
        return (Date) datumSpn.getValue();
    }

    public void setDatum(Date datum) {
        datumSpn.setValue(datum);
    }

    public List<Integer> getNotenAnzahlen() {
        List<Integer> notenAnzahlen = new ArrayList<>();
        for (JSpinner spinner : notenEingabe) {
            notenAnzahlen.add((Integer) spinner.getValue());
        }
        return notenAnzahlen;
    }

    public void setNotenAnzahlen(List<Integer> notenAnzahlen) {
        for (int i = 0; i < notenEingabe.size(); i++) {
            notenEingabe.get(i).setValue(notenAnzahlen.get(i));
        }
    }

    public void resetNotenEingabe() {
        for (JSpinner spinner : notenEingabe) {
            spinner.setValue(0);
        }
    }

    public void setAuswertenAction(ActionListener listener) {
        auswertenButton.addActionListener(listener);
    }

    public void setLoadAction(ActionListener listener) {
        ladenButton.addActionListener(listener);
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

    public void setDurchschnitt(Double durchschnitt) {
        durchschnittAnzeige.setText(String.valueOf(durchschnitt));
    }

    public void setAnzahlAnzeige(int anzahl) {
        anzahlAnzeige.setText(String.valueOf(anzahl));
    }
}
