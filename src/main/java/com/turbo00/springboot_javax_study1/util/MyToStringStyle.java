package com.turbo00.springboot_javax_study1.util;

import org.apache.commons.lang.builder.ToStringStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * used for ReflectionToStringBuilder
 */
public class MyToStringStyle extends ToStringStyle {
    public MyToStringStyle() {
        this.setUseShortClassName(true);
        this.setUseIdentityHashCode(false);
    }


    protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
        if (value instanceof Date) {
            value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
        }
        buffer.append(value);
    }
}
