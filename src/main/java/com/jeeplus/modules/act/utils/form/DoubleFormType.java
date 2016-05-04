package com.jeeplus.modules.act.utils.form;

import org.activiti.engine.form.AbstractFormType;
import org.apache.commons.lang3.ObjectUtils;

/**
 * double表单字段类型
 *
 * @author henryyan
 */
public class DoubleFormType extends AbstractFormType {


    public String getName() {
        return "double";
    }


    public Object convertFormValueToModelValue(String propertyValue) {
        return new Double(propertyValue);
    }


    public String convertModelValueToFormValue(Object modelValue) {
        return ObjectUtils.toString(modelValue);
    }

}
