package com.jeeplus.modules.bug.bean;

import com.jeeplus.modules.bug.entity.Bug;
import com.jeeplus.modules.bug.util.BugStatus;

import java.util.List;

/**
 * Created by always on 16/5/7.
 */
public class Charts {

    /**
     * 图表显示
     */
    private int value;
    private String color;
    private String highlight;
    private String label;

    // 简单状态
    private String projectId;
    private String status;


    public Charts() {
        super();
    }

    public Charts(String label, String color, String highlight){
        this.label=label;
        this.color=color;
        this.highlight=highlight;
    }

    public Charts(BugStatus bugStatus, String color, String highlight){
        this.color=color;
        this.highlight=highlight;

        this.status=bugStatus.getStatus();
        this.label=bugStatus.getStatusPhrase();
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
