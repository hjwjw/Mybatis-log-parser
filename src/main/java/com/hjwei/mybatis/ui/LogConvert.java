package com.hjwei.mybatis.ui;

import javax.swing.*;

/**
 * @author hjw
 * @description Myabtis Log 手动解析窗口
 * @date 2024/04/26 22:28
 */
public class LogConvert {

    private JPanel mainPanel;
    private JTextArea LogInput;
    private JTextPane LogOutput;

    public LogConvert() {

    }

    public JComponent getComponent() {
        return mainPanel;
    }

    public JTextArea getLogInput() {
        return LogInput;
    }

    public JTextPane getLogOutput() {
        return LogOutput;
    }

}
