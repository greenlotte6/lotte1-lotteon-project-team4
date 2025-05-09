
document.addEventListener('DOMContentLoaded', function () {
    const form = document.forms['formRegister'];

    const reUid = /^[a-z]+[a-z0-9]{4,19}$/g;
    const rePass = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$/;

    let isUidOk = false;
    let isPassOk = false;

    // 아이디 중복확인
    const btnCheckUid = document.getElementById('btnCheckUid');
    const uidResult = document.querySelector('.uidResult');

    btnCheckUid.onclick = async function () {
        const aid = form.aid.value.trim();

        if (!aid.match(reUid)) {
            uidResult.innerText = '아이디 형식이 올바르지 않습니다.';
            uidResult.style.color = 'red';
            isUidOk = false;
            return;
        }

        const res = await fetch(`/seller/aid/${aid}`);
        const data = await res.json();

        if (data.count > 0) {
            uidResult.innerText = '이미 사용중인 아이디입니다.';
            uidResult.style.color = 'red';
            isUidOk = false;
        } else {
            uidResult.innerText = '사용 가능한 아이디입니다.';
            uidResult.style.color = 'green';
            isUidOk = true;
        }
    };

    // 비밀번호 확인
    const passResult = document.querySelector('.passResult');
    form.password_confirm.addEventListener('focusout', function () {
        const pass1 = form.password.value;
        const pass2 = form.password_confirm.value;

        if (!pass1.match(rePass)) {
            passResult.innerText = '비밀번호는 영문, 숫자, 특수문자 조합 8~16자 입력';
            passResult.style.color = 'red';
            isPassOk = false;
            return;
        }

        if (pass1 !== pass2) {
            passResult.innerText = '비밀번호가 일치하지 않습니다.';
            passResult.style.color = 'red';
            isPassOk = false;
        } else {
            passResult.innerText = '비밀번호가 일치합니다.';
            passResult.style.color = 'green';
            isPassOk = true;
        }
    });

    // 최종 제출 이벤트
    form.onsubmit = function (e) {
        const aid = form.aid.value.trim();
        const password = form.password.value.trim();
        const passwordConfirm = form.password_confirm.value.trim();
        const company = form.company.value.trim();
        const ceo = form.ceo.value.trim();
        const biz_num = form.biz_num.value.trim();
        const osn = form.osn.value.trim();
        const number = form.number.value.trim();
        const fax = form.fax.value.trim();
        const addr1 = form.addr1.value.trim();
        const addr2 = form.addr2.value.trim();

        // 아이디 검사
        if (!aid || !isUidOk) {
            alert("아이디를 정확히 입력하고 중복확인 해주세요.");
            form.aid.focus();
            e.preventDefault();
            return false;
        }

        // 비밀번호 검사
        if (!password.match(rePass) || !isPassOk) {
            alert("비밀번호를 정확히 입력하고 확인해주세요.");
            form.password.focus();
            e.preventDefault();
            return false;
        }

        // 나머지 항목은 입력 여부만 검사
        if (!company) {
            alert("회사명을 입력해주세요.");
            form.company.focus();
            e.preventDefault();
            return false;
        }

        if (!ceo) {
            alert("대표명을 입력해주세요.");
            form.ceo.focus();
            e.preventDefault();
            return false;
        }

        if (!biz_num) {
            alert("사업자등록번호를 입력해주세요.");
            form.biz_num.focus();
            e.preventDefault();
            return false;
        }

        if (!osn) {
            alert("통신판매업번호를 입력해주세요.");
            form.osn.focus();
            e.preventDefault();
            return false;
        }

        if (!number) {
            alert("전화번호를 입력해주세요.");
            form.number.focus();
            e.preventDefault();
            return false;
        }

        if (!addr1 || !addr2) {
            alert("주소를 모두 입력해주세요.");
            form.addr1.focus();
            e.preventDefault();
            return false;
        }

        // 팩스번호는 선택사항으로 비어 있어도 통과

        // 모든 조건을 만족하면 회원가입 완료 알림
        alert("회원가입이 완료되었습니다!");

        return true; // 정상 제출
    };
});

