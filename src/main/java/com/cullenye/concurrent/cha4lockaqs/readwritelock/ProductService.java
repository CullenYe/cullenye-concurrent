package com.cullenye.concurrent.cha4lockaqs.readwritelock;

/**
 * 商品的服务的接口
 * @author yeguanhong
 * @date 2020-09-29 23:22:20
 */
public interface ProductService {

    ProductInfo getProductInfo();

    void setNumber(int sellNumber);
}
