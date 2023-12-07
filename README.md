# OOP_Proj4
2023-2 객체지향프로그래밍 3분반 과제4 Team5

### InteliJ에서 열기
Open project > bulid.gradle

### 서버 실행하는 법
src > main > java > Proj4Team5Application 에서 직접 실행해도 되고,   
IDE가 알아서 실행하는 부분 잡아주면 단축키 등으로 실행가능.   
브라우저에서 **localhost:8080**으로 확인

### database 설정 바꾸는 법
#### src > main > resources 에 application.yml 파일 생성
#### application.yml
```java
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    // {스키마 이름} 자리에 본인이 생성한 스키마 이름 적어주시면 됩니다.
    url: jdbc:mysql://127.0.0.1:3306/{스키마 이름}?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    // {이름} 자리에 본인 mySQL 이름, {비밀번호} 자리에 본인 비밀번호 적어주시면 됩니다.
    // 저는 원래 0000 이었는데 대소문자 포함, 특수문자 포함, 8자리 이상 조건 때문에 오류 나서 다른 걸로 바꿨습니다.
    // 쉬운 비밀번호 때문에 오류나면 고쳐주시고, 혹시 비밀번호 정책 바꾸는 법 아시면 공유 부탁드려요.
    username: {이름}
    password: {비밀번호}
  thymeleaf:
    cache: false

jpa:
  database: mysql
  open-in-view: true
  show-sql: true
  hibernate:
    ddl-auto: update
  properties:
    hibernate:
      show_sql: true
      format_sql: true
```
