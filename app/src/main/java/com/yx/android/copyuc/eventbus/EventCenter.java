package com.yx.android.copyuc.eventbus;

/**
 * Created by yx on 2016/1/13 0013.
 */
public class EventCenter<T> {
    private T data;
    private int eventCode;

    public EventCenter(int eventCode) {
        this(eventCode, (T)null);
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = -1;
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return this.eventCode;
    }

    public T getData() {
        return this.data;
    }
}
