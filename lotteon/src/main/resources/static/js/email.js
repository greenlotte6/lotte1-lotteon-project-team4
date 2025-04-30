
function updateDomain() {
    const select = document.getElementById('domainSelect');
    const domainInput = document.getElementById('emailaddr');

    if (select.value === "direct") {
      domainInput.value = "";
      domainInput.disabled = false;
      domainInput.focus();
    } else {
      domainInput.value = select.value;
      domainInput.disabled = true;
    }
  }

