package cn.gx.modules.act.utils.form;

import org.activiti.engine.form.AbstractFormType;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 用户表单字段类型
 *
 * @author henryyan
 */
public class UsersFormType extends AbstractFormType {


    public String getName() {
        return "users";
    }


    public Object convertFormValueToModelValue(String propertyValue) {
        String[] split = StringUtils.split(propertyValue, ",");
        return Arrays.asList(split);
    }


    public String convertModelValueToFormValue(Object modelValue) {
        return ObjectUtils.toString(modelValue);
    }

}
