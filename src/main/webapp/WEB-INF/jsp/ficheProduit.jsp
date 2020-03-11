<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="product" value="${groupe.products[0]}"/>
<c:set scope="request" var="subtop" value="fiche produit"/>
<c:set scope="request" var="bgSubtop"
       value="https://s1.lmcdn.fr/multimedia/1f1500847894/269345947f478/salles-de-bains-styles-et-tendances.jpg?$p=tbssrm"/>
<c:set scope="request" var="titleSubtop" value="${product.libelle}"/>
<c:set scope="request" var="arianeSubtop" value="${fn:toLowerCase(as)}"/>

<%@ include file="header.jsp" %>


<div id="topProductSheet">
    <c:choose>
        <c:when test="${fn:length(product.imageCollection) > 0}">
            <div id="imgContainer">
                <div id="mainImg">
                    <div style="display: flex;justify-content: center;align-items: center;width:100%;height:100%;">
                        <img src="img/products/${product.imageCollection[0].url}"/>
                    </div>
                </div>
                <c:if test="${fn:length(product.imageCollection) > 1}">
                    <div id="smallerImgs">
                        <c:forEach items="${product.imageCollection}" var="img" varStatus="status">
                            <div class="img <c:if test="${status.count == 1}"> active </c:if>">
                                <img src="img/products/${img.url}"/>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </c:when>
        <c:otherwise>
            <div id="imgContainer">
                <div id="mainImg">
                    <div style="display: flex;justify-content: center;align-items: center;width:100%;height:100%;">
                        <img src="img/products/nophoto.png">
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <div id="productInfos">
        <div class="quickfit" style="width:calc(100% - 100px); height:30px;">
            <span class="">${product.libelle}</span>
        </div>
        <hr/>
        <c:if test="${product.defi && not empty client && client.type_client != 'particulier'}">
            <div id="productAvantages">
                <img src="https://storage.googleapis.com/${applicationScope.static_files_bucket_name}/clubavantage.jpeg"/>
                <p>Ce produit fait partie des produits club avantages!</p>
            </div>
        </c:if>
        <p class="descriptionTitle">Description</p>
        <c:choose>
            <c:when test="${not empty product.descriptif}">
                <p class="description">${product.descriptif}</p>
            </c:when>
            <c:otherwise>
                <p class="description">Description non disponible pour cet article.</p>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty product.avantages}">
            <p class="descriptionTitle">Points forts</p>
            <p class="description">${product.avantages}</p>
        </c:if>
        <c:if test="${not empty product.marquesByIdMarques && product.marquesByIdMarques.display && not empty product.marquesByIdMarques.logo.url}">
            <img class="brandLogo"
                 src="https://storage.googleapis.com/${applicationScope.product_files_bucket_name}/${product.marquesByIdMarques.logo.url}">
        </c:if>
    </div>
</div>

<%--<div id="middleProductSheet">--%>
<%--<ul id="listOnglets">--%>
<%--<li class="onglet active" data-target="#description">Description</li>--%>
<%--<c:if test="${not empty product.complementairesObligatoire}">--%>
<%--<li class="onglet" data-target="#package">Contenu</li>--%>
<%--</c:if>--%>
<%--<li class="onglet" data-target="#caracteristiques">Caract&eacute;ristiques</li>--%>
<%--<li class="onglet" data-target="#comments">Commentaires acheteurs</li>--%>
<%--<li class="onglet" data-target="#advices">Conseils et services</li>--%>
<%--</ul>--%>
<%--<div style="display: block;" id="description">--%>
<%--${product.descriptif}--%>
<%--</div>--%>
<%--<c:if test="${not empty product.complementairesObligatoire}">--%>
<%--<div id="package">--%>
<%--<p class="description">Voici la composition de ce que vous recevrez en commandant ce produit:</p>--%>
<%--<br/><br/>--%>
<%--<div class="listPackage">--%>
<%--<div>--%>
<%--<p class="nb">1</p>--%>
<%--<div>--%>
<%--<div class="imgWrapper">--%>
<%--<img--%>
<%--src="https://images.unsplash.com/photo-1514626585111-9aa86183ac98?ixlib=rb-1.2.1&amp;w=1000&amp;q=80"/>--%>
<%--</div>--%>
<%--<p>--%>
<%--${product.libelle}--%>
<%--</p>--%>
<%--</div>--%>
<%--</div>--%>
<%--<c:forEach items="${product.complementairesObligatoire}" var="complementaire" varStatus="status">--%>
<%--<div>--%>
<%--<p class="plus">+</p>--%>
<%--<p class="nb">${complementaire.key}</p>--%>
<%--<div>--%>
<%--<div class="imgWrapper">--%>
<%--<img--%>
<%--src="https://images.unsplash.com/photo-1514626585111-9aa86183ac98?ixlib=rb-1.2.1&amp;w=1000&amp;q=80"/>--%>
<%--</div>--%>
<%--<p>--%>
<%--${complementaire.value.libelle}--%>
<%--</p>--%>
<%--</div>--%>
<%--</div>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--</div>--%>
<%--</c:if>--%>
<%--<div id="caracteristiques">--%>
<%--<c:choose>--%>
<%--<c:when test="${not empty product.caracteristiqueCollection}">--%>
<%--Liste des caracteristiques :--%>
<%--<ul>--%>
<%--<c:forEach items="${product.caracteristiqueCollection }"--%>
<%--var="caracteristique">--%>
<%--<li>--%>
<%--<p>${ caracteristique.libelle}:--%>
<%--${caracteristique.valeurProduit }</p>--%>
<%--</li>--%>
<%--</c:forEach>--%>
<%--</ul>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<p>Pas de caracteristiques pour ce produit</p>--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--</div>--%>
<%--<div id="comments">--%>
<%--Commentaires--%>
<%--</div>--%>
<%--<div id="advices">--%>
<%--<c:choose>--%>
<%--<c:when test="${not empty product.caracteristiqueCollection}">--%>
<%--Liste des caracteristiques :--%>
<%--<ul>--%>
<%--<c:forEach items="${product.caracteristiqueCollection }"--%>
<%--var="caracteristique">--%>
<%--<li>--%>
<%--<p>${ caracteristique.libelle}:--%>
<%--${caracteristique.valeurProduit }</p>--%>
<%--</li>--%>
<%--</c:forEach>--%>
<%--</ul>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<p>Pas de caracteristiques pour ce produit</p>--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--</div>--%>
<%--</div>--%>

<div id="references">
    <table>
        <thead>
        <tr>
            <th>R&eacute;f&eacute;rence</th>
            <c:forEach items="${product.caracteristiqueCollection}" var="caracteristique">
                <th>${caracteristique.libelle}</th>
            </c:forEach>
            <th>Prix unitaire</th>
            <th>Disponibilit&eacute;</th>
            <th class="quantityColumn">Quantit&eacute;</th>
            <th class="addToCartColumn"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${groupe.products}" var="prod">
            <tr <c:if test="${not empty highlightRef && highlightRef == prod.codeOrvif}">class="highlightRef"</c:if>>

                <td>${prod.codeOrvif}</td>
                <c:forEach items="${prod.caracteristiqueCollection}" var="caracteristique">
                    <td>${caracteristique.valeurProduit}</td>
                </c:forEach>
                <td>
                    <c:choose>
                        <c:when
                                test="${ not empty client && client.type_client != 'particulier' }">
                            ${ prod.ppht }&euro;
                        </c:when>
                        <c:otherwise>${ prod.ppttc }&euro;</c:otherwise>
                    </c:choose>
                </td>
                <td style="display:flex;align-items: center;justify-content: center;">
                    <div class="circle <c:choose><c:when test="${prod.available}">available tooltip</c:when><c:otherwise>notAvailable</c:otherwise></c:choose>">
                        <c:if test="${prod.available}">
                        <span class="tooltiptext"><c:forEach items="${prod.stockCapitalized}" var="stock">
                            <p style="white-space: nowrap;"><span>${stock.key}</span><span>${stock.value}</span></p>
                        </c:forEach></span>
                        </c:if>
                    </div>

                </td>
                <td>
                    <c:if test="${prod.available}">
                        <div class="quantityProductSheet">
                            <span class="controlProductSheet minusOne">&minus;</span>
                            <input class="qteProductSheet" value='1'/>
                            <span class="controlProductSheet plusOne">&plus;</span>
                        </div>
                    </c:if>
                </td>
                <td style="vertical-align: middle;">
                    <c:if test="${prod.available}">
                        <button data-id="${prod.idProduits}" data-nb="1" class="addToCart button red">Ajouter au panier</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<c:if test="${not empty product.complementaryProduct}">
    <div id="complementaryProducts">
        <h2>Produits compl&eacutementaires</h2>
        <div class="wrapperProducts">
            <ul style="width : calc(${fn:length(product.complementaryProduct) * 20}% - 10px);">
                <c:forEach items="${product.complementaryProduct }"
                           var="produit">
                    <div class="productCard <c:if test="${!produit.available}" >disabled</c:if>">
                        <div class="imgProd">
                            <img
                                    src="https://cache.destock-design.fr/client/gfx/photos/produit/BLISSA.jpg"/>
                            <c:if test="${!produit.available}">
                                <span id="productNotAvailable">Produit non disponible</span>
                            </c:if>
                        </div>
                        <p class="libelle">${ produit.libelle }</p>
                        <p class="price">
                            <c:choose>
                                <c:when
                                        test="${ not empty client && client.type_client != 'particulier' }">Prix HT:
                                    <br/><span
                                            class="font-red">${ produit.ppht }&euro;</span> / Pi&egrave;ce</c:when>
                                <c:otherwise>Prix TTC: <br/><span
                                        class="font-red">${ produit.ppttc }&euro;</span> / Pi&egrave;ce</c:otherwise>
                            </c:choose>
                        </p>
                        <div class="group-btn">
                            <a class="button red detailBtn"
                               href="P${produit.idProduits }__${produit.libelle}">D&eacute;tails</a>
                            <c:if test="${produit.available}">
                                <button class="button blue addToCart" data-id="${produit.idProduits}" data-nb="1">
                                    <i class="icon-add-to-cart"></i>
                                </button>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </ul>
        </div>
    </div>
</c:if>

<c:if test="${not empty product.similarProduct}">
    <div id="similarProducts">
        <h2>Produits similaires</h2>
        <div class="wrapperProducts">
            <ul style="width : calc(${fn:length(product.similarProduct) * 20}% - 10px);">
                <c:forEach items="${product.similarProduct }"
                           var="produit">
                    <div class="productCard <c:if test="${!produit.available}" >disabled</c:if>">
                        <div class="imgProd">
                            <img
                                    src="https://cache.destock-design.fr/client/gfx/photos/produit/BLISSA.jpg"/>
                            <c:if test="${!produit.available}">
                                <span id="productNotAvailable">Produit non disponible</span>
                            </c:if>
                        </div>
                        <p class="libelle">${ produit.libelle }</p>
                        <p class="price">
                            <c:choose>
                                <c:when
                                        test="${ not empty client && client.type_client != 'particulier' }">Prix HT:
                                    <br/><span
                                            class="font-red">${ produit.ppht }&euro;</span> / Pi&egrave;ce</c:when>
                                <c:otherwise>Prix TTC: <br/><span
                                        class="font-red">${ produit.ppttc }&euro;</span> / Pi&egrave;ce</c:otherwise>
                            </c:choose>
                        </p>
                        <div class="group-btn">
                            <a class="button red detailBtn"
                               href="P${produit.idProduits }__${produit.libelle}">D&eacute;tails</a>
                            <c:if test="${produit.available}">
                                <button class="button blue addToCart" data-id="${produit.idProduits}" data-nb="1">
                                    <i class="icon-add-to-cart"></i>
                                </button>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </ul>
        </div>
    </div>
</c:if>


<%--<div>--%>
<%--<p>Libelle : ${product.libelle }</p>--%>
<%--<p>Description : ${product.descriptif }</p>--%>
<%--<p>Avantages : ${product.avantages }</p>--%>
<%--<p>--%>
<%--Prix :--%>
<%--<c:choose>--%>
<%--<c:when--%>
<%--test="${ not empty client && client.type_client != 'particulier' }">${ product.ppht }&euro; HT</c:when>--%>
<%--<c:otherwise>${ product.ppttc }&euro; TTC</c:otherwise>--%>
<%--</c:choose>--%>
<%--</p>--%>
<%--<p>R�f�rence orvif : ${product.codeOrvif }</p>--%>
<%--<p>R�f�rence fournisseur : ${product.refFournisseur }</p>--%>
<%--<h2>Images</h2>--%>
<%--<c:choose>--%>
<%--<c:when test="${not empty product.imageCollection }">--%>
<%--Liste des images :--%>
<%--<ul>--%>
<%--<c:forEach items="${ product.imageCollection }" var="img">--%>
<%--<li>Url de l'image : ${ img.url }</li>--%>
<%--</c:forEach>--%>
<%--</ul>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--Pas d'images pour ce produit--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--<h2>Autres documents</h2>--%>
<%--<c:choose>--%>
<%--<c:when test="${not empty product.otherDocCollection}">--%>
<%--Liste des doc :--%>
<%--<ul>--%>
<%--<c:forEach items="${product.otherDocCollection }" var="doc">--%>
<%--<li>--%>
<%--<p>Titre : ${ doc.titre }</p>--%>
<%--<p>Description : ${ doc.description }</p>--%>
<%--<p>url: ${ doc.url }</p>--%>
<%--<p>type: ${ doc.type }</p>--%>
<%--</li>--%>
<%--</c:forEach>--%>
<%--</ul>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<p>Pas d'autres doc pour ce produit</p>--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>

<%--<h2>Caracteristiques</h2>--%>
<%--<c:choose>--%>
<%--<c:when test="${not empty product.caracteristiqueCollection}">--%>
<%--Liste des caracteristiques :--%>
<%--<ul>--%>
<%--<c:forEach items="${product.caracteristiqueCollection }"--%>
<%--var="caracteristique">--%>
<%--<li>--%>
<%--<p>${ caracteristique.libelle}:--%>
<%--${caracteristique.valeurProduit }</p>--%>
<%--</li>--%>
<%--</c:forEach>--%>
<%--</ul>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<p>Pas de caracteristiques pour ce produit</p>--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--<h2>Autres produits du m&ecirc;me groupe</h2>--%>
<%--<c:choose>--%>
<%--<c:when test="${not empty product.groupe.products}">--%>
<%--<c:forEach items="${ product.groupe.products }" var="p">--%>
<%--<ul>--%>
<%--<li>${p.id } - ${ p.libelle }</li>--%>
<%--</ul>--%>
<%--</c:forEach>--%>
<%--Criteres :--%>
<%--<c:forEach items="${ product.groupe.criteres }" var="c">--%>
<%--<ul>--%>
<%--<li>${c.id} - ${c.libelle}</li>--%>
<%--</ul>--%>
<%--</c:forEach>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--Pas de produit du m&ecirc;me groupe.--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>

<%--<h2>Produits complementaire</h2>--%>
<%--<c:choose>--%>
<%--<c:when test="${not empty product.complementaryProduct}">--%>
<%--Liste des produits complementaires :--%>
<%--<ul>--%>
<%--<c:forEach items="${product.complementaryProduct }"--%>
<%--var="complementary">--%>
<%--<li>--%>
<%--<p>Libelle : ${ complementary.libelle}</p>--%>
<%--<p>--%>
<%--<a href="produit?p=${ complementary.id }">Fiche produit</a>--%>
<%--</p>--%>
<%--</li>--%>
<%--</c:forEach>--%>
<%--</ul>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<p>Pas de produits complementaires pour ce produit</p>--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>

<%--<h2>Produits alternatifs</h2>--%>
<%--<c:choose>--%>
<%--<c:when test="${not empty product.similarProduct}">--%>
<%--Liste des produits alternatifs :--%>
<%--<ul>--%>
<%--<c:forEach items="${product.similarProduct }" var="similar">--%>
<%--<li>--%>
<%--<p>Libelle : ${ similar.libelle}</p>--%>
<%--<p>--%>
<%--<a href="produit?p=${ similar.id }">Fiche produit</a>--%>
<%--</p>--%>
<%--</li>--%>
<%--</c:forEach>--%>
<%--</ul>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<p>Pas de produits alternatifs pour ce produit</p>--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>


<%--<button class="addToCart" data-id="${ product.idProduits }" data-nb="1">Ajouter--%>
<%--au panier--%>
<%--</button>--%>

<%--<h2>Stocks</h2>--%>
<%--<ul>--%>
<%--<c:forEach items="${product.stocks}" var="stock">--%>
<%--<li>${stock.key} : ${stock.value}</li>--%>
<%--</c:forEach>--%>
<%--</ul>--%>

<%--<p>Available pour 1 : ${product.isAvailableForDrive(1)}</p>--%>
<%--<p>Available pour 50 : ${product.isAvailableForDrive(50)}</p>--%>
<%--<p>Available pour 192 : ${product.isAvailableForDrive(192)}</p>--%>
<%--<p>Available pour 193 : ${product.isAvailableForDrive(193)}</p>--%>
<%--<p>Available pour 200 : ${product.isAvailableForDrive(200)}</p>--%>
<%--<p>Available pour 2000 : ${product.isAvailableForDrive(2000)}</p>--%>

<%--</div>--%>

<c:set var="scripts" scope="request" value="/plugins/jquery.zoom.min.js,/js/productSheet.js"/>

<%@ include file="footer.jsp" %>
