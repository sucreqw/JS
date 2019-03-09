package com.sucre.js;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sucre.utils.JsUtil;
import com.sucre.utils.MyUtil;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JSdebuger extends JFrame {

    private JPanel contentPane;
    private JTextField result;
    private JTextField function;
    private JTextField args;
    private JTextArea source;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JSdebuger frame = new JSdebuger();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public JSdebuger() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 807, 499);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(6, 6, 795, 465);
        contentPane.add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 5, 789, 222);
        panel.add(scrollPane);

        JTextArea js = new JTextArea();
        scrollPane.setViewportView(js);

        result = new JTextField();
        result.setBounds(6, 232, 211, 26);
        panel.add(result);
        result.setColumns(10);

        function = new JTextField();
        function.setBounds(229, 232, 163, 26);
        panel.add(function);
        function.setColumns(10);

        args = new JTextField();
        args.setBounds(404, 232, 168, 26);
        panel.add(args);
        args.setColumns(10);

        JButton runs = new JButton("运行");
        runs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //加载js
                JsUtil.SetJs(js.getText());
                //JsUtil.AddJs(js.getText());
                //运行指定function with args and return a reault
                String ret = "";
                try {
                    ret = JsUtil.runJS(function.getText(), args.getText());
                } catch (Exception e2) {
                    result.setText(e2.getMessage());
                }
                //String ret=JsUtil.runJS(function.getText(),args.getText());
                //show the result.
                result.setText(ret);

            }
        });
        runs.setBounds(672, 239, 117, 29);
        panel.add(runs);

        JButton replaces = new JButton("替换");
        replaces.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //add the js to run
                JsUtil.SetJs(js.getText());
                //JsUtil.AddJs(js.getText());

                String sources = source.getText().toString();
                String word = "";
                //get the word between function and args.
                while (null != (word = MyUtil.midWord(function.getText(), args.getText(), sources))) {
                    //put the word into result.txt and js code.
                    String key = result.getText().replace("word", word);
                    String something = "return " + key;
                    String tempJs = " function get() { \r\n"
                            + something + ";"
                            + "}\r\n";
                    // add js code
                    JsUtil.AddJs(tempJs);
                    //run
                    String ret = "";
                    try {
                        ret = JsUtil.runJS("get", "");
                    } catch (Exception e2) {
                        result.setText(e2.getMessage());
                    }

                    //replace all the source js code with result.
                    String words = function.getText() + word + args.getText();

                    sources = sources.replace(words, ret);
                    System.out.print(word);
                }

                source.setText(sources);


            }
        });
        replaces.setBounds(672, 269, 117, 29);
        panel.add(replaces);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(6, 302, 783, 157);
        panel.add(scrollPane_1);

        source = new JTextArea();
        scrollPane_1.setViewportView(source);
    }
}
