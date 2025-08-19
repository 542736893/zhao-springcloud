package com.zhao.order.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.order.infrastructure.persistence.po.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单Mapper接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
    
    /**
     * 根据用户ID查询订单列表
     */
    List<OrderDO> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据用户ID和状态查询订单列表
     */
    List<OrderDO> selectByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);
    
    /**
     * 根据状态查询订单列表
     */
    List<OrderDO> selectByStatus(@Param("status") String status);
    
    /**
     * 根据订单号查询订单
     */
    OrderDO selectByOrderNumber(@Param("orderNumber") String orderNumber);
    
    /**
     * 检查订单号是否存在
     */
    int countByOrderNumber(@Param("orderNumber") String orderNumber);
    
    /**
     * 统计用户订单数量
     */
    int countByUserId(@Param("userId") Long userId);
    
    /**
     * 统计指定状态的订单数量
     */
    int countByStatus(@Param("status") String status);
}


