package com.example.springboot.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by shaolin on 16-8-1.
 */
@Slf4j
public abstract class JsonUtil {
    private static final ObjectMapper om = new ObjectMapper();
    private static final ObjectMapper prettyMapper = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    static {
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        om.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(String data, TypeReference<T> typeRef) throws IOException {
        return (T) om.readValue(data, typeRef);
    }

    public static String writeValue(Object value) throws IOException {
        return om.writeValueAsString(value);
    }

    public static String writeValueQuite(Object value) {
        String result = StringUtils.EMPTY;
        try {
            result = om.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error("write value quite error with [{}]", value, e);
        }
        return result;
    }

    public static <T> T parse(String data, Class<T> clazz) throws IOException {
        return om.readValue(data, clazz);
    }

    public static ObjectMapper getObjectMapper() {
        return om;
    }

    public static String pretty(Object obj) {
        try {
            return prettyMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("prettyMapper parse error", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 将 JsonArray 转换为对应的List
     */
    public static <T> List<T> parseList(String text, Class<T> clazz) throws IOException {
        JavaType type = om.getTypeFactory().constructParametricType(List.class, clazz);
        return om.readValue(text, type);
    }

    /**
     * 转为Map
     */
    public static <K, V> Map<K, V> parseMap(String text, Class<K> keyClass, Class<V> valueClass) throws IOException {
        MapType mapType = om.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        return om.readValue(text, mapType);
    }

    /**
     * 根据键值得到object对象
     */
    public static Object getObjectByKey(Map map, String key) {
        if (null == map || isBlank(key)) {
            return null;
        }
        try {
            int index = key.indexOf(".");
            if (index > 0 && index < key.length() - 1) {
                String prefix = key.substring(0, index);
                String surfix = key.substring(index + 1, key.length());
                return getObjectByKey((Map) map.get(prefix), surfix);
            } else {
                return map.get(key);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据键值得到object对象
     */
    public static Object getObjectByKey(String jsonString, String key) throws IOException {
        if (!isBlank(jsonString)) {
            return getObjectByKey(parse(jsonString, Map.class), key);
        } else {
            return null;
        }
    }

    /**
     * 根据键值得到object对象列表
     */
    public static List getObjectListByKey(String jsonString, String key) throws IOException {
        Object object = getObjectByKey(jsonString, key);
        if (object instanceof List) {
            return (List) object;
        } else if (null != object) {
            List list = new ArrayList();
            list.add(object);
            return list;
        }
        return null;
    }

    public static JsonNode readTree(String content) throws IOException {
        return om.readTree(content);
    }

    /**
     * String 转化为 json 对象，不抛异常
     */
    public static Optional<JsonNode> readTreeSafely(String jsonString) {
        if (isEmpty(jsonString)) return Optional.empty();
        try {
            return Optional.ofNullable(om.readTree(jsonString));
        } catch (Throwable e) {
            //log.error("readTreeSafely fault. content = {}", jsonString);
            return Optional.empty();
        }
    }

    /**
     * 获取 jsonNode 子属性的流
     */
    public static Stream<Map.Entry<String, JsonNode>> stream(JsonNode jsonNode) {
        if (jsonNode == null) return Stream.empty();
        return StreamUtils.iteratorToStream(jsonNode.fields());
    }

    /**
     * 从 json 获取列表
     */
    public static <R> List<R> getList(JsonNode parent, String path, Function<JsonNode, R> valueMapper) {
        if (parent == null || isBlank(path)) return emptyList();
        int splitIdx = path.indexOf(".");
        if (splitIdx == -1) {
            // 结束递归
            return Optional.ofNullable(parent.get(path)).map(node -> {
                if (!node.isArray()) {
                    return singletonList(valueMapper.apply(node));
                } else {
                    return StreamUtils.iteratorToStream(node.elements()).map(valueMapper).collect(toList());
                }
            }).orElse(emptyList());
        } else {
            // 递归
            return getList(parent.get(path.substring(0, splitIdx)), path.substring(splitIdx + 1, path.length()), valueMapper);
        }
    }

    /**
     * 获取父节点的 path 对应的属性
     * 为啥写这个方法呢？
     * JsonNode 自带的查元素方法有俩 1 get(...) 2 findPath(...)
     * 两种写法各有不方便之处
     * get 找不到元素返回 null，每次要 if 判断
     * findPath 是返回子节点中第一个属性名匹配的某个节点，有多个相同名称节点时返回的可能不是想要的那个节点
     */
    public static <R> Optional<R> getValue(JsonNode parent, String path, Function<JsonNode, R> valueMapper) {
        if (parent == null || isBlank(path)) return Optional.empty();
        int splitIdx = path.indexOf(".");
        return splitIdx == -1 ?
                // 结束递归
                Optional.ofNullable(parent.get(path)).map(valueMapper) :
                // 递归
                getValue(parent.get(path.substring(0, splitIdx)), path.substring(splitIdx + 1, path.length()), valueMapper);
    }

    /**
     * 获取父节点的 path 对应的属性
     * 默认返回空值的版本
     */
    public static <R> R getValueNullable(JsonNode parent, String path, Function<JsonNode, R> valueMapper) {
        return getValue(parent, path, valueMapper).orElse(null);
    }

    /**
     * 从 json 字符串获取 path 对应的值
     */
    public static <R> Optional<R> getValue(String jsonString, String path, Function<JsonNode, R> valueMapper) {
        return getValue(readTreeSafely(jsonString).orElse(null), path, valueMapper);
    }

    /**
     * 从 json 字符串获取 path 对应的值
     * @return 默认返回 null
     */
    public static <R> R getValueNullable(String jsonString, String path, Function<JsonNode, R> valueMapper) {
        return getValueNullable(readTreeSafely(jsonString).orElse(null), path, valueMapper);
    }

    /**
     * 常用方法封装：获取字符串
     */
    public static String getText(String jsonString, String path) {
        return getValueNullable(jsonString, path, JsonNode::asText);
    }

    /**
     * 常用方法封装：获取字符串
     */
    public static String getText(JsonNode parent, String path) {
        return getValueNullable(parent, path, JsonNode::asText);
    }

    /**
     * 常用方法封装：获取布尔值
     */
    public static Optional<Boolean> getBoolean(JsonNode parent, String path) {
        return getValue(parent, path, JsonNode::asBoolean);
    }

    /**
     * 常用方法封装：获取布尔值
     */
    public static Optional<Boolean> getBoolean(String jsonString, String path) {
        return getValue(jsonString, path, JsonNode::asBoolean);
    }


}
