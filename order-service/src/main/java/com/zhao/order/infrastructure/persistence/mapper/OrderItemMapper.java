package com.zhao.order.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.order.infrastructure.persistence.po.OrderItemDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单项Mapper接口
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItemDO> {
    
    /**
     * 根据订单ID查询订单项列表
     */
    List<OrderItemDO> selectByOrderId(@Param("orderId") Long orderId);
    
    /**
     * 根据订单ID删除订单项
     */
    int deleteByOrderId(@Param("orderId") Long orderId);
    
    /**
     * 批量插入订单项
     */
    int batchInsert(@Param("items") List<OrderItemDO> items);
}
