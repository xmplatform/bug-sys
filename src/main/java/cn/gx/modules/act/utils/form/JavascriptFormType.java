package cn.gx.modules.act.utils.form;

import org.activiti.engine.form.AbstractFormType;

/**
 * Javascript表单字段
 *
 * @author henryyan
 */
public class JavascriptFormType extends AbstractFormType {


    public String getName() {
        return "javascript";
    }


    public Object convertFormValueToModelValue(String propertyValue) {
        return propertyValue;
    }


    public String convertModelValueToFormValue(Object modelValue) {
        return (String) modelValue;
    }
}
