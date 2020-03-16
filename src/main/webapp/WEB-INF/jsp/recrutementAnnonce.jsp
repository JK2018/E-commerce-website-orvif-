<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="subtop" value="cart"/>
<c:set scope="request" var="bgSubtop"
       value="https://s1.lmcdn.fr/multimedia/1f1500847894/269345947f478/salles-de-bains-styles-et-tendances.jpg?$p=tbssrm"/>
<c:set scope="request" var="titleSubtop" value="Carri&egrave;re"/>
<c:set scope="request" var="arianeSubtop" value="Accueil > Recrutement"/>

<%@ include file="header.jsp" %>
<div id="descriptionJob">
    <h2>
        Description du poste
    </h2>
    <p>${jobOffer.jobDescription}</p>
</div>
<div id="listMissionJob">
    <h2>Vos missions</h2>
    <ul>
        <c:forEach items="${jobOffer.jobMissionsById}" var="theMission">
            <li><span class="circleRed"></span>${theMission.mission}
            </li>
        </c:forEach>
    </ul>
</div>

<%@ include file="footer.jsp" %>