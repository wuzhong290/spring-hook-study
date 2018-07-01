package com.demo.rest.model;

import java.io.Serializable;

public class ReturnPage implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -8039418378198038801L;

    private Integer code;

    private String result;

    private PageData pageData;

    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PageData getPageData() {
        return pageData;
    }

    public void setPageData(PageData pageData) {
        this.pageData = pageData;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
