import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TriangleBotGUI implements ActionListener {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    JTextField a, b, c, A, B, C;

    static int offset = 0;
    public  int definedValues() {
        int result = definedSides();
        if (!A.getText().isEmpty()) result++;
        if (!B.getText().isEmpty()) result++;
        if (!C.getText().isEmpty()) result++;
        return result;
    }
    public  int definedSides() {
        int result = 0;
        if (!a.getText().isEmpty()) result++;
        if (!b.getText().isEmpty()) result++;
        if (!c.getText().isEmpty()) result++;
        return result;
    }
    public static JTextField addField(Container pane, GridBagConstraints c, String label) {
        c.insets = new Insets(0, 10, 0, 0);
        c.weightx = 0;
        pane.add(new JLabel(label), c);
        c.gridx++;
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 1;
        JTextField field = new JFormattedTextField(NumberOrBlankFormat.getInstance());
        field.setColumns(4);
        pane.add(field, c);
        c.gridx++;
        return field;
    }

    public void addComponentsToPane(Container pane) {

        pane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        // constraints.fill = GridBagConstraints.HORIZONTAL;
        // JTextArea description = new JTextArea("Now is the time for all good
        // men to come to the aid of their country");
        // description.setLineWrap(true);
        // description.setWrapStyleWord(true);
        // constraints.insets = new Insets(5,5,5,5);
        // constraints.gridwidth = 4;
        // pane.add(description, constraints);
        // constraints.gridwidth = 1;
        // constraints.fill = GridBagConstraints.NONE;
        // constraints.gridy++;

        a = addField(pane, constraints, "a");
        A = addField(pane, constraints, "A");

        constraints.gridx = 0;
        constraints.gridy++;
        b = addField(pane, constraints, "b");
        B = addField(pane, constraints, "B");

        constraints.gridx = 0;
        constraints.gridy++;
        c = addField(pane, constraints, "c");
        C = addField(pane, constraints, "C");

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 4;
        JButton calculate = new JButton("Calculate");
        pane.add(calculate, constraints);
        calculate.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 4;
        JButton clear = new JButton("Clear");
        pane.add(clear, constraints);
        clear.addActionListener(e -> {
            a.setText("");
            A.setText("");
            b.setText("");
            B.setText("");
            c.setText("");
            C.setText("");
        });

    }
  
    JFrame controlFrame = new JFrame("Triangle Bot GUI");
    
    private void createAndShowGUI() {
        // Create and set up the window.
         controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the content pane.
        addComponentsToPane(controlFrame.getContentPane());

        // Display the window.
        controlFrame.pack();
        controlFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TriangleBotGUI().createAndShowGUI();
            }
        });
    }

    public static double getDouble(JTextField field) {
        String txt = field.getText();
        if (txt.isEmpty())
            return Double.NaN;
        return Double.parseDouble(txt);
    }

    public static String format(double d) {
        if (Double.isNaN(d))
            return "";
        if (d == (long) d)
            return String.format("%d", (long) d);
        return String.format("%.1f", d);
    }

    public static void set(JTextField angle, JTextField side, Side x) {
        angle.setText(format(x.angle()));
        side.setText(format(x.side()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (definedValues() < 3) {
            if (definedSides() == 0) 
            JOptionPane.showMessageDialog(controlFrame,
                    "You must define at least 3 values including at least one side",
                    "Not enough information",
                    JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(controlFrame,
                        "You must define at least 3 values",
                        "Not enough information",
                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (definedSides() == 0) {
            JOptionPane.showMessageDialog(controlFrame,
                    "You must define at least 1 side",
                    "Not enough information",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
            
        Side sideA = new Side(getDouble(a), getDouble(A), "a");
        Side sideB = new Side(getDouble(b), getDouble(B), "b");
        Side sideC = new Side(getDouble(c), getDouble(C), "c");

        try {
        Trig triangle = Trig.solve(sideA, sideB, sideC);
       
        showTriangle(triangle);
        if (triangle.variant != null)
            showTriangle(triangle.variant);
        } catch (IllegalArgumentException excetion) {
            JOptionPane.showMessageDialog(controlFrame,
                    "There are no triangles with those sides and angles",
                    "No Such Triangle",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

    }
    private void showTriangle(Trig triangle) {
        Side sideA = triangle.a;
        Side sideB = triangle.b;
        Side sideC = triangle.c;
        set(A, a, sideA);
        set(B, b, sideB);
        set(C, c, sideC);

        String title = String.format("A = %s°, B = %s°, C = %s°     a = %s, b = %s, c = %s", format(sideA.angle()),
                format(sideB.angle()), format(sideC.angle()),
                format(sideA.side()), format(sideB.side()), format(sideC.side()));
       
        double cx = Trig.cos(sideA.angle()) * sideB.side();
        double cy = Trig.sin(sideA.angle()) * sideB.side();
        double ax = 0;
        double ay = 0;
        double bx = sideC.side();
        double by = 0;

        if (cx < 0) {
            ax += -cx;
            bx += -cx;
            cx = 0.0;
        }

        showTriangle(title, ax, ay, bx, by, cx, cy);
    }

    private void showTriangle(String title, double ax, double ay, double bx, double by, double cx, double cy) {
        // Create and set up the window.
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        offset+=30;
        // Set up the content pane.
        frame.getContentPane().add(new TrianglePanel(ax, ay, bx, by, cx, cy), null);
        Point controlFrameLocation = controlFrame.getLocation();
        frame.setLocation((int)(controlFrameLocation.getX() + controlFrame.getWidth())+offset,
                (int)(controlFrameLocation.getY())+offset);
        // Display the window.
        frame.pack();
        frame.setVisible(true);

    }

}
