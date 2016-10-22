package biz.ostw.rod.connecting;

/**
 * @author mathter
 */
public class ConnectingException extends RuntimeException {
	private static final long serialVersionUID = -1631456000119718681L;

	public ConnectingException() {
		super();
	}

	public ConnectingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConnectingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectingException(String message) {
		super(message);
	}

	public ConnectingException(Throwable cause) {
		super(cause);
	}
}
