# 📁 파일 확장자 차단 과제


- 어떤 파일들은 첨부 시 보안에 문제가 될 수 있습니다.
- 특히 exe, sh 등의 실행 파일이 존재할 경우 서버에 올려서 실행이 될 수 있는 위험이 있습니다.
- 때문에 파일 확장자 차단을 하게 되었습니다.

## 📍 요건

- [x]  고정 확장자는 차단을 자주 하는 확장자를 리스트이며, default는 unCheck 되어 있습니다.
- [x]  고정 확장자를 check or uncheck를 할 경우 db에 저장됩니다.
   - 새로고침시 유지되어야 합니다.
   - 아래쪽 커스텀 확장자에는 표현되지 않으니 유의해주세요.
- [x]  확장자 최대 입력 길이는 20자리
- [x]  추가버튼 클릭 시 db 저장되며, 아래쪽 영역에 표현됩니다.
- [x]  커스텀 확장자는 최대 200개까지 추가가 가능
- [x]  확장자 옆 X를 클릭 시 db에서 삭제
- [x]  커스텀 확장자 중복 체크
   - 커스텀 확장자 sh를 추가한 후 다시 sh를 추가했을 때 고려하여 개발

## 📍 위 요건 이외에 어떤 점을 고려했는지 적어주세요.

- [x]  이미 고정 확장자 리스트에 있는 확장자를 커스텀 확장자로 입력시 고정 확장자에 추가
- [x]  사용자가 차단된 확장자 이름을 수정해서 첨부할 경우 위/변조 체크
   - 확장자가 없는 파일 검사
   - Apache Tika를 이용하여 MIME Type 체크
   - ~~쉘 스크립트 파일 #!/bin/ 검사~~
   - [Complete MIME Types List - FreeFormatter.com](https://www.freeformatter.com/mime-types-list.html)
- [x] 정규화를 통한 커스텀 확장자명 제한
  - 고정 확장자도 정규화 검사정

> 🤔 추후 방향성 
> 
> 반대로 업로드 가능 확장자를 체크하는 방식으로 변경
> 
> image, video, text, audio 유형별로 차단

## ERD
![image](https://github.com/PGRRR/block-file-extensions/assets/82517133/ac3b0d26-1d6a-4224-8b07-6c9732fbead8)

## 화면 구성
![image](https://github.com/PGRRR/block-file-extensions/assets/82517133/91232ca6-7126-4fca-a532-e1e300c7cc6c)