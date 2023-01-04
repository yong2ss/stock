package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import com.example.stock.service.StockServiceForNamedLock;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class NamedLockStockFacade {

    private final LockRepository lockRepository;

    private final StockServiceForNamedLock stockServiceForNamedLock;

    public NamedLockStockFacade(LockRepository lockRepository, StockServiceForNamedLock stockService) {
        this.lockRepository = lockRepository;
        this.stockServiceForNamedLock = stockService;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        try {
            lockRepository.getLock(id.toString());
            stockServiceForNamedLock.decrease(id, quantity);
        } finally {
            lockRepository.releaseLock(id.toString());
        }
    }
}
