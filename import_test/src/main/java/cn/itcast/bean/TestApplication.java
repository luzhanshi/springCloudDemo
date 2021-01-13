package cn.itcast.bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@EnableUserBean
public class TestApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestApplication.class);
        User user = context.getBean(User.class);
        System.out.println(user);
    }
}
