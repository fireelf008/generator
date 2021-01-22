package com.wsf.generator.utils;

import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

public class QueryDslUtils {

    public static OrderSpecifier[] getOrderSpecifier(QPageRequest pageRequest) {
        QSort qSort = (QSort)pageRequest.getSort();
        OrderSpecifier[] orderSpecifiers = new OrderSpecifier[qSort.getOrderSpecifiers().size()];
        return qSort.getOrderSpecifiers().toArray(orderSpecifiers);
    }

    public static OrderSpecifier[] getOrderSpecifier(Sort sort) {
        QSort qSort = (QSort)sort;
        OrderSpecifier[] orderSpecifiers = new OrderSpecifier[qSort.getOrderSpecifiers().size()];
        return qSort.getOrderSpecifiers().toArray(orderSpecifiers);
    }
}
