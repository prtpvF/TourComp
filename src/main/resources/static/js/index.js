var modal = document.getElementById("myModal");
var openModalBtn = document.getElementById("openModal");
var closeBtn = document.getElementsByClassName("close")[0];

document.addEventListener("DOMContentLoaded", function() {
    var modal = document.getElementById("myModal");
    var openModalBtn = document.getElementById("openModal");
    var closeBtn = document.getElementsByClassName("close")[0];

    // Открываем модальное окно при нажатии на кнопку
    openModalBtn.onclick = function() {
        modal.style.display = "block";
        modal.classList.add("fade-in");
        setTimeout(function() {
            modal.classList.remove("fade-in");
        }, 500);
        setTimeout(function() {
            modal.style.display = "none";
        }, 4000); // Модальное окно исчезнет через 4 секунды
    }

    // Закрываем модальное окно при нажатии на "×"
    closeBtn.onclick = function() {
        modal.classList.add("fade-out");
        setTimeout(function() {
            modal.style.display = "none";
            modal.classList.remove("fade-out");
        }, 500);
    }

    // Закрываем модальное окно при клике вне него
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.classList.add("fade-out");
            setTimeout(function() {
                modal.style.display = "none";
                modal.classList.remove("fade-out");
            }, 500);
        }
    }
});

// Открываем модальное окно при нажатии на кнопку
openModalBtn.onclick = function() {
    modal.style.display = "block";
    modal.classList.add("fade-in");
    setTimeout(function() {
        modal.classList.remove("fade-in");
    }, 500);
    setTimeout(function() {
        modal.style.display = "none";
    }, 4000); // Модальное окно исчезнет через 4 секунды
}

// Закрываем модальное окно при нажатии на "×"
closeBtn.onclick = function() {
    modal.classList.add("fade-out");
    setTimeout(function() {
        modal.style.display = "none";
        modal.classList.remove("fade-out");
    }, 500);
}

// Закрываем модальное окно при клике вне него
window.onclick = function(event) {
    if (event.target == modal) {
        modal.classList.add("fade-out");
        setTimeout(function() {
            modal.style.display = "none";
            modal.classList.remove("fade-out");
        }, 500);
    }
}
var loginSuccess = [[${loginSuccess}]];
if (loginSuccess) {
    // Отображаем оверлей
    var overlay = document.getElementById('overlay');
    overlay.style.display = 'block';

    // Устанавливаем таймер на скрытие оверлея через 2 секунды
    setTimeout(function () {
        overlay.style.display = 'none';
    }, 2000);
}