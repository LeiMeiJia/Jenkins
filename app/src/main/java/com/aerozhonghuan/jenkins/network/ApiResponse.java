package com.aerozhonghuan.jenkins.network;

/**
 * Created by Administrator on 2018/2/1.
 */

public class ApiResponse<T> {

    private int resultCode;
    private String message;
    private T data;

    public ApiResponse(int resultCode, String message) {
        this.message = message;
        this.resultCode = resultCode;
    }

    public boolean isSuccess() {
        return 200 == this.resultCode;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ApiResponse{");
        sb.append("data=").append(this.data);
        sb.append(", resultCode=").append(this.resultCode);
        sb.append(", message=\'").append(this.message).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof ApiResponse)) {
            return false;
        } else {
            ApiResponse that = (ApiResponse) o;
            return this.getResultCode() != that.getResultCode() ? false : (this.getMessage() != null ? this.getMessage().equals(that.getMessage()) : that.getMessage() == null);
        }
    }

    public int hashCode() {
        int result = this.getResultCode();
        result = 31 * result + (this.getMessage() != null ? this.getMessage().hashCode() : 0);
        return result;
    }
}
