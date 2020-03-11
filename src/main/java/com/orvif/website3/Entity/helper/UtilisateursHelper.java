package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Adresse;
import com.orvif.website3.Entity.Client;
import com.orvif.website3.Entity.ComplementPro;
import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Repository.*;
import com.orvif.website3.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UtilisateursHelper {



@Autowired
private AdressesRepository ar;

@Autowired
private ClientRepository cr;

@Autowired
private ComplementProRepository cpr;

@Autowired
private UtilisateursRepository ur;



    public boolean resetPassword(int client, String psw, String salt1, String salt2) {
        boolean ret = false;
        try {
            cr.resetPassword(client, salt2, salt1, psw);
            ret = true;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }













    public int inscrireUtilisateur(Utilisateurs u) {
        try {
            cr.inscrireUtilisateur(u.getNumCli(), u.getLogin(), u.getMdp(), u.getMail(), u.getType_client(), Integer.toString(u.getProfil()), Integer.parseInt(u.getEtat()), u.getNom(), u.getPrenom(), u.getTelephone(), u.getSalt1(), u.getSalt2());
            cr.flush();
            Client c = cr.getByMail(u.getMail());
            if(c.getId() > 0){
                return c.getId();
            }else{
                return 0;
            }
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
    }





    public boolean confirmAdressMail(int client) {
        return updateMailStatus(client, true);
    }






    public boolean unconfirmAdressMail(int client) {
        return updateMailStatus(client, false);
    }







    private boolean updateMailStatus(int client, boolean valid) {
        boolean b = false;
        try {
            Utilisateurs u = ur.findById(client).get();
            if (valid == true) {
                u.setVerifiedMail(true);
                ur.save(u);
                ur.flush();
                b = true;
            }
            if (valid == false) {
                u.setVerifiedMail(false);
                ur.save(u);
                ur.flush();
                b = false;
            }
            return b;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getClass().getName());
        }
    }







    public Utilisateurs getByMail(String mail) throws DAOException, BloquedAccountException, ClosedAccountException {
        Utilisateurs user = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultat = null;
        try {
            Client c = cr.getByMail(mail.toUpperCase());
            user = new Utilisateurs();
            user.setVerifiedMail(c.getMailConfirm() == 1);
            user.setIdUtilisateurs(c.getId());
            user.setNumCli(Integer.valueOf(c.getNumCliOrvif()));
            user.setLogin(c.getIdentifiant());
            user.setMdp(c.getMdp());
            user.setLastSignin(c.getDateDerniereConnexion());
            user.setMail(c.getMail());
            user.setType_client(c.getTypeClient());
            user.setProfil(Integer.parseInt(c.getProfil()));
            user.setEtat(Integer.toString(c.getEtat()));
            user.setNom(c.getNom());
            user.setPrenom(c.getPrenom());
            user.setTelephone(c.getTelephone());
            user.setSalt1(c.getSalt1());
            user.setSalt2(c.getSalt2());
            if (!user.getType_client().equals("particulier")) {
                if (user.getNumCli() != 1) {
                    //Get infos from NX
                    this.getNXInfos(user);
                    // Cannot log if customer is bloqued or closed
                    if (user.isClosed())
                        throw new ClosedAccountException();
                    if (user.isBloqued())
                        throw new BloquedAccountException();
                }
            }
            user.setAdresseCollection(ar.getByClient(user.getIdUtilisateurs()));
        } catch (DAOException | ClosedAccountException | BloquedAccountException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error while fetching user.");
        }
        return user;
    }


    public boolean isLoginRegistered(String login) {
        boolean b = false;
        try {
            Client c = cr.findByIdentifiant(login);
            b = true;
            return b;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
    }





    public Utilisateurs getByNx(String login, String psw) throws DAOException {
        Document result = DAOFactory.getInstance().execWebService("WEBN_FIRSTCONNEX " + login + "_" + psw);
        Element racine = result.getDocumentElement();
        Utilisateurs user = new Utilisateurs();
        user.setLogin(login);
        if (racine.getNodeName().equals("RETOUR")) {
            if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
            }
            try {
                user.setNomSociete(racine.getElementsByTagName("RAISONSOCIALE").item(0).getTextContent());
                user.setNumCli(Integer.valueOf(racine.getElementsByTagName("CODECLIENT").item(0).getTextContent()));
                user.setNom(racine.getElementsByTagName("NOM").item(0).getTextContent());
                user.setPrenom(racine.getElementsByTagName("PRENOM").item(0).getTextContent());
                user.setTelephone(racine.getElementsByTagName("PORTABLE").item(0).getTextContent());
                user.setMail(racine.getElementsByTagName("MAIL").item(0).getTextContent());
                user.setNumeroSiren(racine.getElementsByTagName("SIREN").item(0).getTextContent());
                user.setProfil(Integer.parseInt("valideur"));
                if (racine.getElementsByTagName("BLOQUE").item(0).getTextContent().equals("OUI")) {
                    //Etat 1 = compte actif vs etat 0 = compte bloque
                    user.setEtat("0");
                } else {
                    user.setEtat("1");
                }
                if (racine.getElementsByTagName("ORANGE").item(0).getTextContent().equals("OUI")) {
                    user.setType_client("pro comptant orange");
                } else {
                    user.setType_client("pro comptant");
                }
                Adresse adresse = new Adresse();
                adresse.setNomVoie(racine.getElementsByTagName("ADRESSE1").item(0).getTextContent());
                adresse.setComplementVoie(racine.getElementsByTagName("ADRESSE2").item(0).getTextContent());
                adresse.setVille(racine.getElementsByTagName("VILLE").item(0).getTextContent());
                adresse.setPays(racine.getElementsByTagName("PAYS").item(0).getTextContent());
                adresse.setLibelle("DEFAUT");
                adresse.setCodePostal(Integer.valueOf(racine.getElementsByTagName("CODEPOSTAL").item(0).getTextContent()));
                user.getAdresseCollection().add(adresse);
            } catch (Exception e) {
                throw new DAOException("Error while fetching informations from XML result. (first connection) : " + e.getClass().getName() + " exception.");
            }
        } else {
            throw new DAOException("Wrong XML while fetching user.");
        }
        return user;
    }






    /**
     * Get infos from NX (called each time pro user log in)
     *
     * @param user
     * @return
     */
    private void getNXInfos(Utilisateurs user) throws DAOException {
        try {
            Document resultXML = DAOFactory.getInstance().execWebService("WEBN_CONNEX " + user.getNumCli());
            final Element racine = resultXML.getDocumentElement();
            if (racine.getNodeName().equals("RETOUR")) {
                if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                    throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
                } else {
                    //TODO check infos (closed customer, orange customer etc.)
                    user.setNomSociete(racine.getElementsByTagName("RAISONSOCIALE").item(0).getTextContent());
                    user.setNumeroSiren(racine.getElementsByTagName("SIREN").item(0).getTextContent());
                    user.setBloqued(racine.getElementsByTagName("BLOQUE").item(0).getTextContent().equals("OUI"));
                    user.setClosed(racine.getElementsByTagName("FERME").item(0).getTextContent().equals("OUI"));
                    Adresse adresse = new Adresse();
                    adresse.setNomVoie(racine.getElementsByTagName("ADRESSE1").item(0).getTextContent());
                    adresse.setComplementVoie(racine.getElementsByTagName("ADRESSE2").item(0).getTextContent());
                    adresse.setVille(racine.getElementsByTagName("VILLE").item(0).getTextContent());
                    adresse.setPays(racine.getElementsByTagName("PAYS").item(0).getTextContent());
                    adresse.setLibelle("COMPANY");
                    adresse.setCodePostal(Integer.valueOf(racine.getElementsByTagName("CODEPOSTAL").item(0).getTextContent()));
                    user.setCompanyAdress(adresse);
                }
            } else {
                throw new DAOException("Wrong XML answer when fetching customer info from NX.");
            }
        } catch (DAOException ignored) {
            throw ignored;
        } catch (Exception e) {
            throw new DAOException("Could not fetch infos from NX.");
        }
    }






    public void getEncours(Utilisateurs user) throws DAOException {
        // Get encours from associated webservice
        String webService = "WN_ENCOURS";
        String param = String.valueOf(user.getNumCli());
        try {
            Document result = DAOFactory.getInstance().execWebService(webService + " " + param);
            Encours encoursClient = new Encours();
            encoursClient.setTotalCommande(Float.parseFloat(result.getDocumentElement().getElementsByTagName("TOTALCOMMANDE").item(0).getTextContent()));
            encoursClient.setTotalCredit(Float.parseFloat(result.getDocumentElement().getElementsByTagName("TOTALCREDIT").item(0).getTextContent()));
            encoursClient.setTotalEncours(Float.parseFloat(result.getDocumentElement().getElementsByTagName("TOTALENCOURS").item(0).getTextContent()));
            encoursClient.setTotalRetard(Float.parseFloat(result.getDocumentElement().getElementsByTagName("RETARDPAIEMENT").item(0).getTextContent()));
            user.setEncours(encoursClient);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("An error occured when trying to get  the user encours (" + e.getClass().getName() + ")");
        }
    }






    public boolean uniqueLogin(String login) {
        boolean ret = false;
        try {
            Client c = cr.findByIdentifiant(login);
            ret = true;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }






    public String[] getEncoursByNX(String login) {
//		DAOFactory connexion = null;
//		String resultat[] = new String[5];
//		String cmd = "/u3/SCRIPT/a5m_req wn_encours " + login;
//		String ligne;
//		int i = 0;
//		try {
//			connexion = DAOFactory.getInstance();
//			DAOFactory.execChannel.setCommand(cmd);
//			DAOFactory.execChannel.setErrStream(null);
//			DAOFactory.execChannel.connect();
//			BufferedReader input = new BufferedReader(new InputStreamReader(DAOFactory.execChannel.getInputStream()));
//			while ((ligne = input.readLine()) != null) {
//				resultat[i] = ligne;
//				i++;
//			}
//			input.close();
//			if (!resultat[0].equals("OK")) {
//				return null;
//			}
//		} catch (Exception je) {
//			return null;
//		}
        //TODO implement
        String[] result = {};
        return result;
    }







    public int inscrireUtilisateurPro(Utilisateurs u) throws DAOException {
        int ret;
        try {
            cr.inscrireUtilisateur(u.getNumCli(), u.getLogin(), u.getMdp(), u.getMail(), u.getType_client(), Integer.toString(u.getProfil()), Integer.parseInt(u.getEtat()), u.getNom(), u.getPrenom(), u.getTelephone(), u.getSalt1(), u.getSalt2());
            cr.flush();
            Client c = cr.getByMail(u.getMail());
            if(c.getId() > 0){
                ret = c.getId();
                try {
                    cpr.inscrireUtilisateurPro(ret, u.getNumeroSiren(), u.getNomSociete());
                    cpr.flush();
                } catch (Exception e) {
                    throw new DAOException(e.getClass().getName() + " exception while inserting additional pro info.");
                }
            } else {
                throw new DAOException("Unable to insert customer.");
            }
        }  catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }






    public boolean updateUser(Utilisateurs user) {
        boolean ret = false;
        try {
            ur.save(user);
            ur.flush();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }







    public List<Devis> getDevis(String numClient, String page, String nbParPage, String tri) {
        try {
            Document resultXML = DAOFactory.getInstance().execWebService("WN_DEVIS " + numClient + "_" + nbParPage + "_" + page + "_" + tri);
            final Element racine = resultXML.getDocumentElement();
            if (racine.getNodeName().equals("RETOUR")) {
                if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                    throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
                } else {
                    List<Devis> devisList = new ArrayList<>();
                    NodeList noeuds = racine.getElementsByTagName("DEVIS");
                    for (int i = 0; i < noeuds.getLength(); i++) {
                        Element devisXML = (Element) noeuds.item(i);
                        Devis devis = new Devis();
                        devis.setNumero(devisXML.getElementsByTagName("NUMERO").item(0).getTextContent());
                        devis.setDate(devisXML.getElementsByTagName("DATE").item(0).getTextContent());
                        devis.setFinValidite(devisXML.getElementsByTagName("FINVALIDITE").item(0).getTextContent());
                        devis.setRefClient(devisXML.getElementsByTagName("REFERENCECLIENT").item(0).getTextContent());
                        devis.setAcheteur(devisXML.getElementsByTagName("ACHETEUR").item(0).getTextContent());
                        devis.setMontantTTC(Float.parseFloat(devisXML.getElementsByTagName("MONTANTTTC").item(0).getTextContent()));
                        devisList.add(devis);
                    }
                    return devisList;
                }
            } else {
                throw new DAOException("Wrong XML answer when fetching quotes from NX.");
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error occured when fetching quotes.");
        }
    }







    public int countDevisPro(String numClient) {
        try {
            Document resultXML = DAOFactory.getInstance().execWebService("WN_COUNT_DEVIS " + numClient + "_");
            final Element racine = resultXML.getDocumentElement();
            if (racine.getNodeName().equals("RETOUR")) {
                if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                    throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
                } else {
                    return Integer.parseInt(racine.getElementsByTagName("NOMBREDEVIS").item(0).getTextContent());
                }
            } else {
                throw new DAOException("Wrong XML answer when fetching quotes number from NX.");
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error occured when fetching quotes number.");
        }
    }






    public List<Facture> getFacture(String numClient, String page, String nbParPage, String tri) {
        return this.getFacture(numClient, page, nbParPage, tri, false);
    }






    public List<Facture> getFactureNonReglees(String numClient, String page, String nbParPage, String tri) {
        return this.getFacture(numClient, page, nbParPage, tri, true);
    }






    public int countFacturePro(String numClient) {
        try {
            Document resultXML = DAOFactory.getInstance().execWebService("WN_COUNT_FACTURE " + numClient + "_");
            final Element racine = resultXML.getDocumentElement();
            if (racine.getNodeName().equals("RETOUR")) {
                if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                    throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
                } else {
                    return Integer.parseInt(racine.getElementsByTagName("NOMBREFACTURES").item(0).getTextContent());
                }
            } else {
                throw new DAOException("Wrong XML answer when fetching bills number from NX.");
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error occured when fetching bills number.");
        }
    }





    public int countFactureProNonReglees(String numClient) {
        try {
            Document resultXML = DAOFactory.getInstance().execWebService("WN_COUNT_FACTURE_NONREGLEES " + numClient + "_");
            final Element racine = resultXML.getDocumentElement();
            if (racine.getNodeName().equals("RETOUR")) {
                if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                    throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
                } else {
                    return Integer.parseInt(racine.getElementsByTagName("NOMBREFACTURES").item(0).getTextContent());
                }
            } else {
                throw new DAOException("Wrong XML answer when fetching not paid bills number from NX.");
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error occured when fetching not paid bills number.");
        }
    }







    public List<BonDeLivraison> getBL(String numClient, String page, String nbParPage, String tri) {
        try {
            Document resultXML = DAOFactory.getInstance().execWebService("WN_BL " + numClient + "_" + nbParPage + "_" + page + "_" + tri);
            final Element racine = resultXML.getDocumentElement();
            if (racine.getNodeName().equals("RETOUR")) {
                if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                    throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
                } else {
                    List<BonDeLivraison> BLList = new ArrayList<>();
                    NodeList noeuds = racine.getElementsByTagName("BL");
                    for (int i = 0; i < noeuds.getLength(); i++) {
                        Element BLXML = (Element) noeuds.item(i);
                        BonDeLivraison BL = new BonDeLivraison();
                        BL.setNumero(BLXML.getElementsByTagName("NUMERO").item(0).getTextContent());
                        BL.setDate(BLXML.getElementsByTagName("DATE").item(0).getTextContent());
                        BL.setNumeroCommande(BLXML.getElementsByTagName("NUMEROCOMMANDE").item(0).getTextContent());
                        BL.setRefClient(BLXML.getElementsByTagName("REFERENCECLIENT").item(0).getTextContent());
                        BL.setAcheteur(BLXML.getElementsByTagName("ACHETEUR").item(0).getTextContent());
                        BL.setNumeroFacture(BLXML.getElementsByTagName("NUMEROFACTURE").item(0).getTextContent());
                        BL.setMontantTTC(Float.parseFloat(BLXML.getElementsByTagName("MONTANTTTC").item(0).getTextContent()));
                        BL.setaRegler(Float.parseFloat(BLXML.getElementsByTagName("AREGLER").item(0).getTextContent()));
                        BLList.add(BL);
                    }
                    return BLList;
                }
            } else {
                throw new DAOException("Wrong XML answer when fetching delivery orders from NX.");
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error occured when fetching delivery orders.");
        }
    }






    public int countBLPro(String numClient) {
        try {
            Document resultXML = DAOFactory.getInstance().execWebService("WN_COUNT_BL " + numClient + "_");
            final Element racine = resultXML.getDocumentElement();
            if (racine.getNodeName().equals("RETOUR")) {
                if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                    throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
                } else {
                    return Integer.parseInt(racine.getElementsByTagName("NOMBREBL").item(0).getTextContent());
                }
            } else {
                throw new DAOException("Wrong XML answer when fetching delivery orders number from NX.");
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error occured when fetching delivery orders number.");
        }
    }






    public List<Commande> getCommandePro(String numClient, String page, String nbParPage, String tri) {
        try {
            Document resultXML = DAOFactory.getInstance().execWebService("WN_COMMANDE " + numClient + "_" + nbParPage + "_" + page + "_" + tri);
            final Element racine = resultXML.getDocumentElement();
            if (racine.getNodeName().equals("RETOUR")) {
                if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                    throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
                } else {
                    List<Commande> commandeList = new ArrayList<>();
                    NodeList noeuds = racine.getElementsByTagName("COMMANDE");
                    for (int i = 0; i < noeuds.getLength(); i++) {
                        Element commandeXML = (Element) noeuds.item(i);
                        Commande commande = new Commande();
                        commande.setNumero(commandeXML.getElementsByTagName("NUMERO").item(0).getTextContent());
                        commande.setDate(commandeXML.getElementsByTagName("DATE").item(0).getTextContent());
                        commande.setNumeroDevis(commandeXML.getElementsByTagName("NUMERODEVIS").item(0).getTextContent());
                        commande.setRefClient(commandeXML.getElementsByTagName("REFERENCECLIENT").item(0).getTextContent());
                        commande.setAcheteur(commandeXML.getElementsByTagName("ACHETEUR").item(0).getTextContent());
                        commande.setStatus(commandeXML.getElementsByTagName("STATUS").item(0).getTextContent());
                        commande.setMontantTTC(Float.parseFloat(commandeXML.getElementsByTagName("MONTANTTTC").item(0).getTextContent()));
                        commandeList.add(commande);
                    }
                    return commandeList;
                }
            } else {
                throw new DAOException("Wrong XML answer when fetching orders from NX.");
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error occured when fetching orders.");
        }
    }






    public int countCommandePro(String numClient) {
        try {
            Document resultXML = DAOFactory.getInstance().execWebService("WN_COUNT_COMMANDE " + numClient + "_");
            final Element racine = resultXML.getDocumentElement();
            if (racine.getNodeName().equals("RETOUR")) {
                if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                    throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
                } else {
                    return Integer.parseInt(racine.getElementsByTagName("NOMBRECOMMANDES").item(0).getTextContent());
                }
            } else {
                throw new DAOException("Wrong XML answer when fetching orders number from NX.");
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error occured when fetching orders number.");
        }
    }







    public Utilisateurs getAdminByLogin(String login) {
        Utilisateurs ret = null;
        try {
            ret = ur.findByLogin(login);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }







    public List<Utilisateurs> getUserAdminCollection() {
        List<Utilisateurs> ret = new ArrayList<>();
        try {
            ret = ur.findAll();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }







    public boolean removeAdminUser(int id) {
        boolean ret = false;
        try {
            ur.deleteById(id);
            ur.flush();
            ret = true;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }







    public boolean updateAdminPassw(int id, String newPassw, String salt1, String salt2) {
        boolean ret = false;
            try {
                Utilisateurs u = ur.findById(id).get();
                u.setMdp(newPassw);
                u.setSalt1(salt1);
                u.setSalt2(salt2);
                ur.save(u);
                ret = true;
                ur.flush();
            } catch (Exception e) {
                e.printStackTrace();
                throw new DAOException("Une erreur est survenue : " + e.getMessage());
            }
        return ret;
    }






    public boolean createAdminAccount(Utilisateurs user, int profil) {
        boolean ret = false;
        try {
           // connexion = daoFactory.getConnection();
            user.setProfil(profil);
            ur.save(user);
            ur.flush();
            Utilisateurs u = ur.findByLogin(user.getLogin());
            int res = u.getIdUtilisateurs();
            if (res > 0) {
                    user.setIdUtilisateurs(res);
                    ret = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }







    public Utilisateurs getById(int client) {
        Utilisateurs ret = null;
        PreparedStatement preparedStatement2 = null;
        ComplementPro resultat2 = null;
        try {
            ret = ur.findById(client).get();
            if (!ret.getType_client().equals("particulier")) { //verifier si fonctionne bien
                // Recuperation des infos complementaires (numero de SIREN)
                resultat2 = cpr.findById(ret.getIdUtilisateurs()).get();
                ret.setNumeroSiren(resultat2.getNumeroSiren());
                ret.setNomSociete(resultat2.getNomSociete());
            }
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }







    /**
     * Execute web service to get non-paid bills or all bills depending on the last parameter
     *
     * @param numClient
     * @param page
     * @param nbParPage
     * @param tri
     * @param nonReglees
     * @return
     */
    private List<Facture> getFacture(String numClient, String page, String nbParPage, String tri, boolean nonReglees) {
        try {
            Document resultXML = nonReglees ? DAOFactory.getInstance().execWebService("WN_FACTURE_NONREGLEES " + numClient + "_" + nbParPage + "_" + page + "_" + tri) : DAOFactory.getInstance().execWebService("WN_FACTURE " + numClient + "_" + nbParPage + "_" + page + "_" + tri);
            final Element racine = resultXML.getDocumentElement();
            if (racine.getNodeName().equals("RETOUR")) {
                if (racine.getElementsByTagName("ERROR").getLength() > 0) {
                    throw new DAOException(racine.getElementsByTagName("ERROR").item(0).getTextContent());
                } else {
                    List<Facture> factureList = new ArrayList<>();
                    NodeList noeuds = racine.getElementsByTagName("FACTURE");
                    for (int i = 0; i < noeuds.getLength(); i++) {
                        Element factureXML = (Element) noeuds.item(i);
                        Facture facture = new Facture();
                        facture.setNumero(factureXML.getElementsByTagName("NUMERO").item(0).getTextContent());
                        facture.setDate(factureXML.getElementsByTagName("DATE").item(0).getTextContent());
                        facture.setModeReglement(factureXML.getElementsByTagName("MODEREGLEMENT").item(0).getTextContent());
                        facture.setEcheance(factureXML.getElementsByTagName("DATEECHEANCE").item(0).getTextContent());
                        facture.setNumeroReleve(factureXML.getElementsByTagName("NUMERORELEVE").item(0).getTextContent());
                        facture.setMontantTTC(Float.parseFloat(factureXML.getElementsByTagName("MONTANTTTC").item(0).getTextContent()));
                        facture.setaRegler(Float.parseFloat(factureXML.getElementsByTagName("AREGLER").item(0).getTextContent()));
                        factureList.add(facture);
                    }
                    return factureList;
                }
            } else {
                throw new DAOException("Wrong XML answer when fetching bill from NX.");
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error occured when fetching bills.");
        }
    }
















}

