package cn.itcast.order.feign;

import cn.itcast.order.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 1.声明需要调用的微服务名称
 */
//@FeignClient(name = "anyString",url = "http://localhost:8080")//此处url地址是目标微服务对应网关地址
//@FeignClient(name = "anyString",url = "http://localhost:9001")
@FeignClient(name = "service-product")
public interface ProductFeignClient {
    /**
     * 2.配置需要调用的微服务接口
     */
    @RequestMapping(value = "/product/findById/{id}", method = RequestMethod.GET)//网关添加了过滤器,要验证token
//    @RequestMapping(value = "/product/findById/{id}", method = RequestMethod.GET)
    public Product findById(@PathVariable("id") Long id);
    @RequestMapping(value = "/apollo", method = RequestMethod.GET)
    public String testParam(@RequestParam("name") String name, @RequestParam("sex") int sex);
}
