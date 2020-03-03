var NotifyProductAdded = function (product, quantity) {
	let _this = this;
	_this.product = product;
	_this.quantity = quantity;

	// _this.picture = "https://www.presse-citron.net/wordpress_prod/wp-content/uploads/2018/11/meilleure-banque-image.jpg";
	//_this.libelle = "Appareil photo";
	//_this.quantity = 1;

	if (product.imageCollection.length > 0) {
		_this.picture = "https://storage.googleapis.com/orvifdev.appspot.com/" + product.imageCollection[0].url;
	}

	_this.libelle = _this.product.libelle;


	let overlay_prod_added = "<div id='overlay-productAdded' class='elmtNotifAddProd'></div>";
	let modal_prod_added = "" +
		"<div class='elmtNotifAddProd' id='modal-productAdded'>";
	if (product.imageCollection.length > 0) {
		modal_prod_added += "<img src='" + _this.picture + "'/> ";
	}
	modal_prod_added += "<div>" +
		"<p style='font-weight: bold;color: #313e8c;white-space: nowrap; text-align: center;'>Produit ajout&eacute; au panier !</p>" +
		"<br/><br/>" +
		"<p style='text-align:center;font-size: 30px;' class='red'>" + _this.quantity + "</p>" +
		"<p style='text-align: center;'>" + _this.libelle + "</p>" +
		"</div>" +
		"<div>" +
		"<button id='closeAddProductNotification' class='button white'>Continuer mes achats</button>   " +
		"<a href='cart' class='button blue' >Voir mon panier</a>" +
		"</div>" +
		"</div>";


	$("html").append(overlay_prod_added + modal_prod_added);

	$(document).on("click", "#closeAddProductNotification", function () {
		$(".elmtNotifAddProd").remove();
	})

};