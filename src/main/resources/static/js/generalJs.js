// VARIABLES
let GOOGLE_SITEKEY = "6LdsVK4UAAAAAPg27eO5P1KcKa2ULZZgMmJqV";


function showLoader() {
	let loader = "<div id='keepOnTop'><span id='loader' ><div class='overlay' ></div><div class='loaderIcon' ><i class=\"fas fa-spinner fa-7x fa-pulse\"></i></div></span></div>";
	$(loader).hide().appendTo("html").show("200");
}

function hideLoader(_callback) {
	$("#loader").fadeOut(200, function () {
		$("#loader").remove();
		_callback();
	});
}


//Javascript code when page is loaded
$(document)
	.ready(
		function () {
			if ($("#notify-at-load").length) {
				//Notification when page is loaded
				let messageNotification = $("#notify-at-load").data("message");
				let typeNotification = $("#notify-at-load").data("type");
				Notify(messageNotification, typeNotification);
			}

			if ($("#slideshowWrapper").length) {
				let animating = false;
				let i = setInterval(nextCaption, 4000);
				let mouseover = false;

				function nextCaption() {
					let maxTarget = $(".imgWrapper").length;
					clearInterval(i);
					let activeCaption = $("#slideshow").data("activecaption");
					if (activeCaption == maxTarget) {
						animating = true;
						$("#slideshow").animate({
							marginLeft: "0"
						}, 1000, function () {
							if (!mouseover) {
								i = setInterval(nextCaption, 4000);
							}
							$(".dot").removeClass("active");
							$(".dot[data-target='1']").addClass("active");
							animating = false;
						});
						$("#slideshow").data("activecaption", "1");
					} else {
						animating = true;
						$("#slideshow").animate({
							marginLeft: "-" + parseInt(activeCaption * 100) + "%"
						}, 1000, function () {
							if (!mouseover) {
								i = setInterval(nextCaption, 4000);
							}
							$(".dot").removeClass("active");
							$(".dot[data-target='" + (parseInt(activeCaption) + 1) + "']").addClass("active");
							animating = false;
						});
						$("#slideshow").data("activecaption", parseInt(activeCaption) + 1);
					}
				}

				$(document).on("mouseover", "#slideshow", function () {
					mouseover = true;
					clearInterval(i);
				});

				$(document).on("mouseleave", "#slideshow", function () {
					mouseover = false;
					if (!animating) {
						i = setInterval(nextCaption, 4000);
					}
				});

				$(document).on("click", ".dot", function () {
					if (animating)
						return;
					let target = $(this).data("target");
					let $this = $(this);
					animating = true;
					$("#slideshow").animate({
						marginLeft: "-" + (target - 1) * 100 + "%"
					}, 1000, function () {
						animating = false;
						$(".dot").removeClass("active");
						$this.addClass("active");
					});
					$("#slideshow").data("activecaption", target);
				});
			}


			$(document).on("click", "a.moreSubFam", function () {
				let spanToShowHide = $(this).data("attachment");
				let visible = $(this).data("visible");
				if (visible === "y") {
					$(spanToShowHide).effect('slide', {direction: 'up', mode: "hide", duration: 100});
					$(this).data("visible", "n");
					$(this).html("+ de cat&eacute;gories");
				} else if (visible === "n") {
					$(spanToShowHide).effect('slide', {direction: 'up', mode: "show", duration: 100});
					$(this).data("visible", "y");
					$(this).html("- de cat&eacute;gories");
				}
			});


			$(window).resize(function () {
				$(".quickfit").textfill({
					minFontPixels: 16,
					maxFontPixels: 22
				});

				$(".quickfit.onlyWidth").textfill({
					minFontPixels: 16,
					maxFontPixels: 18
				});
			});


			/**
			 * Activate all fittext (text resizing to fit container)
			 */
			$(".quickfit").textfill({
				minFontPixels: 16,
				maxFontPixels: 22
			});

			$(".quickfit.onlyWidth").textfill({
				minFontPixels: 16,
				maxFontPixels: 18
			});

			/**
			 * Si un élément à l'id highlight-text a la fin du
			 * chargement de la page, on surligne le text correspondant
			 * au data-text
			 */
			if ($("#highlight-text").length) {
				const text = $("#highlight-text").data("text");
				const regEx = new RegExp(text, "ig");
				$("#highlight-text").html(
					$("#highlight-text").html().replace(
						regEx,
						"<span style='background-color:grey' >"
						+ text + "</span>"));
			}

			/**
			 * Handler pour le click sur + ou - avant d'ajouter un produit depuis une fiche produit
			 */
			$(document).on("click", ".controlProductSheet", function () {
				if ($(this).hasClass("plusOne")) {
					previousVal = $(this).prev().val();
				} else {
					previousVal = $(this).next().val();
				}
				if ($.isNumeric(previousVal)) {
					if ($(this).hasClass("plusOne")) {
						$(this).prev().val(parseInt(previousVal) + 1);
						$(this).prev().keyup();
					} else {
						$(this).next().val(parseInt(previousVal) - 1);
						$(this).next().keyup();
					}
				} else {
					if ($(this).hasClass("plusOne")) {
						$(this).prev().val(1);
						$(this).prev().keyup();
					} else {
						$(this).next().val(1);
						$(this).next().keyup();
					}

				}
			});


			$(document).on("keyup", "input.qteProductSheet", function () {

				let value = $(this).val();
				if (value !== "") {
					if ($.isNumeric(value)) {
						if (value > 100) {
							$(this).val(100);
							value = 100;
						}
						if (value < 1) {
							$(this).val(1);
							value = 1;
						}
					} else {
						$(this).val(1);
						value = 1;
					}
				}
				$(this).parent().parent().next().find("button.addToCart").data("nb", value);
			});

			/**
			 * Handler pour le click sur un onglet dans la fiche produit
			 */
			$(document).on("click", "#middleProductSheet #listOnglets .onglet", function () {
				$("#middleProductSheet #listOnglets .onglet").removeClass("active");
				$(this).addClass("active");
				$("#middleProductSheet > div").hide();
				$($(this).data("target")).show();
			});


			$(document).on("click", "button.changePage", function (event) {
				event.preventDefault();
				let page = 1;
				if ($(this).hasClass("previousPage")) {
					page = parseInt($(".changePage.active").data("attached-page")) - 1;
					if (page <= 0) {
						page = 1;
					}
				} else if ($(this).hasClass("nextPage")) {
					page = parseInt($(".changePage.active").data("attached-page")) + 1;
					//Check if there is a page with the requested number
					if (!$(".changePage[data-attached-page=" + page + "]").length) {
						page = page - 1;
					}
				} else {
					page = $(this).data("attached-page");
				}
				$("li.productLine").hide();
				$("li.productLine.pageNb" + page).css("display", "flex");
				$("button.changePage").removeClass("active");
				$("button.changePage[data-attached-page=" + page + "]").addClass("active");
				$('html, body').animate({
					scrollTop: $("#listProduct").offset().top - 20
				}, 1000);
			});

			function refreshCartPagination() {
				//Save current page
				let currentPage = $("button.changePage.active").data("attached-page");

				// Get list of products in cart
				$("ul#listProduct li").removeClass(function (index, className) {
					return className.match(/pageNb[0-9]*/).join(" ");
				});
				let nbParPage = 10; // We want 10 item per page
				let lastPage = 0;
				$("#cartPagination").empty();
				let numPage;
				if ($("ul#listProduct li").length > 10) {
					$("#cartPagination").append("<button class=\"changePage previousPage\"><i class=\"icon-left-open\"></i></button>");
					$("ul#listProduct li").each(function (index) {
						numPage = Math.floor(index / nbParPage) + 1;
						if (lastPage != numPage) {
							lastPage = numPage;
							$("#cartPagination").append("<button class=\"changePage\" data-attached-page=\"" + numPage + "\">" + numPage + "</button>");
						}
						$(this).addClass("pageNb" + numPage);
					});
					$("#cartPagination").append("<button class=\"changePage nextPage\"><i class=\"icon-right-open\"></i></button>");
				}

				if (currentPage > numPage) {
					$("button.changePage[data-attached-page=" + numPage + "]").click();
				} else {
					$("button.changePage[data-attached-page=" + currentPage + "]").click();
				}
			}

			/**
			 * Handler pour le click sur le bouton ajouter au panier
			 */
			$(document).on("click", ".addToCart", function () {
				showLoader();
				const button = $(this);
				const idProduit = $(this).data("id");
				let nbProduit = $(this).data("nb");
				if (nbProduit === null || nbProduit === 0) {
					nbProduit = 1;
				}
				// Ajax call
				$.ajax({
					method: "POST",
					url: "gestionPanier",
					dataType: "json",
					data: {
						gestion: "addProduct",
						idProduit: idProduit,
						nb: nbProduit
					},
					success: function (jsonResult) {
						hideLoader(function () {
							if (jsonResult.hasOwnProperty("errors")) {
								Notify(jsonResult.errors.global, "error");
							} else {
								NotifyProductAdded(JSON.parse(jsonResult.data.product), jsonResult.data.quantity);
								if ($("#nbProductInCart").html() === "") {
									$("#nbProductInCart").html(jsonResult.data.quantity);
								} else {
									$("#nbProductInCart").html(parseInt($("#nbProductInCart").html()) + parseInt(jsonResult.data.quantity));
								}

							}
						});
					},
					error: function (data) {
						hideLoader(function () {
							Notify("Une erreur est survenue. Veuillez r&eacute;essayer.", "error");
							console.error("Erreur d'ajout au panier.");
						});
					}
				});
			});

			/**
			 * Handler pour le click sur le bouton de suppression d'un
			 * produit du panier
			 */
			$(document)
				.on(
					"click",
					".removeFromCart",
					function () {
						showLoader();
						let elmt = $(this);
						const id = $(this).data("id");
						if (id !== "null") {
							$
								.ajax({
									method: "POST",
									url: "gestionPanier",
									dataType: "json",
									data: {
										gestion: "removeProduct",
										idProduit: id
									},
									success: function (jsonResult) {
										if (jsonResult.hasOwnProperty("errors")) {
											hideLoader(function () {
												Notify(jsonResult.errors.global, "error");
											});
										} else {
											if ($(".productLine").length == 1) {
												location.reload();
											} else {
												hideLoader(function () {
													elmt.parent().parent().fadeOut(function () {
														$(this).remove();
														refreshCartPagination();
													});
													$("#globalPriceTTC").html(jsonResult.data.newGlobalPriceTTC + "&euro;");
													$("#nbProductInCart").html(jsonResult.data.nbProductInCart);
													Notify("Produit supprim&eacute; du panier.", "success");
												});
											}
										}
									},
									error: function (data) {
										hideLoader(function () {
											Notify("Une erreur s'est produite. Veuillez r&eacute;essayer.", "error");
										});
									}
								});
						} else {
							hideLoader(function () {
								Notify("Une erreur s'est produite. Veuillez r&eacute;essayer.", "error");
							});
						}
					});

			/**
			 * handler pour afficher l'overlay au focus du champ de recherche
			 */
			$(document).on("focus", "#research", function () {
				$("#containerSearch").css("z-index", "3000");
				$("#overlay-focus").fadeIn("fast");
			});

			/**
			 * Handler pour enlever l'overlay lorsque le champ recherche perd le focus
			 */
			$(document).on("blur", "#research", function () {
				$("#overlay-focus").fadeOut("fast", function () {
					$("#containerSearch").css("z-index", "2998");
				});
			});

			/**
			 * Handler pour le click sur le bouton "vider le panier"
			 */
			$(document)
				.on(
					"click",
					"#emptyCart",
					function () {
						$.ajax({
							method: "POST",
							url: "gestionPanier",
							dataType: "json",
							data: {
								gestion: "removePanier",
							},
							success: function (jsonResult) {
								location.reload();
							},
							error: function () {
								Notify("Une erreur s'est produite. Veuillez r&eacute;essayer.", "error");
							}
						});
					});

			//Enregistrement des quantites du panier pour les reinitialiser en cas d'erreur
			let dataCart = {};
			if ($("#topCart").length) {
				$(".productLine").each(function () {
					let idProd = $(this).find(".qteProduct").data("id");
					let qte = $(this).find(".qteProduct").val();
					dataCart[idProd] = qte;
				});
			}


			let timeoutCart = null;

			/**
			 * Handler pour le changement de quantité d'un produit du
			 * panier
			 */
			$(document)
				.on(
					"keyup",
					".qteProduct",
					function () {
						const elmt = $(this);
						const nbBefore = elmt.data("initial");
						const idProduit = $(this).data("id");
						let nb = $(this).val();
						const stock = $(this).data("stock");
						let moreThanMax = false;
						let line = elmt.parent().parent();
						if (line.css("z-index") != "3000") {
							line.css("z-index", "3000");
							$("#overlay-focus").fadeIn();
						}
						clearTimeout(timeoutCart);
						timeoutCart = setTimeout(function () {
							showLoader();
							if (nb == "") {
								return;
							}
							if (!$.isNumeric(nb)) {
								hideLoader(function () {
									line.css("z-index", "1");
									$("#overlay-focus").fadeOut();
									elmt.val(dataCart[idProduit]);
									Notify("Une erreur s'est produite. Veuillez r&eacute;essayer.", "error");
								});
								return;
							}
							if (nb > stock) {
								elmt.val(stock);
								nb = stock;
								moreThanMax = true;
								if (nb == nbBefore) {
									hideLoader(function () {
										line.css("z-index", "1");
										$("#overlay-focus").fadeOut();
										Notify("Stock maximum d&eacute;pass&eacute;", "error");
									});
									return;
								}
							}
							$
								.ajax({
									method: "POST",
									url: "gestionPanier",
									dataType: "json",
									data: {
										gestion: "changeQuantityProduct",
										idProduit: idProduit,
										nb: nb
									},
									success: function (jsonResult) {
										if (jsonResult.hasOwnProperty("errors")) {
											hideLoader(function () {
												elmt.val(nbBefore);
												Notify(jsonResult.errors.global, "error");
												line.css("z-index", "1");
												$("#overlay-focus").fadeOut();
											});
										} else {
											if (nb == 0) {
												if ($(".productLine").length == 1) {
													location.reload();
													return;
												} else {
													elmt.parent().parent().fadeOut(function () {
														$(this).remove();
													});
													$("#globalPriceTTC").html(jsonResult.data.newGlobalPriceTTC + "&euro;");
													$("#nbProductInCart").html(jsonResult.data.nbProductInCart);
												}
											} else {
												elmt.parent().next().html(jsonResult.data.newPriceTTC + "&euro;");
												$("#globalPriceTTC").html(jsonResult.data.newGlobalPriceTTC + "&euro;");
												$("#nbProductInCart").html(jsonResult.data.nbProductInCart);
											}
											hideLoader(function () {
												line.css("z-index", "1");
												$("#overlay-focus").fadeOut();
												if (moreThanMax) {
													Notify("Quantit&eacute; modifi&eacute;e. Stock maximum atteint.", "success");
													elmt.data("initial", nb);
												} else {
													Notify("Quantit&eacute; modifi&eacute;e.", "success");
													elmt.data("initial", nb);
												}
											});
										}
									},
									error: function () {
										hideLoader(function () {
											line.css("z-index", "1");
											$("#overlay-focus").fadeOut();
											Notify("Une erreur s'est produite. Veuillez r&eacute;essayer.", "error");
											elmt.val(dataCart[idProduit]);
										});
									}
								});
						}, 1500);
					});


			/**
			 * Handler for checking promo code in cart page
			 */
			$(document).on("click", "#checkPromoCode", function (event) {
				event.preventDefault();
				showLoader();
				const value = $("input[name=promoCode]").val();
				$.ajax({
					url: "/codePromo",
					method: "post",
					dataType: "json",
					data: {codePromo: value},
					success: function (jsonResult) {
						if (jsonResult.hasOwnProperty("error")) {
							hideLoader(function () {
								Notify("Une erreur s'est produite. Veuillez réessayer à nouveau.", "error");
							});
							return;
						}
						if (jsonResult.hasOwnProperty("promo")) {
							if ($.isNumeric(jsonResult.promo)) {
								//TODO apply promo code
								$("#resultPromoCode").html("Bravo ! -" + jsonResult.promo + "% sur votre commande.");
							} else {
								hideLoader(function () {
									Notify("Une erreur s'est produite. Veuillez réessayer à nouveau.", "error");
								});
							}
						} else {
							hideLoader(function () {
								$("#resultPromoCode").html("Mince ! Ce code promo n'est plus actif.");
							});
						}
					},
					error: function () {
						hideLoader(function () {
							Notify("Une erreur s'est produite. Veuillez réessayer à nouveau.", "error");
						});
					}
				})
			});

			/**
			 * Handler pour le click sur le changement de mode de retrait de la commande DANS LA PANIER
			 */
			$(document).on("click", "#deliveryChoice button", function () {
				$("#deliveryChoice button").removeClass("active");
				$(this).addClass("active");
				const value = $(this).val();
				$("input[name=deliveryChoice]").val(value);
			});

			/**
			 * Handler pour afficher le formulaire pour entrer une autre adresse pour la facturation si necessaire lors de la commande
			 */
			$(document).on("click", ".otherBillAddress", function () {
				$("#orderContent .billAddressDiv .existingAddressDiv").hide();
				$("#orderContent .billAddressDiv .existingAddressDiv input").removeAttr("required");

				$("#orderContent .billAddressDiv .otherAddressDiv").show();
				$("#orderContent .billAddressDiv .otherAddressDiv input").each(function () {
					if ($(this).data("required") == "required") {
						$(this).attr("required", "required");
					}
				});
				$("input[name=existingAddressBillSelection]").val("n");
				$(this).hide();
				$(".existingBillAddress").show();
			});

			/**
			 * Handler pour afficher les adresses existantes pour choisir l'adresse de facturation lors de la commande
			 */
			$(document).on("click", ".existingBillAddress", function () {
				$("#orderContent .billAddressDiv .existingAddressDiv").show();
				$("#orderContent .billAddressDiv .existingAddressDiv input").each(function () {
					if ($(this).data("required") == "required") {
						$(this).attr("required", "required");
					}
				});

				$("#orderContent .billAddressDiv .otherAddressDiv").hide();
				$("#orderContent .billAddressDiv .otherAddressDiv input").removeAttr("required");

				$("input[name=existingAddressBillSelection]").val("y");

				$(this).hide();
				$(".otherBillAddress").show();
			});

			/**
			 * Handler pour afficher le formulaire pour entrer une autre adresse pour la livraison si necessaire lors de la commande
			 */
			$(document).on("click", ".otherDeliveryAddress", function () {
				$("#orderContent .deliveryAddressDiv .existingAddressDiv").hide();
				$("#orderContent .deliveryAddressDiv .existingAddressDiv input").removeAttr("required");

				$("#orderContent .deliveryAddressDiv .otherAddressDiv").show();
				$("#orderContent .deliveryAddressDiv .otherAddressDiv input").each(function () {
					if ($(this).data("required") == "required") {
						$(this).attr("required", "required");
					}
				});

				$("input[name=existingAddressDeliverySelection]").val("n");

				$(this).hide();
				$(".existingDeliveryAddress").show();
			});

			/**
			 * Handler pour afficher les adresses existantes pour choisir l'adresse de la livraison lors de la commande
			 */
			$(document).on("click", ".existingDeliveryAddress", function () {
				$("#orderContent .deliveryAddressDiv .existingAddressDiv").show();
				$("#orderContent .deliveryAddressDiv .existingAddressDiv input").each(function () {
					if ($(this).data("required") == "required") {
						$(this).attr("required", "required");
					}
				});

				$("#orderContent .deliveryAddressDiv .otherAddressDiv").hide();
				$("#orderContent .deliveryAddressDiv .otherAddressDiv input").removeAttr("required");

				$("input[name=existingAddressDeliverySelection]").val("y");

				$(this).hide();
				$(".otherDeliveryAddress").show();
			});

			/**
			 * Handler pour montrer/cacher la saisie de l'adresse de facturation lors du passage d'une commande
			 */
			$(document).on("change", "#orderContent input[name=addressBillDifferent]", function () {
				if ($(this).is(":checked")) {
					$("#orderContent .billAddressDiv").show();
					if ($("#orderContent .billAddressDiv .otherAddressDiv").css("display") !== "none") {
						$("#orderContent .billAddressDiv input").each(function () {
							if ($(this).data("required") == "required") {
								$(this).attr("required", "required");
							}
						});
					}
				} else {
					$("#orderContent .billAddressDiv").hide();
					$("#orderContent .billAddressDiv input").removeAttr("required");
				}
			});


			/**
			 * Handler pour le changement de tri de la liste de produit
			 */
			$(document).on(
				"change",
				"#order",
				function () {
					let url;
					const value = $(this).val();
					let n;
					let i;
					if (value === "libelle"
						|| value === "ppht desc"
						|| value === "ppht asc") {
						// Recuperation de l'url
						url = window.location.href;
						if (url.search("\\?") === -1) {
							url += "?order=" + value;
						} else {
							// Check if order is already set
							n = url.search("order");
							if (n === -1) {
								url += "&order=" + value;
							} else {
								var sub = url.substring(n,
									url.length);
								// Search next "&" index
								i = sub.search("&");
								if (i === -1) {
									url = url
											.replace(url.substring(
												n, url.length),
												"")
										+ "order=" + value;
								} else {
									url = url.replace(url
											.substring(n, n + i),
										"order=" + value);
								}
							}
						}
					}
					// Check if hidden search input exists
					if ($("input#search").length) {
						// Check if search is already in url
						if (url.search("search=") === -1) {
							// Ajout de search dans l'url
							url += "&search="
								+ $("input#search").val();
						}
					}
					// Redirection vers la nouvelle url
					window.location.assign(url);
				});

			/**
			 * Handler pour le changement de nb produits par page
			 */
			$(document).on(
				"change",
				"#nbPerPage",
				function () {
					const value = $(this).val();
					// Recuperation de l'url
					let url = window.location.href;
					let i;
					if (url.search("\\?") === -1) {
						url += "?nbPage=" + value;
					} else {
						// Check if nbPage is already set
						n = url.search("nbPage");
						if (n === -1) {
							url += "&nbPage=" + value;
						} else {
							var sub = url.substring(n,
								url.length);
							// Search next "&" index
							i = sub.search("&");
							if (i === -1) {
								url = url
										.replace(url.substring(
											n, url.length),
											"")
									+ "nbPage=" + value;
							} else {
								url = url.replace(url
										.substring(n, n + i),
									"nbPage=" + value);
							}
						}
					}
					// Check if hidden search input exists
					if ($("input#search").length) {
						// Check if search is already in url
						if (url.search("search=") === -1) {
							// Ajout de search dans l'url
							url += "&search="
								+ $("input#search").val();
						}
					}
					// Redirection vers la nouvelle url
					window.location.assign(url);
				});


			/**
			 * Handler qui se déclenche quand un utilisateur en train de
			 * s'inscrire change le type d'inscription (pro ou
			 * particulier) : permet d'afficher ou non ce dont on a
			 * besoin
			 */
			$(document).on("change", "input[name=type]", function () {
				$("#customerChoiceType label").removeClass("active");
				$(this).parent().addClass("active");
				if ($(this).val() === "pro") {
					$("#proOnly").css("display", "block");
				} else {
					$("#proOnly").css("display", "none");
				}
			});

			/**
			 * Handler qui se déclenche quand un utilisateur en train de
			 * s'inscrire veut ajouter plus d'une adresse. Il permet
			 * d'afficher de nouveaux champs
			 */
			$(document)
				.on(
					"click",
					"#addAdress",
					function (e) {
						e.preventDefault();
						const formAdress = $(".adresse:first")
							.clone();
						formAdress.find("input").val("");
						$(formAdress)
							.append(
								"<button class='removeAdresse'>Enlever adresse</button>");
						$(this).before(formAdress);
					});

			/**
			 * Handler qui se déclenche quand un utilisateur qui
			 * s'inscrit supprime une adresse qu'il a ajouté avant
			 */
			$(document).on("click", ".removeAdresse", function (e) {
				e.preventDefault();
				$(this).parents(".adresse").remove();
			});


			let timer;
			let counter = 0;
			$(document).on("mouseenter", ".familleItem, #header .viewSousFamille", function () {
				if ($(this).hasClass("noOverview")) {
					actionMouseLeaveNav();
				} else {
					counter++;
					if ($(this).attr("class").indexOf("familleItem") !== -1) {
						var elmt = $(this);
						timer = setTimeout(function () {
							$(".familleItem").removeClass("active");
							elmt.addClass("active");
							const list = elmt.find(".sousFamilleList").clone();
							list.show();
							$(".viewSousFamille").show().empty().append(list);
						}, 200);
					}
				}
			});

			/**
			 * Handler qui se declenche quand on passe la souris au
			 * dessus d'une famille dans le menu Il permet d'afficher
			 * les sous familles correspondantes
			 */
			$(document).on(
				"mouseleave",
				"#header .viewSousFamille, li.familleItem",
				actionMouseLeaveNav);

			function actionMouseLeaveNav() {
				clearTimeout(timer);
				counter--;
				setTimeout(function () {
					if (!counter || counter < 0) {
						$(".familleItem").removeClass("active");
						$(".viewSousFamille").css("display", "none");
						counter = 0;
					}
				}, 200);
			}

			/**
			 * Handler qui se declenche quand l'utilisateur clique sur
			 * supprimer une adresse depuis son espace client
			 */
			$(document).on("click", ".removeAddress", function () {
				const toRemove = $(this).closest("li");
				const idAdresse = $(this).data("id");
				// Suppression de l'adresse en ajax
				$.ajax({
					method: "POST",
					dataType: "json",
					data: {
						choix: "removeAdresse",
						idAdresse: idAdresse
					},
					url: "actionClient",
					success: function (result) {
						if (result.hasOwnProperty("errors")) {
							console.error("Une erreur est survenu");
							Notify("Impossible de supprimer l'adresse. Veuillez r&eacute;essayer.", "error");
						} else {
							Notify("Adresse supprim&eacute;e.", "success");
							// Suppression de l'adresse
							toRemove.remove();
						}
					},
					error: function () {
						console.error("Une erreur est survenu");
						// TODO message d'erreur
					}
				});
			});

			$(document).on("submit", "#newPasswordForm", function (e) {
				e.preventDefault();
				let form = $(this).serialize();
				showLoader();
				$.ajax({
					url: "actionClient",
					method: "post",
					data: form + "&choix=changePassword",
					dataType: "json",
					success: function (result) {
						if (result.hasOwnProperty("errors")) {
							hideLoader(function () {
								Notify(result.errors.global, "error");
							});
						} else {
							hideLoader(function () {
								Notify("Mot de passe chang&eacute; avec succ&egrave;s.", "success");
								$("#newPasswordForm").trigger("reset");
							});
						}
					},
					error: function () {
						hideLoader(function () {
							Notify("Impossible de changer le mot de passe. Veuillez r&eacute;essayer ult&eacute;rieurement.", "success");
						});
					}
				});
			});


			if ($("#slider-range").length) {
				const min = $("#slider-range").data("min");
				const max = $("#slider-range").data("max");
				const minSelected = $("#slider-range").data("minselected");
				const maxSelected = $("#slider-range").data("maxselected");
				let filterBox = $("#slider-range").parent().parent();
				let overlay = $("#overlay-focus");
				let optionsRange = {
					range: true,
					min: min,
					max: max,
					values: [minSelected, maxSelected],
					slide: function (event, ui) {
						filterBox.css("z-index", "3000");
						$("#btnPriceRangeGroup").css("display", "flex");
						overlay.fadeIn("fast");
						$("#lowValue").html(ui.values[0] + "&euro;");
						$("#highValue").html(ui.values[1] + "&euro;");
					}
				};
				var sliderRange = $("#slider-range").slider(optionsRange);

				$(document).on("click", "#cancelSliderRange", function () {
					sliderRange.slider("values", 0, minSelected);
					sliderRange.slider("values", 1, maxSelected);
					$("#btnPriceRangeGroup").css("display", "none");
					$("#lowValue").html(minSelected + "&euro;");
					$("#highValue").html(maxSelected + "&euro;");
					overlay.fadeOut("fast", function () {
						filterBox.css("z-index", "2998");
					});
				});

				$(document).on("click", "#validSliderRange", function () {
					let minToFilter = sliderRange.slider("values", 0);
					let maxToFilter = sliderRange.slider("values", 1);
					let href = $(this).data("href");
					href = href.replace("LOWPRICE", minToFilter);
					href = href.replace("HIGHPRICE", maxToFilter);
					document.location.href = href;
				});
			}

			$(document).on("click", ".control", function () {
				if ($(this).hasClass("minusOne")) {
					let quantity = $(this).next().val();
					if ($.isNumeric(quantity)) {
						$(this).next().val(quantity > 0 ? quantity - 1 : 0);
					} else {
						$(this).next().val(1);
					}
					$(this).next().keyup();
				} else if ($(this).hasClass("plusOne")) {
					let quantity = $(this).prev().val();
					if ($.isNumeric(quantity)) {
						$(this).prev().val(parseInt(quantity) + 1);
					} else {
						$(this).prev().val(1);
					}
					$(this).prev().keyup();
				}
			});


			$(document).on("click", "#hideErrorLogin", function () {
				$(this).parent().fadeOut(function () {
					$(this).remove();
				});
			});

			$(document).on("click", "#hideErrorMail", function () {
				$(this).parent().fadeOut(function () {
					$(this).remove();
				});
			});
		});


// header -> Mon Compte
// opens #login-card on click on "Mon Compte" and down arrow, closes #login-card on click outside card
$(document).on("click", "#myAccount", function () {
	$("#login-card").toggle();

	if ($(this).next().hasClass("icon-down-open")) {
		$(this).next().removeClass("icon-down-open");
		$(this).next().addClass("icon-up-open");
	} else {
		$(this).next().removeClass("icon-up-open");
		$(this).next().addClass("icon-down-open");
	}
    $("#login-card").on("click", function (event) {
        event.stopPropagation();
    });
    $(document).on("click", function () {
        $("#login-card").hide();
    });
});









$(document).on("click", "#myOptions", function () {
	$("#account-card").toggle();

	if ($(this).next().hasClass("icon-down-open")) {
		$(this).next().removeClass("icon-down-open");
		$(this).next().addClass("icon-up-open");
	} else {
		$(this).next().removeClass("icon-up-open");
		$(this).next().addClass("icon-down-open");
	}
});


/**
 * Handler qui se déclenche lorsque que l'utilisateur choisit particulier ou professionnel dans le formulaire de contact
 *
 */
$(document).on("click", "input[name=type_customer]", function () {
	if ($(this).attr("id") === "particulier") {
		$(".showPro").hide();
	} else {
		$(".showPro").show();
	}
});

/**
 * Handler du click sur le bouton contact pour afficher un modal
 */
$(document).on("click", "#goContact, .goContact", function () {
	let mail = $("input[name=mailFirst]").val();
	let html = "";
	let agence = "";
	if ($(this).data("agence") != "") {
		agence = $(this).data("agence");
	}
	if (agence != "") {
		agence = "<br/>Nous vous r&eacute;pondrons dans les plus bref d&eacute;lais.";
	}
	$.ajax({
		url: "/contactFormTemplate.jsp",
		method: "get",
		success(result) {
			html = result;
			$(html).find("#mail").attr("test", "test");
		},
		complete: function () {
			let text = "Veuillez &ecirc;tre le plus clair possible afin que nous puissions vous donner une r&eacute;ponse de qualit&eacute;." + agence;
			let modal = new Modal("Contactez nous !", text, html, "Contactez-nous");
			modal.render();
			const captchaWidgetId = grecaptcha.render('captcha', {
				'sitekey': GOOGLE_SITEKEY,
				'theme': 'light'
			});
			$(html).find("input#mail").val(mail);
			$("#mail").val(mail);
		}
	});
});

/**
 * Handler pour simuler le click sur "contactez nous" lorsque l'utilisateur presse "entree"
 */
$(document).on("keypress", "#footer input[name=mailFirst]", function (event) {
	if (event.keyCode === 13) {
		$("#goContact").click();
	}
});

/**
 * Handler pour le click sur le bouton pour s'ajouter à la newsletter (affiche un modal pour entrer une adresse mail
 */
$(document).on("click", "#newsletterSub", function () {
	let html = "<form><input style='padding-left: 10px;" +
		"padding-right: 10px;" +
		"height: 35px;" +
		"box-sizing: border-box;" +
		"margin-bottom: 5px;" +
		"margin-top: 5px;" +
		"border: 1px solid #cccccc;" +
		"width:100%;' type='text' name='mail' />" +
		"<div id=\"captcha\" class=\"g-recaptcha\"></div>" +
		"</form>";
	let modal = new Modal("Inscription &agrave; la newsletter !", "Veuillez entrer votre adresse mail pour &ecirc;tre ajout&eacute; &agrave; notre newsletter.", html, "Inscription", "stretch", function (form) {
		showLoader();
		let mail = form.find("input[name=mail]").val();
		let data = form.serialize();
		if (mail !== undefined) {
			if (validateEmail(mail)) {
				$.ajax({
					url: "newsletter",
					data: data + "&action=add",
					method: "get",
					dataType: "json",
					success: function (result) {
						hideLoader(function () {
							if (result.hasOwnProperty("error")) {
								Notify(result.error, "error");
							} else {
								Notify("Vous &ecirc;tes maintenant inscrit &agrave; la neswletter !", "success");
							}
							modal.destroy();
						});
					},
					error: function () {
						hideLoader(function () {
							Notify("Un probleme est survenu, veuillez réessayer.", "error");
							modal.destroy();
						});
					}
				})
			} else {
				hideLoader(function () {
					Notify("Le mail n'est pas valide. Veuillez réessayer.", "error");
					modal.destroy();
				})
			}
		}
	});
	modal.render();
	const captchaWidgetId = grecaptcha.render('captcha', {
		'sitekey': GOOGLE_SITEKEY,
		'theme': 'light'
	});
});


function validateEmail(email) {
	var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(String(email).toLowerCase());
}