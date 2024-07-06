package com.hjwei.mybatis.module;

import java.awt.*;
import javax.swing.*;

import com.hjwei.mybatis.utils.PrintUtil;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;

/**
 * @author hjw
 * @description
 * @date 2024/04/27 15:24
 */
public class MybatisLogView extends SimpleToolWindowPanel {

    private Project project;
    private ConsoleView consoleView;

    public MybatisLogView(Project project) {
        super(false, true);
        this.project = project;

        TextConsoleBuilder consoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
        consoleView = consoleBuilder.getConsole();
        PrintUtil.setProjectConsoleViewMap(project, consoleView);
        //设置窗体侧边栏
//        DefaultActionGroup actionGroup = new DefaultActionGroup();
//        actionGroup.add(new ConvertButton());
//        ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar("MybatisLog", actionGroup, false);
//        actionToolbar.setTargetComponent(this);
//        setToolbar(actionToolbar.getComponent());
        //设置主窗体
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(consoleView.getComponent(), BorderLayout.CENTER);
        setContent(panel);

    }


    public ConsoleView getConsoleView() {
        return consoleView;
    }
}
