 function copyText() {
            var textToCopy = document.querySelector('.copyable').textContent;
            var tempInput = document.createElement("input");
            tempInput.value = textToCopy;
            document.body.appendChild(tempInput);
            tempInput.select();
            document.execCommand("copy");
            document.body.removeChild(tempInput);
            alert("Đã sao chép: " + textToCopy);
        }
