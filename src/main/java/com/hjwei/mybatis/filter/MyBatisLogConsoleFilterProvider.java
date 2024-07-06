package com.hjwei.mybatis.filter;

import org.jetbrains.annotations.NotNull;

import com.intellij.execution.filters.ConsoleFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;


/**
 * @author hjw
 * @description
 * @date 2024/05/04 16:22
 */
public class MyBatisLogConsoleFilterProvider implements ConsoleFilterProvider {
    @Override
    public Filter[] getDefaultFilters(@NotNull Project project) {
        ConsoleLogFilter filter = new ConsoleLogFilter(project);
        return new Filter[] { filter };
    }
}
