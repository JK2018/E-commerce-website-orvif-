<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="subtop" value="Contact"/>
<c:set scope="request" var="bgSubtop"
       value="https://s1.lmcdn.fr/multimedia/1f1500847894/269345947f478/salles-de-bains-styles-et-tendances.jpg?$p=tbssrm"/>
<c:set scope="request" var="titleSubtop" value="Contact"/>
<c:set scope="request" var="arianeSubtop" value="Accueil > Contact"/>
<%@ include file="header.jsp" %>

<h1 id="titleAgencies">Contactez-nous !</h1>

<p id="textAgencies">
    Pour vos questions, rapprochez-vous de votre agence habituelle
    ou envoyez un email &agrave; : infos.orvif@orvif.fr</p>
<p id="textAgencies">5 agences en Ile-De-France pour &ecirc;tre au plus proche de votre quotidien</p>

<div id="agenciesWrapper">
    <div>
        <div>
            <img src="${pageContext.request.contextPath}/img/BASTILLE.jpg">
            <h2>BASTILLE</h2>
            <p>8 rue du Biscornet<br/>75012, PARIS</p>
            <p>Tel : 01.43.41.15.21 | <a target="_blank"
                                         href="https://www.google.fr/maps/place/8+Rue+Biscornet,+75012+Paris/@48.8498303,2.3678559,17z/data=!3m1!4b1!4m5!3m4!1s0x47e67203c5bbcdfb:0x65a4f53b72106ea2!8m2!3d48.8498268!4d2.3700446">Plan
                d&rsquo;acc&egrave;s</a></p>
            <p>Fax : 01 44 74 34 30</p>
            <p>Horaires : 7h30-17h
                Sauf vendredi 14h30</p>
            <button class="button red-blue goContact" data-agence="BASTILLE">Contactez-nous</button>
        </div>
    </div>
    <div>
        <div>
            <img src="${pageContext.request.contextPath}/img/MONTREUIL.jpg">
            <h2>MONTREUIL</h2>
            <p>70-74 rue des Roches<br/>93100, MONTREUIL</p>
            <p>01.56.93.38.10 | <a target="_blank"
                                   href="https://www.google.fr/maps/place/70+Rue+des+Roches,+93100+Montreuil/@48.8714232,2.462672,17z/data=!3m1!4b1!4m5!3m4!1s0x47e612b662674245:0xf3afb6ea23f7f6eb!8m2!3d48.8714197!4d2.4648607">Plan
                d&rsquo;acc&egrave;s</a></p>
            <p>Fax : 01 56 93 38 28</p>
            <p>Horaires : 7h-12h
                & 13h30-17h15
                Sauf vendredi 15h45</p>
            <button class="button red-blue goContact" data-agence="MONTREUIL">Contactez-nous</button>
        </div>
    </div>
    <div>
        <div>
            <img src="${pageContext.request.contextPath}/img/MONTMARTRE.jpg">
            <h2>MONTMARTRE</h2>
            <p>49 rue Saint-Vincent<br/>75018, PARIS</p>
            <p>01.42.64.80.37 | <a target="_blank"
                                   href="https://www.google.fr/maps/place/49+Rue+Saint-Vincent,+75018+Paris/@48.8887738,2.3359917,17z/data=!3m1!4b1!4m5!3m4!1s0x47e66e5afa2aaf3d:0xc6e110c0f7c71ed9!8m2!3d48.8887703!4d2.3381804">Plan
                d&rsquo;acc&egrave;s</a></p>
            <p>Fax : 01 42 62 05 87</p>
            <p>Horaires : 7h30-17h
                Sauf vendredi 14h30</p>
            <button class="button red-blue goContact" data-agence="MONTMARTRE">Contactez-nous</button>
        </div>
    </div>
    <div>
        <div>
            <img src="${pageContext.request.contextPath}/img/GENTILLY.jpg">
            <h2>GENTILLY</h2>
            <p>24 av. Jean Jaur&egrave;s<br/>94250, GENTILLY</p>
            <p>01.49.08.82.00 | <a target="_blank"
                                   href="https://www.google.fr/maps/place/24+Avenue+Jean+Jaur&egrave;s,+94250+Gentilly/@48.8133387,2.348367,17z/data=!3m1!4b1!4m5!3m4!1s0x47e6717709f0224d:0x78570c6b3fafac8a!8m2!3d48.8133352!4d2.3505557">Plan
                d&rsquo;acc&egrave;s</a></p>
            <p>Fax : 01 49 08 82 22</p>
            <p>Horaires : 7h-12h
                & 13h30-17h15
                Sauf vendredi 15h45</p>
            <button class="button red-blue goContact" data-agence="GENTILLY">Contactez-nous</button>
        </div>
    </div>
    <div>
        <div>
            <img src="${pageContext.request.contextPath}/img/SAINTDENIS.jpg">
            <h2>SAINT-DENIS</h2>
            <p>1 route de la R&eacute;volte<br/>93200, SAINT-DENIS</p>
            <p>01.49.21.13.33 | <a target="_blank"
                                   href="https://www.google.fr/maps/place/1+Route+de+la+R&eacute;volte,+93200+Saint-Denis/@48.9240156,2.3420859,17z/data=!3m1!4b1!4m5!3m4!1s0x47e66ec42a4bc7e3:0xa6c6f1dea2902445!8m2!3d48.9240121!4d2.3442746">Plan
                d&rsquo;acc&egrave;s</a></p>
            <p>Fax : 01 49 21 13 21</p>
            <p>Horaires : 7h-12h
                & 13h30-17h15
                Sauf vendredi 15h45</p>
            <button class="button red-blue goContact" data-agence="SAINT-DENIS">Contactez-nous</button>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>