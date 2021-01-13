package cn.itcast.bean;

import org.springframework.context.annotation.Bean;

public class UserConfigation {

    @Bean
    public User getUser(){
        return new User("测试人员",17);
    }
}
