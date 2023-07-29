package com.fsse2305.project_backend_v2.api;
import com.fsse2305.project_backend_v2.config.EnvConfig;
import com.fsse2305.project_backend_v2.data.cartItem.domainObject.CartItemDetailData;
import com.fsse2305.project_backend_v2.data.cartItem.dto.response.CartItemResponseDto;
import com.fsse2305.project_backend_v2.data.cartItem.dto.response.SuccessResponseDto;
import com.fsse2305.project_backend_v2.service.CartItemService;
import com.fsse2305.project_backend_v2.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin({EnvConfig.devConfig,EnvConfig.prodConfig})
@RestController
@RequestMapping("/cart")
public class CartItemApi {
    private final CartItemService cartItemService;

    @Autowired
    public CartItemApi(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PutMapping("/{pid}/{quantity}")
    public SuccessResponseDto putCartItem(JwtAuthenticationToken jwtToken, @PathVariable Integer pid, @PathVariable Integer quantity) {
        if (cartItemService.putCartItem(JwtUtil.getFirebaseUserData(jwtToken), pid, quantity)) {
            return new SuccessResponseDto();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public List<CartItemResponseDto> getUserCartItem(JwtAuthenticationToken jwtToken) {
        List<CartItemResponseDto> cartItemResponseDtoList = new ArrayList<>();
        for (CartItemDetailData data : cartItemService.getUserCartItem(JwtUtil.getFirebaseUserData(jwtToken))) {
            cartItemResponseDtoList.add(new CartItemResponseDto(data));
        }
        return cartItemResponseDtoList;
    }

    @PatchMapping("/{pid}/{quantity}")
    public CartItemResponseDto patchCartItem(JwtAuthenticationToken jwtToken,
                                             @PathVariable Integer pid,
                                             @PathVariable Integer quantity) {
        return new CartItemResponseDto(cartItemService.patchCartItemQuantity(JwtUtil.getFirebaseUserData(jwtToken), pid, quantity));
    }

    @DeleteMapping("/{pid}")
    public SuccessResponseDto deleteCartItem(JwtAuthenticationToken jwtToken, @PathVariable Integer pid) {
        if (cartItemService.deleteCartItemByPid(JwtUtil.getFirebaseUserData(jwtToken),pid)){
            return new SuccessResponseDto();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
