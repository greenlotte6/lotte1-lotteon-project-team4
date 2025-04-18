
document.addEventListener('DOMContentLoaded', function () {
    const bar = document.querySelector('.bar');
    const barcate = document.querySelector('.bar-cate');
    const fullview = document.querySelector('.full-view');
    const fullmenu = document.querySelector('.full-menu');
    const all = document.getElementsByClassName('all')[0];
    const rank = document.querySelector('.rank');
    const ranknav = document.getElementById('rank-nav');

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

    /*
    function showNextItem() {
        currentIndex = (currentIndex + 1) % rankitem.length;
        ranklist.style.transform = `translateY(-${currentIndex * 100}%)`;
    }

    setInterval(showNextItem, 3000);
    */

    const selectAll = document.getElementById("select-all");

    selectAll.addEventListener("change", function () {
        const checkboxes = document.querySelectorAll(".item-check");
        checkboxes.forEach(chk => {
            chk.checked = selectAll.checked;
        });
        updateSummary();
    });

    // 개별 체크박스 상태에 따라 전체 선택 체크 여부 제어
    const updateSelectAllState = () => {
        const checkboxes = document.querySelectorAll(".item-check");
        const checked = document.querySelectorAll(".item-check:checked");
        selectAll.checked = checkboxes.length === checked.length;
    };

    document.querySelectorAll(".item-check").forEach(chk => {
        chk.addEventListener("change", () => {
            updateSelectAllState();
            updateSummary();
        });
    });

});