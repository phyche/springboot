/*
package com.example.springboot.module.production;

public class I18nContextHolder {
    private static final ThreadLocal<Locale> LOCALE_THREAD_LOCAL = new ThreadLocal();
    private static I18nContext i18nContext;

    public I18nContextHolder() {
    }

    public static String getMessage(String name, Object... params) {
        if (i18nContext == null) {
            throw new I18nException(ExceptionDescEnum.I18nContextBeanNotLoad, "please use @DependsOn(value = DependsOnBean.DEPENDS_ON_I18N_BEAN)");
        } else {
            return i18nContext.getMessage(name, params, getLocale());
        }
    }

    private static Locale getLocale() {
        return (Locale)LOCALE_THREAD_LOCAL.get();
    }

    public static void setLocale(Locale locale) {
        LOCALE_THREAD_LOCAL.set(locale);
    }

    public static void clearThreadLocal() {
        LOCALE_THREAD_LOCAL.remove();
    }

    public static void setI18nContext(I18nContext i18nContext) {
        i18nContext = i18nContext;
    }
}
*/
