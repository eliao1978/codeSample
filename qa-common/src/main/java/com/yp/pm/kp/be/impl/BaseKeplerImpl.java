package com.yp.pm.kp.be.impl;

import com.yp.util.DateUtil;
import com.yp.util.LogUtil;
import org.apache.log4j.Logger;

import java.sql.Timestamp;

public class BaseKeplerImpl {
    protected Timestamp now = DateUtil.getTime();
    protected Logger logger = new LogUtil().getLogger();
}
