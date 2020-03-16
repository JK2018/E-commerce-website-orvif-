<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="subtop" value="cart"/>
<c:set scope="request" var="bgSubtop"
       value="https://s1.lmcdn.fr/multimedia/1f1500847894/269345947f478/salles-de-bains-styles-et-tendances.jpg?$p=tbssrm"/>
<c:choose>
    <c:when test="${not empty theFamille}">
        <c:set scope="request" var="arianeSubtop"
               value="Accueil > ${theFamille.libelleCapitalized}"></c:set>
        <c:set scope="request" var="titleSubtop" value="${theFamille.libelleCapitalized}"/>
    </c:when>
    <c:when test="${ not empty theSsFamille && empty theCategorie }">
        <c:set scope="request" var="arianeSubtop"
               value="Accueil > <a href='${theSsFamille.famillesByParentFamille.libelle}'>${theSsFamille.famillesByParentFamille.libelleCapitalized}</a> > ${theSsFamille.libelleCapitalized} "/>
        <c:set scope="request" var="titleSubtop" value="${ssFamille.libelleCapitalized}"/>
    </c:when>
   <%-- <c:when test="${ not empty sousCategorie}">
        <c:set scope="request" var="arianeSubtop"
               value="Accueil > <a href='${famille.libelle}'>${famille.libelleCapitalized}</a> > <a href='${famille.libelle}'>${ssFamille.libelleCapitalized}</a> > <a href='${categorie.libelle}'>${categorie.libelleCapitalized}</a> > ${ssCategorie.libelleCapitalized}"/>
        <c:set scope="request" var="titleSubtop" value="${ssCategorie.libelleCapitalized}"/>
    </c:when>--%>
    <c:when test="${ not empty theCategorie }">
        <c:set scope="request" var="arianeSubtop"
               value="Accueil > <a href='${theCategorie.ssFamillesByParentSsfamille.famillesByParentFamille.libelle}'>${theCategorie.ssFamillesByParentSsfamille.famillesByParentFamille.libelleCapitalized}</a> > <a href='${theCategorie.ssFamillesByParentSsfamille.libelle}'>${theCategorie.ssFamillesByParentSsfamille.libelleCapitalized}</a> > ${theCategorie.libelleCapitalized}"/>
        <c:set scope="request" var="titleSubtop" value="${ssFamille.libelleCapitalized}"/>
    </c:when>
</c:choose>
<%@ include file="header.jsp" %>










<%-- ////////////////////////////////////////////////


<c:set var="noFilter" value="false"/>
<c:if test="${empty objectDisplay.availableFilters}">
    <c:choose>
        <c:when test="${not empty objectDisplay.categorie && empty objectDisplay.sousCategorie }">
            <c:if test="${empty objectDisplay.categorie.sousCategorieCollection}">
                <c:set var="noFilter" value="true"/>
            </c:if>
        </c:when>
        <c:when test="${ not empty objectDisplay.sousFamille && empty objectDisplay.categorie }">
            <c:if test="${empty objectDisplay.categorie.sousCategorieCollection}">
                <c:set var="noFilter" value="true"/>
            </c:if>
        </c:when>
        <c:otherwise>
            <c:set var="noFilter" value="true"/>
        </c:otherwise>
    </c:choose>
</c:if>


<c:if test="${not noFilter}">
    <div class="filtersDiv">
        <c:if test="${ not empty objectDisplay.appliedFilters}">
            <c:set var="noFilter" value="false"/>
            <div class="filterBox">
                <span class="title">Filtres appliqu&eacute;</span>
                <hr/>
                <ul>
                    <c:forEach items="${ objectDisplay.appliedFilters }" var="filtre">
                        <c:forEach items="${ filtre.items }" var="valeur">
                            <!-- Generation de l'url pour enlever le filtre -->
                            <c:url value="${requestScope['javax.servlet.forward.servlet_path']}" var="urlTmp">
                                <c:forEach items="${paramValues }" var="entry">
                                    <c:if test="${entry.key != 'c' && entry.key != 'sc' && entry.key != 'sf'}">
                                        <c:forEach items="${ entry.value }" var="val">
                                            <c:set var='strCompare' value='${filtre.idFeature}|${valeur.left}'/>
                                            <c:if test="${val != strCompare }">
                                                <c:param name="${entry.key }" value="${val }"></c:param>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                            </c:url>
                            <li><a id="removeFilterLink" href="${urlTmp }">${ valeur.left }</a></li>
                        </c:forEach>
                    </c:forEach>
                </ul>
            </div>
        </c:if>




        <c:choose>
            <c:when test="${not empty objectDisplay.categorie && empty objectDisplay.sousCategorie }">
                <div class="filterBox">
                    <span class="title">Sous cat&eacute;gories</span>
                    <hr/>
                    <ul>
                        <c:forEach items="${ objectDisplay.categorie.sousCategorieCollection }"
                                   var="sousCategorie">
                            <c:if test="${sousCategorie.nbProducts > 0}">
                                <li>
                                    <a href="listSC${sousCategorie.id}__${objectDisplay.famille.libelle}__${objectDisplay.sousFamille.libelle}__${objectDisplay.categorie.libelle}__${sousCategorie.libelle}">${sousCategorie.libelleCapitalized }</a><span>(${sousCategorie.nbProducts})</span>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </c:when>
            <c:when test="${ not empty objectDisplay.sousFamille && empty objectDisplay.categorie }">
                <div class="filterBox">
                    <span class="title">Cat&eacute;gories</span>
                    <hr/>
                    <ul>
                        <c:forEach items="${ objectDisplay.sousFamille.categorieCollection }"
                                   var="categorie">
                            <c:if test="${categorie.nbProducts > 0}">
                                <li>
                                    <a href="listC${categorie.id}__${objectDisplay.famille.libelle}__${objectDisplay.sousFamille.libelle}__${categorie.libelle}">${categorie.libelleCapitalized }</a><span>(${categorie.nbProducts})</span>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </c:when>
        </c:choose>

        <c:forEach items="${ objectDisplay.availableFilters }" var="filtre">
            <c:if test="${not empty filtre.items && filtre.idFeature != '1'}">
                <div class="filterBox">
                    <span class="title">${ filtre.title }</span>
                    <hr/>
                    <ul>
                        <c:forEach items="${ filtre.items }" var="valeur">

                            <c:url value="${requestScope['javax.servlet.forward.servlet_path']}" var="urltmp">
                                <c:forEach items="${ paramValues }" var="entry">
                                    <c:forEach items="${ entry.value }" var="val">
                                        <c:if test="${entry.key != 'c' && entry.key != 'sc' && entry.key != 'sf'}">
                                            <c:param name="${ entry.key }" value="${val}"/>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                                <c:param name="f" value="${ filtre.idFeature }|${ valeur.left }"/>
                            </c:url>
                            <li><a href="${urltmp}">${ valeur.left }</a></li>

                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        </c:forEach>
    </div>
</c:if>

<div class="listProductsDiv <c:if test='${noFilter}'>noFilter</c:if>">
    <div class="sortingDiv">
        <div></div>
        <div>
            <div class="selectDiv">
                <select name="nbPerPage" id="nbPerPage">
                    <option value="8"
                            <c:if test="${objectDisplay.nbPerPage == 8}">selected</c:if>
                    >
                        8 &eacute;lements / page
                    </option>
                    <option value="16"
                            <c:if test="${objectDisplay.nbPerPage == 16}">selected</c:if>
                    >
                        16 &eacute;lements / page
                    </option>
                    <option value="32"
                            <c:if test="${objectDisplay.nbPerPage == 32}">selected</c:if>
                    >
                        32 &eacute;lements / page
                    </option>
                    <option value="64"
                            <c:if test="${objectDisplay.nbPerPage == 64}">selected</c:if>
                    >
                        64 &eacute;lements / page
                    </option>
                </select>
            </div>
            <div>Affichage de ${objectDisplay.nbCurrentPage} produits sur ${objectDisplay.nbResult}</div>
        </div>
    </div>
    <div class="products">
        <c:forEach items="${objectDisplay.groupeList}" var="groupe">
            <c:set var="produit" value="${groupe.products[0]}"/>
            <div class="productCard <c:if test="${!produit.available && fn:length(groupe.products) == 1}" >disabled</c:if>">
                <div class="imgProd">
                    <img
                            <c:choose>
                                <c:when test="${fn:length(produit.imageCollection) > 0}">
                                    src="https://storage.googleapis.com/${applicationScope.product_files_bucket_name}/${produit.imageCollection[0].url}"
                                </c:when>
                                <c:otherwise>
                                    src="https://storage.googleapis.com/${applicationScope.static_files_bucket_name}/nophoto.png"
                                </c:otherwise>
                            </c:choose>
                    />
                    <c:if test="${!produit.available && fn:length(groupe.products) == 1}">
                        <span id="productNotAvailable">Produit non disponible</span>
                    </c:if>
                    <c:if test="${produit.available && produit.defi}">
                        <span id="productNotAvailable">Club avantages !</span>
                    </c:if>
                    <c:if test="${produit.destockage}">
                        <span id="productNotAvailable">Destockage !</span>
                    </c:if>
                </div>
                <p class="libelle">${ produit.libelle }</p>
                <p class="price">
                    <c:choose>
                        <c:when
                                test="${ not empty client && client.type_client != 'particulier' }">
                            <c:choose>
                                <c:when test="${fn:length(groupe.products) == 1 || groupe.minPriceHT == groupe.maxPriceHT}">
                                    Prix HT: <br/><span
                                        class="font-red">${ produit.ppht }&euro;</span>
                                </c:when>
                                <c:otherwise>
                                    Prix HT: <br/><span
                                        class="font-red">${ groupe.minPriceHT } - ${ groupe.maxPriceHT }&euro;</span>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${fn:length(groupe.products) == 1 || groupe.minPriceTTC == groupe.maxPriceTTC}">
                                    Prix TTC: <br/><span
                                        class="font-red">${ produit.ppttc }&euro;</span>
                                </c:when>
                                <c:otherwise>
                                    Prix TTC: <br/><span
                                        class="font-red">${ groupe.minPriceTTC } - ${ groupe.maxPriceTTC }&euro;</span>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </p>
                <div class="group-btn">
                    <a class="button red detailBtn" href="P${produit.id }__${produit.libelleUrl}">+ de
                        d&eacute;tails</a>
                    <c:if test="${produit.available && fn:length(groupe.products) == 1}">
                        <button class="button blue addToCart" data-id="${produit.id}" data-nb="1">
                            <i
                                    class="icon-add-to-cart"></i></button>
                    </c:if>
                </div>
            </div>
        </c:forEach>

    </div>


    <div class="pagination">
        <c:choose>
            <c:when test="${objectDisplay.pageNumber == 1}">
                <span class="item previousPage disabled"><a><i class="icon-left-open"></i></a></span>
            </c:when>
            <c:otherwise>
                <!-- DEBUT GENERATION DES LIENS PAGE PRECEDENTE ET PAGE SUIVANTE -->
                <c:url value="/listeproduits" var="url">
                    <c:forEach items="${paramValues}" var="entry">
                        <c:forEach items="${ entry.value }" var="val">
                            <c:if test="${ entry.key != 'p' }">
                                <c:param name="${entry.key}" value="${val}"/>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                    <c:param name="p" value="${ objectDisplay.pageNumber-1 }"/>
                </c:url>
                <span class="item previousPage"><a href="${url}"><i class="icon-left-open"></i></a></span>
            </c:otherwise>
        </c:choose>


        <c:choose>
            <c:when test="${ objectDisplay.pageNumber < 5}">
                <c:forEach var="i" begin="1" end="5" step="1">
                    <c:url value="/listeproduits" var="url">
                        <c:forEach items="${paramValues}" var="entry">
                            <c:forEach items="${ entry.value }" var="val">
                                <c:if test="${ entry.key != 'p' }">
                                    <c:param name="${entry.key}" value="${val}"/>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                        <c:param name="p" value="${ i }"/>
                    </c:url>
                    <c:choose>
                        <c:when test="${ i == objectDisplay.pageNumber}">
                            <span class="item active"><a href="${url}">${i}</a></span>
                        </c:when>
                        <c:when test="${objectDisplay.lastPage >= i}">
                            <span class="item"><a href="${url}">${i}</a></span>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach var="i" begin="${objectDisplay.pageNumber - 2}" end="${objectDisplay.pageNumber + 2}"
                           step="1">
                    <c:url value="/listeproduits" var="url">
                        <c:forEach items="${paramValues}" var="entry">
                            <c:forEach items="${ entry.value }" var="val">
                                <c:if test="${ entry.key != 'p' }">
                                    <c:param name="${entry.key}" value="${val}"/>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                        <c:param name="p" value="${ i }"/>
                    </c:url>
                    <c:choose>
                        <c:when test="${ i == objectDisplay.pageNumber}">
                            <span class="item active"><a href="${url}">${i}</a></span>
                        </c:when>
                        <c:when test="${objectDisplay.lastPage >= i}">
                            <span class="item"><a href="${url}">${i}</a></span>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </c:otherwise>
        </c:choose>


        <c:choose>
            <c:when test="${objectDisplay.pageNumber == objectDisplay.lastPage}">
                <span class="item nextPage disabled"><a><i class="icon-right-open"></i></a></span>
            </c:when>
            <c:otherwise>
                <c:url value="/listeproduits" var="url">
                    <c:forEach items="${paramValues}" var="entry">
                        <c:forEach items="${ entry.value }" var="val">
                            <c:if test="${ entry.key != 'p' }">
                                <c:param name="${entry.key}" value="${val}"/>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                    <c:param name="p" value="${ objectDisplay.pageNumber+1 }"/>
                </c:url>
                <span class="item nextPage"><a href="${url}"><i class="icon-right-open"></i></a></span>
            </c:otherwise>
        </c:choose>

    </div>
</div>
 ///////////////////////////////////////////////////////////////////////////////// --%>
<%@ include file="footer.jsp" %> 