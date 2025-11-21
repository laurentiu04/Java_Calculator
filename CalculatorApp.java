package Tema;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class Calculator extends JFrame implements ActionListener {

    // Constante
    private static final int WIDTH = 320;
    private static final int HEIGTH = 500;
    private static final Color backgroundColor = new Color(240, 244, 243);
    private static final String fontSemiBold = "Yu Gothic UI Semibold";
    private static final String font = "Tahoma";

    // Declaratii variabile
    GridBagLayout layout;
    GridBagConstraints gbc;
    JTextField resultField;
    JTextField lastOperation;
    double operant = Double.NaN;
    String operator = "";
    boolean newCalc = false;

    Calculator() {
        // Initializare grid si constrangeri
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();

        // Initializare fereastra si atibute pentru aceasta
        setTitle("Calculator");
        setIconImage(new ImageIcon("Tema\\calculator.png").getImage());
        setSize(WIDTH, HEIGTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
        setLayout(layout);
        setResizable(false);
        setLocationRelativeTo(null);

        // Initializare camp rezultat
        resultField = new JTextField();
        resultField.setText("0");
        resultField.setFocusable(false);
        resultField.setBackground(backgroundColor);
        resultField.setBorder(null);
        resultField.setHorizontalAlignment(SwingConstants.RIGHT);
        resultField.setFont(new Font(font, Font.BOLD, 40));

        // -- adaugare in grid --
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 15);
        add(resultField, gbc);
        // ------------------------

        lastOperation = new JTextField();
        lastOperation.setText("");
        lastOperation.setFocusable(false);
        lastOperation.setBackground(backgroundColor);
        lastOperation.setBorder(null);
        lastOperation.setHorizontalAlignment(SwingConstants.RIGHT);
        lastOperation.setFont(new Font(font, Font.PLAIN, 20));
        lastOperation.setForeground(new Color(200, 200, 200));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 3, -5, 15);
        add(lastOperation, gbc);

        // Definire etichete pentru butoane
        String[] labels = {
                "del", "c", "sqrt", "+",
                "7", "8", "9", "-",
                "4", "5", "6", "*",
                "1", "2", "3", "/",
                ".", "0", "", "="
        };

        // Crearea si initializarea butoanelor
        for (int i = 0; i < labels.length; i++) {
            JButton bt1 = new JButton(labels[i]);
            bt1.setFont(new Font(fontSemiBold, Font.PLAIN, 20));
            bt1.setBorder(null);
            bt1.setFocusPainted(false);
            if (labels[i] == "+" || labels[i] == "-" || labels[i] == "*" || labels[i] == "/" || labels[i] == "sqrt"
                    || labels[i] == "c" || labels[i] == "del") {
                bt1.setBackground(new Color(230, 230, 230));
                // bt1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            } else if (labels[i] == "=") {
                bt1.setBackground(new Color(230, 130, 100));
            } else
                bt1.setBackground(Color.white);

            bt1.addActionListener(this);

            gbc.gridx = (i % 4);
            gbc.gridy = 2 + (i / 4);
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.fill = GridBagConstraints.BOTH;

            gbc.insets = new Insets(3, 3, 3, 3);

            if (gbc.gridx == 0)
                gbc.insets.left = 10;
            if (gbc.gridx == 3)
                gbc.insets.right = 10;
            if (gbc.gridy == 6)
                gbc.insets.bottom = 10;

            add(bt1, gbc);
        }
        this.setVisible(true);
    }

    static String buttonText(JButton b) {
        return b.getActionCommand();
    }

    static double computeOperation(double op1, double op2, String operator) {

        switch (operator) {
            case "/":
                op1 /= op2;
                break;

            case "*":
                op1 *= op2;
                break;

            case "+":
                op1 += op2;
                break;

            case "-":
                op1 -= op2;
                break;

            default:
                break;
        }

        return op1;
    }

    void clearCalc() {
        resultField.setText("");
        lastOperation.setText("");
        operant = Double.NaN;
        operator = "";
        newCalc = false;
    }

    // Crearea functionalitatilor pentru butoane
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (newCalc)
            clearCalc();

        switch (buttonText(btn)) {

            case "*":
            case "/":
            case "+":
            case "-": {

                if (operator.equals("/") && resultField.getText().equals("0"))
                {
                    resultField.setText("0div!");
                    break;
                }

                if (buttonText(btn).equals("-")
                    && (resultField.getText().equals("") || resultField.getText().equals("0"))) {
                    resultField.setText("-");
                    break;
                }

                if (resultField.getText().equals("") || resultField.getText().equals("-"))
                    break;

                if (Double.isNaN(operant)) {
                    operant = Double.parseDouble(resultField.getText());
                    operator = buttonText(btn).toString();
                    resultField.setText("");
                    lastOperation.setText(operant + " " + operator);
                } else {
                    operant = computeOperation(operant, Double.valueOf(resultField.getText()), operator);
                    operator = buttonText(btn).toString();
                    resultField.setText("");
                    lastOperation.setText(operant + " " + operator);
                }
                break;
            }

            case "sqrt": {
                
            }

            case "=": {
                if (operator.equals("/") && resultField.getText().equals("0"))
                {
                    resultField.setText("0div!");
                    break;
                }

                if (Double.isNaN(operant)) {
                    lastOperation.setText(resultField.getText() + " =");
                } else {
                    lastOperation.setText(operant + " " + operator + " " + resultField.getText() + " =");
                    operant = computeOperation(operant, Double.valueOf(resultField.getText()), operator);
                    resultField.setText(String.valueOf(operant));
                }
                newCalc = true;
                break;
            }

            case "c": {
                clearCalc();
                break;
            }

            case "del": {
                if (!resultField.getText().equals(""))
                    resultField.setText(
                        resultField.getText().substring(0, resultField.getText().length()-1)
                    );
                break;
            }

            case "0": {
                if (resultField.getText().equals("0")) {
                    break;
                }
            }

            case ".": {

                if (resultField.getText().contains("."))
                    break;
            }

            default: {

                if (resultField.getText().equals("0"))
                    resultField.setText(buttonText(btn));
                else if (buttonText(btn).equals(""))
                    break;
                else
                    resultField.setText(resultField.getText() + buttonText(btn));

                break;
            }
        }
    }
}

public class CalculatorApp {
    public static void main(String[] args) {
        new Calculator();
    }
}
