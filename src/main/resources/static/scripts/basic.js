
    let targetId;

    // 미리 작성된 영역 - 수정하지 않으셔도 됩니다.
    // 사용자가 내용을 올바르게 입력하였는지 확인합니다.
    function isValidContents(contents) {
        if (contents == '') {
            alert('내용을 입력해주세요');
            return false;
        }
        if (contents.trim().length > 140) { //trim은 앞뒤로 공백이 있으면 잘라주는 녀석임// 공백확인을 위해
            alert('공백 포함 140자 이하로 입력해주세요');
            return false;
        }
        return true;
    }

    // 익명의 username을 만듭니다.
    // function genRandomName(length) {
    //     let result = '';
    //     let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    //     let charactersLength = characters.length;
    //     for (let i = 0; i < length; i++) {
    //         let number = Math.random() * charactersLength;
    //         let index = Math.floor(number);
    //         result += characters.charAt(index);
    //     }
    //     return result;
    // }

    // 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
    // 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
    function editPost(id) {
        showEdits(id);
        let contents = $(`#${id}-contents`).text().trim();
        $(`#${id}-textarea`).val(contents);
    }

    function showEdits(id) {
        $(`#${id}-editarea`).show();
        $(`#${id}-submit`).show();
        $(`#${id}-delete`).show();

        $(`#${id}-contents`).hide();
        $(`#${id}-edit`).hide();
    }

    function hideEdits(id) {
        $(`#${id}-editarea`).hide();
        $(`#${id}-submit`).hide();
        $(`#${id}-delete`).hide();

        $(`#${id}-contents`).show();
        $(`#${id}-edit`).show();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 여기서부터 코드를 작성해주시면 됩니다.

    $(document).ready(function () {
        // HTML 문서를 로드할 때마다 실행합니다.
        getMessages();
    })

    // 메모를 불러와서 보여줍니다.
    function getMessages() {
        // 1. 기존 메모 내용을 지웁니다.
        $('#cards-box').empty();
        // 2. 메모 목록을 불러와서 HTML로 붙입니다.
        $.ajax({
            type: 'GET',
            url: '/api/blogs',
            data: {},
            success: function (response) {
                for (let i= 0; i<response.length;i++){
                    let blog = response[i];
                    let id = blog.id;
                    let contents = blog.contents;
                    let title = blog.title;
                    let userName = blog.username
                    let modifiedAt = blog.modifiedAt;
                    addHTML(id, contents, modifiedAt,title,userName);

                }
            }
        })
    }
    // 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
    function addHTML(id, contents, modifiedAt,title,userName) {
        let tempHtml = makeMessage(id, userName, title, contents, modifiedAt);
        $('#cards-box').append(tempHtml);


    }

    // 1. HTML 태그를 만듭니다.
    function makeMessage(id, userName, title, contents, modifiedAt){
        return  `
                      <div class="card">
                                <!-- date/username 영역 // 제목영역 추가 ! -->
                            <div class="metadata">
                                <div id="${id}-title" class="title">
                                ${title}
                                </div>
                            <div class="date">
                                ${modifiedAt}
                            </div>
                            <div id="${id}-username" class="username">
                                ${userName}
                            </div>
                        </div>
                            <!-- contents 조회/수정 영역-->
                            <div class="contents">
                                <div id="${id}-contents" class="text">
                                    ${contents}
                                </div>
                                <div id="${id}-editarea" class="edit">
                                    <textarea id="${id}-textarea" class="te-edit" name="" id="" cols="30" rows="5"></textarea>
                                </div>
                            </div>
                            <!--비밀번호영역-->
                            <div class="footer">
                                <button class="comments" onclick ="location.href= '/api/blogs/${id}'">댓글</button>
                                <img id="${id}-edit" class="icon-start-edit" src="../images/edit.png" alt=""  onclick="editPost('${id}')">
                                    <img id="${id}-delete" class="icon-delete" src="../images/delete.png" alt=""  onclick="deleteOne('${id}')">
                                        <img id="${id}-submit" class="icon-end-edit" src="../images/done.png" alt="" onclick="submitEdit('${id}')">
                            </div>                            
                        </div>
                        `;
    }









    // 메모를 생성합니다.
    function writePost() {
        //제목, 비밀번호 추가
        let title = $('#title').val();

        // 1. 작성한 메모를 불러옵니다.
        let contents = $('#contents').val();
        // 2. 작성한 메모가 올바른지 isValidContents 함수를 통해 확인합니다.
        if (isValidContents(contents) == false) {
            return;
        }
        // 3. genRandomName 함수를 통해 익명의 username을 만듭니다.
        let username = $('#username').val();
        // 4. 전달할 data JSON으로 만듭니다.
        let data = {'username': username,'title': title ,'contents': contents};
        // 5. POST /api/memos 에 data를 전달합니다.
        $.ajax({
            type: "POST",
            url: "/api/blogs",
            contentType: "application/json", // JSON 형식으로 전달함을 알리기
            data: JSON.stringify(data), //JSON.stringify(data)는 JSON 문자열로 변환시켜줌
            success: function (response) {
                alert('메시지가 성공적으로 작성되었습니다.');
                window.location.reload();
            }
        });
    }

    function open_box(){
        $('#write-box').show()
    }
    function close_box(){
        $('#write-box').hide()

    }



    // 메모를 수정합니다.
    function submitEdit(id) {
        let title = $(`#${id}-title`).text().trim();
        // let passwordCheck =$(`#${id}-password2`).val();
        // 1. 작성 대상 메모의 username과 contents 를 확인합니다.
        let contents = $(`#${id}-textarea`).val().trim();
        // 2. 작성한 메모가 올바른지 isValidContents 함수를 통해 확인합니다.
        if (isValidContents(contents) == false) {
            return;
        }

        let data = {'title': title, 'contents': contents};
        // 4. PUT /api/memos/{id} 에 data를 전달합니다.
        $.ajax({
            type: "PUT",
            url: `/api/blogs/${id}`,
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) {
                // alert("수정완료");
                // window.location.reload();
                // 이미 서버의 서비스 클래스에서 비밀번호 대조 검사가 완료되어져 있기 때문에
                if (response == 1) {        //그 값을 어떻게 받아올지를 생각해주면 된다. controller에서 putMapping이
                    alert("수정완료");   //Long타입으로 선언되어져 있었기 때문에 return을 1또는 0으로 받았지만
                    window.location.reload(); // boolean타입으로 변경을 했을 경우 true 또는 false로도 값을 받을 수 있었을
                } else {                        //것이다.
                    alert("작성자 이외에는 수정할 수 없습니다");
                    window.location.reload();
                }


            }
        });
    }
    // $.ajax( { success : function( 파라미터 )
    // { //ajax 성공시 작업 입력 }, error : function( 파라미터 ) { //error 발생시 내용 입력 } });
    //이런식으로 사용한다고함!





    // 메모를 삭제합니다.
    function deleteOne(id) {
        // 1. DELETE /api/memos/{id} 에 요청해서 메모를 삭제합니다.
        $.ajax({
            type: "DELETE",
            url: `/api/blogs/${id}`,
            success: function (response) {
                if (response===1){
                    alert("삭제완료!");
                    window.location.reload();
                } else {
                    alert("작성자 이외에는 삭제가 불가능합니다");
                }
            }
        })
    }
