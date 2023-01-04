package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void before() {
        Stock stock = new Stock(1L, 100L);

        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    public void stock_decrease() {
        //when
        stockService.decrease(1L, 1L);

        //then
        Stock stock = stockRepository.findById(1L).orElseThrow();
        assertEquals(99, stock.getQuantity());
    }

    @Test
    @DisplayName("동시에 100개의 재고감소 요청 시 Race Condition 발생")
    public void stock_decrease_request_100() throws InterruptedException {
        int threadCount = 100;
        //비동기를 위한 멀티 스레드
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        //100개의 요청이 끝날때까지 대기
        //다른 스레드의 작업 대기
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();

        //fail (Race Condition)
        //둘 이상의 스레드가 공유 데이터에 액세스하면서 동시에 변경하려고 할 때 발생
        assertEquals(0L, stock.getQuantity());
    }
}