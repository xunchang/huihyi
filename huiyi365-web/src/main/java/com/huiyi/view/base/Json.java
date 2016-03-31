package com.huiyi.view.base;

public class Json implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5356118523786220234L;

    private boolean success = false;//成功与否，false失败，true成功

    private String msg = "";

    private Object obj = null;

    public boolean isSuccess() {
            return success;
    }

    public void setSuccess(boolean success) {
            this.success = success;
    }

    public String getMsg() {
            return msg;
    }

    public void setMsg(String msg) {
            this.msg = msg;
    }

    public Object getObj() {
            return obj;
    }

    public void setObj(Object obj) {
            this.obj = obj;
    }

}