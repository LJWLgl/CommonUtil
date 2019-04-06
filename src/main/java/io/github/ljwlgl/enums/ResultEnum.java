package io.github.ljwlgl.enums;

public enum ResultEnum {

    SUCCESS(0, "成功"),
    ERROR(201, "其它错误"),
    LACK_PARAM(202, "缺少必要参数"),
    INVALID_PRAM(203, "参数无效"),
    NOT_LOGGED_IN(205, "未登录"),
    INNER_ERROR(306, "内部系统异常");

    private int code;
    private String desc;

    ResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ResultEnum getByCode(int code) {
        ResultEnum result = null;
        for (ResultEnum e : values()) {
            if (e.getCode()==code) {
                result = e;
                break;
            }
        }
        return result;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
