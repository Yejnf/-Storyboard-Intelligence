package com.example.alibabademo.mapper;
import com.example.alibabademo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.stream.BaseStream;

@Mapper
public interface UserMapper{
    @Select("select * from user where name=#{name} and password=#{password} ")
    User finduser(String name,String password);

    @Insert("insert into user values(#{name},#{password})")
    int add(User user);


}
