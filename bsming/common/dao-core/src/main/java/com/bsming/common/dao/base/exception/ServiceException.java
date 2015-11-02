package com.bsming.common.dao.base.exception;


/**
 * 服务异常
 * @author huwei
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = -319844289653018942L;
    
    /**
     * 默认为-1，表示未知错误.
     */
    private int errorCode = -1;
    private String message;
    private Object[] params;

    /**
     * The throwable that caused this throwable to get thrown, or null if this
     * throwable was not caused by another throwable, or if the causative
     * throwable is unknown.  If this field is equal to this throwable itself,
     * it indicates that the cause of this throwable has not yet been
     * initialized.
     *
     * @serial
     * @since 1.4
     */
    private Throwable cause = this;
    
    /**
     * 返回错误编码, 默认为-1表示未知错误.
     * @return 错误编码
     */
    public int getErrorCode() {
        return this.errorCode;
    }

    /**
     * 获取参数.
     * @return
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     * @param   errorCode   the error code.
     */
    public ServiceException(int errorCode) {
        this.errorCode = errorCode;
        this.message = new StringBuffer("error code is:").append(errorCode).toString();
    }
    
    /**
     * 服务异常构造函数.
     * @param errorCode 异常编码
     * @param args 异常信息中的参数
     */
    public ServiceException(int errorCode, Object ... params) {
        this.errorCode = errorCode;
        this.params = params;
        this.message = new StringBuffer("error code is:").append(errorCode).toString();
    }

    /**
     * 服务异常构造函数.
     * @param errorCode 异常编码
     * @param cause 异常
     * @param args 异常信息中的参数
     */
    public ServiceException(int errorCode, Throwable cause, Object ... params) {
        fillInStackTrace();
        this.errorCode = errorCode;
        this.params = params;
        this.message = new StringBuffer("error code is:").append(errorCode).toString();
        this.cause = cause;
    }

    /**
     * 获取异常消息.
     * @return  异常的信息.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 服务异常构造函数.
     * @param message 异常的信息.
     */
    public ServiceException(String message) {
        this.message = message;
    }

    /**
     * 服务异常构造函数.
     * @param cause 异常
     */
    public ServiceException(Throwable cause) {
        fillInStackTrace();
        this.message = (cause==null ? null : cause.toString());
        this.cause = cause;
    }
    
    /**
     * 服务异常构造函数.
     * @param message 异常的信息.
     * @param cause 异常
     */
    public ServiceException(String message, Throwable cause) {
        fillInStackTrace();
        this.message = message;
        this.cause = cause;
    }
    
    /**
     * Returns the cause of this throwable or <code>null</code> if the
     * cause is nonexistent or unknown.  (The cause is the throwable that
     * caused this throwable to get thrown.)
     *
     * <p>This implementation returns the cause that was supplied via one of
     * the constructors requiring a <tt>Throwable</tt>, or that was set after
     * creation with the {@link #initCause(Throwable)} method.  While it is
     * typically unnecessary to override this method, a subclass can override
     * it to return a cause set by some other means.  This is appropriate for
     * a "legacy chained throwable" that predates the addition of chained
     * exceptions to <tt>Throwable</tt>.  Note that it is <i>not</i>
     * necessary to override any of the <tt>PrintStackTrace</tt> methods,
     * all of which invoke the <tt>getCause</tt> method to determine the
     * cause of a throwable.
     *
     * @return  the cause of this throwable or <code>null</code> if the
     *          cause is nonexistent or unknown.
     * @since 1.4
     */
    public Throwable getCause() {
        return (cause==this ? null : cause);
    }

}
