function openModal(url) {
    fetch(url)
        .then(res => res.text())
        .then(html => {
            document.getElementById("modalBody").innerHTML = html;
            document.getElementById("modalOverlay").style.display = "flex";
            document.body.style.overflow = 'hidden'; // 💡 배경 스크롤 잠금
        });
}

function closeModal() {
    document.getElementById("modalBody").innerHTML = "";
    document.getElementById("modalOverlay").style.display = "none";
    document.body.style.overflow = ''; // 💡 스크롤 원상복구
}

function confirmBuy() {
    alert("구매가 확정되었습니다!");
    closeModal();
}