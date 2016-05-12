package cn.gx.modules.act.utils.form;

import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.FormEngine;

/**
 * 自定义表单引擎
 *
 * @author henryyan
 */
public class MyFormEngine implements FormEngine {


    public String getName() {
        return "myformengine";
    }


    public Object renderStartForm(StartFormData startForm) {
        javax.swing.JButton jButton = new javax.swing.JButton();
        jButton.setName("My Start Form Button");
        return jButton;
    }

    public Object renderTaskForm(TaskFormData taskForm) {
        javax.swing.JButton jButton = new javax.swing.JButton();
        jButton.setName("My Task Form Button");
        return jButton;
    }

}
