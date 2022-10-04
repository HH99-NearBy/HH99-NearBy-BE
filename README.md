# WHO

💻 Front-end developer :::

🏆 강태훈 [FE리더]
* 메인페이지 네비게이션
* 헤더
* 글로벌 css
* 메인페이지
* 랭킹 페이지
* createPortal을 이용한  챌린지 디테일 모달
* 챌린징 페이지
* 실시간 채팅/화상기능
* 챌린지 카드
* 챌린지 작성 페이지
* 챌린지 작성, 수정, 삭제
* 챌린지 참여, 취소 
* 챌린지 그래프
* 챌린지 검색 기능
* 무한스크롤
  
🏆 권용준
* 로그인,회원가입 페이지 구현
* 소셜 로그인(카카오) 구현
* 마이페이지 구현
* 닉네임,이미지 수정 구현

## WHEN

2022.08.12 ~ 2022.08.18

### WHAT

Web Service '쓱관
:::좋은습관 개발을 위한 챌린지를 수행할 수 있는 공간을 제공하는 플랫폼 서비스


### 주요 기술 스택
<img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=000000"/>
<img src="https://img.shields.io/badge/React Query-FF4154?style=flat-square&logo=React Query&logoColor=000000"/>
<img src="https://img.shields.io/badge/React Router-CA4245?style=flat-square&logo=React Router&logoColor=000000"/>
<img src="https://img.shields.io/badge/TypeScript-3278C6?style=flat-square&logo=TypeScript&logoColor=ffffff"/>
<img src="https://img.shields.io/badge/styled-components-DB7093?style=flat-square&logo=styled-components&logoColor=ffffff"/>
<img src="https://img.shields.io/badge/Axios-5A29E4?style=flat-square&logo=Axios&logoColor=ffffff"/>
<img src="https://img.shields.io/badge/WebRTC-333333?style=flat-square&logo=WebRTC&logoColor=ffffff"/>
<img src="https://img.shields.io/badge/NGINX-009639?style=flat-square&logo=NGINX&logoColor=ffffff"/>

### 서비스 아키텍쳐

![image (12)](https://user-images.githubusercontent.com/75843118/193716645-745baa08-997b-4e7a-9ee2-344c3f2dbeda.png)

### 주요기능

- 💻 Infinite Scroll
- 💻 CreatePotal
- 💻 Image Upload
- 💻 Image Resizing(Compression)
- 💻 LogIn & SignIn
- 💻 Post CRUD
- 💻 WebSocket Live Chatting
- 💻 WebRTC Live Camera Share

### Trouble Shooting

* 이미지 프리뷰 업로드 및 리사이징, 서버 전송

* 게시글 이미지(AWS S3) 전송 후 게시글 컨텐츠 전송

* 모달창 구현으로 상세 게시글 불러올 때 랜더링 안되는 현상

* 무한 스크롤 게시글 중복 현상

* 자동 스크롤 시 내역이 중복되어 렌더링 되는 현상/ 레이아웃이 깨지면서 스크롤 자체가 동작하지 않는 현상

* 자동 스크롤이 브라우저마다 동작이 다르거나 아예 동작하지 않는 현상

### 패키지

+ 스타일 적용 : styled-components
+ s3 연결 : aws-sdk
+ axios(통신) 설치 : yarn add axios
+ image resizing : yarn add browser-image-compression
+ openvidu를 이용한 webRTC 제어 : openvidu-broswer
+ websocket을 이용한 리얼타임 채팅 : webstomp-client
+ websocket을 지원하지 않는 브라우저를 위한 크로스 브라우징 : sockJs
+ react 상의 페이징 처리 : react-router-dom
+ 챌린지 페이지에서 전체화면 제어 : react-full-screenf


### git commit rules

파일 생성 ✨add : ~~~ 생성에 대한 커밋
파일 수정 ✂modify : ~~~ 수정에 대한 커밋
파일 삭제 🗑delete : ~~~ 삭제에 대한 커밋
