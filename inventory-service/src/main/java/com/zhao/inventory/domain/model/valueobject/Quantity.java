package com.zhao.inventory.domain.model.valueobject;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数量值对象
 */
@Value
public class Quantity {
    
    private final BigDecimal value;
    
    public static final Quantity ZERO = new Quantity(BigDecimal.ZERO);
    
    private Quantity(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * 从BigDecimal创建数量
     */
    public static Quantity of(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("数量不能为空");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("数量不能为负数");
        }
        return new Quantity(value);
    }
    
    /**
     * 从字符串创建数量
     */
    public static Quantity of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("数量字符串不能为空");
        }
        try {
            return of(new BigDecimal(value));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无效的数量格式: " + value);
        }
    }
    
    /**
     * 从double创建数量
     */
    public static Quantity of(double value) {
        return of(BigDecimal.valueOf(value));
    }
    
    /**
     * 从int创建数量
     */
    public static Quantity of(int value) {
        return of(BigDecimal.valueOf(value));
    }
    
    /**
     * 获取BigDecimal值
     */
    public BigDecimal getValue() {
        return value;
    }
    
    /**
     * 加法
     */
    public Quantity add(Quantity other) {
        return new Quantity(this.value.add(other.value));
    }
    
    /**
     * 减法
     */
    public Quantity subtract(Quantity other) {
        BigDecimal result = this.value.subtract(other.value);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("减法结果不能为负数");
        }
        return new Quantity(result);
    }
    
    /**
     * 乘法
     */
    public Quantity multiply(BigDecimal multiplier) {
        if (multiplier.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("乘数不能为负数");
        }
        return new Quantity(this.value.multiply(multiplier));
    }
    
    /**
     * 乘法（整数）
     */
    public Quantity multiply(int multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException("乘数不能为负数");
        }
        return new Quantity(this.value.multiply(BigDecimal.valueOf(multiplier)));
    }
    
    /**
     * 比较数量
     */
    public int compareTo(Quantity other) {
        return this.value.compareTo(other.value);
    }
    
    /**
     * 检查是否为零
     */
    public boolean isZero() {
        return this.value.compareTo(BigDecimal.ZERO) == 0;
    }
    
    /**
     * 检查是否为正数
     */
    public boolean isPositive() {
        return this.value.compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * 检查是否为负数
     */
    public boolean isNegative() {
        return this.value.compareTo(BigDecimal.ZERO) < 0;
    }
    
    /**
     * 检查是否大于等于指定数量
     */
    public boolean isGreaterThanOrEqualTo(Quantity other) {
        return this.value.compareTo(other.value) >= 0;
    }
    
    /**
     * 检查是否小于指定数量
     */
    public boolean isLessThan(Quantity other) {
        return this.value.compareTo(other.value) < 0;
    }
    
    /**
     * 获取绝对值
     */
    public Quantity abs() {
        return new Quantity(this.value.abs());
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
