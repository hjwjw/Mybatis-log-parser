package com.hjwei.mybatis.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;

/**
 * @author hjw
 * @description
 * @date 2024/05/07 10:31
 */
public class PrintUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintUtil.class);

    private static final Map<Project, ConsoleView> PROJECT_CONSOLE_VIEW_MAP = new ConcurrentHashMap<>();

    public static void printSql(Project project, String sqlLog) {
        //SQL格式化
//        BasicFormatter basicFormatter = new BasicFormatter();
//        PrintUtil.getProjectConsoleViewMap(project).print(basicFormatter.format(sqlLog), ConsoleViewContentType.NORMAL_OUTPUT);
        ConsoleView consoleView = getProjectConsoleViewMap(project);
        consoleView.print("---------------------------------------------------------------------------------------------------------------------\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print(sqlLog, ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public static void setProjectConsoleViewMap(Project project, ConsoleView consoleView) {
        PROJECT_CONSOLE_VIEW_MAP.put(project, consoleView);
    }

    public static ConsoleView getProjectConsoleViewMap(Project project) {
        return PROJECT_CONSOLE_VIEW_MAP.get(project);
    }
}
