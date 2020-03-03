<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no">
    <c:choose>
        <c:when test="${not empty metadata}">
            <title>${metadata.pageTitle}</title>
            <meta name="description" content="${metadata.pageDescription}"/>
        </c:when>
        <c:otherwise>
            <title>ORVIF</title>
        </c:otherwise>
    </c:choose>
    <title><c:if test="${not empty metadata}"></c:if></title>

    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/static.css">
    <link rel="stylesheet" type="text/css" href="css/orvifModal.css">
    <link rel="stylesheet" type="text/css" href="css/loader.css">
    <link rel="stylesheet" type="text/css" href="css/notification.css">
    <link rel="stylesheet" type="text/css" href="css/notificationProductAdded.css">
    <link rel="stylesheet" type="text/css" href="css/iconfont/css/fontello.css">
    <link rel="stylesheet" type="text/css" href="css/iconfont/css/animation.css">
    <link rel="stylesheet" type="text/css" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.1/dist/leaflet.css" integrity="sha512-Rksm5RenBEKSKFjgI3a41vrjkw4EVPlJ3+OiI65vTjIdo9brlAacEuKOiQ5OFh7cOI1bkDwLqdLw3Zg0cRJAAQ==" crossorigin=""/>

    <script src="https://unpkg.com/leaflet@1.3.1/dist/leaflet.js" integrity="sha512-/Nsx9X4HebavoBvEBuyp3I7od5tA0UzAxs+j83KgC8PU0kgB4XiK4Lfe4y4cgBtaRJQEIFCW+oC506aPT2L1zw==" crossorigin=""></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<c:if test="${not empty notifyAtLoad}">
<span id="notify-at-load" data-message="${notifyAtLoad[0]}" data-type="${notifyAtLoad[1]}"></span>
    <c:remove var="notifyAtLoad"></c:remove>
</c:if>

<body>
<div id="header">
    <div id="overlay-focus"></div>
    <div class="top">
        <a href="/"><img
                src="img/LOGO_ORVIF.png"></a>
        <div class="searchBox">
            <form method="get" action="search" accept-charset="UTF-8">
                <div id="containerSearch">
                    <span class="triangle-left"></span>
                    <input id="research" type="text" name="search"
                           placeholder="Rechercher un produit..." autocomplete="off"/>
                    <button>
                        <i class="icon-search"></i>
                    </button>
                </div>
            </form>
        </div>
        <div class="right">
            <c:choose>
                <c:when test="${ not empty sessionScope.client }">
                    <div class="flex relative">
                        <i class="icon-user fs-25"></i>&nbsp;<a id="myOptions"
                                                                href="#">&nbsp;<c:out
                            value="${sessionScope.client.nom} ${sessionScope.client.prenom}"/></a>&nbsp;&nbsp;&nbsp;<i
                            class="icon-down-open fs-20">
                    </i>
                        <div id="account-card">
                            <span class="triangle-up"></span>
                            <p class="title">Bonjour ${sessionScope.client.prenom}</p>
                            <ul class="links">
                                <li><a href="/monCompte"><i class="icon-user"></i>Espace client</a></li>
                                <c:choose>
                                    <c:when test="${sessionScope.client.type_client != 'particulier' && sessionScope.client.profil == 'acheteur'}">
                                        <li><a href="#"><i class="icon-ok"></i>Paniers en cours de validation</a></li>
                                    </c:when>
                                    <c:when test="${sessionScope.client.type_client != 'particulier' && sessionScope.client.profil == 'valideur'}">
                                        <li><a href="#"><i class="icon-ok"></i>Paniers &agrave; valider</a></li>
                                    </c:when>
                                </c:choose>
                                <li><a href="/logout"><i class="icon-logout"></i>D&eacute;connexion</a></li>
                            </ul>
                        </div>
                    </div>
                    <%--<a href="logout">D&eacute;connexion</a>--%>
                </c:when>
                <c:otherwise>
                    <div class="flex relative">
                        <i class="icon-user fs-25"></i>&nbsp;<a
                            href="#" id="myAccount">&nbsp;Mon compte</a>&nbsp;&nbsp;&nbsp;<i id="myAccount"
                            class="icon-down-open fs-25"></i>
                        <div id="login-card">
                            <span class="triangle-up"></span>
                            <form action="login" method="post" accept-charset="UTF-8">
                                <p class="title">Espace client</p>
                                <input name="login" autocomplete="username" type="text"
                                       placeholder="Nom d'utilisateur"/> <input name="psw"
                                                                                type="password"
                                                                                autocomplete="current-password"
                                                                                type="text"
                                                                                placeholder="Mot de passe"/> <a
                                    class="pswForgot"
                                    href="forgottenPassword">Mot de passe oubli&eacute;?</a> <a
                                    href="signup">Nouveau client?</a>
                                <button type="submit" class="button red-blue">CONNEXION</button>
                            </form>
                        </div>
                    </div>
                    &nbsp;&nbsp;&nbsp;
                    <!-- <a href="signup">Inscription</a> -->
                </c:otherwise>
            </c:choose>
            <div class="flex">
                <i id="iconCart" class="icon-cart fs-25"><span id="nbProductInCart">${panier.nbProduit}</span>
                </i>&nbsp;<a
                    href="cart">&nbsp;Mon panier</a>
            </div>
        </div>
    </div>

    <div class="bottom">
        <ul class="nav">
            <c:forEach items="${ familleCollection }" var="famille">
                <li class="familleItem"><a
                        href="listF${famille.idFamilles}__${famille.libelle }">${ famille.libelleCapitalized }</a>
                    <ul style="display: none;" class="sousFamilleList">
                        <c:forEach items="${famille.ssFamillesByIdFamilles}"
                                   var="sousFamille">
                            <li class="itemSousFamille"><span class="title"><a
                                    href="listSF${sousFamille.idSsfamilles}__${ famille.libelle }__${sousFamille.libelle}">${sousFamille.libelleCapitalized}</a></span>
                                <ul class="categorieList">
                                    <c:set var="compteur" value="0"/>
                                    <c:forEach items="${sousFamille.categoriesByIdSsfamilles }"
                                               var="categorie">
                                    <c:set var="compteur" value="${compteur + 1}"/>
                                    <c:if test="${compteur == 6}">
                                    <span style="display:none;" class="subFamList${sousFamille.idSsfamilles}">
                                        </c:if>
                                        <li class="itemCategorie"><a
                                                href="listC${categorie.idCategories}__${ famille.libelle }__${sousFamille.libelle}__${categorie.libelle }">${ categorie.libelleCapitalized }</a>
                                        </li>
                                    </c:forEach>
                                                <c:if test="${compteur > 5}">
                                            </span>
                                    <li class="itemCategorie">
                                        <a class="moreSubFam" data-visible="n"
                                           data-attachment=".subFamList${sousFamille.idSsfamilles}">+ de cat&eacute;gories</a>
                                    </li>
                                    </c:if>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
            <%--<li class="familleItem"><a href="catalogues">Catalogues--%>
            <%--orvif</a></li>--%>
            <li class="familleItem noOverview"><a href="marques">Nos marques</a></li>
            <li class="familleItem noOverview noHover destockNav"><a href="#">Destockage</a></li>
        </ul>
        <div class="viewSousFamille"></div>
        <c:if test="${not empty subtop}">
            <div id="subtop">
                <div class="contentWrapper">
                    <h3 id="title">${titleSubtop}</h3>
                    <p id="ariane">${arianeSubtop}</p>
                </div>
                <div style="display:flex;flex-direction: row; height:100%;">
                    <img src="img/banniere.png"/>
                    <img src="img/banniere.png"/>
                </div>
                <div class="shadow"></div>
            </div>
        </c:if>

    </div>


</div>
<div id="content">