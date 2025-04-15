    document.addEventListener("DOMContentLoaded", function () {
    const modal = document.querySelector(".modal-overlay");
    const closeBtn = document.querySelector(".close-btn");
    const closeIcon = document.querySelector(".modal-close");

  // 쿠폰번호(td) 클릭 시 모달 열기
    document.querySelectorAll(".coupon-number").forEach(function (el) {
    el.addEventListener("click", function () {
    modal.style.display = "flex";

      // ✅ 여기에 쿠폰번호를 가져와서 출력하거나 ajax로 정보 가져오는 코드 추가 가능
    const couponNumber = el.dataset.coupon;
    console.log("클릭된 쿠폰번호:", couponNumber);
    });
});

  // 모달 닫기 (닫기 버튼 또는 X)
function closeModal() {
    modal.style.display = "none";
}

closeBtn.addEventListener("click", closeModal);
closeIcon.addEventListener("click", closeModal);

  // 바깥 클릭 시 모달 닫기
modal.addEventListener("click", function (e) {
    if (e.target === modal) {
    closeModal();
    }
});

  // 쿠폰 등록 모달 관련 코드 추가
const registerModal = document.querySelector("#registerModal");
const openRegisterModalBtn = document.querySelector("#openModalBtn");
const registerCloseBtn = document.querySelector("#registerCloseBtn");
const registerCloseBtn2 = document.querySelector("#registerCloseBtn2");

  // 쿠폰 등록 모달 열기
    openRegisterModalBtn.addEventListener("click", function () {
    registerModal.style.display = "flex";
});

  // 쿠폰 등록 모달 닫기
    registerCloseBtn.addEventListener("click", function () {
    registerModal.style.display = "none";
});

    registerCloseBtn2.addEventListener("click", function () {
    registerModal.style.display = "none";
});

  // 날짜 선택 / 발급일로부터 선택 토글
const calendarInputs = document.querySelector("#calendarInputs");
const daysInput = document.querySelector("#daysInput");
const periodRadios = document.querySelectorAll('input[name="periodType"]');

periodRadios.forEach(function (radio) {
    radio.addEventListener("change", function () {
    if (radio.value === "calendar") {
        calendarInputs.style.display = "block";
        daysInput.style.display = "none";
    } else if (radio.value === "days") {
        calendarInputs.style.display = "none";
        daysInput.style.display = "block";
    }
    });
});
});
