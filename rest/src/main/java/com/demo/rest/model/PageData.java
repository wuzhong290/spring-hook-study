package com.demo.rest.model;

import java.io.Serializable;
import java.util.List;

public class PageData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -151069343420216417L;

    /**
     * 页面基础信息
     */
    private FormMap<String, Object> pageInfo;

    /**
     * 页面菜单
     */
    private List<FormMap<String, Object>> menus;


    /**
     * 页面推荐
     */
    private FormMap<String, Object> recommends;

    /**
     * 页面公告
     */
    private FormMap<String, Object> notices;


    private List<FormMap<String, Object>> buttons;

    private FormMap<String, Object> backInfo;

    /**
     * 其他信息
     */
    private FormMap<String, Object> otherInfo = new FormMap<String, Object>();


    public FormMap<String, Object> getPageInfo() {
        return pageInfo;
    }


    public void setPageInfo(FormMap<String, Object> pageInfo) {
        this.pageInfo = pageInfo;
    }


    public List<FormMap<String, Object>> getMenus() {
        return menus;
    }


    public void setMenus(List<FormMap<String, Object>> menus) {
        this.menus = menus;
    }


    public FormMap<String, Object> getRecommends() {
        return recommends;
    }


    public void setRecommends(FormMap<String, Object> recommends) {
        this.recommends = recommends;
    }


    public FormMap<String, Object> getNotices() {
        return notices;
    }


    public void setNotices(FormMap<String, Object> notices) {
        this.notices = notices;
    }


    public FormMap<String, Object> getOtherInfo() {
        return otherInfo;
    }


    public void setOtherInfo(FormMap<String, Object> otherInfo) {
        this.otherInfo = otherInfo;
    }


    public List<FormMap<String, Object>> getButtons() {
        return buttons;
    }


    public void setButtons(List<FormMap<String, Object>> buttons) {
        this.buttons = buttons;
    }


    public FormMap<String, Object> getBackInfo() {
        return backInfo;
    }


    public void setBackInfo(FormMap<String, Object> backInfo) {
        this.backInfo = backInfo;
    }


    @Override
    public String toString() {
        return "PageData [pageInfo=" + pageInfo + ", menus=" + menus
                + ", recommends=" + recommends + ", notices=" + notices
                + ", buttons=" + buttons + ", backInfo=" + backInfo
                + ", otherInfo=" + otherInfo + "]";
    }


}
