package com.cqupt.pub.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.exception.CquptExceptionCode;


public class DateFormat {
    private static DateFormat instance = new DateFormat();

    private final static String FORMAT = "yyyy-MM-dd hh:mm:ss";

    private SimpleDateFormat simpleDateFormat;

    private ObjectCensor objCensor = ObjectCensor.getInstance();

    private Logger logger = Logger.getLogger(this.getClass());

    private DateFormat() {
    }

    public static DateFormat getInstance() {
        return instance;
    }

    /**
     * 将字符串型时间数据转化为date类
     * @param String format -时间的字符串格式, String date -要转换的时间字符串
     * @return Data
     * @throws CquptException
     **/
    public Date parseDate(String format, String date) throws CquptException {
        logger.debug("parseDate(String format, String date) begin");

        if (objCensor.checkObjectIsNull(format)) {
            throw new CquptException("入参format == null",
            		CquptExceptionCode.SYSEXCODE);
        }

        if (objCensor.checkObjectIsNull(date)) {
            throw new CquptException("入参date == null", CquptExceptionCode.SYSEXCODE);
        }

        logger.debug("format: " + format);
        logger.debug("date: " + date);

        Date d = null;
        try {
            simpleDateFormat = this.getSimpleDateFormat(format);

            d = simpleDateFormat.parse(date);
        } catch (Exception e) {
            throw new CquptException(e);
        }

        logger.debug("parseDate(String format, String date) end");
        return d;
    }

    /**
     * 将date类转换为相应的字符串型时间数据
     * @param String format -时间的字符串格式，Date date -要转换的时间类
     * @return String
     * @throws CquptException
     **/
    public String format(String format, Date date) throws CquptException {
        logger.debug("format(String format, Date date) begin");

        if (objCensor.checkObjectIsNull(format)) {
            throw new CquptException("入参format == null",
                                   CquptExceptionCode.SYSEXCODE);
        }

        if (objCensor.checkObjectIsNull(date)) {
            throw new CquptException("入参date == null", CquptExceptionCode.SYSEXCODE);
        }

        logger.debug("format: " + format);
        logger.debug("date: " + date);

        String str = null;

        simpleDateFormat = this.getSimpleDateFormat(format);

        str = simpleDateFormat.format(date);

        logger.debug("format(String format, Date date) end");
        return str;
    }

    /**
     * 根据format取得SimpleDateFormat类
     *
     * @param String
     *            format -时间的字符串格式
     * @return SimpleDateFormat
     * @throws CquptException
     * @throws CquptException
     * @throws BssException
     */
    private SimpleDateFormat getSimpleDateFormat(String format) throws
    CquptException {
        logger.debug("getSimpleDateFormat(String format) begin");
        if (objCensor.checkObjectIsNull(format)) {
            throw new CquptException("入参format == null",
            		CquptExceptionCode.SYSEXCODE);
        }
        logger.debug("format: " + format);
        SimpleDateFormat sf = null;
        if (format == null || format.equals("")) {
            sf = new SimpleDateFormat(FORMAT);
        } else {
            sf = new SimpleDateFormat(format);
        }

        logger.debug("getSimpleDateFormat(String format) end");
        return sf;
    }
}
