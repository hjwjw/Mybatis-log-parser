package com.hjwei.mybatis.module;

import com.hjwei.mybatis.ui.LogConvert;
import com.hjwei.mybatis.utils.PrintUtil;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.JBSplitter;

/**
 * @author hjw
 * @description
 * @date 2024/04/27 15:24
 */
public class ManualConvertView extends SimpleToolWindowPanel {

    private Project project;
    private LogConvert logConvertUI;

    public ManualConvertView(Project project) {
        super(false, true);
        this.project = project;
        logConvertUI = new LogConvert();

        //设置窗体侧边栏
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new ConvertButton(project,this));

        ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar("LogConvert", actionGroup, false);
        actionToolbar.setTargetComponent(this);
        setToolbar(actionToolbar.getComponent());

        JBSplitter splitter = new JBSplitter(false);
        splitter.setSplitterProportionKey("main.splitter.key");
        splitter.setFirstComponent(logConvertUI.getComponent());
        splitter.setProportion(0.3f);
        setContent(splitter);

    }

    public LogConvert getLogConvertUI() {
        return logConvertUI;
    }
}
