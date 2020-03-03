$(document).ready(function () {

	$('#mainImg img')
		.wrap('<span style="display:inline-block;width: 100%;height: 100%;text-align: center;"></span>')
		.css('display', 'inline-block')
		.parent()
		.zoom();

	$(document).on("click", "#smallerImgs img", function () {
		let newSrc = $(this).attr("src");
		$("#mainImg img").attr("src", newSrc);
		$("#smallerImgs .img").removeClass("active");
		$(this).parent().addClass("active");
	});

	if ($("#references .highlightRef").length) {
		$('html, body').animate({
			scrollTop: $(".highlightRef").offset().top - 20
		}, 1000);
	}
});