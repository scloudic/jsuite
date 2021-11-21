package com.scloudic.jsuite.weixin.core.test;
import com.scloudic.jsuite.weixin.core.model.AccessToken;
import org.junit.Test;

public class ServiceTest {
    @Test
    public void accessToken() {
        AccessToken accessToken = new TestService()
                .getAccessToken("", "", null);
        System.out.printf(accessToken.getAccess_token());
    }
}