package com.example.springboot.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * create by lichengzhen in 18-5-8
 */
public class StreamUtils {

    /**
     * 获取集合中 所有不一样的 映射值
     * 如：distinct(orgs, Org::getId); 返回 orgs 集合中所有不一样的 机构 id 列表
     */
    public static <T, R> List<R> distinct(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        return collection.stream().map(mapper).distinct().collect(toList());
    }

    /**
     * 迭代器 转 流
     */
    public static <T> Stream<T> iteratorToStream(Iterator<T> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }

}
