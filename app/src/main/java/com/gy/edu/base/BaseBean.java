package com.gy.edu.base;

import java.io.Serializable;

/**
 * Created by 高岳 on 2016/8/1.
 * Describe:接口返回基类
 */
public class BaseBean implements Serializable{
    private static final long serialVersionUID = 1288672488853863594L;
    public boolean success;
    public String errMsg;
    public String errCode;
    public String data;
}
