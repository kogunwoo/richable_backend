# 💰 richable_backend 💰

<br/>
2030 목표 자산 형성을 위한 맞춤 관리 서비스 <strong>리처블(Richable)</strong> 입니다.
<br/>

![image 96](https://github.com/user-attachments/assets/f3b78481-85fd-4392-b69e-2dc26f4268d3)

<br/>
<br/>

# 🙌 팀원 소개

<table>
  <tr height="160px">
    <th align="center" width="140px">
      <a href="https://github.com/find11570"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/74519181?v=4"/></a>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/kogunwoo"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/113786196?v=4"/>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/soyoung1832"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/68853692?v=4"/></a>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/singingsandhill"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/64348312?v=4"/></a>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/YooSeokHwan"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/170384539?v=4"/></a>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/realcold0"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/65608503?v=4"/></a>
    </th>
  </tr>
  <tr>
    <td align="center" width="160px">
      <a href="https://github.com/find11570"><strong>김현수</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/kogunwoo"><strong>고건우</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/soyoung1832"><strong>김소영</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/singingsandhill"><strong>김지수</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/YooSeokHwan"><strong>유석환</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/realcold0"><strong>이승환</strong></a>
    </td>
  </tr>
  <tr>
    <td align="center" width="160px">
      프로젝트 관리<br/>
      CI/CD 관리
    </td>
    <td align="center" width="160px">
      Backend 관리<br/>
      DB 관리
    </td>
    <td align="center" width="160px">
      UI/UX 관리<br/>
      Frontend 관리
    </td>
    <td align="center" width="160px">
      DB 관리</strong><br/>
      프로젝트 관리
    </td>
    <td align="center" width="160px">
      Frontend 관리<br/>
      Backend 관리
    </td>
    <td align="center" width="160px">
      Backend 관리<br/>
      CI/CD 관리
    </td>
  </tr>
          <tr>
    <td align="center" width="160px">
      FE · BE 개발
    </td>
    <td align="center" width="160px">
      FE · BE 개발
    </td>
    <td align="center" width="160px">
      FE · BE 개발
    </td>
    <td align="center" width="160px">
      FE · BE 개발
    </td>
    <td align="center" width="160px">
      FE · BE 개발
    </td>
    <td align="center" width="160px">
      FE · BE 개발
    </td>
  </tr>
</table>

<br/>

# ⚒ 프로젝트 구조(Project Architecture)
<img width="1024" alt="아키텍쳐" src="https://github.com/user-attachments/assets/dc2db2a4-f950-4a82-b91c-7f75a255b465">


# ⚒ 개발 가이드
#### Branch 전략, 커밋 규칙, 네이밍 규칙, 코딩 규칙을 지켜 개발한다.
<br/><br/>

## ✅ Branch 전략
### 💡 GITHUB-FLOW 전략
```
master: 제품 출시 버전을 관리하는 메인 브랜치
{branch_name}: 새로운 기능을 개발하는 브랜치 // 사용자화
```
### 1️⃣ 브랜치 생성
- 새로운 브랜치는 항상 master 브랜치에서 만든다.
- 브랜치 이름을 통해 의도를 명확하게 드러내야 한다. ex) ```feature-login```
  - 브랜치 이름에는 소문자를 사용하고, 단어 사이는 하이픈('-')으로 구분한다.

### 2️⃣ 개발, 커밋, 푸쉬
- 커밋메시지를 명확하게 작성한다.
- 원격지 브랜치로 수시로 push 한다.
- 항상 원격지에 자신이 하고 있는 일들을 올려 다른 사람들도 확인할 수 있도록 해준다

### 3️⃣ PR(Pull Request) 생성
- 피드백이나 도움이 필요할 때, 그리고 merge 준비가 완료되었을 때는 pull request를 생성한다.
  - pull request는 코드 리뷰를 도와주는 시스템
  - merge 준비가 완료되었다면 master 브랜치로 반영을 요구

### 4️⃣ 코드 리뷰
- Pull-Request가 master 브랜치 쪽에 합쳐진다면 곧장 라이브 서버에 배포되는 것과 다름 없으므로, 상세한 리뷰와 토의가 이루어져야 한다.

### 5️⃣ 테스트
- 리뷰와 토의가 끝났다면 해당 내용을 라이브 서버(혹은 테스트 환경)에 배포해본다.
- 배포 시 문제가 발생한다면 곧장 master 브랜치의 내용을 다시 배포하여 초기화 시킨다.

### 6️⃣ 배포
- 라이브 서버(혹은 테스트 환경)에 배포했음에도 문제가 발견되지 않았다면 그대로 master 브랜치에 푸시를 하고, 즉시 배포를 진행한다.
- master로 merge되고 push 되었을 때는, 즉시 배포되어야 한다.
  - master로 merge가 일어나면 자동으로 배포가 되도록 설정해놓는다. (CI / CD)
  - master 브랜치는 항상 최신 브랜치이다.
 
<br/>

## ✅ 커밋 규칙(Commit Convention)
### 1️⃣ 커밋 메세지(Commit Message)
```
feat: 새로운 기능 추가
fix: 버그 수정
docs: 문서 수정
style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
refactor: 코드 리팩토링
test: 테스트 코드, 리팩토링 테스트 코드 추가
chore: production code와 무관한 부분 (.gitignore, build.gradle)
comment: 주석 추가 및 변경
remove: 파일, 폴더 삭제
rename: 파일, 폴더명 수정
```

### 2️⃣ 커밋 메세지 예시(Commit Message Example)
```
feat: "로그인 기능 구현"
```

### 3️⃣ PR 예시(Pull Request Example)
- **PR 제목** : [날짜] [이름] [기능] [작업 내용] <br/>
```
ex) 240919 홍길동 로그인 기능 추가
240919 홍길동 목표 자산 조회 기능 css 수정
```

<br/>


## ✅ 네이밍 규칙(Naming Convention)
### 1️⃣ 공통
- 단일 글자로 이름을 짓지 않고 이름을 통해 쓰임새를 알 수 있도록 한다.
- 줄임말을 사용하지 않는다.

### 2️⃣ 패키지(Package) 
- 패키지 이름은 소문자로 표기한다.

### 3️⃣ 클래스(Class)
- 클래스 이름은 upperCamelCase로 표기한다.
- 클래스 이름은 명사를 사용한다.
- 테스트 클래스 이름은 항상 'Test'로 끝낸다.
  
### 4️⃣ 인터페이스(Interface)
- 인터페이스 이름은 upperCamelCase로 표기한다.
- 인터페이스 이름은 명사 또는 형용사를 사용한다.
- 테스트 클래스 이름은 항상 'Test'로 끝낸다.
  
### 5️⃣ 메서드(Method)
- 메서드 이름은 lowerCamelCase로 표기한다.
- 메서드 이름은 동사/전치사로 시작한다.

### 6️⃣ 상수(Constant)
- 상수는 대문자 및 언더스코어로 표기한다.
 
### 7️⃣ 변수(Variable)
- 변수명은 upperCamelCase로 표기한다.
- 임시 변수 외에는 한글자 이름을 사용하지 않는다.
  - 임시 변수 : 반복문이나 인덱스, 람다 표현식의 파라미터와 같은 짧은 범위의 변수
    
<br/>

## ✅ 코딩 규칙(Coding Convention)
### 1️⃣ 들여쓰기(Indent)
  - 탭의 크기는 4개의 스페이스를 사용한다.
   
### 2️⃣ 중괄호(Braces)
- 중괄호는 클래스, 메서드, 제어문의 블럭을 구분한다.
- 중괄호의 선언은 K&R 스타일을 적용한다.
   - 줄의 마지막에서 시작 중괄호 '{' 를 열고 블럭을 마친후 새줄 삽입 후 '}' 작성
- else, catch, finally, while은 닫는 중괄호와 같은 줄에 선언한다.
  - while은 do-while문을 나타내는 것
- 단, 빈 블럭이라면 새줄 없이 중괄호를 닫는 것을 허용한다.
- 조건문, 반복문에는 중괄호를 필수적으로 사용한다.

### 3️⃣ 선언(Declarations)
- 배열에서 대괄호는 타입 뒤에 선언한다. 절대 변수명 뒤에 선언하지 않는다.
- Long 타입의 값에는 마지막에 'L'로 끝낸다.

<br/><br/><br/><br/><br/><br/>
<hr/>
<br/>

### 참고 자료

https://hyunjun.kr/21 <br/>
https://inpa.tistory.com/entry/GIT-⚡️-github-flow-git-flow-📈-브랜치-전략 <br/>
https://velog.io/@hwsa1004/JAVA-Java-Coding-Conventions#naming
