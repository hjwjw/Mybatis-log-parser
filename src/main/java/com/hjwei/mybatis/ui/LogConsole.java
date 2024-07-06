package com.hjwei.mybatis.ui;

import javax.swing.*;

/**
 * @author hjw
 * @description Mybatis Log 实时解析窗口
 * @date 2024/05/04 15:05
 */
public class LogConsole {

    private JPanel mainPanel;
    private JScrollBar scrollBar;
    private JTextArea textArea;

    public JPanel getComponent() {
        return mainPanel;
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
