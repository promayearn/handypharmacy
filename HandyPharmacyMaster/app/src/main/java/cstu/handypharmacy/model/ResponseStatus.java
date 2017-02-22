package cstu.handypharmacy.model;

public class ResponseStatus {

    public final boolean success;
    public final String message;
    public final int status;

    public ResponseStatus(boolean success, String message, int status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    public ResponseStatus(boolean success, String message) {
        this.success = success;
        this.message = message;
        status = 0;
    }
}