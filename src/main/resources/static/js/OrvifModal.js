/**
 * Create a modal with Orvif colors
 * @param title title of the modal
 * @param subtitle subtitle of modal
 * @param content Html content of modal
 * @param btn Text of the button in modal
 * @param size [Optional] Either big, small or stretch. Default : big
 * @param callback [Optional] Function called when user click on the modal button. Called if content provide a <form>. The function take the form as only parameter.
 */
let Modal = function (title, subtitle, content, btn, size = "big", callback = "") {
	let _this = this;
	_this.events = {};
	_this.title = title;
	_this.subtitle = subtitle;
	_this.content = content;
	_this.btn = btn;
	_this.size = size;
	_this.callback = callback;

	let cssModalContainer = "";

	if (_this.size === "stretch") {
		cssModalContainer = "height:unset;width:unset; max-height:86vh;";
	} else if (_this.size === "small") {
		cssModalContainer = "height: 50vh;";
	}

	let cssTitles = "";

	if (_this.size === "stretch") {
		cssTitles = "padding:20px;";
	} else if (_this.size === "small") {
		cssTitles = "padding:20px;";
	}

	let cssContent = "";

	if (_this.size === "stretch") {
		cssContent = "padding:20px;";
	} else if (_this.size === "small") {
		cssContent = "padding:20px;";
	}

	let cssButton = "";

	if (_this.size === "stretch") {
		cssButton = "height:45px;";
	} else if (_this.size === "small") {
		cssButton = "height:45px;";
	}

	_this.addEventListener = function (name, handler) {
		if (_this.events.hasOwnProperty(name))
			_this.events[name].push(handler);
		else
			_this.events[name] = [handler];
	};

	_this.on = function (name, handler) {
		if (_this.events.hasOwnProperty(name))
			_this.events[name].push(handler);
		else
			_this.events[name] = [handler];
	};

	_this.fireEvent = function (name, args) {
		if (!_this.events.hasOwnProperty(name))
			return;

		if (!args || !args.length)
			args = [];

		let evs = _this.events[name], l = evs.length;
		for (let i = 0; i < l; i++) {
			evs[i].apply(null, args);
		}
	};

	_this.destroy = function () {
		$("#orvifModal").fadeOut(400, function () {
			_this.fireEvent("modalDestroyed");
			$("#orvifModal").remove();
		});

		$(document).on("keypress",function (event) {
			$(document).off('keypress');
		});
	};

	_this.render = function () {
		let modal = "<span id='orvifModal'><div id='overlay'></div>";
		modal += "<div id='modalContainer' style='" + cssModalContainer + "'>" +
			"<div class='topOrvifColors'>" +
			"<span class='blue' ></span>" +
			"<span class='red' ></span>" +
			"</div>" +
			"<div class='flexContainer'>" +
			"<div class='titles' style='" + cssTitles + "'>" +
			"<div class='bigTitle' >" + _this.title + "</div>" +
			"<div class='subtitle' >" + _this.subtitle + "</div>" +
			"</div>" +
			"<div class='content' style='" + cssContent + "' > " + _this.content + "</div>" +
			"<div class='modalFooter'><button id='btnValidModalForm' class='button red-blue' style='" + cssButton + "'>" + _this.btn + "</button></div>" +
			"</div>" +
			"</div>";
		modal += "</span>";
		let cpt = 0;

		$(modal).hide().appendTo("html").fadeIn(400, function () {
			_this.fireEvent("modalShown");
		});


		$(document).on("click", "#overlay", function () {
			_this.destroy();
		});

		$(document).on("click", "#btnValidModalForm", function (e) {
			e.preventDefault();
			let form = $("#orvifModal .content form");
			if (form.length) {
				if (_this.callback === "") {
					form.submit();
				} else {
					_this.callback(form);
				}
			} else {
				$("#overlay").click();
			}
		});

		$(document).on("keypress", function (e) {
			if (e.keyCode === 13) {
				e.preventDefault();
				$("#btnValidModalForm").click();
			}
		});
	}
};