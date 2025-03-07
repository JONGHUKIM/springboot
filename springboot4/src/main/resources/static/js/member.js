function checkUsername() {
    let username = document.getElementById("username").value;
    fetch(`/member/check-username?username=` + username)
        .then(response => response.json())
        .then(data => {
            let message = document.getElementById("usernameCheckMessage");

            if (data) {
                message.style.color = "green";
                message.textContent = "사용 가능한 아이디입니다.";
                document.getElementById("usernameValid").value = "true";
            } else {
                message.style.color = "red";
                message.textContent = "이미 사용중인 아이디입니다.";
                document.getElementById("usernameValid").value = "false";
            }
            updateSubmitButton();
        });
}

function checkEmail() {
    let email = document.getElementById("email").value;
    let message = document.getElementById("emailCheckMessage");

    fetch(`/member/check-email?email=` + encodeURIComponent(email))
        .then(response => response.json())
        .then(data => {
            console.log("Email Check Response:", data);
            
            if (data === true) {
                message.style.color = "green";
                message.textContent = "사용 가능한 이메일입니다.";
                document.getElementById("emailValid").value = "true";
            } else {
                message.style.color = "red";
                message.textContent = "이미 사용 중인 이메일입니다.";
                document.getElementById("emailValid").value = "false";
            }
            updateSubmitButton();
        })
        .catch(error => {
            console.error("Error checking email:", error);
        });
}

function updateSubmitButton() {
    let usernameValid = document.getElementById("usernameValid").value;
    let emailValid = document.getElementById("emailValid").value;
    let submitButton = document.getElementById("submitButton");

    if (usernameValid === "true" && emailValid === "true") {
        submitButton.disabled = false;
    } else {
        submitButton.disabled = true;
    }
}
