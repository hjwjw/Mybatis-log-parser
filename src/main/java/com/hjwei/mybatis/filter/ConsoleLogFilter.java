package com.hjwei.mybatis.filter;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hjwei.mybatis.utils.LogUtil;
import com.hjwei.mybatis.utils.PrintUtil;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;

/**
 * @author hjw
 * @description
 * @date 2024/05/02 12:19
 */
public class ConsoleLogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleLogFilter.class);

    private Project project;

    public ConsoleLogFilter(Project project) {
        this.project = project;
    }


    @Override
    public @Nullable Result applyFilter(@NotNull String outputLine, int i) {
        if (null == PrintUtil.getProjectConsoleViewMap(project)) {
            return null;
        }
        //日志行解析
        String sqlLog = LogUtil.parseLogLine(outputLine);
        if (StringUtils.isBlank(sqlLog)) {
            return null;
        }
        PrintUtil.printSql(project, sqlLog);
        return null;
    }
}
