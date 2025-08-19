package com.zhao.inventory.domain.model.valueobject;

import lombok.Value;

/**
 * 库位值对象
 */
@Value
public class Location {
    
    private final String zone;      // 区域
    private final String aisle;     // 通道
    private final String shelf;     // 货架
    private final String position;  // 位置
    
    private Location(String zone, String aisle, String shelf, String position) {
        this.zone = zone;
        this.aisle = aisle;
        this.shelf = shelf;
        this.position = position;
    }
    
    /**
     * 创建库位
     */
    public static Location of(String zone, String aisle, String shelf, String position) {
        if (zone == null || zone.trim().isEmpty()) {
            throw new IllegalArgumentException("区域不能为空");
        }
        if (aisle == null || aisle.trim().isEmpty()) {
            throw new IllegalArgumentException("通道不能为空");
        }
        if (shelf == null || shelf.trim().isEmpty()) {
            throw new IllegalArgumentException("货架不能为空");
        }
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("位置不能为空");
        }
        
        return new Location(
            zone.trim().toUpperCase(),
            aisle.trim().toUpperCase(),
            shelf.trim().toUpperCase(),
            position.trim().toUpperCase()
        );
    }
    
    /**
     * 从完整位置码创建库位
     * 格式: ZONE-AISLE-SHELF-POSITION (例如: A-01-02-03)
     */
    public static Location fromCode(String locationCode) {
        if (locationCode == null || locationCode.trim().isEmpty()) {
            throw new IllegalArgumentException("库位代码不能为空");
        }
        
        String[] parts = locationCode.trim().split("-");
        if (parts.length != 4) {
            throw new IllegalArgumentException("库位代码格式错误，应为: ZONE-AISLE-SHELF-POSITION");
        }
        
        return of(parts[0], parts[1], parts[2], parts[3]);
    }
    
    /**
     * 获取完整位置码
     */
    public String getLocationCode() {
        return zone + "-" + aisle + "-" + shelf + "-" + position;
    }
    
    /**
     * 获取区域码
     */
    public String getZoneCode() {
        return zone + "-" + aisle;
    }
    
    /**
     * 获取货架码
     */
    public String getShelfCode() {
        return zone + "-" + aisle + "-" + shelf;
    }
    
    @Override
    public String toString() {
        return getLocationCode();
    }
}
