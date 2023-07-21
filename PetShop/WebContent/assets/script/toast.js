	function showToast(message, color) {
		let toast = document.querySelector(".toast");
		toast.textContent = message;
		toast.classList.add("show");
		toast.style.backgroundColor = color;
		setTimeout(function() {
			toast.classList.remove("show");
		}, 3000);
	}