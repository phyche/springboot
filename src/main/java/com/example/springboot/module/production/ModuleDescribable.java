package com.example.springboot.module.production;



/**
 * @author Zhaomingqiang
 * @Description
 * @Date Created in 2018/4/16 21:31
 */
public interface ModuleDescribable extends Describable {

    String MODULE_FACTORY = "module_factory";

    /**
     * 模块标识
     *
     * @return
     */
    @Override
    default String getModuleId() {
        return MODULE_FACTORY;
    }
}
