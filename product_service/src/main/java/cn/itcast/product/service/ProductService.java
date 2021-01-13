package cn.itcast.product.service;

import cn.itcast.product.entity.Product;

public interface ProductService {
    /**
     * 查询
     */
Product findProductById(Long id);
    /**
     * 删除
     */
    void delete(Long id);
    /**
     * 保存
     */
    void save(Product product);
    /**
     * 更新
     */
    void update(Product product);
}
