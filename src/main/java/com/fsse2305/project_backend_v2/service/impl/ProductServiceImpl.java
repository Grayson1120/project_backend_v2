package com.fsse2305.project_backend_v2.service.impl;

import com.fsse2305.project_backend_v2.data.product.domainObject.ProductDetailData;
import com.fsse2305.project_backend_v2.data.product.entity.ProductEntity;
import com.fsse2305.project_backend_v2.exception.product.NoStockException;
import com.fsse2305.project_backend_v2.exception.product.ProductNotFoundException;
import com.fsse2305.project_backend_v2.repository.ProductRepository;
import com.fsse2305.project_backend_v2.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDetailData> getAllProduct() {
        List<ProductDetailData> productDetailDataList = new ArrayList<>();
        for (ProductEntity productEntity : productRepository.findAll()) {
            productDetailDataList.add(new ProductDetailData(productEntity));
        }
        return productDetailDataList;
    }

    @Override
    public ProductDetailData getProductById(Integer pid) {
        try {
            return new ProductDetailData(getEntityByPid(pid));
        } catch (ProductNotFoundException ex) {
            logger.warn("Get Product By Pid failed: Product Not Found - " + pid);
            throw ex;
        }
    }

    @Override
    public ProductEntity getEntityByPid(Integer pid) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findByPid(pid);
        if (optionalProductEntity.isEmpty()) {
            logger.warn("Product Not Found " + pid);
            throw new ProductNotFoundException();
        }
        return optionalProductEntity.get();
    }

    @Override
    public Boolean setProductStock(Integer pid, Integer quantity) {
        ProductEntity productEntity = getEntityByPid(pid);
        if (productEntity.getStock() < quantity) {
            logger.warn("Out of Stock");
            throw new NoStockException();
        }
        productEntity.setStock(productEntity.getStock() - quantity);
        return true;
    }
}
