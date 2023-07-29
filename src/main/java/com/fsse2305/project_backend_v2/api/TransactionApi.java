package com.fsse2305.project_backend_v2.api;

import com.fsse2305.project_backend_v2.config.EnvConfig;
import com.fsse2305.project_backend_v2.data.cartItem.dto.response.SuccessResponseDto;
import com.fsse2305.project_backend_v2.data.transaction.domainObject.TransactionDetailData;
import com.fsse2305.project_backend_v2.data.transaction.dto.response.TransactionResponseDto;
import com.fsse2305.project_backend_v2.service.TransactionService;
import com.fsse2305.project_backend_v2.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin({EnvConfig.devConfig,EnvConfig.prodConfig})
@RestController
@RequestMapping("/transaction")
public class TransactionApi {
    private final TransactionService transactionService;

    @Autowired
    public TransactionApi(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/prepare")
    public TransactionResponseDto createTransaction(JwtAuthenticationToken token) {
        TransactionDetailData transactionDetailData = transactionService.createTransaction(JwtUtil.getFirebaseUserData(token));
        return new TransactionResponseDto(transactionDetailData);
    }

    @GetMapping("{tid}")
    public TransactionResponseDto findTransactionByTid(JwtAuthenticationToken token, @PathVariable Integer tid) {
        return new TransactionResponseDto(transactionService.findTransactionByTid(JwtUtil.getFirebaseUserData(token), tid));
    }

    @PatchMapping("{tid}/pay")
    public SuccessResponseDto payTransaction(JwtAuthenticationToken token, @PathVariable Integer tid) {
        transactionService.updateTransactionStatus(JwtUtil.getFirebaseUserData(token),tid);
            return new SuccessResponseDto();
    }

    @PatchMapping("{tid}/finish")
    public TransactionResponseDto finishTransactionStatus(JwtAuthenticationToken token, @PathVariable Integer tid) {
        return new TransactionResponseDto(transactionService.finishTransactionStatus(JwtUtil.getFirebaseUserData(token),tid));
    }
}