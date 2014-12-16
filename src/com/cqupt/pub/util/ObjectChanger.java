package com.cqupt.pub.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import com.cqupt.pub.exception.*;


/**
 * 对象互转,例如将字符串转化为num(BigDecimal)等
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author df
 * @version 1.0
 */
public class ObjectChanger {
	private static ObjectChanger instance = new ObjectChanger();
    private ObjectCensor objCensor = ObjectCensor.getInstance();
    private DateFormat dateFormat = DateFormat.getInstance();
    
    private ObjectChanger() {
    }

    public static ObjectChanger getInstance() {
        return instance;
    }
    
    /**
     * 将字符串转化为数字
     * @param str String
     * @return BigDecimal
     */
    public BigDecimal strToNum(String str) {
        BigDecimal num = null;

        if(!this.objCensor.checkObjectIsNull(str)) {
            num = new BigDecimal(str);
        }

        return num;
    }
    
    /**
     * 将Timestamp转化为Date
     * @param time Timestamp
     * @param format String
     * @return Date
     * @throws DataStormException
     */
    public Date timestampToDate(Timestamp time, String format) throws
    CquptException {
        Date date = new Date(time.getTime());
        return this.dateFormat.parseDate(format, this.dateFormat.format(format,
                date));
    }

    /**
     * 将Timestamp转化为Date
     * @param time Timestamp
     * @return Date
     * @throws DataStormException
     */

    public Date timestampToDate(Timestamp time) throws CquptException {
        return this.timestampToDate(time, "yyyy-MM-dd");
    }

    /**
     * 将Timestamp转化为Date（例如Date为字符串“yyyy-MM-dd”）
     * @param time Timestamp
     * @param format String
     * @return String
     * @throws DataStormException
     */
    public String timestampToDateStr(Timestamp time, String format) throws
    CquptException {
        Date date = new Date(time.getTime());

        return this.dateFormat.format(format, date);

    }

    /**
     * 将Timestamp转化为Date（例如Date为字符串“yyyy-MM-dd”）
     * @param time Timestamp
     * @return String
     * @throws DataStormException
     */
    public String timestampToDateStr(Timestamp time) throws
    CquptException {
        return this.timestampToDateStr(time, "yyyy-MM-dd");
    }

    /**
     * 将date转化为Timestamp
     * @param date Date
     * @param format String
     * @return Timestamp
     * @throws DataStormException
     */
    public Timestamp dateToTimestamp(Date date, String format) throws
    CquptException {
        Date dateNew = this.dateFormat.parseDate(format, this.dateFormat.format(format, date));

        Timestamp time = new Timestamp(dateNew.getTime());

        return time;
    }

    /**
     * 将date转化为Timestamp
     * @param date Date
     * @return Timestamp
     * @throws DataStormException
     */
    public Timestamp dateToTimestamp(Date date) throws
    CquptException {
        return this.dateToTimestamp(date, "yyyy-MM-dd");
    }

    /**
     * 将date转化为str
     * @param date Date
     * @param format String
     * @return String
     * @throws DataStormException
     */
    public String dateToStr(Date date, String format) throws CquptException {
        return this.dateFormat.format(format, date);
    }

    /**
     * 将date转化为str
     * @param date Date
     * @return String
     * @throws DataStormException
     */

    public String dateToStr(Date date) throws CquptException {
        return this.dateToStr(date, "yyyy-MM-dd");
    }
    
    /**
     * 将str转化为timeStamp
     * @param str String
     * @param format String
     * @return Timestamp
     * @throws DataStormException
     */
    public Timestamp strToTimestamp(String str,String format) throws CquptException {
        if(this.objCensor.checkObjectIsNull(str)) {
        	throw new CquptException("str不能为空或\"\"", CquptExceptionCode.SYSEXCODE);
        }
        if(this.objCensor.checkObjectIsNull(format)) {
        	format = "yyyy-MM-dd";
        }
        Date date = dateFormat.parseDate(format, str);
        return new Timestamp(date.getTime());
    }
    
    /**
     * 将str转化为timeStamp
     * @param str String
     * @return Timestamp
     * @throws DataStormException
     */
    public Timestamp strToTimestamp(String str) throws CquptException {
        String format = "yyyy-MM-dd";
        return this.strToTimestamp(str, format);
    }
}
