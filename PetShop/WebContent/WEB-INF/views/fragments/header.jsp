<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/headerStyle.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<div id="header">
		<div class="container-logo">
			<div class="logo">
				<img alt="logo" src="${pageContext.request.contextPath}/assets/img/GUI/logo.png">
			</div>
		</div>
		<div class="navbar">
			<a class="home" href="${pageContext.request.contextPath}">HOME</a>
			<div class="dropdown">
				<button class="dropbtn" onclick="window.location.href = '${pageContext.request.contextPath}/prodotti?animale=cane';">Prodotti per cani</button>
				<div class="dropdown-content">
					<a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cibo" class="bold">Cibo</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cibo&tipologiaIn=secco">Cibo Secco</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cibo&tipologiaIn=secco%20dietetico">Cibo Secco Dietetico</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cibo&tipologiaIn=snack">Snack</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cibo&tipologiaIn=umido">Cibo Umido</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cibo&tipologiaIn=umido%20dietetico">Cibo Umido Dietetico</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=guinzaglierai" class="bold">Guinzagliera</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=guinzagliera&tipologiaIn=collari">Collari</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=guinzagliera&tipologiaIn=guinzagli">Guinzagli</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=guinzagliera&tipologiaIn=museruole">Museruole</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=guinzagliera&tipologiaIn=pettorine">Pettorine</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=guinzagliera&tipologiaIn=salvagenti">Salvagenti</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=giochi" class="bold">Giochi</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=giochi&tipologiaIn=agility%20%26%20sport">Agility Sport</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=giochi&tipologiaIn=intelligenti">Intelligenti</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=giochi&tipologiaIn=intrattenimento">Intrattenimento</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=igiene" class="bold">Igiene</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=igiene&tipologiaIn=casa">Casa</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=igiene&tipologiaIn=mutandine">Mutandine</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=igiene&tipologiaIn=palette%20e%20sacchetti">Palette e Sacchetti</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=igiene&tipologiaIn=panni">Panni</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=igiene&tipologiaIn=tappetini%20igienici">Tappetini Igienici</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=toelettatura" class="bold">Toelettatura</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=toelettatura&tipologiaIn=asciugamani">Asciugamani</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=toelettatura&tipologiaIn=dentifrici%20%26%20collutori">Dentifrici Collutori</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=toelettatura&tipologiaIn=deodoranti">Deodoranti</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=toelettatura&tipologiaIn=shampoo">Shampoo</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=toelettatura&tipologiaIn=spazzole">Spazzole</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=antiparassitari" class="bold">Antiparassitari</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=antiparassitari&tipologiaIn=collari">Collari</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=antiparassitari&tipologiaIn=linea%20naturale">Linea Naturale</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=antiparassitari&tipologiaIn=spot-on">Spot-On</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=antiparassitari&tipologiaIn=spray%20polvere">Spray Polvere</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=integratori" class="bold">Integratori</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=integratori&tipologiaIn=antiansia">Antiansia</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=integratori&tipologiaIn=condroprotettori">Condroprotettori</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=integratori&tipologiaIn=fermenti%20lattici">Fermenti Lattici</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=integratori&tipologiaIn=multivitaminici">Multivitaminici</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=integratori&tipologiaIn=sali%20minerali">Sali Minerali</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=auto" class="bold">Auto</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=auto&tipologiaIn=comfort">Comfort</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=auto&tipologiaIn=coprisedili">Coprisedili</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=auto&tipologiaIn=divisori">Divisori</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=auto&tipologiaIn=rampe">Rampe</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cucce%20lettini" class="bold">Cucce Lettini</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cucce%20lettini&tipologiaIn=brandine">Brandine</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cucce%20lettini&tipologiaIn=cucce%20esterne">Cucce Esterne</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cucce%20lettini&tipologiaIn=cucce%20interne">Cucce Interne</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=cucce%20lettini&tipologiaIn=cuscini">Cuscini</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=trasportini" class="bold">Trasportini</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=trasportini&tipologiaIn=borse">Borse</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=trasportini&tipologiaIn=trasportini%20morbidi">Trasportini Morbidi</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=trasportini&tipologiaIn=trasportini%20rigidi">Trasportini Rigidi</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=ciotole" class="bold">Ciotole</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=ciotole&tipologiaIn=accessori">Accessori</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=ciotole&tipologiaIn=ciotole">Ciotole</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=ciotole&tipologiaIn=distributori">Distributori</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=abbigliamento" class="bold">Abbigliamento</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=abbigliamento&tipologiaIn=cappottini">Cappottini</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=abbigliamento&tipologiaIn=impermeabili">Impermeabili</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=abbigliamento&tipologiaIn=scarpe">Scarpe</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=accessori" class="bold">Accessori:</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=accessori&tipologiaIn=abbigliamento%20veterinario">Abbigliamento Veterinario</a> <a href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=accessori&tipologiaIn=porte%20basculanti">Porte Basculanti</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=cane&tipologia=accessori&tipologiaIn=targhe">Targhe</a>
				</div>
			</div>

			<div class="dropdown">
				<button class="dropbtn" onclick="window.location.href = '${pageContext.request.contextPath}/prodotti?animale=gatto';">Prodotti per gatti</button>
				<div class="dropdown-content">
					<a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cibo" class="bold">Cibo</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cibo&tipologiaIn=secco">Secco</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cibo&tipologiaIn=secco%20dietetico">Secco Dietetico</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cibo&tipologiaIn=snack">Snack</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cibo&tipologiaIn=umido">Umido</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cibo&tipologiaIn=umido%20dietetico">Umido Dietetico</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=toelettatura" class="bold">Toelettatura</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=toelettatura&tipologiaIn=asciugamani">Asciugamani</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=toelettatura&tipologiaIn=dentifrici%20%26%20collutori">Dentifrici e Collutori</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=toelettatura&tipologiaIn=profumi%20ambienti">Profumi
						Ambienti</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=toelettatura&tipologiaIn=shampoo">Shampoo</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=toelettatura&tipologiaIn=spazzole">Spazzole</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=toelettatura&tipologiaIn=taglia%20unghie">Taglia Unghie</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=antiparassitari" class="bold">Antiparassitari</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=antiparassitari&tipologiaIn=collari">Collari</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=antiparassitari&tipologiaIn=linea%20naturale">Linea Naturale</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=antiparassitari&tipologiaIn=spot-on">Spot-On</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=antiparassitari&tipologiaIn=spray%20e%20polvere">Spray e Polvere</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=integratori" class="bold">Integratori</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=integratori&tipologiaIn=antiansia">Antiansia</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=integratori&tipologiaIn=cure%20del%20pelo">Cura del Pelo</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=integratori&tipologiaIn=fermenti%20lattici">Fermenti Lattici</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=integratori&tipologiaIn=multivitaminici">Multivitaminici</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=integratori&tipologiaIn=sali%20minerali">Sali Minerali</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=tiragraffi" class="bold">Tiragraffi</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=tiragraffi&tipologiaIn=arredo">Arredo</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=tiragraffi&tipologiaIn=tappetini">Tappetini</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=lettieri" class="bold">Lettieri</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=lettieri&tipologiaIn=accessori">Accessori</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=lettieri&tipologiaIn=agglomeranti">Agglomeranti</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=lettieri&tipologiaIn=cassette">Cassette</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=lettieri&tipologiaIn=non%20agglomeranti">Non Agglomeranti</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=lettieri&tipologiaIn=toilette">Toilette</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=igiene&tipologiaIn=casa">Casa</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=giochi" class="bold">Giochi</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=giochi&tipologiaIn=interattivi">Interattivi</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=giochi&tipologiaIn=intrattenimento">Intrattenimento</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=giochi&tipologiaIn=tunnel">Tunnel</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=guinzagliera" class="bold">Guinzagliera</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=guinzagliera&tipologiaIn=collari">Collari</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=guinzagliera&tipologiaIn=collari%20fashion">Collari Fashion</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=guinzagliera&tipologiaIn=guinzagli">Guinzagli</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=ciotole" class="bold">Ciotole</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=ciotole&tipologiaIn=accessori">Accessori</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=ciotole&tipologiaIn=ciotole">Ciotole</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=ciotole&tipologiaIn=distributori">Distributori</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=trasportini" class="bold">Trasportini</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=trasportini&tipologiaIn=borse">Borse</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=trasportini&tipologiaIn=trasportini%20morbidi">Trasportini Morbidi</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=trasportini&tipologiaIn=trasportini%20rigidi">Trasportini Rigidi</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cucce%20lettini" class="bold">Cucce e Lettini</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cucce%20lettini&tipologiaIn=brandine">Brandine</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cucce%20lettini&tipologiaIn=cucce%20esterne">Cucce Esterne</a> <a href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cucce%20lettini&tipologiaIn=cucce%20interne">Cucce Interne</a> <a
						href="${pageContext.request.contextPath}/prodotti?animale=gatto&tipologia=cucce%20lettini&tipologiaIn=cuscini">Cuscini</a>
				</div>
			</div>
			<div class="search-container">
				<c:choose>
					<c:when test='${empty sessionScope.nomeUtente || empty sessionScope.role}'>
						<c:set var="label" value="Ciao" />
					</c:when>
					<c:otherwise>
						<c:set var="label" value="${sessionScope.nomeUtente}" />
					</c:otherwise>
				</c:choose>

				<a href="${pageContext.request.contextPath}/login" class="cart-icon" style="border-right: 2px solid #333;"> <img src="${pageContext.request.contextPath}/assets/img/GUI/area-personale.png" alt="MYpetshop" />
				</a> <a href="carrello" class="cart-icon"><img src="${pageContext.request.contextPath}/assets/img/GUI/carrello.png" alt="Carrello" /></a>

				<button onclick="myFunction()" class="dropbtn-search">${label},cerca qui</button>
				<div id="myDropdown" class="dropdown-search-content">
					<div id="input-text">
						<input type="text" placeholder="Search.." id="myInput" onkeyup="myFunction2()" />
					</div>
					<div id="elements">
						<div id="element">
							<img alt="foto" src="${pageContext.request.contextPath}/assets/img/prodotti/1000.jpg">
							<h4>Titolo prodotto</h4>
							<span>12.0$</span>
						</div>
						<div id="element">
							<img alt="foto" src="${pageContext.request.contextPath}/assets/img/prodotti/1000.jpg">
							<h4>Titolo prodotto</h4>
							<span>12.0$</span>
						</div>
						<div id="element">
							<img alt="foto" src="${pageContext.request.contextPath}/assets/img/prodotti/1000.jpg">
							<h4>Titolo prodotto</h4>
							<span>12.0$</span>
						</div>
						<div id="element">
							<img alt="foto" src="${pageContext.request.contextPath}/assets/img/prodotti/1000.jpg">
							<h4>Titolo prodotto</h4>
							<span>12.0$</span>
						</div>
						<div id="element">
							<img alt="foto" src="${pageContext.request.contextPath}/assets/img/prodotti/1000.jpg">
							<h4>Titolo prodotto</h4>
							<span>12.0$</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
function myFunction() {
	  document.getElementById("myDropdown").classList.toggle("show");
	}

	function myFunction2(){
	  var links = document.getElementsByTagName("a");

	  for (var i = 0; i < links.length; i++) {
	    links[i].style.display = "block";
	  }
	}
</script>
</html>
