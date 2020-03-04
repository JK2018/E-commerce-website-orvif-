<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="subtop" value="cart"/>
<c:set scope="request" var="bgSubtop"
       value="https://s1.lmcdn.fr/multimedia/1f1500847894/269345947f478/salles-de-bains-styles-et-tendances.jpg?$p=tbssrm"/>
<c:set scope="request" var="titleSubtop" value="Nos marques"/>
<c:set scope="request" var="arianeSubtop" value="Accueil > Nos marques"/>
<c:import url="header.jsp"/>

<!-- <p id="textBrands">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci consequatur, culpa cum cupiditate
dignissimos
distinctio eum facilis in iusto labore laboriosam minus molestias pariatur quo suscipit ut velit, veritatis
voluptates?</p> -->
<div id="brandsWrapper">
    <c:choose>
        <c:when test="${not empty marques }">
            <c:forEach items="${ marques }" var="m">
                <c:if test="${not empty m.logo.url}">
                    <div>
                        <div>
                            <img src="img/products/${m.logo.url}">
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>Aucune marque à afficher.</p>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="footer.jsp" %>