function openModal(page) {
    const overlay = document.getElementById('modalOverlay');
    const frame = document.getElementById('modalFrame');
    frame.src = page;
    overlay.style.display = 'flex';
  }

  // 모달 바깥 클릭 시 닫기
  window.addEventListener('click', (e) => {
    const overlay = document.getElementById('modalOverlay');
    if (e.target === overlay) {
      overlay.style.display = 'none';
    }
  });

  // ESC 키로 닫기
  window.addEventListener('keydown', e => {
    if (e.key === 'Escape') {
      document.getElementById('modalOverlay').style.display = 'none';
    }
  });