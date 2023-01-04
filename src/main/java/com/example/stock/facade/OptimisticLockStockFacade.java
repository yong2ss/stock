package com.example.stock.facade;

import com.example.stock.service.OptimisticLockStockService;
import org.springframework.stereotype.Service;

/**
 * Optimistic Lock 으로 인해 update 실패 시 재시도를 위한 서비스
 */
@Service
public class OptimisticLockStockFacade {

    private OptimisticLockStockService stockService;

    public OptimisticLockStockFacade(OptimisticLockStockService stockService) {
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while(true) {
            try {
                stockService.decrease(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
