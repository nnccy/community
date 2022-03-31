package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository
//这是访问数据库的bean
public class AlphaDaoImpl implements AlphaDao{
    @Override
    public String select() {
        return "hibernate";
    }
}
