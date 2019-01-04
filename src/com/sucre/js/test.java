package com.sucre.js;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test {
    private JPanel jpanle;
    private JButton runButton;
    private JTextArea source;
    private JComboBox mission;

    public test() {
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m=(String)mission.getSelectedItem();
                switch (m){

                    case "java" :
                        String sourceText=source.getText();
                        String temp="private byte[] getdata(String cookies, String uid) {\r\n"
                                  + "    StringBuilder data = new StringBuilder(900);\r\n"
                                  + "    String temp = \"\";\r\n"
                                  + "    data.append(\""+sourceText.replace("\n","\");\r\n    data.append(")//")\r\ndata.append("

                                  + "\")\r\nreturn data.toString().getBytes();\r\n"
                                  + "}\r\n"

                        ;
                        source.setText(temp);
                        break;
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setContentPane(new test().jpanle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 100, 807, 499);
        frame.pack();
        frame.setVisible(true);

    }
}
