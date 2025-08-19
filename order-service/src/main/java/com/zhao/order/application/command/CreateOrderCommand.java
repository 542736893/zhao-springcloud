package com.zhao.order.application.command;

import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * 创建订单命令
 */
@Data
public class CreateOrderCommand {
    
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    /**
     * 省份
     */
    @NotNull(message = "省份不能为空")
    @Size(max = 50, message = "省份长度不能超过50个字符")
    private String province;
    
    /**
     * 城市
     */
    @NotNull(message = "城市不能为空")
    @Size(max = 50, message = "城市长度不能超过50个字符")
    private String city;
    
    /**
     * 区县
     */
    @NotNull(message = "区县不能为空")
    @Size(max = 50, message = "区县长度不能超过50个字符")
    private String district;
    
    /**
     * 详细地址
     */
    @NotNull(message = "详细地址不能为空")
    @Size(max = 200, message = "详细地址长度不能超过200个字符")
    private String detailAddress;
    
    /**
     * 收件人姓名
     */
    @NotNull(message = "收件人姓名不能为空")
    @Size(max = 50, message = "收件人姓名长度不能超过50个字符")
    private String receiverName;
    
    /**
     * 收件人电话
     */
    @NotNull(message = "收件人电话不能为空")
    @Size(max = 20, message = "收件人电话长度不能超过20个字符")
    private String receiverPhone;
    
    /**
     * 邮政编码
     */
    @Size(max = 10, message = "邮政编码长度不能超过10个字符")
    private String zipCode;
    
    /**
     * 订单项列表
     */
    @NotEmpty(message = "订单项不能为空")
    @Valid
    private List<CreateOrderItemCommand> items;
    
    /**
     * 订单项命令
     */
    @Data
    public static class CreateOrderItemCommand {
        
        /**
         * 商品ID
         */
        @NotNull(message = "商品ID不能为空")
        private Long productId;
        
        /**
         * 商品名称
         */
        @NotNull(message = "商品名称不能为空")
        @Size(max = 100, message = "商品名称长度不能超过100个字符")
        private String productName;
        
        /**
         * 商品图片
         */
        @Size(max = 500, message = "商品图片URL长度不能超过500个字符")
        private String productImage;
        
        /**
         * 商品单价
         */
        @NotNull(message = "商品单价不能为空")
        private String unitPrice;
        
        /**
         * 商品数量
         */
        @NotNull(message = "商品数量不能为空")
        private Integer quantity;
    }
}


