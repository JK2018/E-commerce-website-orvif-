$(document)
	.ready(
		function () {

			//slideshow table handling
			if ($("#slideshowTable").length) {
				let toDelete = [];
				let initialPositions = {};
				let newPositions = {};
				$("#slideshowTable tbody").find("tr").each(function (index) {
					let url = $($(this).find("td")[0]).text();
					initialPositions[url] = index + 1;
					newPositions[url] = index + 1;
				});

				function refreshPositions() {
					$("#slideshowTable tbody").find("tr").each(function (index) {
						let url = $($(this).find("td")[0]).text();
						newPositions[url] = index + 1;
					});
				}

				$(document).on("click", ".moveUp", function () {
					//Get tr
					let tr = $(this).parent().parent();
					//Check if it's not already the first tr
					if (!tr.prev().length) {
						return;
					}
					//Change between prev and this one
					tr.after(tr.prev());
					//Refresh new positions
					refreshPositions();
				});

				$(document).on("click", ".moveDown", function () {
					//Get tr
					let tr = $(this).parent().parent();
					//Check if it's not already the last tr
					if (!tr.next().length) {
						return;
					}
					//Change between prev and this one
					tr.next().after(tr);
					refreshPositions();
				});

				$(document).on("click", ".deleteSlideShowImage", function () {
					//get tr
					let tr = $(this).parent().parent();
					//Get the url and add it to the array "toDelete"
					let url = tr.find("td")[0];
					toDelete.push($(url).text());
					tr.remove();
					refreshPositions();
				});

				$(document).on("click", "#saveSlideshow", function () {
					showLoader();
					let changedPositions = {};
					// determine changed positions
					for (let key in initialPositions) {
						if (jQuery.inArray(key, toDelete) !== -1)
							continue;
						if (initialPositions[key] !== newPositions[key]) {
							changedPositions[key] = newPositions[key];
						}
					}
					// Construction of param
					let bodyRequest = {
						action: "save",
						toDelete: toDelete,
						newPositions: changedPositions
					};

					// ajax to perform the update
					$.ajax({
						url: "adminSlideshow",
						method: "post",
						contentType: "application/json",
						data: JSON.stringify(bodyRequest),
						dataType: "json",
						success: function (result) {
							if (result.hasOwnProperty("error")) {
								console.error("ERROR : " + result.error);
								alert("Une erreur s'est produite. Veuillez consulter les log pour plus d'information.");
								return;
							}
						},
						error: function () {
							alert("Une erreur s'est produite. Veuillez reessayer.");
						},
						complete: function () {
							endLoader();
						}
					});
				});
			}

			$(document).on("submit", "#formAddSlideshow", function (event) {
				showLoader();
				event.preventDefault();
				//Start by posting the file to google cloud storage
				let form = new FormData();
				console.log($("#newSlideshowFile")[0].files[0]);
				form.append("file", $("#newSlideshowFile")[0].files[0]);
				form.append("bucket", "slideshow_files_bucket_name");
				//Ajax call to use the servlet to post document on the bucket
				$.ajax({
					url: "/postFile",
					type: "post",
					contentType: false,
					data: form,
					processData: false,
					dataType: "json",
					success: function (result) {
						if (result.hasOwnProperty("error")) {
							console.error("Une erreur est survenue : " + result.error);
							alert("Impossible d'ajouter le fichier sur le serveur. Veuillez reessayer.");
							return;
						}
						//Add file to database with another ajax call to the right url
						let filename = result.filename;
						let position = $("#slideshowTable tbody").find("tr").length + 1;
						let bodyRequest = {
							action: "add",
							filename: filename,
							position: position,
							alt: $("#altNewSlideshow").val()
						}

						console.log(bodyRequest);
						// ajax to perform the update
						$.ajax({
							url: "adminSlideshow",
							method: "post",
							contentType: "application/json",
							data: JSON.stringify(bodyRequest),
							dataType: "json",
							success: function (result) {
								if (result.hasOwnProperty("error")) {
									console.error("ERROR : " + result.error);
									alert("Une erreur s'est produite. Veuillez consulter les log pour plus d'information.");
								}
								document.location.reload(true);
							},
							error: function () {
								alert("Une erreur s'est produite. Veuillez reessayer.");
							},
							complete: function () {
								endLoader();
							}
						});
					},
					error: function () {
						endLoader();
						console.error("Une erreur est survenue. (Erreur serveur!)");
						alert("Impossible d'ajouter le fichier sur le serveur. Veuillez reessayer.");
						return;
					}
				});
			});


			$('[data-toggle="tooltip"]').tooltip();

			$(document).on("click", ".accesProd", function () {
				var cle = $(this).data("cle");
				var newForm = jQuery('<form>', {
					'action': '',
					'method': 'POST',
					'style': 'display:hidden'
				}).append(jQuery('<input>', {
					'name': 'cleSysteme',
					'value': cle,
					'type': 'text'
				})).append(jQuery('<button>', {
					'name': 'action',
					'value': "byCle",
					'type': 'submit'
				}));
				$("body").append(newForm);
				newForm.find("button[name='action']").click();
			});


			if ($("#searchValue").length) {
				var text = $("#searchValue").html();

				var table = $("table:first");
				highlightRecursive(table, text);
			}

			/**
			 * Fonction qui met a jour l'attribut data-initial des
			 * selects
			 */
			function changeInitialValues() {
				$("select").each(function () {
					var val = $(this).val();
					$(this).data("initial", val);
				});
			}

			/**
			 * Handler pour recuperer les sous familles, categories et
			 * sous categories quand l'utilisateur change la famille
			 */
			$(document).on("change", "select[name=famille]",
				function () {
					showLoader();
					var idFamille = $(this).val();
					var idInitial = $(this).data("initial");
					var data = {famille: idFamille};
					$.ajax({
						method: "post",
						data: data,
						dataType: "json",
						url: "GetListesHierarchie",
						success: function (json) {
							if (json.hasOwnProperty("error")) {
								console.error("ERROR");
								$("select[name=famille]").val(idInitial);
							} else {
								// Injection des resultats dans les
								// select correspondants
								var options = "";
								for (key in json["sousFamilles"]) {
									options += "<option value='" + key + "' >" + json["sousFamilles"][key] + "</option>";
								}
								$("select[name=sousFamille]").empty().append(options);
								var options = "";
								for (key in json["categories"]) {
									options += "<option value='" + key + "' >" + json["categories"][key] + "</option>";
								}
								$("select[name=categorie]").empty().append(options);
								var options = "";
								for (key in json["sousCategories"]) {
									options += "<option value='" + key + "' >" + json["sousCategories"][key] + "</option>";
								}
								$("select[name=sousCategorie]").empty().append(options);
								changeInitialValues();
							}
						},
						error: function () {
							console.error("ERROR");
							$("select[name=famille]").val(idInitial);
						},
						complete: function () {
							endLoader();
						}
					});
				});


			/**
			 * Handler pour recuperer la categorie et la sous categorie
			 * quand l'utilisateur change la sous famille
			 */
			$(document).on("change", "select[name=sousFamille]",
				function () {
					showLoader();
					var idSousFamille = $(this).val();
					var idInitial = $(this).data("initial");
					var data = {sousFamille: idSousFamille};
					$.ajax({
						method: "post",
						data: data,
						dataType: "json",
						url: "GetListesHierarchie",
						success: function (json) {
							if (json.hasOwnProperty("error")) {
								console.error("ERROR");
								$("select[name=sousFamille]").val(idInitial);
							} else {
								// Injection des resultats dans les
								// select correspondants
								var options = "";
								for (key in json["categories"]) {
									options += "<option value='" + key + "' >" + json["categories"][key] + "</option>";
								}
								$("select[name=categorie]").empty().append(options);
								var options = "";
								for (key in json["sousCategories"]) {
									options += "<option value='" + key + "' >" + json["sousCategories"][key] + "</option>";
								}
								$("select[name=sousCategorie]").empty().append(options);
								changeInitialValues();
							}
						},
						error: function () {
							console.error("ERROR");
							$("select[name=sousFamille]").val(idInitial);
						},
						complete: function () {
							endLoader();
						}
					});
				});
			/**
			 * Handler pour recuperer les sous categories quand
			 * l'utilisateur change la categorie
			 */
			$(document).on("change", "select[name=categorie]",
				function () {
					showLoader();
					var idCategorie = $(this).val();
					var idInitial = $(this).data("initial");
					var data = {categorie: idCategorie};
					$.ajax({
						method: "post",
						data: data,
						dataType: "json",
						url: "GetListesHierarchie",
						success: function (json) {
							if (json.hasOwnProperty("error")) {
								console.error("ERROR");
								$("select[name=categorie]").val(idInitial);
							} else {
								// Injection des resultats dans les
								// select correspondants
								var options = "";
								for (key in json["sousCategories"]) {
									options += "<option value='" + key + "' >" + json["sousCategories"][key] + "</option>";
								}
								$("select[name=sousCategorie]").empty().append(options);
								changeInitialValues();
							}
						},
						error: function () {
							console.error("ERROR");
							$("select[name=categorie]").val(idInitial);
						},
						complete: function () {
							endLoader();
						}
					});
				});

			/**
			 * Handler pour recuperer les gammes lorsuqe l'utilisateur
			 * change la marque
			 */
			$(document).on("change", "select[name=marque]", function () {
				showLoader();
				var idInitial = $(this).data("initial");
				var val = $(this).val();
				var data = {marque: val};
				$.ajax({
					method: "post",
					data: data,
					dataType: "json",
					url: "GetListeGamme",
					success: function (json) {
						if (json.hasOwnProperty("error")) {
							console.log("ERROR");
							$("select[name=marque]").val(idInitial);
						} else {
							// Injection des resultats dans les select
							// correspondants
							var options = "";
							for (key in json["gammes"]) {
								options += "<option value='" + key + "' >" + json["gammes"][key] + "</option>";
							}
							$("select[name=gamme]").empty().append(options);
							changeInitialValues();
						}
					},
					error: function () {
						console.error("ERROR");
						$("select[name=marque]").val(idInitial);
					},
					complete: function () {
						endLoader();
					}
				});
			});

			/**
			 * Handler pour charger les valeurs d'une caracteristique
			 * lorsqu'on change un select caracteristique
			 */
			$(document).on("change", "select[name=idC]", function () {
				showLoader();
				var val = $(this).val();
				var toChange = $(this).parent().next().find("[name=valeurC]:first");
				// Req ajax en fonction de la val
				$.ajax({
					url: "GetValeurCaracteristique",
					method: "post",
					data: {idC: val},
					dataType: "json",
					success: function (json) {
						if (json.hasOwnProperty("error")) {
							console.error(json.error);
						} else {
							// Prepa du select en html
							var html = "<select class='form-control' name='valeurC'>";
							for (key in json) {
								html += "<option value='" + json[key] + "' >" + json[key] + "</option>";
							}
							html += "</select>";
							if (toChange.is("input")) {
								html += "<small class='form-text text-muted'> <a class='valNotFound' href='#'>Je ne trouve pas ma valeur</a></small>";
							}
							// Injection des valeurs
							toChange.replaceWith(html);
						}
					},
					error: function () {
						console.error("error");
					},
					complete: function () {
						endLoader();
					}
				});
			});

			/**
			 * Handler pour charger un input text a la place du select
			 * lorsque l'utilisateur ne trouve pas sa valeur dans une
			 * valeur de caracteristique
			 */
			$(document).on("click", ".valNotFound", function () {
				var elmtToChange = $(this).parent().prev();
				var helpBlock = $(this).parent();
				var name = elmtToChange.attr("name");
				console.log(elmtToChange);
				swal({
					title: "Confirmation",
					text: "Etes vous certain(e) de ne pas trouver la valeur ?",
					icon: "warning",
					buttons: true,
					dangerMode: true
				}).then((willAgree) => {
					if (willAgree != null) {
						// Transformer le select en input
						elmtToChange.replaceWith("<input class='form-control' name='" + name + "' type='text' />");
						helpBlock.remove();
					}
				});
			});
			/**
			 * handler pour ajouter une ligne pour l'ajout ou la
			 * modification de caracteristique
			 *
			 */
			$(document).on("click", "#addChampCaracteristique", function () {
				var sample = $("#sampleCaracteristique").children(":first").clone();
				$(this).parent().before(sample);
			});

			$(document).on("click", ".removeLine", function () {
				$(this).parent().parent().remove();
			});

			/**
			 * Handler pour afficher le modal de recherche de produit
			 * pour l'ajouter a la liste des produits complementaires.
			 */
			$(document).on("click", "#recherchComplSimi", function () {

				var CompOuAssoc = $(this).data('type');
				$("#modal .modal-title").html("Rechercher un produit");
				var partCleSystem = '<div class="form-group"> <label>Saisir la clé système</label><div class="input-group"><input name="cle" type="text" class="form-control"><div class="input-group-prepend"><div id="addByCle" style="cursor: pointer;" class="input-group-text"><i class="fas fa-search"></i></div></div></div></div>';
				var partLibelle = '<div class="form-group" ><label>Rechercher</label><div class="input-group"><input name="cle" type="text" class="form-control"><div class="input-group-prepend"><div id="addByLibelle" style="cursor: pointer;" class="input-group-text"><i class="fas fa-search"></i></div></div></div></div>';
				$("#modal .modal-body").html(partCleSystem + partLibelle);
				$("#modal .modal-body").data("type", CompOuAssoc);
				$("#modal").modal();
			});

			/**
			 * Handler pour chercher un produit avec sa cle systeme pour
			 * les produits complémentaire ou alternatif
			 */
			$(document).on("click", "#addByCle", function () {
				showLoader();
				var val = $(this).parent().prev().val();
				var ligne = $(this).parent().parent().parent().parent();
				$.ajax({
					method: "post",
					dataType: "json",
					data: {action: "searchByCle", val: val},
					url: "AdminGestionProduitAssocies",
					success: function (json) {
						if (json.hasOwnProperty("error")) {
							swal("Erreur", json["error"], "error");
						} else {
							var produit = json["produit"];
							var id = produit["id"];
							var cleSystem = val;
							var cleOrvif = produit["codeOrvif"];
							var libelle = produit["libelle"];
							var html = "<div id='contentResult' ><div class='table-responsive'><table id='table-results-modal' class='table table-bordered'><thead><tr><th>Clé système</th><th>Référence orvif</th><th>Libellé</th><th>Sélection</th></tr></thead><tbody><tr data-id='" + id + "' ><td>" + cleSystem + "</td><td>" + cleOrvif + "</td><td>" + libelle + "</td><td><input type='checkbox' ></td></tr></tbody></table></div></div>";
							var btnValid = "<div class='text-center' ><button id='addProduct' class='btn btn-success'>Valider</button></div>";
							if ($("#contentResult").length) {
								$("#contentResult").replaceWith(html);
							} else {
								$("#modal .modal-body").append(html);
							}
							$("#contentResult").append(btnValid);
						}
					},
					error: function () {
						swal("Erreur", "Une erreur est survenue. Veuillez réessayer.", "error");
					},
					complete: function () {
						endLoader();
					}
				});
			});

			/**
			 * Handler pour ajouter un produit complementaire ou
			 * alternatif par id apres validation
			 */
			$(document).on("click", "#addProduct", function () {
				showLoader();
				// Recupération des produits cochés
				var trCollection = $("#table-results-modal").find("tr");
				var trs = [];
				var prodsToAdd = [];
				trCollection.each(function () {
					if ($(this).find("input:checked").length) {
						// Coché, on ajoute la ligne à la liste des trs
						// à ajouter
						trs.push($(this));
						prodsToAdd.push($(this).data("id"));
					}
				});
				dataId = {productsToAdd: prodsToAdd};
				var productBase = $("#modal .modal-body").data("id-base");
				var CompOuAssoc = $("#modal .modal-body").data("type");
				var action = "";
				if (CompOuAssoc == "complementaire") {
					action = "addProdComp";
				} else if (CompOuAssoc == "associe") {
					action = "addProdAssocie";
				}
				$.ajax({
					method: "post",
					dataType: "json",
					data: {action: action, productBase: productBase, productsToAdd: prodsToAdd},
					url: "AdminGestionProduitAssocies",
					success: function (json) {
						if (json.hasOwnProperty("error")) {
							swal("Erreur", json["error"], "error");
						} else {
							// Ajout réussi
							var table = "";
							var actionRemove = "";
							if (CompOuAssoc == "complementaire") {
								table = $("#table-prod-comp");
								actionRemove = "removeComplementary";
							} else if (CompOuAssoc == "associe") {
								table = $("#table-prod-assoc");
								actionRemove = "removeSimilar";
							}
							// Recuperation des lignes et ajout dans le
							// table
							var table = table.DataTable();
							index = 0;
							for (var k in trs) {
								var cle = "";
								var row = [];
								var tds = trs[k].find("td");
								tds.each(function (index) {
									if (index == 0) {
										// clé système -> on fait un
										// lien
										cle = $(this).html();
										var link = "<a href='adminUpdateProduct?action=byCle&cleSysteme=" + cle + "'>" + cle + "</a>";
										row.push(link);
									} else {
										if (!$(this).find("input[type=checkbox]").length) {
											row.push($(this).html());
										}
									}

								});
								var lastTd = "<i data-action='" + actionRemove + "' data-id='" + prodsToAdd[index] + "' data-idbase='" + productBase + "' style='cursor: pointer' class='fas fa-trash-alt'></i>";
								row.push(lastTd);
								table.row.add(row);
								index++;
							}
							table.draw();
							$("#modal").modal('hide');
							swal("Succès", "L'ajout a fonctionné.", "success");
						}
					},
					error: function () {
						swal("Erreur", "Une erreur est survenue. Veuillez réessayer.", "error");
					},
					complete: function () {
						endLoader();
					}
				});
			});

			/**
			 * Handler pour chercher un produit avec son libelle
			 */
			$(document).on("click", "#addByLibelle", function () {
				showLoader();
				var val = $(this).parent().prev().val();
				var CompOuAssoc = $("#modal .modal-body").data("type");
				var modal = $("#modal .modal-body");
				var productBase = $("#modal .modal-body").data("id-base");
				$.ajax({
					method: "post",
					dataType: "json",
					data: {action: "searchByText", val: val, productBase: productBase, compOuSimi: CompOuAssoc},
					url: "AdminGestionProduitAssocies",
					success: function (json) {
						if (json.hasOwnProperty("error")) {
							swal("Erreur", json["error"], "error");
						} else {
							var produits = json["produits"];
							var html = "<div id='contentResult' ><div class='table-responsive'><table id='table-results-modal' class='table table-bordered'><thead><tr><th>Clé système</th><th>Référence orvif</th><th>Libellé</th><th>Selection</th></tr></thead><tbody>";
							for (var key in produits) {
								var produit = produits[key];
								var id = produit["id"];
								var cleSystem = produit["cleSystem"];
								var cleOrvif = produit["codeOrvif"];
								var libelle = produit["libelle"];
								var tr = "<tr data-id='" + id + "' ><td>" + cleSystem + "</td><td>" + cleOrvif + "</td><td>" + libelle + "</td><td><input type='checkbox' ></td></tr>";
								html += tr;
							}
							html += "</tbody></table></div></div>";
							var btnValid = "<div class='text-center' ><button id='addProduct' class='btn btn-success'>Valider</button></div>";
							if ($("#contentResult").length) {
								$("#contentResult").replaceWith(html);
							} else {
								$("#modal .modal-body").append(html);
							}
							$("#contentResult").append(btnValid);
						}
					},
					error: function () {
						swal("Erreur", "Une erreur est survenue. Veuillez réessayer.", "error");
					},
					complete: function () {
						endLoader();
					}
				});
			});
		});

$(document).on("click", "[data-action=removeSimilar], [data-action=removeComplementary]", function () {
	showLoader();
	var tr = $(this).closest("tr");
	var action = $(this).data("action");
	var idToRemove = $(this).data("id");
	var removeFromId = $(this).data("idbase");
	var table = tr.closest("table").DataTable();
	$.ajax({
		url: "AdminGestionProduitAssocies",
		method: "post",
		dataType: "json",
		data: {action: action, productBase: removeFromId, val: idToRemove},
		success: function (json) {
			if (json.hasOwnProperty("error")) {
				swal("Erreur", json["error"], "error");
			} else {
				// Suppression reussi
				swal("Succes", "La suppression a bien été prise en compte.", "success");
				table.row(tr).remove().draw();
			}
		},
		error: function () {
			swal("Erreur", "Une erreur est survenue. Veuillez réessayer.", "error");
		},
		complete: function () {
			endLoader();
		}
	});
});

$(document).on("click", ".changeVisibility", function () {
	let element = $(this);
	let newVisibility = $(this).data("new_visibility") === "visible" ? "visible" : "!visible";
	let cleSysteme = $(this).data("key");
	showLoader();
	$.ajax({
		url: "adminChangeVisibility",
		method: "post",
		data: {cleSystem: cleSysteme, action: newVisibility},
		dataType: "json",
		success: function (result) {
			if (result.hasOwnProperty("error")) {
				swal("Erreur", "Une erreur est survenue. Veuillez réessayer s'il vous plait.", "error");
				console.error(result.error);
				endLoader();
				return;
			}
			swal("Succès", "La visibilité à été changée avec succès.", "success");
			element.data("new_visibility", newVisibility === "visible" ? "hidden" : "visible");
			element.prev().prev().text(newVisibility === "visible" ? "OUI" : "NON");
			element.text(newVisibility === "visible" ? "Cacher" : "Rendre visible");
			element.removeClass("btn-danger");
			element.removeClass("btn-success");
			element.addClass(newVisibility === "visible" ? "btn-danger" : "btn-success");
			endLoader();
		},
		error: function () {
			swal("Erreur", "Une erreur est survenue. Veuillez réessayer s'il vous plait.", "error");
			console.error("Erreur 500 !")
			endLoader();
		}
	})
});

/**
 * Handler pour la suppression d'un produit
 *
 * @returns
 */
$(document).on("click", "#removeProduct", function () {
	var idProduit = $(this).data("idprod");
	showLoader();
	$.ajax({
		url: "AdminGestionProduitAssocies",
		method: "post",
		dataType: "json",
		data: {action: "removeProduct", val: idProduit},
		success: function (json) {
			if (json.hasOwnProperty("error")) {
				swal("Erreur", json["error"], "error");
			} else {
				// Suppression reussi
				swal({
					title: "Succès",
					text: "La suppression a bien été prise en compte.",
					icon: "success"
				}).then((tmp) => {
					window.location.href = "adminUpdateProduct";
				});
			}
		},
		error: function () {
			swal("Erreur", "Une erreur est survenue. Veuillez réessayer.", "error");
		},
		complete: function () {
			endLoader();
		}
	});
});

//Handler pour l'affichage du formulaire d'ajout d'un compte admin
$(document).on("click", "#btnNewUser", function () {
	$("#modalNewUser").modal("toggle");
});

//Handler pour le traitement de l'ajout d'un compte admin après validation
$(document).on("click", "#validNewUsr", function () {
	showLoader();
	var nom = $("#nom").val();
	var prenom = $("#prenom").val();
	var login = $("#login").val();
	var password = $("#apassword").val();
	var password2 = $("#apassword2").val();
	var profil = $("#profil").val();
	$.ajax({
		url: "adminGestionUser",
		method: "post",
		data: {
			action: "newUser",
			nom: nom,
			prenom: prenom,
			login: login,
			password: password,
			password2: password2,
			profil: profil
		},
		success: function (json) {
			endLoader();
			if (json.hasOwnProperty("error")) {
				//Check si la la valeur associé à error est un texte ou autre objet json
				if (typeof json.error === "string" || json.error instanceof String) {
					$("#errorPlaceNewUser").empty().append(json["error"]);
				} else {
					$("#errorPlaceNewUser").empty();
					var i = 0;
					for (var err in json.error) {
						if (i > 0) {
							$("#errorPlaceNewUser").append("<br>" + json.error[err]);
						} else {
							$("#errorPlaceNewUser").append(json.error[err]);
							i++;
						}
					}
				}
				$("#errorPlaceNewUser").show();
			} else {
				swal("Succès", "Compte créé avec succès.", "success");
				location.reload();
				$(".modal").modal("hide");
			}
		},
		error: function () {
			endLoader();
			swal("Erreur", "Une erreur est survenue. Veuillez réessayer.", "error");
		}
	});
});


//Handler pour l'affichage du formulaire de modification de mot de passe d'un compte
$(document).on("click", ".updatePsw", function () {
	var id = $(this).data("iduser");
	$("#modalNewPassw").modal("toggle");
	$("#modalNewPassw").find("#validUpdatePsw").data("idUser", id);
});

//Handler pour le traitement de la modification de mot de passe d'un compte après validation
$(document).on("click", "#validUpdatePsw", function () {
	showLoader();
	var id = $(this).data("idUser");
	var password1 = $("#password").val();
	var password2 = $("#password2").val();
	$.ajax({
		url: "adminGestionUser",
		method: "post",
		data: {action: "newPsw", idUser: id, password: password1, password2: password2},
		dataType: "json",
		success: function (json) {
			endLoader();
			if (json.hasOwnProperty("error")) {
				$("#errorPlaceNewPassword").empty().append(json["error"]);
				$("#errorPlaceNewPassword").show();
			} else {
				swal("Succès", "Mot de passe mofifié avec succès.", "success");
				$(".modal").modal("hide");
			}
		},
		error: function () {
			endLoader();
			swal("Erreur", "Une erreur est survenue. Veuillez réessayer.", "error");
		}
	});
});

//Handler de traitement de suppression d'un utilisateur
$(document).on("click", ".removeUser", function () {
	showLoader();
	var id = $(this).data("iduser");
	var tr = $(this).closest("tr");
	$.ajax({
		url: "adminGestionUser",
		method: "post",
		data: {action: "removeUser", idUser: id},
		dataType: "json",
		success: function (json) {
			endLoader();
			if (json.hasOwnProperty("error")) {
				swal("Erreur", json["error"], "error");
			} else {
				swal("Succès", "L'utilisateur a été supprimé.", "success");
				tr.remove();
			}
		},
		error: function () {
			endLoader();
			swal("Erreur", "Une erreur est survenue. Veuillez réessayer.", "error");
		}
	});
});

//fonction d'affichage d'un loader
function showLoader() {
	$("html").css("overflow", "hidden");
	var top = $(window).scrollTop();
	$("#containerLoading").css('top', top + "px");
	$("#containerLoading").fadeIn("200");
}


//Fonction de fermeture du loader
function endLoader() {
	$("#loading .fas").removeClass("fa-sync").removeClass("fa-spin").addClass("fa-check");
	$("#containerLoading").fadeOut("700", function () {
		$("#loading .fas").removeClass("fa-check").addClass("fa-sync").addClass("fa-spin");
	});
	$("html").css("overflow", "auto");
}

function closeImgZoom() {
	$("#showImg").hide();
	$("html").css("overflow", "auto");
	$("#showImgSrc").attr('src', '');
	$("#removeDoc").attr("data-iddoc", "");
	$("#removeDoc").attr("data-idprod", "");
}


$(document).on("click", "#removeDoc, #removePdf", function () {

	var idDoc = $(this).data("iddoc");
	var idProd = $(this).data("idprod");
	swal({
		title: "Confirmation",
		text: "Etes vous certain(e) de vouloir supprimer ce document?",
		icon: "warning",
		buttons: true,
		dangerMode: true
	}).then((willAgree) => {
		if (willAgree != null) {
			// Suppression du doc/img
			showLoader();
			$.ajax({
				url: "gestionDocProduit",
				data: {idProd: idProd, idDoc: idDoc, action: "suppression"},
				method: "post",
				success: function (json) {
					if (json.hasOwnProperty("error")) {
						swal("Erreur", json.error, "error");
					} else {
						swal("OK", "Suppression effectuée.", "success");
						$("img[data-iddoc=" + idDoc + "]").parent().parent().remove();
						closeImgZoom();
						closeDocZoom();
					}
				},
				error: function () {
					swal("Erreur", "Une erreur est survenue, veuillez réessayer.", "error");
				},
				complete: function () {
					endLoader();
				}
			});
		}
	});
});


$(document).on("click", ".img-card.zoom", function () {
	$("html").css("overflow", "hidden");
	$("#showImgSrc").attr('src', $(this).attr('src'));
	$("#showImg").show();
	$("#removeDoc").data("iddoc", $(this).data("iddoc"));
	$("#removeDoc").data("idprod", $(this).data("idprod"));
	var top = $(window).scrollTop();
	$("#showImg").css('top', top + "px");
});

function closeDocZoom() {
	$("#showPdf").hide();
	$("html").css("overflow", "auto");
	$("#srcDoc").attr('src', '');
}

$(document).on("click", ".showPdf", function () {
	$("html").css("overflow", "hidden");
	$("#srcPdf").attr("src", $(this).data("srcpdf"));
	$("#showPdf").show();
	var top = $(window).scrollTop();
	$("#showPdf").css('top', top + "px");
	$("#removePdf").data("iddoc", $(this).data("iddoc"));
	$("#removePdf").data("idprod", $(this).data("idprod"));
});

$(document).on("click", "#closeModalDoc", function () {
	closeDocZoom();
});


$(document).on("click", "#closeModalImg", function () {
	closeImgZoom();
});


$(document).on("click", "#validAddImg", function () {
	showLoader();
	// Construction du formulaire pour l'envoyer à la page qui
	// s'occupe de l'upload
	var fa = new FormData();
	var placeToAddIfSuccess = $("#imageCollection");
	var elementToAdd = $("<div></div>").addClass("col-sm-3").append("<a class='d-block mb-4 h-100' ><img class='img-fluid img-thumbnail img-card zoom' src='' /></a>");
	fa.append("file", document.getElementById("fileImg").files['0']);
	fa.append("type", "image");
	var pathResult = "";
	var idProd = $(this).data("idprod");
	$.ajax({
		url: "http://client.orvif.fr/uploadFile.php",
		method: "POST",
		data: fa,
		cache: false,
		contentType: false,
		processData: false,
		success: function (result) {
			if (result === "error") {
				swal("Erreur", "Une erreur est survenue. Veuillez réessayer", "error");
			} else {
				// Image sauvegardé au chemin pathResult,
				// maintenant appel ajax de la servlet qui va
				// inserer l'info dans la bdd
				pathResult = result;
				var type = "image";
				var titre = $("#titleImg").val();
				var description = $("#descriptionImg").val();
				$.ajax({
					method: "post",
					url: "gestionDocProduit",
					data: {
						title: titre,
						type: type,
						description: description,
						path: pathResult,
						idProd: idProd,
						action: "ajout"
					},
					dataType: "json",
					success: function (json) {
						if (json.hasOwnProperty("error")) {
							// Ca n'a pas fonctionné, il faut
							// supprimer l'image uploadé sur
							// l'autre serveur
							$.ajax({
								url: "http://client.orvif.fr/deleteFile.php",
								method: "post",
								data: {PATH: pathResult}
							});
							swal("Erreur", json.error, "error");
						} else {
							// ça a fonctionné, ajout dynamique
							// de l'image dans la liste
							var idDoc = json.idDoc;
							var idProd = $("#removeProduct").data("idprod");
							elementToAdd.find("img").attr('src', 'http://client.orvif.fr/downloadFile.php?file=' + pathResult).data("idprod", idProd).data("iddoc", idDoc).attr("data-iddoc", idDoc);
							placeToAddIfSuccess.prepend(elementToAdd);
							$(".modal").modal("hide");
							swal("Succès", "L'ajout a fonctionné.", "success");
						}
					},
					error: function () {
						$.ajax({
							url: "http://client.orvif.fr/deleteFile.php",
							method: "post",
							data: {PATH: pathResult}
						});
						swal("Erreur", "Une erreur est survenue, veuillez réessayer.", "error");
					}
				});
			}
		},
		error: function () {
			swal("Erreur", "Une erreur est survenue. Veuillez réessayer", "error");
		},
		complete: function () {
			endLoader();
		}
	});
});


$(document).on("click", "#validAddDoc", function () {
	// Construction du formulaire pour l'envoyer à la page qui
	// s'occupe de l'upload
	showLoader();
	var fa = new FormData();
	var placeToAddIfSuccess = $("#pdfCollection");
	var elementToAdd = $("<div></div>").addClass("col-sm-3").append("<a class='d-block mb-4 h-100' ><img class='img-fluid img-thumbnail img-card showPdf' src='' /></a>");
	fa.append("file", document.getElementById("fileDoc").files['0']);
	fa.append("type", "doc");
	var pathResult = "";
	var idProd = $(this).data("idprod");
	$.ajax({
		url: "http://client.orvif.fr/uploadFile.php",
		method: "POST",
		data: fa,
		cache: false,
		contentType: false,
		processData: false,
		success: function (result) {
			if (result === "error") {
				swal("Erreur", "Une erreur est survenue. Veuillez réessayer", "error");
			} else {
				// Image sauvegardé au chemin pathResult,
				// maintenant appel ajax de la servlet qui va
				// inserer l'info dans la bdd
				pathResult = result;
				var type = "pdf";
				var titre = $("#titleDoc").val();
				var description = $("#descriptionDoc").val();
				$.ajax({
					method: "post",
					url: "gestionDocProduit",
					data: {
						title: titre,
						type: type,
						description: description,
						path: pathResult,
						idProd: idProd,
						action: "ajout"
					},
					dataType: "json",
					success: function (json) {
						if (json.hasOwnProperty("error")) {
							// Ca n'a pas fonctionné, il faut
							// supprimer l'image uploadé sur
							// l'autre serveur
							$.ajax({
								url: "http://client.orvif.fr/deleteFile.php",
								method: "post",
								data: {PATH: pathResult}
							});
							swal("Erreur", json.error, "error");
						} else {
							// ça a fonctionné, ajout dynamique
							// de l'image dans la liste
							var idDoc = json.idDoc;
							var idProd = $("#removeProduct").data("idprod");
							elementToAdd.find("img").attr('src', 'http://placehold.it/120x120&text=' + titre).data("idprod", idProd).data("iddoc", idDoc).attr("data-iddoc", idDoc).data("srcpdf", "http://client.orvif.fr/uploads/docs/" + pathResult);
							console.log(elementToAdd);
							console.log(placeToAddIfSuccess);
							placeToAddIfSuccess.prepend(elementToAdd);
							$(".modal").modal("hide");
							swal("Succès", "L'ajout a fonctionné.", "success");
						}
					},
					error: function () {
						$.ajax({
							url: "http://client.orvif.fr/deleteFile.php",
							method: "post",
							data: {PATH: pathResult}
						});
						swal("Erreur", "Une erreur est survenue, veuillez réessayer.", "error");
					}
				});
			}
		},
		error: function () {
			swal("Erreur", "Une erreur est survenue. Veuillez réessayer", "error");
		},
		complete: function () {
			endLoader();
		}
	});


});

$(document).on("click", "#addMissionLine", function () {
	let html = "<div class=\"form-group jobMissionWrapper\">\n" +
		"                <input class=\"form-control\" name=\"jobMission[]\" type=\"text\"/>\n" +
		"                <i style=\"color:red;cursor: pointer;\" class=\"deleteMissionLine fas fa-2x fa-times\"></i>\n" +
		"            </div>";
	$("#missionList").append(html);
});


$(document).on("click", ".deleteMissionLine", function () {
	$(this).parent().remove();
});

$(document).on("click", ".showMissions", function () {
	let idOffer = $(this).data("id");
	$.ajax({
		method: "get",
		dataType: "json",
		data: {jobOffer: idOffer},
		url: "careerManagement",
		success: function (result) {
			if (result.hasOwnProperty("error")) {
				alert(result.error);
			} else {
				let html = "<ul>";
				for (var mission of result.jobMissions) {
					html += "<li>" + mission + "</li>";
				}
				html += "</ul>";
				$("#modalMissions .modal-body").empty().append(html);
				$("#modalMissions").modal('toggle');
			}
		}
	});
});

$(document).on("click", ".deleteMissions", function () {
	let idOffer = $(this).data("id");
	let elmt = $(this);
	$.ajax({
		method: "delete",
		dataType: "json",
		//"data" option does not work with delete method
		url: "careerManagement?jobOffer=" + idOffer,
		success: function (result) {
			if (result.hasOwnProperty("error")) {
				alert(result.error);
			} else {
				document.location.reload();
			}
		},
		error: function () {
			alert("Une erreur est survenue, contactez le service informatique si le problème persiste.");
		}
	});
});


function highlightRecursive(element, text) {
	if (element.children().length > 0) {
		element.children().each(function () {
			if (!$(this).is("th")) {
				highlightRecursive($(this), text);
			}
		});
	} else {
		var regEx = new RegExp(text, "ig");
		element.html(element.html().replace(regEx,
			"<mark>" + text + "</mark>"));
	}
}
