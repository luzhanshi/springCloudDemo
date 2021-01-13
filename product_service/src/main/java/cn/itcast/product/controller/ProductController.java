package cn.itcast.product.controller;

import cn.itcast.product.entity.Product;
import cn.itcast.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    /**
     * 首先我们获取一下商品服务的ip和端口(方便我们后续区分是哪一个被访问了)
     */
    @Value("${spring.cloud.client.ip-address}")//springcould自动获取当前应用的IP地址
    private String ip;
    @Value("${server.port}")
    private String port;

    @Autowired
    ProductService productService;
    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    public Product findById(@PathVariable Long id){
        Product product = productService.findProductById(id);
        product.setProductName("访问的服务地址:"+ip+":"+port);
        return product;
    }

    /**
     * 模拟网络波动问题
     */
    @RequestMapping(value = "/findById2/{id}",method = RequestMethod.GET)
    public Product findById2(@PathVariable Long id){
        try {
            new Thread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Product product = productService.findProductById(id);
        product.setProductName("访问的服务地址:"+ip+port);
        return product;
    }

    /**
     * apollo修改配置测试
     */
    @Value("${name}")
    String name;

    @RequestMapping(value = "/apollo", method = RequestMethod.GET)
    public String apollo(@RequestParam("name") String name,@RequestParam("sex") int sex) {

        return name+sex;
    }


}
