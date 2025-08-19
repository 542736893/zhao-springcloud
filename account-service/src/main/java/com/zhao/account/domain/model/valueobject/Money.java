package com.zhao.account.domain.model.valueobject;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额值对象
 */
@Value
public class Money {
    
    private final BigDecimal amount;
    private final Currency currency;
    
    public static final Money ZERO = new Money(BigDecimal.ZERO, Currency.CNY);
    
    private Money(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }
    
    /**
     * 创建人民币金额
     */
    public static Money ofCNY(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("金额不能为空");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("金额不能为负数");
        }
        return new Money(amount, Currency.CNY);
    }
    
    /**
     * 创建人民币金额
     */
    public static Money ofCNY(double amount) {
        return ofCNY(BigDecimal.valueOf(amount));
    }
    
    /**
     * 创建人民币金额
     */
    public static Money ofCNY(String amount) {
        if (amount == null || amount.trim().isEmpty()) {
            throw new IllegalArgumentException("金额字符串不能为空");
        }
        try {
            return ofCNY(new BigDecimal(amount));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无效的金额格式: " + amount);
        }
    }
    
    /**
     * 加法
     */
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("不同货币不能进行运算");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }
    
    /**
     * 减法
     */
    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("不同货币不能进行运算");
        }
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("金额不能为负数");
        }
        return new Money(result, this.currency);
    }
    
    /**
     * 乘法
     */
    public Money multiply(BigDecimal multiplier) {
        return new Money(this.amount.multiply(multiplier), this.currency);
    }
    
    /**
     * 比较大小
     */
    public int compareTo(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("不同货币不能进行比较");
        }
        return this.amount.compareTo(other.amount);
    }
    
    /**
     * 是否大于
     */
    public boolean isGreaterThan(Money other) {
        return compareTo(other) > 0;
    }
    
    /**
     * 是否大于等于
     */
    public boolean isGreaterThanOrEqualTo(Money other) {
        return compareTo(other) >= 0;
    }
    
    /**
     * 是否小于
     */
    public boolean isLessThan(Money other) {
        return compareTo(other) < 0;
    }
    
    /**
     * 是否为零
     */
    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }
    
    /**
     * 是否为正数
     */
    public boolean isPositive() {
        return this.amount.compareTo(BigDecimal.ZERO) > 0;
    }
    
    @Override
    public String toString() {
        return currency.getSymbol() + amount.toString();
    }
}
