package cn.gx.modules.bug.exception;

/**
 * Created by always on 16/5/24.
 */
public class NoSuchUserTaskRuntimeException extends RuntimeException {


    public NoSuchUserTaskRuntimeException() {
        super();
    }

    public NoSuchUserTaskRuntimeException(String message) {
        super(String.format("此「%s」用户任务不存在,请确认流程图是否正确.",message));
    }

    public NoSuchUserTaskRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
