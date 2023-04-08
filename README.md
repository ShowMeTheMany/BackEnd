![showmethemany_상단이미지](https://user-images.githubusercontent.com/117756400/230520235-f678649a-ef4f-40e9-afd0-c79cf75ff79c.png)

<br /> <br />

# 💰 쇼미더마니
- 설명 : ***온라인 쇼핑몰 상품에 대한 대용량 데이터 처리***  
- 프로젝트 이름 : ShowMeTheMany  
- 프로젝트 기간 : 23.03.01 ~ 23.03.31
- 프로젝트 목표 : 대용량 상품 데이터를 활용한 검색 및 주문 서비스 제공  
- 초기 시나리오
  - 쿠팡 상품 데이터 : 최소 100만 건
  - 목표 동시 접속자 : 최소 1000명 ~ 최대 2000명
  - latency : 1s 이내
  - 에러율 : 최대 2%
- 구현
  - 쿠팡 상품 데이터 : 약 200만 건  
  - 목표 동시 접속자 : 최소 1000명 ~ 최대 2000명  
  - latency : 100ms 이내
  - 에러율 : 0.05% 미만
- 팀 노션
  - [쇼미더마니☘️](https://rogue-brook-6ec.notion.site/Show-Me-The-Many-7160e21c29a947c98c9a6fde49d20739)
- 팀 멤버
  - [김아영](https://github.com/isladaisy), [김재영](https://github.com/code0613), [조소영](https://github.com/littlezero48), [홍윤재](https://github.com/PigletHong)
  

<br /> <br />

## ⚙️ 서비스 아키텍쳐
![showmethemany_architecture](https://user-images.githubusercontent.com/117756400/230712853-35d4e2c5-bf1e-4d12-9d34-eb439160ec4b.png)

<br /> <br />

## 🖌 ERD
<details>
<summary>ERD</summary>
<div markdown="1">
<br />

![showmethemany_erd](https://user-images.githubusercontent.com/117756400/230522331-722eb31d-c266-4891-a742-308f740ce1d8.png)
</div>
</details>

<br /> <br />

## 📝 기술적 의사 결정
<details>
<summary>MySQL</summary>
<div markdown="1">

- **기술적 필요성**
  - 프로젝트에서 사용되는 데이터가 구조화되어 있기 때문에 각각의 데이터들을 테이블로 구성하여 관리해야 한다고 판단해 RDBMS 사용 결정
  
- **후보군**
  - MySQL / PostreSQL
  
- **의사 조율 및 결정**
  - 대용량 데이터 처리가 목적이기 때문에 대규모 DB 시스템에 적합한 확장성과 높은 성능을 제공하고 멀티 쓰레드 아키텍처를 사용하여 동시성 처리를 가능하게 하며 상대적으로 레퍼런스가 많은 MySQL을 선택
<br />
</div>
</details>
<details>
<summary>Redis</summary>
<div markdown="1">

- **기술적 필요성**
  - 주문하기 및 주문 취소하기 API를 멀티 쓰레드 환경에서 실행할 때 자료가 업데이트 되는 과정에서 정보가 유실될 경우를 방지하기 위해 도입
  
- **후보군**
  - Synchronized / Pessimistic Lock / Redisson
  
- **의사 조율 및 결정**
  - 데드락을 방지하기 위한 타임 아웃 기능을 제공하고 락을 어플리케이션 단계에서 관리하기 때문에 데이터베이스까지 책임이 전파되지 않는 Redisson을 사용하기로 결정
<br />
</div>
</details>
<details>
<summary>Github Actions</summary>
<div markdown="1">

- **기술적 필요성**
  - 지속적 통합과 지속적 배포를 통한 업무 효율 상승을 위해 도입  
  
- **후보군**
  - Jenkins / Github Action / Travis CI  
  
- **의사 조율 및 결정**
  - 현재 프로젝트 관리를 깃허브를 통하여 진행하고 있고, 소규모 프로젝트이고 추가적인 설치 과정 없이 Github에서 제공하는 환경에서 CI 작업이 가능하기 때문에 Github Action을 사용하는 것이 용이할 거라 생각함  
<br />
</div>
</details>
<details>
<summary>QueryDSL</summary>
<div markdown="1">

- **기술적 필요성**
  - DB에서 데이터를 쿼리하기 위해 도입
  
- **후보군**
  - JPA / Native Query / JPQL / QueryDSL
  
- **의사 조율 및 결정**
  - 데이터 처리 시 다양한 검색 방식으로 동적 쿼리 구현이 필요하다고 판단. 그리고 쿼리를 문자열이 아닌 코드로 작성하고 컴파일 시점에 문법 오류를 확인할 수 있는 QueryDSL를 사용하기로 결정. 여기에 단순 조회는 JPA 메서드를 함께 사용하는 방향으로 함
<br />
</div>
</details>
<details>
<summary>Logback</summary>
<div markdown="1">

- **기술적 필요성**
  - 개발 과정에서 문제 원인 파악 및 개발의 안정석 확보 위해 도입
  
- **후보군**
  - Hibernate log / Log4jdbc
  
- **의사 조율 및 결정**
  - 설정에 따라 어느 쓰레드가 전송한 쿼리인지 볼 수 있고 해당 쿼리에 대한 결과 값도 알 수 있는 Log4jdbc 도입
<br />
</div>
</details>

<br /> <br />

## 📢 성능 개선
### ➖ [크롤링](https://www.notion.so/4f753a3dd2534a51abd5a9d281b2646a)
### ➖ [검색](https://www.notion.so/62a535b9629a43ddb4b1bb8d639f7ebc)
### ➖ [주문](https://www.notion.so/696207f719754385bb18991fa5641fdf)
<br /> <br />

## ⚽ 트러블 슈팅
<details>
<summary>📌 N+1 문제 원인과 해결책</summary>
<div markdown="1">

- **문제 상황**  
  - 반복문 내 하위 엔티티 개수만큼 불필요한 쿼리 발생

- **이유**  
  - 프록시 객체로 내려받은 하위 엔티티는 데이터를 사용할 때마다 초기화하기 때문 

- **해결 방법**  
  - JPQL fetch join를 통해 하위 엔티티의 데이터를 한 번에 내려받은 후 조회하기로 변경
  <br />
</div>
</details>
<details>
<summary>📌 테스트 클래스에서 @Transactional 사용 불가</summary>
<div markdown="1">

- **문제 상황**  
  - 테스트 클래스 내에서 Transactional 어노테이션이 정상적으로 실행되지 않아 TransactionlRequiredException 발생

- **이유**  
  - 스프링에서는 프록시 객체를 생성한 후에 프록시 객체가 @Transactional이 적용된 빈을 대신해서 호출해주는데 스프링이 관리하지 않는 클래스는 프록시 객체를 생성할 수 없기 때문에 @Component 등으로 빈에 등록해야 함
  - 테스트 클래스에는 스프링 빈에 등록된 적이 없기 때문에 @Transactional 작동 안 함

- **해결 방법**  
  - PlatformTransactionManager 인터페이스를 주입받아 트랜잭션 적용
  <br />
</div>
</details>
<details>
<summary>📌 Primitive Type에서 IllegalArgumentException 발생</summary>
<div markdown="1">

- **문제 상황**  
  - JPA로 insert 하는 과정에서 set 하지 않은 필드에 대해 에러 발생

- **이유**  
  - Primitive Type은 null을 담을 수 없기 때문

- **해결 방법**  
  - 필드 타입을 Reference Type으로 수정하여 해결
  <br />
</div>
</details>

<br /> <br />
  
## 🛠 기술 스택

### Backend Tech Stack  
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">  <img src="https://img.shields.io/badge/apache jmeter-D22128?style=for-the-badge&logo=apachejmeter&logoColor=white">  <img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">
<br /> <br />

### Infrastructure  
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">  <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"> <img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">  <img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">  <img src="https://img.shields.io/badge/amazon rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> <br /> <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">  <img src="https://img.shields.io/badge/aws codedeploy-FF9E9F?style=for-the-badge&logo=amazonaws&logoColor=white">  <img src="https://img.shields.io/badge/github actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">
<br /> <br />
  
### Team Collaboration Tool  
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">  <img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">  <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">  <img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">  <img src="https://img.shields.io/badge/intellij idea-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
<br />
