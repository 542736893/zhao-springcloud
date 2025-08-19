package com.zhao.order.domain.model.valueobject;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额值对象
 */
@Value
public class Money {
    
    private final BigDecimal amount;
    
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    
    private Money(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * 从BigDecimal创建金额
     */
    public static Money of(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("金额不能为空");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("金额不能为负数");
        }
        return new Money(amount);
    }
    
    /**
     * 从字符串创建金额
     */
    public static Money of(String amount) {
        if (amount == null || amount.trim().isEmpty()) {
            throw new IllegalArgumentException("金额字符串不能为空");
        }
        try {
            return of(new BigDecimal(amount));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无效的金额格式: " + amount);
        }
    }
    
    /**
     * 从double创建金额
     */
    public static Money of(double amount) {
        return of(BigDecimal.valueOf(amount));
    }
    
    /**
     * 从long创建金额（以分为单位）
     */
    public static Money ofCents(long cents) {
        return of(BigDecimal.valueOf(cents).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
    }
    
    /**
     * 获取BigDecimal值
     */
    public BigDecimal getAmount() {
        return amount;
    }
    
    /**
     * 获取以分为单位的long值
     */
    public long getCents() {
        return amount.multiply(BigDecimal.valueOf(100)).longValue();
    }
    
    /**
     * 加法
     */
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }
    
    /**
     * 减法
     */
    public Money subtract(Money other) {
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("减法结果不能为负数");
        }
        return new Money(result);
    }
    
    /**
     * 乘法
     */
    public Money multiply(BigDecimal multiplier) {
        if (multiplier.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("乘数不能为负数");
        }
        return new Money(this.amount.multiply(multiplier));
    }
    
    /**
     * 乘法（整数）
     */
    public Money multiply(int multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException("乘数不能为负数");
        }
        return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)));
    }
    
    /**
     * 比较金额
     */
    public int compareTo(Money other) {
        return this.amount.compareTo(other.amount);
    }
    
    /**
     * 检查是否为零
     */
    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }
    
    /**
     * 检查是否大于零
     */
    public boolean isPositive() {
        return this.amount.compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * 检查是否大于等于指定金额
     */
    public boolean isGreaterThanOrEqualTo(Money other) {
        return this.amount.compareTo(other.amount) >= 0;
    }
    
    /**
     * 检查是否小于指定金额
     */
    public boolean isLessThan(Money other) {
        return this.amount.compareTo(other.amount) < 0;
    }
    
    @Override
    public String toString() {
        return amount.toString();
    }
}
