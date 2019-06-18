package com.example.demo.entity;

import lyj.forward.generation.annotation.LColumn;
import lyj.forward.generation.annotation.LTable;
import lyj.forward.generation.annotation.LTableId;
import lyj.forward.generation.enums.IdType;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/8 21:40
 */
@LTable(name = "tb_order")
public class Order extends BaseEntity
{
    @LTableId(type = IdType.NONE)
    private String id;


    private Double price;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }
}
