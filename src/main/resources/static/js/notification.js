var Notify = function (message, type) {
	let _this = this;
	_this.messages = message;
	_this.type = type;

	switch (_this.type) {
		case "success":
			_this.background = "#283480";
			_this.color = "#ffffff";
			_this.border = "#283480";
			break;
		case "error":
			_this.background = "#dc2e38";
			_this.color = "#ffffff";
			_this.border = "##dc2e38";
			break;
		case "info":
			_this.background = "#283480";
			_this.color = "#ffffff";
			_this.border = "#283480";
			break;
		default:
			console.error("Type de message \"" + _this.type + "\" non reconnu.");
			return;
	}

	if (!$("#notifications").length) {
		$("html").append("<div id='notifications'></div>");
	}
	_this.container = $("#notifications");

	let notification = "<div class='notify' style='color: " + _this.color + " ;background-color: " + _this.background + ";'><span>" + _this.messages + "</span><i class=\"far fa-times-circle\"></i></div>";

	if (_this.container.find(".notify").length) {
		//Remove the existing notification before appending the new one
		$(_this.container.find(".notify")[0]).effect('slide', {
			direction: 'down',
			mode: 'hide',
			duration: 200
		}, function () {
			$(notification).hide().appendTo(_this.container).effect('slide', {
				direction: 'down',
				mode: 'show',
				duration: 200
			});

			$(this).remove();
		});
	} else {
		$(notification).hide().appendTo(_this.container).effect('slide', {
			direction: 'down',
			mode: 'show',
			duration: 200
		});
	}

	$(document).on("click", "#notifications .notify i", function () {
		$(this).parent().effect('slide', {direction: 'down', mode: 'hide', duration: 200}, function () {
			$(this).remove();
		});
	});
};