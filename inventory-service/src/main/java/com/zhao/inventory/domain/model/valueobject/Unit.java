package com.zhao.inventory.domain.model.valueobject;

import lombok.Value;

/**
 * 单位值对象
 */
@Value
public class Unit {
    
    private final String code;
    private final String name;
    
    // 常用单位常量
    public static final Unit PIECE = new Unit("PCS", "件");
    public static final Unit KILOGRAM = new Unit("KG", "千克");
    public static final Unit GRAM = new Unit("G", "克");
    public static final Unit BOX = new Unit("BOX", "箱");
    public static final Unit PACK = new Unit("PACK", "包");
    
    private Unit(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    /**
     * 创建单位
     */
    public static Unit of(String code, String name) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("单位代码不能为空");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("单位名称不能为空");
        }
        return new Unit(code.trim().toUpperCase(), name.trim());
    }
    
    /**
     * 从代码创建单位
     */
    public static Unit fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("单位代码不能为空");
        }
        
        String upperCode = code.trim().toUpperCase();
        
        // 尝试匹配预定义单位
        switch (upperCode) {
            case "PCS": return PIECE;
            case "KG": return KILOGRAM;
            case "G": return GRAM;
            case "BOX": return BOX;
            case "PACK": return PACK;
            default: return new Unit(upperCode, upperCode);
        }
    }
    
    @Override
    public String toString() {
        return name + "(" + code + ")";
    }
}
