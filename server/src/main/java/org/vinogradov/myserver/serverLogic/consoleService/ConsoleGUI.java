package org.vinogradov.myserver.serverLogic.consoleService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConsoleGUI extends JFrame implements ActionListener {
    private final String waiting = "Введите <help> для помощи. Ожидание команды:";

    private final JTextArea log;
    private final JTextField fieldInput;
    private final ExecuteTheCommand executeTheCommand;
    private final ConsoleLogic consoleLogic;

    public ConsoleGUI(ExecuteTheCommand executeTheCommand, ConsoleLogic consoleLogic) {
        this.executeTheCommand = executeTheCommand;
        this.consoleLogic = consoleLogic;

        log = new JTextArea(waiting + "\n");
        fieldInput = new JTextField();
        log.setFont(new Font("Dialog", Font.PLAIN, 18));
        fieldInput.setFont(new Font("Dialog", Font.PLAIN, 18));

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(this);
        log.setEditable(false);
   //     log.setLineWrap(true);
  //      log.setWrapStyleWord(true);

        fieldInput.addActionListener(this);

        add(new JScrollPane(log), BorderLayout.CENTER);
        add(fieldInput, BorderLayout.SOUTH);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                consoleLogic.closeNetty();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                consoleLogic.closeNetty();
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

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

    public void exit() {
        this.dispose();
    }
}
