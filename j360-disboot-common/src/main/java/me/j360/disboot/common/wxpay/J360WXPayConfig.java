package me.j360.disboot.common.wxpay;

import com.vip.vjtools.vjkit.base.ExceptionUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author: min_xu
 * @date: 2018/1/18 下午2:30
 * 说明：
 */

@Slf4j
public class J360WXPayConfig extends WXPayConfig {

    @Getter
    private String appid;
    @Getter
    private String mchid;
    @Getter
    private String key;
    @Getter
    private String certPath;

    public J360WXPayConfig(String appid, String mchid, String key, String certPath) {
        this.appid = appid;
        this.mchid = mchid;
        this.key = key;
        this.certPath = certPath;
    }


    private String wxPayDomain = "api.mch.weixin.qq.com";

    @Override
    String getAppID() {
        return appid;
    }

    @Override
    String getMchID() {
        return mchid;
    }

    @Override
    String getKey() {
        return key;
    }

    @Override
    InputStream getCertStream() {
        File cert = new File(certPath);
        try {
            return new FileInputStream(cert);
        } catch (FileNotFoundException e) {
            log.error("找不到本地的cert地址: {}", certPath);
            throw ExceptionUtil.unchecked(e);
        }
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
                log.info("上报信息: {}, {}", domain, elapsedTimeMillis, ex);
            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(wxPayDomain, true);
            }
        };
    }
}
