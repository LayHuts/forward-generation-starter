package com.example.demo.entity;

import lyj.forward.generation.annotation.*;
import lyj.forward.generation.enums.ColumnType;
import lyj.forward.generation.enums.IdType;

import java.util.Date;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/7 17:31
 */
@LTable(name = "tb_user")
public class User extends BaseEntity
{
    @LTableId(type = IdType.AUTO)
    @LColumn(width = 11,type = ColumnType.INT,isNull = false,comment = "主键")
    private Integer id;

    @LColumn(width = 64,isNull = true,comment = "姓名",type = ColumnType.VARCHAR)
    private String name;

    @LColumn(width = 0,isNull = true,comment = "出生日期")
    private Date birthDay;

    @LNotTableField
    private Company company;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getBirthDay()
    {
        return birthDay;
    }

    public void setBirthDay(Date birthDay)
    {
        this.birthDay = birthDay;
    }
}
