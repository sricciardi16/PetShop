<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
.toast {
	visibility: hidden;
	max-width: 250px;
	height: auto;
	background-color: #333;
	color: #fff;
	text-align: center;
	border-radius: 25px;
	padding: 20px;
	position: fixed;
	z-index: 1;
	left: 50%;
	top: -100px;
	font-size: 17px;
	margin-left: -125px;
	box-shadow: 0px 10px 10px rgba(0, 0, 0, 0.1);
	opacity: 0;
	transition: top 0.5s ease, opacity 0.5s ease;
}

.toast.show {
	visibility: visible;
	top: 30px;
	opacity: 1;
	transition: top 0.5s ease, opacity 0.5s ease;
}
</style>
<script>
	function showToast(message, color) {
		let toast = document.querySelector(".toast");
		toast.textContent = message;
		toast.classList.add("show");
		toast.style.backgroundColor = color;

		setTimeout(function() {
			toast.classList.remove("show");
		}, 3000);
	}
</script>
</head>
<body>
	<div class="toast"></div>
	<c:choose>
		<c:when test="${status eq 'success'}">
			<script>
				showToast("${message}", "#34c759");
			</script>
		</c:when>
		<c:when test="${status eq 'error'}">
			<script>
				showToast("${message}", "#ff3b30");
			</script>
		</c:when>
		<c:otherwise>
			<c:if test="${not empty message}">
				<script>
					showToast("${message}", "#007bff");
				</script>
			</c:if>
		</c:otherwise>
	</c:choose>
</body>
</html>