package com.example.router_compiler.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author: raotong
 * @data：2020/12/21 17:31
 * @Description :
 */
public class Utils {
    public static boolean isEmpty(CharSequence cs) {
        if (cs == null || cs.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Collection<?> coll) {
        if (coll == null || coll.isEmpty()) {
            return true;
        }
        return false;

    }

    public static boolean isEmpty(final Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;

    }

}
