package com.hjwei.mybatis.module;

import org.jetbrains.annotations.NotNull;
import com.hjwei.mybatis.utils.Icons;
import com.hjwei.mybatis.utils.LogUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * @author hjw
 * @description
 * @date 2024/04/27 12:19
 */
public class ConvertButton extends DumbAwareAction {

    private Project project;
    private ManualConvertView manualConvertView;

    public ConvertButton() {
    }

    public ConvertButton(Project project, ManualConvertView manualConvertView) {
        super("Convert", "Convert", Icons.CONVERT);
        this.project = project;
        this.manualConvertView = manualConvertView;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        int lineCount = manualConvertView.getLogConvertUI().getLogInput().getLineCount();
        if (lineCount == 0 ) {
            Messages.showWarningDialog("请输入日志", "Error");
            return;
        }
        // 从输入中提取SQL
        String sqlLog = LogUtil.extractSql(manualConvertView.getLogConvertUI().getLogInput().getText());
        manualConvertView.getLogConvertUI().getLogOutput().setText(sqlLog);
        // SQL格式化
//        BasicFormatter basicFormatter = new BasicFormatter();
//        manualConvertView.getLogConvertUI().getLogOutput().setText(basicFormatter.format(wholeSql));
    }
}
