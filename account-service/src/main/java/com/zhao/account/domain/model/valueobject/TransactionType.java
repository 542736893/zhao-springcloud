package com.zhao.account.domain.model.valueobject;

/**
 * 交易类型值对象
 */
public enum TransactionType {
    
    /**
     * 充值
     */
    DEPOSIT("DEPOSIT", "充值"),
    
    /**
     * 提现
     */
    WITHDRAW("WITHDRAW", "提现"),
    
    /**
     * 转账转出
     */
    TRANSFER_OUT("TRANSFER_OUT", "转账转出"),
    
    /**
     * 转账转入
     */
    TRANSFER_IN("TRANSFER_IN", "转账转入"),
    
    /**
     * 消费
     */
    CONSUME("CONSUME", "消费"),
    
    /**
     * 退款
     */
    REFUND("REFUND", "退款"),
    
    /**
     * 冻结
     */
    FREEZE("FREEZE", "冻结"),
    
    /**
     * 解冻
     */
    UNFREEZE("UNFREEZE", "解冻");
    
    private final String code;
    private final String description;
    
    TransactionType(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * 根据代码获取交易类型
     */
    public static TransactionType fromCode(String code) {
        for (TransactionType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的交易类型代码: " + code);
    }
    
    /**
     * 是否为借记交易（减少余额）
     */
    public boolean isDebit() {
        return this == WITHDRAW || this == TRANSFER_OUT || this == CONSUME || this == FREEZE;
    }
    
    /**
     * 是否为贷记交易（增加余额）
     */
    public boolean isCredit() {
        return this == DEPOSIT || this == TRANSFER_IN || this == REFUND || this == UNFREEZE;
    }
}
