package com.scloudic.jsuite.weixin.core.exception;

import com.scloudic.rabbitframework.core.exceptions.RabbitFrameworkException;
import com.scloudic.rabbitframework.core.utils.StatusCode;

public class WeiXinException extends RabbitFrameworkException {
    private StatusCode status = StatusCode.SC_CACHE_ERROR;

    public WeiXinException() {
        super();
    }

    public WeiXinException(String message, Throwable cause) {
        super(message, cause);
        this.description = message;
    }

    public WeiXinException(String message) {
        super(message);
        this.description = message;
    }

    public WeiXinException(Throwable cause) {
        super(cause);
    }

    @Override
    public StatusCode getStatus() {
        return status;
    }
}
