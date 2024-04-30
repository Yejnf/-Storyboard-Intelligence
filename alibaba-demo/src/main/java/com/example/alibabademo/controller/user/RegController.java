package com.example.alibabademo.controller.user;
import com.example.alibabademo.entity.User;
import com.example.alibabademo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class RegController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping(value = "/register")
    public int regUser(@RequestBody User user) {
        User luser = userMapper.finduser(user.getName(), user.getPassword());
        if (luser != null) {
            // 登录成功
            return 100;
        } else  {
            userMapper.add(user);

            return 200;
        }

    }
}





