package org.springrain.system.exceptions;

/**
 * 
 * @description TODO <br/>
 * @date 2014年9月28日 下午4:01:27 <br/>
 * @author wangbo
 */
public class BaseRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -6467064949852030673L;

	public BaseRuntimeException() {
	}

	public BaseRuntimeException(String message) {
		super(message);
	}

	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseRuntimeException(Throwable cause) {
		super(cause);
	}
}
