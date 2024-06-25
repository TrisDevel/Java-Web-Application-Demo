// js/notification.js
function showPopup(message) {
    var popup = document.getElementById('notificationPopup');
    var messageElement = document.getElementById('notificationMessage');
    messageElement.textContent = message;
    popup.style.display = "block";
}

function closePopup() {
    document.getElementById('notificationPopup').style.display = "none";
}

// Đóng modal khi nhấn ngoài vùng modal
window.onclick = function(event) {
    if (event.target == document.getElementById('notificationPopup')) {
        closePopup();
    }
}
