package com.hjwei.mybatis.utils;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * @author hjw
 * @description
 * @date 2024/04/27 16:28
 */
public class LogUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);

    private static Multimap<String, String> preparingMap = LinkedListMultimap.create();
    private static Multimap<String, String> parametersMap = LinkedListMultimap.create();

    private static boolean isPreparing(String log) {
        if (log.contains(Constants.LOG_PREPARING)) {
            String logPrefix = StringUtils.substringBefore(log, Constants.LOG_PREPARING);
            String sql = StringUtils.substringAfter(log, Constants.LOG_PREPARING);
            String[] logPrefixs = StringUtils.splitByWholeSeparator(logPrefix, " ");
            String className = logPrefixs[logPrefixs.length-4];
            if (StringUtils.isNotBlank(className)) {
                preparingMap.put(className, sql);
                return true;
            }
        }
        return false;
    }
    private static boolean isParameters(String log) {
        if (log.contains(Constants.LOG_PARAMETERS)) {
            String logPrefix = StringUtils.substringBefore(log, Constants.LOG_PARAMETERS);
            String parameters = StringUtils.substringAfter(log, Constants.LOG_PARAMETERS);
            String[] logPrefixs = StringUtils.splitByWholeSeparator(logPrefix, " ");
            String className = logPrefixs[logPrefixs.length-4];
            if (StringUtils.isNotBlank(className)) {
                parametersMap.put(className, parameters);
                return true;
            }
        }
        return false;
    }


    /**
     * 解析控制台输出日志行，提取Mybatis Log 前缀与完整SQL
     * @param log 日志行
     * @return
     */
    public static String parseLogLine(String log) {
        String line = log.replaceAll("\t|\r|\n", "");
        StringBuilder sqlLog = new StringBuilder();
        if (isPreparing(log)) {
            return sqlLog.toString();
        } else if (isParameters(log)) {
            String logPrefix = StringUtils.substringBefore(line, Constants.LOG_PARAMETERS);
            String parameters = StringUtils.substringAfter(line, Constants.LOG_PARAMETERS);
            String[] logPrefixs = StringUtils.splitByWholeSeparator(logPrefix, " ");
            String className = logPrefixs[logPrefixs.length-4];
            if (StringUtils.isNotBlank(className)) {
                if (CollectionUtils.isNotEmpty(preparingMap.get(className))) {
                    String sql = preparingMap.get(className).stream().findFirst().get();
                    //参数匹配
                    if (StringUtils.isNotBlank(sql)) {
                        StringBuilder completeSql = parseSql(sql, parseParams(parameters));
                        LOGGER.info("complete  Sql :{}", completeSql);
                        sqlLog.append(logPrefix).append("\n").append(StringUtils.trim(completeSql.toString())).append(";").append("\n");
                        //移除已匹配上的SQL
                        preparingMap.remove(className, sql);
                    }
                } else {
                    parametersMap.put(className, parameters);
                }
            }
        }
        return sqlLog.toString();
    }

    /**
     * Log 中提取所有 SQL与参数 组装成完整SQL
     * @param log
     * @return 返回SQL前缀与完整SQL对应Map
     */
    public static String extractSql(String log) {
        Multimap<String, String> preparingMap = LinkedListMultimap.create();
        Multimap<String, String> parametersMap = LinkedListMultimap.create();

        String[] lines = StringUtils.splitByWholeSeparator(log, "\n");
        Map<String,String> sqlMap = new LinkedHashMap<>();
        StringBuilder sqlLog = new StringBuilder();

        for (String line : lines) {
            if (line.contains(Constants.LOG_PREPARING)) {
                String logPrefix = StringUtils.substringBefore(line, Constants.LOG_PREPARING);
                String sql = StringUtils.substringAfter(line, Constants.LOG_PREPARING);
                String[] logPrefixs = StringUtils.splitByWholeSeparator(logPrefix, " ");
                String className = logPrefixs[logPrefixs.length-4];
                if (StringUtils.isNotBlank(className)) {
                    preparingMap.put(className, sql);
                }
            } else if (line.contains(Constants.LOG_PARAMETERS)) {
                String logPrefix = StringUtils.substringBefore(line, Constants.LOG_PARAMETERS);
                String parameters = StringUtils.substringAfter(line, Constants.LOG_PARAMETERS);
                String[] logPrefixs = StringUtils.splitByWholeSeparator(logPrefix, " ");
                String className = logPrefixs[logPrefixs.length-4];
                if (StringUtils.isNotBlank(className)) {
                    if (CollectionUtils.isNotEmpty(preparingMap.get(className))) {
                        String sql = preparingMap.get(className).stream().findFirst().get();
                        //参数匹配
                        if (StringUtils.isNotBlank(sql)) {
                            StringBuilder completeSql = parseSql(sql, parseParams(parameters));
                            LOGGER.info("complete  Sql :{}", completeSql);
                            sqlMap.put(logPrefix, StringUtils.trim(completeSql.toString()));
                            //移除已匹配上的SQL
                            preparingMap.remove(className, sql);
                        }
                    } else {
                        parametersMap.put(className, parameters);
                    }
                }
            }
        }
        //前缀拼接SQL
        if (MapUtils.isNotEmpty(sqlMap)) {
            for (String key : sqlMap.keySet()) {
                sqlLog.append("---------------------------------------------------------------------------------------------------------------------\n");
//                sqlLog.append(key).append("\n").append(sqlMap.get(key)).append(";").append("\n");
                sqlLog.append(sqlMap.get(key)).append(";").append("\n");
            }
        }
        return sqlLog.toString();
    }

    public static StringBuilder parseSql(String sql, Queue<Map.Entry<String, String>> params) {
        final StringBuilder sb = new StringBuilder(sql);

        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) != Constants.PLACEHOLDER) {
                continue;
            }

            final Map.Entry<String, String> entry = params.poll();
            if (Objects.isNull(entry)) {
                continue;
            }


            sb.deleteCharAt(i);

            if (Constants.NEED_BRACKETS.contains(entry.getValue())) {
                sb.insert(i, String.format("'%s'", entry.getKey()));
            } else {
                sb.insert(i, entry.getKey());
            }
        }
        return sb;
    }

    /**
     * 参数提取
     * @param parameters
     */
    public static Queue<Map.Entry<String, String>> parseParams(String parameters) {
//        String[] params = StringUtils.splitByWholeSeparator(parameters, ",");
//        //Mybatis Params参数转换为Map
//        Map<String,String> paramMap = new TreeMap<>();
//        for(String param : params) {
//            param = StringUtils.trim(param);
//            String value = StringUtils.substringBefore(param, "(");
//            String type = StringUtils.substringBetween(param, "(", ")");
//            paramMap.put(value, type);
//        }
//        return paramMap;
        final String[] strings = StringUtils.splitByWholeSeparator(parameters, ", ");
        final Queue<Map.Entry<String, String>> queue = new ArrayDeque<>(strings.length);

        for (String s : strings) {
            String value = StringUtils.substringBeforeLast(s, "(");
            String type = StringUtils.substringBetween(s, "(", ")");
            if (StringUtils.isEmpty(type)) {
                queue.offer(new AbstractMap.SimpleEntry<>(value, null));
            } else {
                queue.offer(new AbstractMap.SimpleEntry<>(value, type));
            }
        }

        return queue;
    }




}
