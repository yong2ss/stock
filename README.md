# 동시성 이슈 (재고 시스템을 이용한 예제)

> ### Race Condition 발생 이슈
> - 둘 이상의 스레드가 공유 데이터에 액세스하면서 동시에 변경하려고 할 때 발생

## 해결방법 
1. Application Level
   - Synchronized 사용
     - 한 서버에서 여러 쓰레드 동시성 이슈 해결은 가능
     - 하지만 서버가 여러대인 경우 해결하지 못한다. 
2. Database Lock
    - Database가 제공하는 Lock을 이용해서 동시성 제어
        - Pessimistic Lock
        - Optimistic Lock
        - Named Lock
3. Redis Distributed Lock
   - 분산환경에서 레디스를 활용하여 동시성 제어
     - Lettuce
       - 구현이 간단
       - spring-data-redis의 기본이기 때문에 별도 라이브러리 사용안해도 된다
       - spin lock 방식
         - 동시에 많은 쓰레드가 lock 획득 대기라면 redis에 부하가 생길 가능성
       - 실무 관점에서는 재시도가 필요하지 않은 lock에서 사용
     - Redisson
       - 락 획득 재시도 기능을 기본으로 제공
       - 별도의 라이브러리 추가해야한다
       - pub-sub 기반으로 Lock 구현
         - redis에 부하가 덜하지만 사용법 학습 필요
       - 실무 관점에서는 재시도가 필요한 경우에 사용

> ## Mysql vs Redis
> #### Mysql
> - 이미 사용중이면 별도 비용없이 사용 가능
> - 어느정도 트래픽까지는 문제없이 활용 가능
> - Redis보다는 성능이 좋지 않다
> 
> #### Redis
> - 활용중인 Redis가 없다면 별도의 구축비용과 인프라 관리비용
> - Mysql보다는 성능이 좋다

---

### Docker 설치 (Mac - homebrew 기준)
```
brew install docker
brew link docker
docker version
```

### Mysql 설치 및 실행
```
docker pull mysql
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 —name mysql mysql
docker ps
```

### Trouble shouting
```
docker pull —platform linux/x86/64 mysql
```

### Mysql 데이터 베이스 설성
```
docker exec -it mysql bash
mysql -u root -p
create database stock_example;
use stock_example;
```

### Redis 설치 및 실행
```
docker pull redis
docker run --name myredis -d -p 6379:6379 redis
```