package com.hjwei.mybatis.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hjw
 * @description
 * @date 2024/05/02 20:33
 */
public class Constants {

    public static final String LOG_PREPARING = "Preparing: ";
    public static final String LOG_PARAMETERS = "Parameters: ";
    public static final char PLACEHOLDER = '?';

    public static final Set<String> NEED_BRACKETS;

    static {
        Set<String> types = new HashSet<>(8);
        types.add("String");
        types.add("Date");
        types.add("Time");
        types.add("LocalDate");
        types.add("LocalTime");
        types.add("LocalDateTime");
        types.add("BigDecimal");
        types.add("Timestamp");
        NEED_BRACKETS = Collections.unmodifiableSet(types);
    }



}
