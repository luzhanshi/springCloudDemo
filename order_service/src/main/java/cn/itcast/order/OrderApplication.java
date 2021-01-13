package cn.itcast.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan("cn.itcast.product.order")
@EnableFeignClients//通过@EnableFeignClients注解开启Spring Cloud Feign的支持功能
public class OrderApplication {

    /**
     * 使用spring的RestTemplate发送http请求到商品服务
     * 1.创建RestTemplate对象交给容器管理
     * 2.在使用的时候调用其方法完成操作
     */
    /**
     * 基于Ribbon的服务调用与负载均衡
     */
//    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
