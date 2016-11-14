function inicializaMapa(postes) {
	var mapOptions = {
		center : new google.maps.LatLng(-5.831979, -35.205291),
		zoom : 15,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};

	var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

	var listaPostes = jQuery.parseJSON(postes);
	
	for(p in listaPostes){
		var poste = listaPostes[p];
		criaMarker(map, poste.descricaoPoste, poste.latitudePoste, poste.longitudePoste);
	}
}

function criaMarker(mapPoste, nomePoste, latitudePoste, longitudePoste) {
	var ponto = new google.maps.LatLng(latitudePoste, longitudePoste);
	var marker = new google.maps.Marker({
		position : ponto,// seta posição
		map : mapPoste,// Objeto mapa
		title : nomePoste
		//icon: caminho_da_imagem
	});
}

function loadScript(url, callback) {
	// Adding the script tag to the head as suggested before
	var head = document.getElementsByTagName('head')[0];
	var script = document.createElement('script');
	script.type = 'text/javascript';
	script.src = url;

	// Then bind the event to the callback function.
	// There are several events for cross browser compatibility.
	script.onreadystatechange = callback;
	script.onload = callback;

	// Fire the loading
	head.appendChild(script);
}

loadScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyDivW8PdvQcx8XAHp_pRdOHav0Wuxj1mWY", inicializaMapa);