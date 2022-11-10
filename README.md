## UPDATE 0001 (2022-11-09 수 13:47)

* README.md 새로 추가
* 앞으로 여기에 작업 내역을 기록함
* Footer 텍스트 수정 (src/main/resources/templates/fragments/footer.html)
* Header 로고 수정 (src/main/resources/templates/fragments/header.html)

## UPDATE 0002 (2022-11-09 수 15:59)

* 재료 등록 버튼 추가, 유효성 검사 기능
* java 파일: controller/IngredientController.java, dto/IngredientFormDTO.java
* html 파일: ingredient/create.html, ingredient/list.html)
* 기타: 회원가입 버튼 위치 조정, css 파일에 유효성 불만족 클래스 추가

## UPDATE 0003 (2022-11-09 수 22:38)

* 재료 삭제 기능 추가 (숨김 처리, 완전 삭제 두 가지)
* 재료, 가맹점, 창고 엔티티에 숨김 속성 (isValid) 추가
* 재료, 가맹점, 창고 목록에 유효한 레코드만 표시
* 에러 페이지 설정
* 페이지별 권한 적절하게 설정
* 기타: 잘못된 'OLD' 표시 제거, 메인 페이지 문구 수정 등

## UPDATE 0004 (2022-11-10 목 10:10)

* 재료 삭제 시 확인 메시지 추가

# GITHUB TEST