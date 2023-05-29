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

- [x]  사용자가 커스텀 확장자를 입력할 때, 입력한 확장자가 이미 고정 확장자 리스트에 존재하는 경우
   - 해당 확장자를 고정 확장자로 추가합니다.
   - 이를 통해 사용자가 고정 확장자를 입력하는 경우에도 자연스럽게 처리할 수 있습니다.


- [x]  사용자가 확장자 이름을 수정해서 첨부할 경우를 대비하여 위/변조 체크를 수행합니다.
   - 확장자가 없는 파일도 검사가 가능합니다.
   - Apache Tika를 이용하여 MIME Type 체크를 수행합니다.
   - [Complete MIME Types List - FreeFormatter.com](https://www.freeformatter.com/mime-types-list.html)

- [x] 저장 시 랜덤 한 파일명으로 치환하고 제공받은 파일명은 별도로 보관합니다.

- [x] 저장 공간을 이용한 서버 공격을 막기 위하여 업로드 용량 파일 크기를 제한합니다.

- [x] 정규화를 통해 확장자 및 파일 이름을 제한합니다.
  - 고정, 커스텀 확장자 모두 정규화 검사를 수행합니다.
  - NULL Byte Injection을 막기 위한 검사를 수행합니다. (shell.sh%00.png)
  - URL Encoding을 이용한 확장자 차단 우회를 방지합니다. (.bat -> .ba%74)

- [x] 업로드 파일의 파일 시그니처(File Magic Number)를 검사합니다.
  - [List_of_file_signatures - Wiki](https://en.wikipedia.org/wiki/List_of_file_signatures)
  - 쉘 스크립트 - "#!", EXE 실행 파일 - "MZ" 같이 필수로 막아야하는 확장자인 경우 시그니처 검사를 진행합니다.

> 🤔 추후 방향성 
> 
> 1. 반대로 업로드 가능 확장자를 체크하는 방식으로 변경
> 
> 
> 2. image, video, text, audio 유형별로 차단
>
> 
> 3. 사용자가 접근할 수 없도록 별도 서버에 업로드 파일 저장
> 
> 
> 4. 파일이 업로드되는 디렉토리의 실행 권한 제거
> 
> 
> 5. 업로드된 실제 파일의 확장자는 제거하고 해당 확장자를 따로 저장 관리
> 
> 
> 6. 악성 파일 검사 API를 사용

## ERD
![image](https://github.com/PGRRR/block-file-extensions/assets/82517133/ac3b0d26-1d6a-4224-8b07-6c9732fbead8)

## 화면 구성
![image](https://github.com/PGRRR/block-file-extensions/assets/82517133/91232ca6-7126-4fca-a532-e1e300c7cc6c)