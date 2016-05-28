package cn.gx.modules.bug.exception;

/**
 * Created by always on 16/5/25.
 */
public class NoUserTaskToEndEventException extends Exception {

    public NoUserTaskToEndEventException() {
        super();
    }

    public NoUserTaskToEndEventException(String message) {
        super(message);
    }

    public NoUserTaskToEndEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
