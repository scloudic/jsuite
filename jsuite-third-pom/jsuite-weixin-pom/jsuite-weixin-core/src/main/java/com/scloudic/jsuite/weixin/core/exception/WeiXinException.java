package com.scloudic.jsuite.weixin.core.exception;

import com.scloudic.rabbitframework.core.exceptions.RabbitFrameworkException;
import com.scloudic.rabbitframework.core.utils.StatusCode;

public class WeiXinException extends RabbitFrameworkException {
    private StatusCode status = StatusCode.FAIL;

    public WeiXinException() {
        super();
    }

    public WeiXinException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeiXinException(String message) {
        super(message);
    }

    public WeiXinException(Throwable cause) {
        super(cause);
    }

    @Override
    public StatusCode getStatus() {
        return status;
    }
}
