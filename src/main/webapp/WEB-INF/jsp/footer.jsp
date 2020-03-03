</div>
<div id="footer" style="padding-bottom: 0px;">
    <div class="top" >
        <div class="firstPart">
            <span class="title"> Une question ? </span> <br/> <br/> <span
                class="description"> Entrez votre adresse mail pour nous
					contacter! </span>
        </div>
        <div class="secondPart">
            <input name="mailFirst" placeholder="Votre adresse e-mail...">
            <button id="goContact" class="button red">CONTACTEZ-NOUS</button>
        </div>
    </div>



    <div class="middle" >
        <div class="block">
            <span class="title">Services Orvif</span>
            <ul>
                <li><a href="services">Services</a></li>
                <li><a href="livraison">Livraison</a></li>
                <li><a href="cgv">Conditions g&eacute;n&eacute;rales de vente</a></li>
                <li><a href="club_avantages">Club avantages</a></li>
            </ul>
        </div>
        <div class="block">
            <span class="title">Nous contacter</span>
            <ul>
                <li><a href="contact">Contact</a></li>
                <li><a href="recrutement">Recrutement</a></li>
            </ul>
        </div>
        <div class="block">
            <span class="title">D&eacute;couvrir Orvif</span>
            <ul>
                <li><a href="quisommesnous">Qui sommes nous ?</a></li>
                <li><a href="showrooms">Nos showrooms</a></li>
            </ul>
        </div>

        <div class="block">
            <span class="title">Social media</span>
            <ul>
                <li class="socialIcon"><a href="http://www.facebook.com"><i
                        class="icon-facebook"></i></a> <a
                        href="http://www.twitter.com"><i
                        class="icon-twitter"></i></a> <a
                        href="http://www.instagram.com"><i
                        class="icon-instagram"></i></a><a
                        href="https://www.linkedin.com/company/orvif/"> <i
                        class="icon-linkedin"></i></a></li>
                <li class="bigItem">Inscrivez-vous &agrave; nos newsletters !</li>
                <li><a>Les offres et promos avant tout le monde</a></li>
                <li><a id="newsletterSub" class="button blue">S'abonner</a></li>
            </ul>
        </div>
    </div>
    <div class="bottom" >&reg;ORVIF 2018, Tous droits r&eacute;serv&eacute;s |
        Mentions l&eacute;gales, CGU et donn&eacute;es personnelles | Plan du site
    </div>
</div>
<script
        src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"
        integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU="
        crossorigin="anonymous"></script>
<script type="text/javascript" src="plugins/jquery.textfill.min.js"></script>
<script type="text/javascript" src="js/OrvifModal.js"></script>
<script type="text/javascript" src="js/notification.js"></script>
<script type="text/javascript" src="js/notificationProductAdded.js"></script>
<script type="text/javascript" src="js/generalJs.js"></script>
<script type="text/javascript"
        src="//cdn.rawgit.com/icons8/bower-webicon/v0.10.7/jquery-webicon.min.js"></script>
<script src="https://unpkg.com/leaflet@1.3.1/dist/leaflet.js"
        integrity="sha512-/Nsx9X4HebavoBvEBuyp3I7od5tA0UzAxs+j83KgC8PU0kgB4XiK4Lfe4y4cgBtaRJQEIFCW+oC506aPT2L1zw=="
        crossorigin=""></script>


<c:forTokens items="${scripts}" delims="," var="script">
    <script src="${script}"></script>
</c:forTokens>
</body>
</html>