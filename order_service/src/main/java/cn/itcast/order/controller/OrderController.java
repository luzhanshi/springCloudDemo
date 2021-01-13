package cn.itcast.order.controller;

import cn.itcast.order.entity.Product;
import cn.itcast.order.feign.ProductFeignClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    /**
     * @param id  商品id
     * @return 通过订单服务调用商品服务根据id查询商品信息
     * 1.需要配置商品对象
     * 2.需要调用商品服务
     */
    //注入RestTemplate对象
    @Autowired
    RestTemplate restTemplate;
    //注意,该接口需要注释掉OrderApplication类中的@LoadBalance,否则不可用
    @RequestMapping(value = "/buy/{id}", method = RequestMethod.GET)//注意,该接口需要注释掉OrderApplication类中的@LoadBalance,否则不可用
    public Product findById(@PathVariable Long id) {
//        Product product = productService.findProductById(id);
        //如何调用商品服务
        /**
         * 1.使用java中的urlconnection
         * 2.httpclient
         * 3.okhttp
         * 4.spring的RestTemplate
         */
        Product product = restTemplate.getForObject("http://127.0.0.1:9001/product/findById/1", Product.class);
        return product;

    }

    /**
     * 注入DiscoveryClient
     * springCloud提供的获取元数据的工具类
     * 调用方法获取元数据信息
     */
    @Autowired
    private DiscoveryClient discoveryClient;
    //注意,该接口需要注释掉OrderApplication类中的@LoadBalance,否则不可用
    @RequestMapping(value = "/buybuy/{id}", method = RequestMethod.GET)//注意,该接口需要注释掉OrderApplication类中的@LoadBalance,否则不可用
    public Product findById2(@PathVariable Long id) {
//        调用discorveryClient方法
        //获取到所有元数据
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
//        for (ServiceInstance instance:instances){
//            System.out.println(instance);
//        }
//        获取唯一一个元数据集合
        ServiceInstance instance = instances.get(0);
//        Product product = restTemplate.getForObject("http://localhost:9001/product/findById/1", Product.class);
//        根据元数据中的主机地址和端口号拼接请求商品微服务(prodect_service)的url
//        获取自定义元数据
        Map<String, String> metadata = instance.getMetadata();
       metadata.forEach((key,value) ->{
           System.out.println(key+":"+value);
       });
        String url="http://" + instance.getHost() + ":" + instance.getPort() + "/product/findById/1";
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    /**
     * 基于Ribbon的形式调用远程微服务
     * 1.在启动类里面使用@LoadBalanced声明RestTemplate
     * 2.使用服务名替换IP地址
     */
    @RequestMapping(value = "/buybuybuy/{id}", method = RequestMethod.GET)
    public Product findById3(@PathVariable Long id) {
//        Product product = restTemplate.getForObject("http://127.0.0.1:9001/product/findById/1", Product.class);
        Product product = restTemplate.getForObject("http://service-product/product/findById/1", Product.class);
        return product;
    }
    /**
     * 基于feign的形式调用远程微服务
     *      1.注入我们配置了feign的接口
     */
    @Autowired
    ProductFeignClient productFeignClient;

    @RequestMapping(value = "/buybuybuybuy/{id}", method = RequestMethod.GET)
    public Product findById4(@PathVariable Long id) {
//        Product product = restTemplate.getForObject("http://127.0.0.1:9001/product/findById/1", Product.class);
//        Product product = restTemplate.getForObject("http://service-product/product/findById/1", Product.class);
        Product product = productFeignClient.findById(id);
        return product;
    }
    @RequestMapping(value = "/testParam", method = RequestMethod.GET)
    public String testParam(@RequestParam("name") String name, @RequestParam("sex") int sex) {
        return name+sex;
    }


    @RequestMapping(value = "/buybuybuybuybuy/{id}", method = RequestMethod.GET)
    public Product findById5(@PathVariable Long id) {
        Product product = restTemplate.getForObject("http://service-product/product/findById2/1", Product.class);
        return product;

    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findById6(@PathVariable Long id) {
        return "一个空方法,什么也不做";
    }
}
