package cn.itcast.product.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 商品实体类
 */
@Data
@Entity
@Table(name = "tb_product")
public class Product {
    /**
     * id` int(1
     * product_n
     * status` i
     * price` de
     * product_d
     * caption`
     * inventory
     */
    @Id
    private Long id;
    private String productName;
    private Integer status;
    private BigDecimal price;
    private String productDesc;
    private String caption;
    private Integer inventory;
}
