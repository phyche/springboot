package com.example.springboot.module.production;

public interface Describable {
    String PLEASE_SETTING = "PLEASE_SETTING_MODULE_ID";

    Integer getId();

    String getDescription();

    default String getModuleId() {
        return PLEASE_SETTING;
    }
}
