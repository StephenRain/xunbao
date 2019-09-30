package com.inspiration.xunbao.exception;

import lombok.Getter;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
public enum ExceptionMessage {

    DIGESTCONFLICT("消息摘要冲突");

    @Getter
    private String desc;

    ExceptionMessage(String desc) {
        this.desc = desc;
    }


}
