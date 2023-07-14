$(document).ready(function() {
	$('#loginForm').on('submit', function(event) {
		event.preventDefault();

		$.ajax({
			url: contextPath + '/login',
			type: 'POST',
			data: $(this).serialize(),
			success: function(response) {  
				if (response.status === 'success') {
					showToast(response.message, "#34c759");
					window.location.href = contextPath + '/login';
				} else if (response.status == 'error') {
					showToast(response.message, "#ff3a30");
				}
			},
			error: handleError
		});
	});
});
