
<%@ include file="header.jsp" %>
<div id="slideshowWrapper">
    <div id="slideshow" data-activeCaption="1" style="width:${fn:length(slideshowImageList)*100}%;">
        <c:forEach items="${slideshowImageList}" var="slideshowImage">
            <div class="imgWrapper">
                <img src="img/slideShow/${slideshowImage}">
            </div>
        </c:forEach>

    </div>
    <span id="wrapperDots">
        <span class="dot active" data-target="1"></span>
        <c:forEach var="i" begin="2" end="${fn:length(slideshowImageList)}">
            <span class="dot" data-target="${i}"></span>
        </c:forEach>
</span>
</div>

<div class="bandeauInfos">
    <div>
        <i class="icon icon-location"></i>
        <div class="quickfit onlyWidth" style="width:100%;height:50px;"> <span class="text">6 adresses
		</span>
        </div>
    </div>
    <div>
        <i class="icon icon-box"></i>
        <div class="quickfit onlyWidth" style="width:100%;height:50px;"><span class="text">10
        000 r&eacute;f&eacute;rences</span></div>
    </div>
    <div>
        <i class="icon icon-users"></i>
        <div class="quickfit onlyWidth" style="width:100%;height:50px;"><span class="text">160
        experts m&eacute;tiers</span></div>
    </div>
    <div>
        <i class="icon icon-truck"></i>
        <div class="quickfit onlyWidth" style="width:100%;height:50px;"> <span class="text">Livraison
        &agrave; la &#189; journ&eacute;e sur IDF *</span></div>
    </div>
    <div>
        <i class="icon icon-mouse"></i>
        <div class="quickfit onlyWidth" style="width:100%;height:50px;"><span class="text">Click
        & collect 1h</span></div>
    </div>
</div>









<div class="bandeauProduit">
    <div class="bandeauHeader">
        <span class="left">S&eacute;lection produits</span>
    </div>

    <div class="products">
        <c:forEach items="${groups}" var="group">
            <c:set var="produit" value="${group.products.get(0)}"/>


            <div class="productCard <c:if test="${!produit.available && group.products.size() > 1}" >disabled</c:if>">
                <div class="imgProd">
                    <c:if test="${!produit.available}">
                        <span id="productNotAvailable">Produit non disponible</span>
                    </c:if>
                    <img
                            <c:choose>
                                <c:when test="${fn:length(produit.imageCollection) > 0}">
                                    src="img/products/${produit.imageCollection[0].url}"
                                </c:when>
                                <c:otherwise>
                                    src="img/nophoto.png"
                                </c:otherwise>
                            </c:choose>
                    />

                    <c:if test="${produit.available && produit.defi}">
                        <span id="productNotAvailable">Club avantages !</span>
                    </c:if>
                </div>
                <p class="libelle">${ produit.libelle }</p>
                <p class="price">
                    <c:choose>
                        <%-- -------------------- NOT PARTICULIER ------------------- --%>
                        <c:when
                                test="${ not empty client && client.type_client != 'particulier' }">
                            <c:choose>
                                <c:when test="${fn:length(group.products) == 1}">
                                    Prix HT: <br/><span
                                        class="font-red">${ produit.ppht }&euro;</span>
                                </c:when>
                                <c:otherwise>
                                    Prix HT: <br/><span
                                        class="font-red">${ group.minPriceHT } - ${ group.maxPriceHT }&euro;</span>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <%-- -----------------PARTICULIER (DEFAULT WHEN NOT CONNECTED)------------------ --%>
                        <c:otherwise>
                            <c:choose>
                                <%-- if single product --%>
                                <c:when test="${fn:length(group.products) == 1}">
                                    Prix TTC: <br/><span
                                        class="font-red">${ produit.ppttc }&euro;</span>
                                </c:when>
                                <%-- if several product (diff size etc) --%>
                                <c:otherwise>
                                    Prix TTC: <br/><span
                                        class="font-red">${ group.minPriceTTC } - ${ group.maxPriceTTC }&euro;</span>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </p>
                <div class="group-btn">
                    <a class="button red detailBtn" href="P${produit.idProduits }__${produit.libelleUrl}">+ de
                        d&eacute;tails</a>
                    <c:if test="${produit.available && fn:length(group.products) == 1}">
                        <button class="button blue addToCart" data-id="${produit.idProduits}" data-nb="1">
                            <i
                                    class="icon-add-to-cart"></i></button>
                    </c:if>
                </div>
            </div>

        </c:forEach>
    </div>
</div>










<div class="bandeauProduit">
    <div class="bandeauHeader">
        <span class="left">Nouveaut&eacute;s</span>
    </div>
    <div class="products">
        <c:forEach items="${groups}" var="group">
            <c:set var="produit" value="${group.products[0]}"/>
            <div class="productCard <c:if test="${!produit.available && fn:length(group.products) == 1}" >disabled</c:if>">
                <div class="imgProd">
                    <img

                            <c:choose>
                                <c:when test="${fn:length(produit.imageCollection) > 0}">
                                    src="img/products/${produit.imageCollection[0].url}"
                                </c:when>
                                <c:otherwise>
                                    src="img/nophoto.png"
                                </c:otherwise>
                            </c:choose>
                    />
                    <c:if test="${!produit.available}">
                        <span id="productNotAvailable">Produit non disponible</span>
                    </c:if>
                    <c:if test="${produit.available && produit.defi}">
                        <span id="productNotAvailable">Club avantages !</span>
                    </c:if>
                </div>
                <p class="libelle">${ produit.libelle }</p>
                <p class="price">
                    <c:choose>
                        <c:when
                                test="${ not empty client && client.type_client != 'particulier' }">
                            <c:choose>
                                <c:when test="${fn:length(group.products) == 1}">
                                    Prix HT: <br/><span
                                        class="font-red">${ produit.ppht }&euro;</span>
                                </c:when>
                                <c:otherwise>
                                    Prix HT: <br/><span
                                        class="font-red">${ group.minPriceHT } - ${ group.maxPriceHT }&euro;</span>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${fn:length(group.products) == 1}">
                                    Prix TTC: <br/><span
                                        class="font-red">${ produit.ppttc }&euro;</span>
                                </c:when>
                                <c:otherwise>
                                    Prix TTC: <br/><span
                                        class="font-red">${ group.minPriceTTC } - ${ group.maxPriceTTC }&euro;</span>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </p>
                <div class="group-btn">
                    <a class="button red detailBtn" href="P${produit.idProduits }__${produit.libelleUrl}">+ de
                        d&eacute;tails</a>
                    <c:if test="${produit.available && fn:length(group.products) == 1}">
                        <button class="button blue addToCart" data-id="${produit.idProduits}" data-nb="1">
                            <i
                                    class="icon-add-to-cart"></i></button>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
</div>











<div class="bandeauProduit">
    <div class="bandeauHeader">
        <span class="left">Promotions</span>
    </div>
    <div class="products">
        <c:forEach items="${groups}" var="group">
            <c:set var="produit" value="${group .products[0]}"/>
            <div class="productCard <c:if test="${!produit.available && fn:length(groupe.products) > 1}" >disabled</c:if>">
                <div class="imgProd">
                    <img
                            <c:choose>
                                <c:when test="${fn:length(produit.imageCollection) > 0}">
                                    src="img/products/${produit.imageCollection[0].url}"
                                </c:when>
                                <c:otherwise>
                                    src="img/nophoto.png"
                                </c:otherwise>
                            </c:choose>
                    />
                    <c:if test="${!produit.available}">
                        <span id="productNotAvailable">Produit non disponible</span>
                    </c:if>
                    <c:if test="${produit.available && produit.defi}">
                        <span id="productNotAvailable">Club avantages !</span>
                    </c:if>
                </div>
                <p class="libelle">${ produit.libelle }</p>
                <p class="price">
                    <c:choose>
                        <c:when
                                test="${ not empty client && client.type_client != 'particulier' }">
                            <c:choose>
                                <c:when test="${fn:length(group.products) == 1}">
                                    Prix HT: <br/><span
                                        class="font-red">${ produit.ppht }&euro;</span>
                                </c:when>
                                <c:otherwise>
                                    Prix HT: <br/><span
                                        class="font-red">${ group.minPriceHT } - ${ group.maxPriceHT }&euro;</span>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${fn:length(group.products) == 1}">
                                    Prix TTC: <br/><span
                                        class="font-red">${ produit.ppttc }&euro;</span>
                                </c:when>
                                <c:otherwise>
                                    Prix TTC: <br/><span
                                        class="font-red">${ group.minPriceTTC } - ${ group.maxPriceTTC }&euro;</span>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </p>
                <div class="group-btn">
                    <a class="button red detailBtn" href="P${produit.idProduits }__${produit.libelleUrl}">+ de
                        d&eacute;tails</a>
                    <c:if test="${produit.available && fn:length(group.products) == 1}">
                        <button class="button blue addToCart" data-id="${produit.idProduits}" data-nb="1">
                            <i
                                    class="icon-add-to-cart"></i></button>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
</div>









<div class="bandeauNosAgences">
    <div class="bandeauHeader">
        <span class="left">Nos agences</span><span class="right">Nos
			catalogues</span>
    </div>
    <div class="corpsBandeau">
        <div class="listAgence">
            <img src="img/contact.png"/>
            <a href="contact" class="btn-agences"><i
                    class="icon-angle-double-right"></i>&nbsp;Voir toutes nos agences</a>
        </div>
        <div class="divMagazines">
            <img src="img/catalogues.png"/> <a
                href="catalogues"><i
                class="icon-angle-double-right"></i>&nbsp;Voir nos catalogues</a>
        </div>
    </div>

</div>
<%@ include file="footer.jsp" %>