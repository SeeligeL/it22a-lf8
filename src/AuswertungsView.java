import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AuswertungsView extends JFrame {

    private JSpinner[] anzahlSpinner;
    private JTextField anzahlAnzeige;
    private JTextField durchschnittAnzeige;
    private JButton speichernButton;
    private JButton auswertenButton;

    public AuswertungsView() {
        setTitle("Notenverwaltung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new BorderLayout());

        JPanel inputPanel = createEingabePanel();
        JPanel outputPanel = createAuswertenPanel();
        JPanel buttonPanel = createButtonPanel();

        add(inputPanel, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        auswertenButton = new JButton("Auswerten");
        speichernButton = new JButton("Speichern");

        panel.add(auswertenButton);
        panel.add(speichernButton);
        return panel;
    }

    private JPanel createEingabePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        anzahlSpinner = new JSpinner[6];
        JLabel[] noteLabels = new JLabel[6];

        for (int i = 0; i < 6; i++) {
            JPanel zeilePanel = new JPanel(new FlowLayout());
            noteLabels[i] = new JLabel("Note " + (i + 1));
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 99, 1);
            anzahlSpinner[i] = new JSpinner(spinnerModel);
            zeilePanel.add(noteLabels[i]);
            zeilePanel.add(anzahlSpinner[i]);
            contentPanel.add(zeilePanel);
        }

        panel.add(contentPanel);
        return panel;
    }

    private JPanel createAuswertenPanel() {

        JPanel layoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel durchschnittPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel durchschnittLabel = new JLabel("Durchschnitt:");
        durchschnittAnzeige = new JTextField(5);
        durchschnittAnzeige.setEnabled(false);
        durchschnittPanel.add(durchschnittLabel);
        durchschnittPanel.add(durchschnittAnzeige);

        JPanel anzahlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel anzahlLabel = new JLabel("Anzahl:");
        anzahlAnzeige = new JTextField(5);
        anzahlAnzeige.setEnabled(false);
        anzahlPanel.add(anzahlLabel);
        anzahlPanel.add(anzahlAnzeige);

        contentPanel.add(anzahlPanel);
        contentPanel.add(durchschnittPanel);

        layoutPanel.add(contentPanel);

        return layoutPanel;
    }

    public void setAuswertenAction(ActionListener listener) {
        auswertenButton.addActionListener(listener);
    }

    public void setSaveAction(ActionListener listener) {
        speichernButton.addActionListener(listener);
    }

    public JSpinner[] getAnzahlSpinner() {
        return anzahlSpinner;
    }

    public int getAnzahl()
    {
        int anzahl = 0;

        for (JSpinner spinner : anzahlSpinner)
        {
            anzahl = anzahl + (int) spinner.getValue();
        }

        return anzahl;
    }

    public double getDurchschnitt()
    {
        int anzahl = getAnzahl();

        if (anzahl == 0) {
            return Double.NaN;
        } else {
            int summe = 0;
            for (int i = 0; i < anzahlSpinner.length; i++) {
                int anzahlNote = (int) anzahlSpinner[i].getValue();
                anzahl += anzahlNote;
                summe += (i + 1) * anzahlNote;
            }
            return Math.round(10.0 * summe / anzahl) / 10.0;
        }
    }

    public void setDurchschnitt(Double durchschnitt)
    {
        durchschnittAnzeige.setText(String.valueOf(durchschnitt));
    }

    public void setAnzahlAnzeige(int anzahl)
    {
        anzahlAnzeige.setText(String.valueOf(anzahl));
    }
}
