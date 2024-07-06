package com.hjwei.mybatis.factory;

import org.jetbrains.annotations.NotNull;

import com.hjwei.mybatis.module.ManualConvertView;
import com.hjwei.mybatis.module.MybatisLogView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

/**
 * @author hjw
 * @description
 * @date 2024/04/27 15:39
 */
public class TabFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        //Mybatis Log 实时解析Tab
        MybatisLogView mybatisLogView = new MybatisLogView(project);
        //Mybatis Log 手动解析Tab
        ManualConvertView manualConvertView = new ManualConvertView(project);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content mybatisLogContent = contentFactory.createContent(mybatisLogView, "Mybatis Log Console", false);
        Content mybatisExtractContent = contentFactory.createContent(manualConvertView, "Mybatis Log Convert", false);

        toolWindow.getContentManager().addContent(mybatisLogContent,0);
        toolWindow.getContentManager().addContent(mybatisExtractContent,1);
    }
}
