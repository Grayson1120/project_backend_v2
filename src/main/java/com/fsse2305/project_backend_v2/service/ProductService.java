package com.fsse2305.project_backend_v2.service;

import com.fsse2305.project_backend_v2.data.product.domainObject.ProductDetailData;
import com.fsse2305.project_backend_v2.data.product.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    List<ProductDetailData> getAllProduct();

    ProductDetailData getProductById(Integer pid);

    ProductEntity getEntityByPid(Integer pid);

    Boolean setProductStock(Integer pid, Integer quantity);
}
