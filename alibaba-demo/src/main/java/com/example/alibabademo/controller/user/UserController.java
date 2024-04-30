package com.example.alibabademo.controller.user;

import com.example.alibabademo.entity.User;
import com.example.alibabademo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @PostMapping(value = "/login")
    public int login(@RequestBody User user){

        User luser= userMapper.finduser(user.getName(),user.getPassword());
        if (luser != null) {
            // 登录成功
            return 200;
        } else {
            // 登录失败
            return 0;
        }
    }
}
