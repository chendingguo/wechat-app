package com.airsupply.monitor.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

@Service
@PropertySource(value = "classpath:/url.properties", ignoreResourceNotFound = true)
public class HttpMonitorService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplate restTemplate;
    @Value("${web.url}")
    String webUrl;

    public Object getUrl() {
        String wxAuthUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxbbee0dfafc6ec3b1&secret=d9cb0edb969d1b796f8e3ba669968a85";
        try {
            return restTemplate.getForObject(wxAuthUrl, String.class);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return "error";
        }

    }
}
