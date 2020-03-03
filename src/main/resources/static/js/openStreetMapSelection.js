let lat = 48.746453;
let lon = 2.391279;

var macarte = null;
var agences = {
	"Montreuil": {"lat": 48.871409, "lon": 2.464823},
	"Bastille": {"lat": 48.849846, "lon": 2.369973},
	"Orly": {"lat": lat, "lon": lon},
	"Gentilly": {"lat": 48.813408, "lon": 2.350639},
	"Montmartre": {"lat": 48.888767, "lon": 2.338162}
};

let agencySelected = agences.Orly;
let popupOpening = false;

// Fonction d'initialisation de la carte
function initMap() {
	// Créer l'objet "macarte" et l'insèrer dans l'élément HTML qui a l'ID "map"
	macarte = L.map('map').setView([lat, lon], 11);
	// Leaflet ne récupère pas les cartes (tiles) sur un serveur par défaut. Nous devons lui préciser où nous souhaitons les récupérer. Ici, openstreetmap.fr
	L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
		// Il est toujours bien de laisser le lien vers la source des données
		attribution: 'données © <a href="//osm.org/copyright">OpenStreetMap</a>/ODbL - rendu <a href="//openstreetmap.fr">OSM France</a>',
		minZoom: 4,
		maxZoom: 20
	}).addTo(macarte);

	// Nous parcourons la liste des villes
	for (agence in agences) {
		let marker = L.marker([agences[agence].lat, agences[agence].lon]).addTo(macarte);
		marker.bindPopup("<div style='background-color: red;height:100px;width:100px;'>" + agence + "</div>");
		marker.on("popupclose", function (event) {
			if (popupOpening) {
				console.log("popup opening");
				popupOpening = false;
			} else {
				console.log("NO POPUP OPENING !");
				return;
			}
		});
		marker.on("click", function (event) {
			agencySelected = agences[agence];
			popupOpening = true;
			console.log("click on popup")
		});
		if(agence === "Orly"){
			marker.openPopup();
		}
	}
}

window.onload = function () {
	// Fonction d'initialisation qui s'exécute lorsque le DOM est chargé
	initMap();
};