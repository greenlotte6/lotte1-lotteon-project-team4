function formatDate(date) {
    return date.toISOString().split('T')[0];
  }

  function createMonthButtons() {
  const container = document.getElementById('monthButtons');
  container.innerHTML = '';

  const now = new Date();
  const buttonList = [];

  for (let i = 4; i >= 0; i--) { 
      const tempDate = new Date(now.getFullYear(), now.getMonth() - i, 1);
      const month = tempDate.getMonth() + 1;
      const year = tempDate.getFullYear();

      const btn = document.createElement('button');
      btn.textContent = `${month}월`;
      btn.onclick = () => setMonthRange(year, month);
      buttonList.push(btn);
  }

 
  buttonList.forEach(btn => container.appendChild(btn));
  }
          function setMonthRange(year, month) {
          const startDate = new Date(year, month - 1, 1);
          const endDate = new Date(year, month, 0);
      
          document.getElementById('startDate').value = formatDate(startDate);
          document.getElementById('endDate').value = formatDate(endDate);
          }
      
          function setFixedRange(days) {
          const endDate = new Date();
          const startDate = new Date();
          startDate.setDate(endDate.getDate() - days + 1);
      
          document.getElementById('startDate').value = formatDate(startDate);
          document.getElementById('endDate').value = formatDate(endDate);
          }

  function query() {
    const start = document.getElementById('startDate').value;
    const end = document.getElementById('endDate').value;
    alert(`조회 기간: ${start} ~ ${end}`);
  }

  // 초기 실행
  createMonthButtons();