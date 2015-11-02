package com.qding.community.common.weixin.util;

import com.bsming.common.util.http.HttpClientUtil;

import java.util.concurrent.*;

public class WeiXinCallable implements Callable<String> {
    private String url;
    private String content;

    public WeiXinCallable(String url, String content) {
        this.url = url;
        this.content = content;
    }

    public String call() throws Exception {
        return HttpClientUtil.sendPostRequestByJava(url, content);
    }
}
