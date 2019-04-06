package io.github.ljwlgl.warpper;

/**
 * @author zqgan
 * @since 2019/3/29
 **/

public class ApiResponseWrapper<T> {

    private int resCode;
    private String resMsg;
    private String referTrace;
    private String trace;
    private T data;

    public static <T> ApiResponseWrapper<T> returnSuccess(T data) {
        ApiResponseWrapper<T> response = new ApiResponseWrapper<>();
        response.setData(data);
        return response;
    }

    public static ApiResponseWrapper returnFail(int resCode, String resMsg) {
        ApiResponseWrapper response = new ApiResponseWrapper();
        response.setResCode(resCode);
        response.setResMsg(resMsg);
        return response;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getReferTrace() {
        return referTrace;
    }

    public void setReferTrace(String referTrace) {
        this.referTrace = referTrace;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
