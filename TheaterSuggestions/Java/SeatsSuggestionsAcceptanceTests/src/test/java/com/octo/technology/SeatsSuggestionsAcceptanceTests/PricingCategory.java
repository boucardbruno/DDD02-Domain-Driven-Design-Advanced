package com.octo.technology.SeatsSuggestionsAcceptanceTests;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum PricingCategory {
    First(1),
    Second(2),
    Third(3),
    Mixed(4);

    private int value;
    private static Map map = new HashMap();

    private PricingCategory(int value) {
        this.value = value;
    }

    static {
        for (PricingCategory pageType : PricingCategory.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static PricingCategory valueOf(int pageType) {
        return (PricingCategory) map.get(pageType);
    }

}