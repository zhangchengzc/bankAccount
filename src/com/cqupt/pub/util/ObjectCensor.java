package com.cqupt.pub.util;

import java.util.*;

/**
 * 此类为object检查类，检查object是否为空，此类所用模式为无上限多例模式
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: cqupt</p>
 *
 * @author sl
 * @version 1.0
 */

public class ObjectCensor {
    private static ObjectCensor instance = new ObjectCensor();

    private ObjectCensor() {
    }

    public static ObjectCensor getInstance() {
        return instance;
    }

    /**
     * 检查object是否为null
     * @param Object obj - 需要检查的0bject
     * @return boolean -true(0bject为空) -false(object不为空)
     */
    public boolean checkObjectIsNull(Object obj) {
        if (obj == null) {
            return true;
        } else {
            if (obj instanceof String) {
                return this.checkStringIsNull((String) obj);
            } else if (obj instanceof Map) {
                return this.checkMapIsNull((Map) obj);
            } else if (obj instanceof Collection) {
                return this.checkCollectionIsNull((Collection) obj);
            } else if (obj instanceof Object[]) {
                return this.checkArrayIsNull((Object[]) obj);
            } else {
                return false;
            }
        }
    }

    /**
     * 检查字符串是否为""
     * @param String str - 需要检查的字符串
     * @return boolean -true(字符串为"") -false(字符串不为"")
     */
    private boolean checkStringIsNull(String str) {
        if (str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查Map的size是否为0
     * @param Map map - Map
     * @return boolean -true(Map的size为0) -false(Map的size不为0)
     */
    private boolean checkMapIsNull(Map map) {
        return map.isEmpty();
    }

    /**
     * 检查Collection的size是否为0
     * @param Collection collection - 需要检查的Collection
     * @return boolean -true(Collection的size为0) -false(Collection的size不为0)
     */
    private boolean checkCollectionIsNull(Collection collection) {
        return collection.isEmpty();
    }

    /**
     * 检查数组的length是否为0，检查数组里的数据是否都为null
     * @param Object[] obj - 需要检查的0bject
     * @return boolean -true(0bject[]为空) -false(object[]不为空)
     */
    private boolean checkArrayIsNull(Object[] obj) {
        if (obj.length == 0) {
            return true;
        } else {
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    return false;
                }
            }

            return true;
        }
    }
}
