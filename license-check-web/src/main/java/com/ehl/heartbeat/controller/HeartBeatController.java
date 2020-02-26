package com.ehl.heartbeat.controller;

import com.ehl.heartbeat.entity.HeartBeatCheckResp;
import com.ehl.heartbeat.service.HeartBeatService;
import com.ehl.heartbeat.tool.ServerInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author created by guanchen on 2020/2/18 16:16
 */
@RestController
@Slf4j
@CrossOrigin
public class HeartBeatController {

    @Autowired
    private HeartBeatService heartBeatService;

    @GetMapping("heartbeat")
    public HeartBeatCheckResp heartbeat() {
        //TODO: 添加缓存
        HeartBeatCheckResp resp = new HeartBeatCheckResp();
        resp.setCode(0);
        resp.setServerInfo(ServerInfoUtil.getServerInfo());
        try {
            heartBeatService.doInstall();
            resp.setExpireFlag(false);
            resp.setExpireDays(heartBeatService.getAfterDays());
        } catch (Exception e) {
            log.error("证书认证失败：" + e.getMessage());
            resp.setExpireFlag(true);
            return resp;
        }
        return resp;
    }
}
