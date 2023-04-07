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
![showmethemany_architecture](https://user-images.githubusercontent.com/117756400/230522441-19e6df42-69df-4235-a1de-bfffc512ea2b.png)

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

</div>
</details>
<details>
<summary>Redis</summary>
<div markdown="1">

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
  - 프로젝트 규모를 생각했을 때 초기 설정이 적고 편의성이 높아 리소스를 줄이는 방향으로 진행. 따라서 Github Action과 AWS에서 제공하는 Code Deploy를 이용하여 자동화 배포를 하기로 결정
<br />
</div>
</details>
<details>
<summary>QueryDSL</summary>
<div markdown="1">

</div>
</details>
<details>
<summary>JUnit5</summary>
<div markdown="1">

</div>
</details>

<br /> <br />

## 📢 성능 개선
### ➖ 검색
<details>
<summary>Full Scan</summary>
<div markdown="1">

</div>
</details>
<details>
<summary>B-TREE Index</summary>
<div markdown="1">

</div>
</details>
<details>
<summary>Full Text Index✅</summary>
<div markdown="1">

</div>
</details>

<br />

### ➖ 주문
<details>
<summary>Synchronized</summary>
<div markdown="1">

</div>
</details>
<details>
<summary>Pessimistic Lock</summary>
<div markdown="1">

</div>
</details>
<details>
<summary>Redisson 분산락✅</summary>
<div markdown="1">

</div>
</details>

<br /> <br />

## ⚽ 트러블 슈팅
<details>
<summary>📌 N+1 문제 원인과 해결책</summary>
<div markdown="1">

- **문제 상황**  
  - 

- **이유**  
  - 

- **해결 방법**  
  - 
  <br />
</div>
</details>
<details>
<summary>📌 테스트 클래스에서 @Transactional 사용 불가</summary>
<div markdown="1">

- **문제 상황**  
  - 

- **이유**  
  - 

- **해결 방법**  
  -   
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
