
document.addEventListener('DOMContentLoaded', function () {
    const bar = document.querySelector('.bar');
    const barcate = document.querySelector('.bar-cate');
    const fullview = document.querySelector('.full-view');
    const fullmenu = document.querySelector('.full-menu');
    const all = document.getElementsByClassName('all')[0];
    const rank = document.querySelector('.rank');
    const ranknav = document.getElementById('rank-nav');
    const form = document.forms['formRegister'];


    rank.addEventListener('click', () => {
        ranknav.classList.toggle('rank-show');
    });

    rank.addEventListener('mouseleave', () => {
        ranknav.classList.remove('rank-show');
    });
    /*
    const rankitem = document.getElementsByClassName('rank-item')[0];
    let currentIndex = 0;
    */

    bar.addEventListener('click', () => {
        barcate.classList.toggle('show');
    });

    bar.addEventListener('mouseleave', () => {
        barcate.classList.remove('show');
    });

    fullview.addEventListener('click', () => {
        fullmenu.classList.toggle('menu');
    });

    all.addEventListener('mouseleave', () => {
        fullmenu.classList.remove('menu');
    });

    form.onsubmit = function (e) {
        // 유효성 검사 로직...
        e.preventDefault(); // 실패 시 페이지 넘어가는 것 방지
        return false;
    };

    /*
    function showNextItem() {
        currentIndex = (currentIndex + 1) % rankitem.length;
        ranklist.style.transform = `translateY(-${currentIndex * 100}%)`;
    }

    setInterval(showNextItem, 3000);
    */
});

//유효성 검사에 사용할 정규표현식
const reUid   = /^[a-z]+[a-z0-9]{4,19}$/g;
const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
const reName  = /^[가-힣]{2,10}$/
const reNick  = /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

document.addEventListener('DOMContentLoaded', function(){

    // 유효성 검사에 사용할 상태 변수
    let isUidOk = false;
    let isPassOk = false;
    let isNameOk = false;
    let isNickOk = false;
    let isEmailOk = false;
    let isHpOk = false;

    // 1.아이디 유효성 검사(중복체크 포함)
    const btnCheckUid = document.getElementById('btnCheckUid');
    const uidResult = document.getElementsByClassName('uidResult')[0];

    btnCheckUid.onclick = function(){
        const value = formRegister.uid.value;

        // 아이디 유효성 검사
        if(!value.match(reUid)){
            uidResult.innerText = '아이디 형식에 맞지 않습니다.';
            uidResult.style.color = 'red';
            isUidOk = false;
            return; // 처리 종료
        }

        // 아이디 중복 체크
        fetch(`/user/uid/${value}`)
            .then(response => response.json())
            .then((data)=>{
                console.log(data);

                if(data.count > 0){
                    // 이미 사용중인 아이디
                    uidResult.innerText = '이미 사용중인 아이디 입니다.';
                    uidResult.style.color = 'red';
                    isUidOk = false;
                }else{
                    // 사용 가능한 아이디
                    uidResult.innerText = '사용 가능한 아이디 입니다.';
                    uidResult.style.color = 'green';
                    isUidOk = true;
                }
            })
            .catch((err)=>{
                console.log(err);
            });
    }

    // 2.비밀번호 유효성 검사
    const passResult = document.getElementsByClassName('passResult')[0];

    formRegister.password_confirm.addEventListener('focusout', function () {
        const value1 = formRegister.password.value;
        const value2 = formRegister.password_confirm.value;

        if(!value1.match(rePass)){
            passResult.innerText = '비밀번호는 숫자, 소문자, 대문자, 특수문자 조합 8자리';
            passResult.style.color = 'red';
            isPassOk = false;
            return;
        }

        if(value1 === value2){
            passResult.innerText = '사용 가능한 비밀번호 입니다.';
            passResult.style.color = 'green';
            isPassOk = true;
        }else{
            passResult.innerText = '비밀번호가 일치하지 않습니다.';
            passResult.style.color = 'red';
            isPassOk = false;
        }
    });

    // 3.이름 유효성 검사
    formRegister.uname.addEventListener('focusout', function(){
        const value = this.value;

        if(!value.match(reName)){
            nameResult.innerText = '이름이 유효하지 않습니다.';
            nameResult.style.color = 'red';
            isNameOk = false;
        }else{
            nameResult.innerText = '';
            isNameOk = true;
        }
    });

    // 4.별명 유효성 검사(중복체크 포함)
    // const btnCheckNick = document.getElementById('btnCheckNick');
    //const nickResult = document.getElementsByClassName('nickResult')[0];

    //btnCheckNick.onclick = async function(){

    //const value = formRegister.nick.value;

    // if(!value.match(reNick)){
    //     nickResult.innerText = '유효하지 않은 별명 입니다.';
    //     nickResult.style.color = 'red';
    //     isNickOk = false;
    //    return;
    // }

    // try {
    //   const response = await fetch(`/user/nick/${value}`);
    //  const data = await response.json();
    //  console.log(data);

    //   if(data.count > 0){
    //     nickResult.innerText = '이미 사용중인 별명 입니다.';
    //     nickResult.style.color = 'red';
    //      isNickOk = false;
    //  }else{
    //       nickResult.innerText = '사용 가능한 별명 입니다.';
    //     nickResult.style.color = 'green';
    //      isNickOk = true;
    //  }
    //  }catch(err){
    //       console.log(err);
    ///   }
    // };

    // 5. 이메일 유효성 검사(중복/인증처리 포함)
    const btnSendEmail = document.getElementById('btnSendEmail');
    const emailResult = document.querySelector('.emailResult');
    const auth = document.querySelector('.auth');
    let preventDoubleClick = false;

    btnSendEmail.onclick = async function(){

        const value = formRegister.email.value;

        // 이중 클릭 방지
        if(preventDoubleClick){
            return;
        }

        if(!value.match(reEmail)){
            emailResult.innerText = '이메일이 유효하지 않습니다.';
            emailResult.style.color = 'red';
            isEmailOk = false;
            return;
        }

        preventDoubleClick = true;
        const response = await fetch(`/user/email/${value}`);
        const data = await response.json();

        if(data.count > 0){
            emailResult.innerText = '이미 사용중인 이메일 입니다.';
            emailResult.style.color = 'red';
            isEmailOk = false;
        }else {
            // 인증번호 입력 필드 출력
            auth.style.display = 'block';
        }
    };

    const btnAuthEmail = document.getElementById('btnAuthEmail');

    btnAuthEmail.onclick = async function(){

        const value = formRegister.auth.value;

        // JSON 데이터 생성
        const jsonData = {
            "authCode": value
        };

        // 서버 전송
        const response = await fetch('/user/email/auth', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(jsonData)
        });

        const data = await response.json();
        console.log(data);

        if(data){
            emailResult.innerText = '이메일이 인증 되었습니다.';
            emailResult.style.color = 'green';
            isEmailOk = true;
        }else{
            emailResult.innerText = '유효한 인증코드가 아닙니다.';
            emailResult.style.color = 'red';
            isEmailOk = false;
        }

    } // btnAuthEmail.onclick end

    // 6.휴대폰 유효성 검사(중복체크 포함)
    const hpResult = document.getElementsByClassName('hpResult')[0];

    formRegister.hp.addEventListener('focusout', async function(){

        const value = this.value;

        if(!value.match(reHp)){
            hpResult.innerText = '휴대폰번호가 유효하지 않습니다.(- 포함)';
            hpResult.style.color = 'red';
            isHpOk = false;
            return;
        }

        // 휴대폰 중복체크
        const response = await fetch(`/user/hp/${value}`);
        const data = await response.json();

        if(data.count > 0){
            hpResult.innerText = '이미 사용중인 휴대폰번호 입니다.';
            hpResult.style.color = 'red';
            isHpOk = false;
        }else{
            hpResult.innerText = '사용 가능한 휴대폰번호 입니다.';
            hpResult.style.color = 'green';
            isHpOk = true;
        }
    });


    // 최종 폼 전송 이벤트
    formRegister.onsubmit = function(e){
        const uid = formRegister.uid.value.trim();
        const pass = formRegister.password.value.trim();
        const passConfirm = formRegister.password_confirm.value.trim();
        const uname = formRegister.uname.value.trim();
        const nick = formRegister.nick?.value?.trim() || "";
        const email = formRegister.email.value.trim();
        const hp = formRegister.hp.value.trim();
        const addr1 = formRegister.addr1.value.trim();
        const addr2 = formRegister.addr2.value.trim();

        // 1. 아이디
        if (!uid || !isUidOk) {
            alert("아이디를 정확히 입력하고 중복 확인을 해주세요.");
            formRegister.uid.focus();
            e.preventDefault();
            return false;
        }

        // 2. 비밀번호
        if (!pass.match(rePass)) {
            alert("비밀번호 형식을 확인해주세요.");
            formRegister.password.focus();
            e.preventDefault();
            return false;
        }

        if (pass !== passConfirm) {
            alert("비밀번호가 일치하지 않습니다.");
            formRegister.password_confirm.focus();
            e.preventDefault();
            return false;
        }

        // 3. 이름
        if (!uname.match(reName)) {
            alert("이름 형식을 확인해주세요.");
            formRegister.uname.focus();
            e.preventDefault();
            return false;
        }

        // 4. 별명
        //if (formRegister.nick && !nick.match(reNick)) {
        //  alert("별명 형식이 올바르지 않습니다.");
        //formRegister.nick.focus();
        //e.preventDefault();
        //return false;
        //}

        //if (!isNickOk) {
        //  alert("별명 중복 확인을 해주세요.");
        // e.preventDefault();
        // return false;
        // }

        // 5. 이메일
        if (!email.match(reEmail)) {
            alert("올바른 이메일 형식을 입력해 주세요.");
            formRegister.email.focus();
            e.preventDefault();
            return false;
        }

        if (!isEmailOk) {
            alert("이메일 인증을 완료해 주세요.");
            e.preventDefault();
            return false;
        }

        // 6. 휴대폰
        if (!hp.match(reHp)) {
            alert("올바른 휴대폰 번호 형식을 입력해 주세요.");
            formRegister.hp.focus();
            e.preventDefault();
            return false;
        }

        if (!isHpOk) {
            alert("휴대폰 인증을 완료해 주세요.");
            e.preventDefault();
            return false;
        }

        // 7. 주소
        if (!addr1 || !addr2) {
            alert("주소를 모두 입력해 주세요.");
            formRegister.addr1.focus();
            e.preventDefault();
            return false;
        }

        // 모든 조건 만족
        return true;
    };
});

window.checkHpValidation = async function(hp, resultSpan) {
    if (!hp.match(reHp)) {
        resultSpan.innerText = '휴대폰번호가 유효하지 않습니다.(- 포함)';
        resultSpan.style.color = 'red';
        return false;
    }

    try {
        const response = await fetch(`/user/hp/${hp}`);
        const data = await response.json();

        if (data.count > 0) {
            resultSpan.innerText = '이미 사용중인 휴대폰번호 입니다.';
            resultSpan.style.color = 'red';
            return false;
        } else {
            resultSpan.innerText = '사용 가능한 휴대폰번호 입니다.';
            resultSpan.style.color = 'green';
            return true;
        }
    } catch (e) {
        resultSpan.innerText = '서버 오류로 확인할 수 없습니다.';
        resultSpan.style.color = 'red';
        return false;
    }
}
