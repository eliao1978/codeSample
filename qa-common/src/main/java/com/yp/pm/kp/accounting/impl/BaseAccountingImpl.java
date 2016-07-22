package com.yp.pm.kp.accounting.impl;

import com.yp.util.DBUtil;
import com.yp.util.DateUtil;
import com.yp.util.LogUtil;
import org.apache.log4j.Logger;

import java.sql.Timestamp;

public class BaseAccountingImpl {
    protected Timestamp now = DateUtil.getTime();
    protected Logger logger = new LogUtil().getLogger();
    protected DBUtil db = new DBUtil();
}
