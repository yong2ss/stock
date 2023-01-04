package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void decrease(Long id, Long quantity) {
        //get stock
        Stock stock = stockRepository.findById(id).orElseThrow();

        //decrease stock
        stock.decrease(quantity);

        //save stock
        stockRepository.saveAndFlush(stock);
    }
}
