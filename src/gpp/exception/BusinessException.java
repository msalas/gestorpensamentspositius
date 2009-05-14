package gpp.exception;


/**
 * Exception that indicates violation of some business rule. For exceptions that
 * should not be handled by client code use {@link SystemException} instead.
 * 
 */
public class BusinessException extends Exception {
    private static final long serialVersionUID = 1L;
    private Exception e;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Exception e) {
        super(message);
        this.e = e;
    }

    @Override
    public void printStackTrace(java.io.PrintWriter out) {
        out.println("S60SystemException: Exception:");
        out.println("Error message: " + getMessage());
        out.println("StackTrace(lowlevel exception):\n");
        if (e != null) {
            e.printStackTrace(out);
        } else {
            out.println("There was no initial Exception -> Business Exception");
        }
        out.println("StackTrace(highlevel exception):\n");
        super.printStackTrace(out);
    }
}
