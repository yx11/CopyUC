package com.yx.android.copyuc.bean.params;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yx on 2016/1/11 0011.
 */
public abstract class BaseParams {
    protected Map<String, String> params = new HashMap<>();
    public abstract Map<String, String> getMapParams();
}
