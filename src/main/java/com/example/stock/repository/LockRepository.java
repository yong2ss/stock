package com.example.stock.repository;

import com.example.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//실무에선 이렇게 쓰지 말고 별도의 JDBC 이용
public interface LockRepository extends JpaRepository<Stock, Long> {
    @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
    void getLock(String key);

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    void releaseLock(String key);
}
