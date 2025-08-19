package com.zhao.account.domain.model.valueobject;

/**
 * 货币值对象
 */
public enum Currency {
    
    CNY("CNY", "人民币", "¥"),
    USD("USD", "美元", "$"),
    EUR("EUR", "欧元", "€"),
    JPY("JPY", "日元", "¥"),
    GBP("GBP", "英镑", "£");
    
    private final String code;
    private final String name;
    private final String symbol;
    
    Currency(String code, String name, String symbol) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * 根据代码获取货币
     */
    public static Currency fromCode(String code) {
        for (Currency currency : values()) {
            if (currency.code.equals(code)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("未知的货币代码: " + code);
    }
    
    @Override
    public String toString() {
        return code;
    }
}
