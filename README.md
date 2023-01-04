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
       - setnx 명령어
       - spin lock 방식
     - Redisson
       - pub-sub 기반으로 Lock 구현


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