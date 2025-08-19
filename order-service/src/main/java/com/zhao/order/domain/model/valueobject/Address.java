package com.zhao.order.domain.model.valueobject;

import lombok.Value;

/**
 * 地址值对象
 */
@Value
public class Address {
    
    private final String province;
    private final String city;
    private final String district;
    private final String detailAddress;
    private final String receiverName;
    private final String receiverPhone;
    private final String zipCode;
    
    private Address(String province, String city, String district, String detailAddress, 
                   String receiverName, String receiverPhone, String zipCode) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.detailAddress = detailAddress;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.zipCode = zipCode;
    }
    
    /**
     * 创建地址
     */
    public static Address of(String province, String city, String district, String detailAddress,
                           String receiverName, String receiverPhone, String zipCode) {
        if (province == null || province.trim().isEmpty()) {
            throw new IllegalArgumentException("省份不能为空");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("城市不能为空");
        }
        if (district == null || district.trim().isEmpty()) {
            throw new IllegalArgumentException("区县不能为空");
        }
        if (detailAddress == null || detailAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("详细地址不能为空");
        }
        if (receiverName == null || receiverName.trim().isEmpty()) {
            throw new IllegalArgumentException("收件人姓名不能为空");
        }
        if (receiverPhone == null || receiverPhone.trim().isEmpty()) {
            throw new IllegalArgumentException("收件人电话不能为空");
        }
        
        return new Address(province, city, district, detailAddress, receiverName, receiverPhone, zipCode);
    }
    
    /**
     * 获取完整地址
     */
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(province).append(" ").append(city).append(" ").append(district);
        if (detailAddress != null && !detailAddress.trim().isEmpty()) {
            sb.append(" ").append(detailAddress);
        }
        return sb.toString();
    }
    
    /**
     * 获取收件人信息
     */
    public String getReceiverInfo() {
        return receiverName + " " + receiverPhone;
    }
    
    /**
     * 检查是否是有效地址
     */
    public boolean isValid() {
        return province != null && !province.trim().isEmpty() &&
               city != null && !city.trim().isEmpty() &&
               district != null && !district.trim().isEmpty() &&
               detailAddress != null && !detailAddress.trim().isEmpty() &&
               receiverName != null && !receiverName.trim().isEmpty() &&
               receiverPhone != null && !receiverPhone.trim().isEmpty();
    }
    
    @Override
    public String toString() {
        return String.format("Address{province='%s', city='%s', district='%s', detailAddress='%s', receiverName='%s', receiverPhone='%s'}", 
                province, city, district, detailAddress, receiverName, receiverPhone);
    }
}
