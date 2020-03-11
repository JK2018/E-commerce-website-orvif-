<%@ include file="header.jsp"%>

<form method="post">
    <div>
        <label for="mail">Adresse mail<input value="${ mail }"
                                             id="mail" type="text" name="mail"></label>
    </div>
    <div>
        <h3>Vous &ecirc;tes :</h3>
        <label for="particulier"><input type="radio" id="particulier"
                                        name="type_customer" checked />Particulier</label> <label
            for="professionnel"><input type="radio" id="professionnel"
                                       name="type_customer" />Professionnel</label>
    </div>
    <fieldset class="showPro" style="display: none;">
        <h1>Informations soci&eacute;t&eacute;</h1>
        <div>
            <label for="id_raison_sociale">Raison sociale <input
                    id="id_raison_sociale" type="text" name="raison_sociale"></label>
        </div>

        <div>
            <label for="id_forme_juridique">Raison sociale <input
                    id="id_forme_juridique" type="text" name="forme_juridique"></label>
        </div>

        <div>
            <label for="siret">Num&eacute;ro Siret <input id="siret" type="text"
                                                          name="siret"></label>
        </div>

        <div>
            <label for="adresse">Adresse<input id="adresse" type="text"
                                               name="adresse"></label>
        </div>

        <div>
            <label for="adresse2">Adresse suite<input id="adresse2"
                                                      type="text" name="adresse2"></label>
        </div>

        <div>
            <label for="ville">Ville<input id="ville" type="text"
                                           name="ville"></label>
        </div>
    </fieldset>

    <fieldset id="fieldset_contact">
        <h1>Informations contact</h1>
        <div>
            <label for="civilite">Civilite <select id="civilite"
                                                   name="civilite"><option value="Madame">Madame</option>
                <option value="Monsieur">Monsieur</option></select>
            </label>
        </div>
        <div>
            <label for="nom">Nom<input id="nom" type="text" name="nom"></label>
        </div>

        <div>
            <label for="prenom">Pr&eacute;nom<input id="prenom" type="text"
                                                    name="prenom"></label>
        </div>



        <div>
            <label for="tel">T&eacute;l&eacute;phone<input id="tel" type="text"
                                                           name="tel"></label>
        </div>

        <div>
            <label for="fax">Fax<input id="fax" type="text" name="fax"></label>
        </div>

        <div>
            <label for="cp">Code postal<input id="cp" type="text"
                                              name="cp"></label>
        </div>
    </fieldset>
    <fieldset>
        <h1>Commentaire</h1>
        <div>
            <div>
                <textarea name="commentaire"></textarea>
            </div>
        </div>
    </fieldset>


    <div class="g-recaptcha"
         data-sitekey="6LcRmnQUAAAAAGsYRH73T6Oerul_yLuiXl3yZ1gS"></div>


    <div>
        <input type="submit" name="form" value="Valider">
    </div>
</form>


<%@ include file="footer.jsp"%>
