package io.appium.uiautomator2.model.internal;


import io.appium.uiautomator2.common.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.model.By;
import io.appium.uiautomator2.utils.Logger;


public class NativeAndroidBySelector {
    public static final String SELECTOR_NATIVE_ID = "id";
    // TODO review this, not perfect, but main goal is to use default bindings
    public static final String SELECTOR_TEXT = "link text";
    public static final String SELECTOR_PARTIAL_TEXT = "partial link text";
    public static final String SELECTOR_XPATH = "xpath";
    public static final String SELECTOR_NAME = "name";
    public static final String SELECTOR_CLASS = "class name";
    public static final String SELECTOR_ANDROID_UIAUTOMATOR = "-android uiautomator";

    public By pickFrom(String method, String selector) throws UiAutomator2Exception {
        if (SELECTOR_NATIVE_ID.equals(method)) {
            return By.id(selector);
        } else if (SELECTOR_NAME.equals(method)) {
            return By.name(selector);
        } else if (SELECTOR_TEXT.equals(method)) {
            return By.linkText(selector);
        } else if (SELECTOR_PARTIAL_TEXT.equals(method)) {
            return By.partialLinkText(selector);
        } else if (SELECTOR_XPATH.equals(method)) {
            return By.xpath(selector);
        } else if (SELECTOR_CLASS.equals(method)) {
            return By.className(selector);
        } else if (SELECTOR_ANDROID_UIAUTOMATOR.equals(method)) {
            return By.androidUiAutomator(selector);
        } else {
            Logger.info("By type for method not found: " + method);
            throw new UiAutomator2Exception("method (by) not found: " + method);
        }
    }
}