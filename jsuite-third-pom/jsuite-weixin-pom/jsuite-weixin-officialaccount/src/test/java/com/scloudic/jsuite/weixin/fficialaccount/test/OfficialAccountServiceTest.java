package com.scloudic.jsuite.weixin.fficialaccount.test;

import com.scloudic.jsuite.weixin.fficialaccount.model.OfficialAccountUser;
import com.scloudic.jsuite.weixin.fficialaccount.model.OfficialAccountUserDetail;
import com.scloudic.jsuite.weixin.fficialaccount.service.OfficialAccountService;
import com.scloudic.jsuite.weixin.fficialaccount.service.impl.OfficialAccountServiceImpl;
import org.junit.Test;

public class OfficialAccountServiceTest {
    private static String token = "";

    @Test
    public void users() {
        OfficialAccountService officialAccountService = new OfficialAccountServiceImpl();
        OfficialAccountUser officialAccountUser = officialAccountService.getUsers(token, "");
        System.out.printf("总数：" + officialAccountUser.getData().getOpenid());
    }

    @Test
    public void userInfos() {
        OfficialAccountService officialAccountService = new OfficialAccountServiceImpl();
        OfficialAccountUserDetail detail = officialAccountService.getUserInfo(token, "oora5573dZcar-hDy7QfAdHP73mM", null);
        System.out.printf(detail.getHeadimgurl());
    }
}
