<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="subtop" value="recrutement"/>
<c:set scope="request" var="bgSubtop"
       value="https://s1.lmcdn.fr/multimedia/1f1500847894/269345947f478/salles-de-bains-styles-et-tendances.jpg?$p=tbssrm"/>
<c:set scope="request" var="titleSubtop" value="Carri&egrave;re"/>
<c:set scope="request" var="arianeSubtop" value="Accueil > Recrutement"/>

<%@ include file="header.jsp" %>
<h1 id="carreerTitle">Carri&egrave;re</h1>
<p id="carreerDescription">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet blanditiis cupiditate
    dolores eos eveniet facere magni maiores mollitia nam, odit perspiciatis quis quo rem sequi sunt, voluptas voluptate
    voluptates.</p>
<ul id="listJob">
    <c:forEach items="${jobOfferList}" var="career">
        <li>
            <p>${career.jobTitle}</p>
            <a href="annonce${career.id}" class="button red-blue">Voir l'annonce</a>
        </li>
    </c:forEach>
</ul>

<%@ include file="footer.jsp" %>