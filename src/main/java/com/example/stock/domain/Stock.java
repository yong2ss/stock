package com.example.stock.domain;

import javax.persistence.*;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long quantity;

    //Optimistic Lock을 위한 version column 추가
    @Version
    private long version;

    public Stock() { }

    public Stock(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    //재고감소
    public void decrease(Long quantity) {
        if(this.quantity < quantity) {
            throw new RuntimeException("stock quantity error");
        }

        this.quantity = this.quantity - quantity;
    }
}
