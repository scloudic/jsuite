package com.scloudic.jsuite.weixin.fficialaccount.test;

import com.scloudic.jsuite.weixin.mp.model.WeiXinMpUser;
import com.scloudic.jsuite.weixin.mp.model.WeiXinMpUserDetail;
import com.scloudic.jsuite.weixin.mp.service.WeiXinMpService;
import com.scloudic.jsuite.weixin.mp.service.impl.WeiXinMpServiceImpl;
import org.junit.Test;

public class OfficialAccountServiceTest {
    private static String token = "";

    @Test
    public void users() {
        WeiXinMpService officialAccountService = new WeiXinMpServiceImpl();
        WeiXinMpUser officialAccountUser = officialAccountService.getUsers(token, "");
        System.out.printf("总数：" + officialAccountUser.getData().getOpenid());
    }

    @Test
    public void userInfos() {
        WeiXinMpService officialAccountService = new WeiXinMpServiceImpl();
        WeiXinMpUserDetail detail = officialAccountService.getUserInfo(token, "oora5573dZcar-hDy7QfAdHP73mM", null);
        System.out.printf(detail.getHeadimgurl());
    }
}
