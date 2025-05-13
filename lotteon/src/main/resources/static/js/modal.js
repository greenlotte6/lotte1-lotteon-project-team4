document.addEventListener("DOMContentLoaded", () => {
    window.openModal = function (url) {
        fetch(url)
            .then(res => {
                if (!res.ok) throw new Error("404 Not Found: " + url);
                return res.text();
            })
            .then(html => {
                const modalBody = document.getElementById("modalBody");
                const modalOverlay = document.getElementById("modalOverlay");

                if (!modalBody || !modalOverlay) {
                    console.error("모달 요소가 없습니다.");
                    return;
                }

                modalBody.innerHTML = html;
                modalOverlay.style.display = "flex";
                document.body.style.overflow = "hidden";
            })
            .catch(err => {
                alert("모달을 불러오지 못했습니다.\n" + err.message);
                console.error(err);
            });
    };

    window.closeModal = function () {
        const modalBody = document.getElementById("modalBody");
        const modalOverlay = document.getElementById("modalOverlay");

        if (modalBody && modalOverlay) {
            modalBody.innerHTML = "";
            modalOverlay.style.display = "none";
            document.body.style.overflow = "";
        }
    };

    window.confirmBuy = function () {
        alert("구매가 확정되었습니다!");
        closeModal();
    };
});
