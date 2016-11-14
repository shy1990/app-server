package com.wangge.app.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author barton
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class JsonResponse<R> {

    public enum Status {

        SUCCESS("success"), ERROR("error");

        private final String value;

        private Status(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return this.value;
        }
    }

    // Default value is success
    private Status status = Status.SUCCESS;

    // Result data
    private R result;

    // Success message
    private String successMsg;

    private String errorCode;
    private String total;

    // Error message
    private String errorMsg;

    public JsonResponse() {
        super();
    }

    public JsonResponse(R result) {
        super();
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.status = Status.ERROR;
        this.errorMsg = errorMsg;
    }

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}
