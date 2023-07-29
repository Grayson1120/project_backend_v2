package com.fsse2305.project_backend_v2.api;

import com.fsse2305.project_backend_v2.config.EnvConfig;
import com.fsse2305.project_backend_v2.data.product.domainObject.ProductDetailData;
import com.fsse2305.project_backend_v2.data.product.dto.response.GetAllProductResponseDto;
import com.fsse2305.project_backend_v2.data.product.dto.response.ProductResponseDto;
import com.fsse2305.project_backend_v2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin({EnvConfig.devConfig,EnvConfig.prodConfig})
@RestController
@RequestMapping("/public/product")
public class ProductApi {
    private final ProductService productService;

    @Autowired
    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<GetAllProductResponseDto> getAllProduct(){
        List<GetAllProductResponseDto> responseDtoList = new ArrayList<>();
        for (ProductDetailData data : productService.getAllProduct()){
            responseDtoList.add(new GetAllProductResponseDto(data));
        }
        return responseDtoList;
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductByPid(@PathVariable("id") Integer pid){
        return new ProductResponseDto(productService.getProductById(pid));
    }

}
