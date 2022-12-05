package org.vinogradov.myserver.serverLogic.consoleService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsoleGUI extends JFrame implements ActionListener {
    private final String waiting = "Ожидание команды:";

    private final JTextArea log;
    private final JTextField fieldInput;
    private final ExecuteTheCommand executeTheCommand;

    public ConsoleGUI(ExecuteTheCommand executeTheCommand) {
        this.executeTheCommand = executeTheCommand;

        log = new JTextArea(waiting + "\n");
        fieldInput = new JTextField();
        log.setFont(new Font("Dialog", Font.PLAIN, 20));
        fieldInput.setFont(new Font("Dialog", Font.PLAIN, 20));

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
 //       setAlwaysOnTop(true);

        log.setEditable(false);
        log.setLineWrap(true);
        log.setWrapStyleWord(true);

        fieldInput.addActionListener(this);

        add(new JScrollPane(log), BorderLayout.CENTER);
        add(fieldInput, BorderLayout.SOUTH);

        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setLog("Вы ввели команду: " + fieldInput.getText());
        executeTheCommand.execute(fieldInput.getText());
        if (fieldInput.getText().equals("clear")) {
            fieldInput.setText("");
            return;
        }
        fieldInput.setText("");
        setLog("");
        setLog(waiting);
    }

    public void setLog(String txt) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(txt + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    public void clearLog() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.setText("" + waiting + "\n");
            }
        });
    }

    public void closeConsole() {
        this.dispose();
    }
}
