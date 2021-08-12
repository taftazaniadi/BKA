
package com.buka.amanah.model;

import com.google.gson.annotations.Expose;

public class ResponseFailed {

    @Expose
    private String error;
    @Expose
    private String message;
    @Expose
    private String path;
    @Expose
    private Long status;
    @Expose
    private String timestamp;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
