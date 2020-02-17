package com.orvif.website3.Repository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class REPOFactory {

    @Autowired
    private AdresseClientsRepository adresseClientsRepository;
    @Autowired
    private AdressesRepository adressesRepository;
    @Autowired
    private CaracteristiqueReferenceRepository caracteristiqueReferenceRepository;
    @Autowired
    private CaracteristiquesCategoriesRepository caracteristiquesCategoriesRepository;
    @Autowired
    private CaracteristiquesProduitsRepository caracteristiquesProduitsRepository;
    @Autowired
    private CaracteristiquesRepository caracteristiquesRepository;
    @Autowired
    private CaracteristiquesSsfamillesRepository caracteristiquesSsfamillesRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CodeConfirmRepository codeConfirmRepository;
    @Autowired
    private CodepostalSecteurRepository codepostalSecteurRepository;
    @Autowired
    private ComplementProRepository complementProRepository;
    @Autowired
    private CouleursRepository couleursRepository;
    @Autowired
    private DocumentProduitRepository documentProduitRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private FamillesRepository famillesRepository;
    @Autowired
    private FideliteRepository fideliteRepository;
    @Autowired
    private GammesRepository gammesRepository;
    @Autowired
    private GroupeCritereRepository groupeCritereRepository;
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private HistoriqueModificationRepository historiqueModificationRepository;
    @Autowired
    private JobMissionRepository jobMissionRepository;
    @Autowired
    private JobOfferRepository jobOfferRepository;
    @Autowired
    private LignePanierRepository lignePanierRepository;
    @Autowired
    private MarquesRepository marquesRepository;
    @Autowired
    private MailContactRepository mailContactRepository;
    @Autowired
    private MatieresRepository matieresRepository;
    @Autowired
    private MotifContactRepository motifContactRepository;
    @Autowired
    private MotsClesProduitsRepository motsClesProduitsRepository;
    @Autowired
    private MotsClesRepository motsClesRepository;
    @Autowired
    private NewsletterRepository newsletterRepository;
    @Autowired
    private ProduitsRepository produitsRepository;
    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private ProduitsAlternatifsRepository produitsAlternatifsRepository;
    @Autowired
    private ProduitsComplementairesRepository produitsComplementairesRepository;
    @Autowired
    private ProfilsRepository profilsRepository;
    @Autowired
    private ReferenceRepository referenceRepository;
    @Autowired
    private SecteurMailRepository secteurMailRepository;
    @Autowired
    private SsCategoriesRepository ssCategoriesRepository;
    @Autowired
    private SsFamillesRepository ssFamillesRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private TelephonesRepository telephonesRepository;
    @Autowired
    private TelsClientsRepository telsClientsRepository;
    @Autowired
    private UnitesFacturationRepository unitesFacturationRepository;
    @Autowired
    private UnitesVenteRepository unitesVenteRepository;
    @Autowired
    private UtilisateursRepository utilisateursRepository;


    public REPOFactory() {
    }

    public AdresseClientsRepository getAdresseClientsRepository() {
        return adresseClientsRepository;
    }

    public void setAdresseClientsRepository(AdresseClientsRepository adresseClientsRepository) {
        this.adresseClientsRepository = adresseClientsRepository;
    }

    public AdressesRepository getAdressesRepository() {
        return adressesRepository;
    }

    public void setAdressesRepository(AdressesRepository adressesRepository) {
        this.adressesRepository = adressesRepository;
    }

    public CaracteristiqueReferenceRepository getCaracteristiqueReferenceRepository() {
        return caracteristiqueReferenceRepository;
    }

    public void setCaracteristiqueReferenceRepository(CaracteristiqueReferenceRepository caracteristiqueReferenceRepository) {
        this.caracteristiqueReferenceRepository = caracteristiqueReferenceRepository;
    }

    public CaracteristiquesCategoriesRepository getCaracteristiquesCategoriesRepository() {
        return caracteristiquesCategoriesRepository;
    }

    public void setCaracteristiquesCategoriesRepository(CaracteristiquesCategoriesRepository caracteristiquesCategoriesRepository) {
        this.caracteristiquesCategoriesRepository = caracteristiquesCategoriesRepository;
    }

    public CaracteristiquesProduitsRepository getCaracteristiquesProduitsRepository() {
        return caracteristiquesProduitsRepository;
    }

    public void setCaracteristiquesProduitsRepository(CaracteristiquesProduitsRepository caracteristiquesProduitsRepository) {
        this.caracteristiquesProduitsRepository = caracteristiquesProduitsRepository;
    }

    public CaracteristiquesRepository getCaracteristiquesRepository() {
        return caracteristiquesRepository;
    }

    public void setCaracteristiquesRepository(CaracteristiquesRepository caracteristiquesRepository) {
        this.caracteristiquesRepository = caracteristiquesRepository;
    }

    public CaracteristiquesSsfamillesRepository getCaracteristiquesSsfamillesRepository() {
        return caracteristiquesSsfamillesRepository;
    }

    public void setCaracteristiquesSsfamillesRepository(CaracteristiquesSsfamillesRepository caracteristiquesSsfamillesRepository) {
        this.caracteristiquesSsfamillesRepository = caracteristiquesSsfamillesRepository;
    }

    public CategoriesRepository getCategoriesRepository() {
        return categoriesRepository;
    }

    public void setCategoriesRepository(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CodeConfirmRepository getCodeConfirmRepository() {
        return codeConfirmRepository;
    }

    public void setCodeConfirmRepository(CodeConfirmRepository codeConfirmRepository) {
        this.codeConfirmRepository = codeConfirmRepository;
    }

    public CodepostalSecteurRepository getCodepostalSecteurRepository() {
        return codepostalSecteurRepository;
    }

    public void setCodepostalSecteurRepository(CodepostalSecteurRepository codepostalSecteurRepository) {
        this.codepostalSecteurRepository = codepostalSecteurRepository;
    }

    public ComplementProRepository getComplementProRepository() {
        return complementProRepository;
    }

    public void setComplementProRepository(ComplementProRepository complementProRepository) {
        this.complementProRepository = complementProRepository;
    }

    public CouleursRepository getCouleursRepository() {
        return couleursRepository;
    }

    public void setCouleursRepository(CouleursRepository couleursRepository) {
        this.couleursRepository = couleursRepository;
    }

    public DocumentProduitRepository getDocumentProduitRepository() {
        return documentProduitRepository;
    }

    public void setDocumentProduitRepository(DocumentProduitRepository documentProduitRepository) {
        this.documentProduitRepository = documentProduitRepository;
    }

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public FamillesRepository getFamillesRepository() {
        return famillesRepository;
    }

    public void setFamillesRepository(FamillesRepository famillesRepository) {
        this.famillesRepository = famillesRepository;
    }

    public FideliteRepository getFideliteRepository() {
        return fideliteRepository;
    }

    public void setFideliteRepository(FideliteRepository fideliteRepository) {
        this.fideliteRepository = fideliteRepository;
    }

    public GammesRepository getGammesRepository() {
        return gammesRepository;
    }

    public void setGammesRepository(GammesRepository gammesRepository) {
        this.gammesRepository = gammesRepository;
    }

    public GroupeCritereRepository getGroupeCritereRepository() {
        return groupeCritereRepository;
    }

    public void setGroupeCritereRepository(GroupeCritereRepository groupeCritereRepository) {
        this.groupeCritereRepository = groupeCritereRepository;
    }

    public GroupeRepository getGroupeRepository() {
        return groupeRepository;
    }

    public void setGroupeRepository(GroupeRepository groupeRepository) {
        this.groupeRepository = groupeRepository;
    }

    public HistoriqueModificationRepository getHistoriqueModificationRepository() {
        return historiqueModificationRepository;
    }

    public void setHistoriqueModificationRepository(HistoriqueModificationRepository historiqueModificationRepository) {
        this.historiqueModificationRepository = historiqueModificationRepository;
    }

    public JobMissionRepository getJobMissionRepository() {
        return jobMissionRepository;
    }

    public void setJobMissionRepository(JobMissionRepository jobMissionRepository) {
        this.jobMissionRepository = jobMissionRepository;
    }

    public JobOfferRepository getJobOfferRepository() {
        return jobOfferRepository;
    }

    public void setJobOfferRepository(JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
    }

    public LignePanierRepository getLignePanierRepository() {
        return lignePanierRepository;
    }

    public void setLignePanierRepository(LignePanierRepository lignePanierRepository) {
        this.lignePanierRepository = lignePanierRepository;
    }

    public MarquesRepository getMarquesRepository() {
        return marquesRepository;
    }

    public void setMarquesRepository(MarquesRepository marquesRepository) {
        this.marquesRepository = marquesRepository;
    }

    public MailContactRepository getMailContactRepository() {
        return mailContactRepository;
    }

    public void setMailContactRepository(MailContactRepository mailContactRepository) {
        this.mailContactRepository = mailContactRepository;
    }

    public MatieresRepository getMatieresRepository() {
        return matieresRepository;
    }

    public void setMatieresRepository(MatieresRepository matieresRepository) {
        this.matieresRepository = matieresRepository;
    }

    public MotifContactRepository getMotifContactRepository() {
        return motifContactRepository;
    }

    public void setMotifContactRepository(MotifContactRepository motifContactRepository) {
        this.motifContactRepository = motifContactRepository;
    }

    public MotsClesProduitsRepository getMotsClesProduitsRepository() {
        return motsClesProduitsRepository;
    }

    public void setMotsClesProduitsRepository(MotsClesProduitsRepository motsClesProduitsRepository) {
        this.motsClesProduitsRepository = motsClesProduitsRepository;
    }

    public MotsClesRepository getMotsClesRepository() {
        return motsClesRepository;
    }

    public void setMotsClesRepository(MotsClesRepository motsClesRepository) {
        this.motsClesRepository = motsClesRepository;
    }

    public NewsletterRepository getNewsletterRepository() {
        return newsletterRepository;
    }

    public void setNewsletterRepository(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    public ProduitsRepository getProduitsRepository() {
        return produitsRepository;
    }

    public void setProduitsRepository(ProduitsRepository produitsRepository) {
        this.produitsRepository = produitsRepository;
    }

    public PanierRepository getPanierRepository() {
        return panierRepository;
    }

    public void setPanierRepository(PanierRepository panierRepository) {
        this.panierRepository = panierRepository;
    }

    public ProduitsAlternatifsRepository getProduitsAlternatifsRepository() {
        return produitsAlternatifsRepository;
    }

    public void setProduitsAlternatifsRepository(ProduitsAlternatifsRepository produitsAlternatifsRepository) {
        this.produitsAlternatifsRepository = produitsAlternatifsRepository;
    }

    public ProduitsComplementairesRepository getProduitsComplementairesRepository() {
        return produitsComplementairesRepository;
    }

    public void setProduitsComplementairesRepository(ProduitsComplementairesRepository produitsComplementairesRepository) {
        this.produitsComplementairesRepository = produitsComplementairesRepository;
    }

    public ProfilsRepository getProfilsRepository() {
        return profilsRepository;
    }

    public void setProfilsRepository(ProfilsRepository profilsRepository) {
        this.profilsRepository = profilsRepository;
    }

    public ReferenceRepository getReferenceRepository() {
        return referenceRepository;
    }

    public void setReferenceRepository(ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    public SecteurMailRepository getSecteurMailRepository() {
        return secteurMailRepository;
    }

    public void setSecteurMailRepository(SecteurMailRepository secteurMailRepository) {
        this.secteurMailRepository = secteurMailRepository;
    }

    public SsCategoriesRepository getSsCategoriesRepository() {
        return ssCategoriesRepository;
    }

    public void setSsCategoriesRepository(SsCategoriesRepository ssCategoriesRepository) {
        this.ssCategoriesRepository = ssCategoriesRepository;
    }

    public SsFamillesRepository getSsFamillesRepository() {
        return ssFamillesRepository;
    }

    public void setSsFamillesRepository(SsFamillesRepository ssFamillesRepository) {
        this.ssFamillesRepository = ssFamillesRepository;
    }

    public StockRepository getStockRepository() {
        return stockRepository;
    }

    public void setStockRepository(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public TelephonesRepository getTelephonesRepository() {
        return telephonesRepository;
    }

    public void setTelephonesRepository(TelephonesRepository telephonesRepository) {
        this.telephonesRepository = telephonesRepository;
    }

    public TelsClientsRepository getTelsClientsRepository() {
        return telsClientsRepository;
    }

    public void setTelsClientsRepository(TelsClientsRepository telsClientsRepository) {
        this.telsClientsRepository = telsClientsRepository;
    }

    public UnitesFacturationRepository getUnitesFacturationRepository() {
        return unitesFacturationRepository;
    }

    public void setUnitesFacturationRepository(UnitesFacturationRepository unitesFacturationRepository) {
        this.unitesFacturationRepository = unitesFacturationRepository;
    }

    public UnitesVenteRepository getUnitesVenteRepository() {
        return unitesVenteRepository;
    }

    public void setUnitesVenteRepository(UnitesVenteRepository unitesVenteRepository) {
        this.unitesVenteRepository = unitesVenteRepository;
    }

    public UtilisateursRepository getUtilisateursRepository() {
        return utilisateursRepository;
    }

    public void setUtilisateursRepository(UtilisateursRepository utilisateursRepository) {
        this.utilisateursRepository = utilisateursRepository;
    }


    @Override
    public String toString() {
        return "REPOFactory{" +
                "adresseClientsRepository=" + adresseClientsRepository +
                ", adressesRepository=" + adressesRepository +
                ", caracteristiqueReferenceRepository=" + caracteristiqueReferenceRepository +
                ", caracteristiquesCategoriesRepository=" + caracteristiquesCategoriesRepository +
                ", caracteristiquesProduitsRepository=" + caracteristiquesProduitsRepository +
                ", caracteristiquesRepository=" + caracteristiquesRepository +
                ", caracteristiquesSsfamillesRepository=" + caracteristiquesSsfamillesRepository +
                ", categoriesRepository=" + categoriesRepository +
                ", clientRepository=" + clientRepository +
                ", codeConfirmRepository=" + codeConfirmRepository +
                ", codepostalSecteurRepository=" + codepostalSecteurRepository +
                ", complementProRepository=" + complementProRepository +
                ", couleursRepository=" + couleursRepository +
                ", documentProduitRepository=" + documentProduitRepository +
                ", documentRepository=" + documentRepository +
                ", famillesRepository=" + famillesRepository +
                ", fideliteRepository=" + fideliteRepository +
                ", gammesRepository=" + gammesRepository +
                ", groupeCritereRepository=" + groupeCritereRepository +
                ", groupeRepository=" + groupeRepository +
                ", historiqueModificationRepository=" + historiqueModificationRepository +
                ", jobMissionRepository=" + jobMissionRepository +
                ", jobOfferRepository=" + jobOfferRepository +
                ", lignePanierRepository=" + lignePanierRepository +
                ", marquesRepository=" + marquesRepository +
                ", mailContactRepository=" + mailContactRepository +
                ", matieresRepository=" + matieresRepository +
                ", motifContactRepository=" + motifContactRepository +
                ", motsClesProduitsRepository=" + motsClesProduitsRepository +
                ", motsClesRepository=" + motsClesRepository +
                ", newsletterRepository=" + newsletterRepository +
                ", produitsRepository=" + produitsRepository +
                ", panierRepository=" + panierRepository +
                ", produitsAlternatifsRepository=" + produitsAlternatifsRepository +
                ", produitsComplementairesRepository=" + produitsComplementairesRepository +
                ", profilsRepository=" + profilsRepository +
                ", referenceRepository=" + referenceRepository +
                ", secteurMailRepository=" + secteurMailRepository +
                ", ssCategoriesRepository=" + ssCategoriesRepository +
                ", ssFamillesRepository=" + ssFamillesRepository +
                ", stockRepository=" + stockRepository +
                ", telephonesRepository=" + telephonesRepository +
                ", telsClientsRepository=" + telsClientsRepository +
                ", unitesFacturationRepository=" + unitesFacturationRepository +
                ", unitesVenteRepository=" + unitesVenteRepository +
                ", utilisateursRepository=" + utilisateursRepository +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        REPOFactory that = (REPOFactory) o;
        return Objects.equals(adresseClientsRepository, that.adresseClientsRepository) &&
                Objects.equals(adressesRepository, that.adressesRepository) &&
                Objects.equals(caracteristiqueReferenceRepository, that.caracteristiqueReferenceRepository) &&
                Objects.equals(caracteristiquesCategoriesRepository, that.caracteristiquesCategoriesRepository) &&
                Objects.equals(caracteristiquesProduitsRepository, that.caracteristiquesProduitsRepository) &&
                Objects.equals(caracteristiquesRepository, that.caracteristiquesRepository) &&
                Objects.equals(caracteristiquesSsfamillesRepository, that.caracteristiquesSsfamillesRepository) &&
                Objects.equals(categoriesRepository, that.categoriesRepository) &&
                Objects.equals(clientRepository, that.clientRepository) &&
                Objects.equals(codeConfirmRepository, that.codeConfirmRepository) &&
                Objects.equals(codepostalSecteurRepository, that.codepostalSecteurRepository) &&
                Objects.equals(complementProRepository, that.complementProRepository) &&
                Objects.equals(couleursRepository, that.couleursRepository) &&
                Objects.equals(documentProduitRepository, that.documentProduitRepository) &&
                Objects.equals(documentRepository, that.documentRepository) &&
                Objects.equals(famillesRepository, that.famillesRepository) &&
                Objects.equals(fideliteRepository, that.fideliteRepository) &&
                Objects.equals(gammesRepository, that.gammesRepository) &&
                Objects.equals(groupeCritereRepository, that.groupeCritereRepository) &&
                Objects.equals(groupeRepository, that.groupeRepository) &&
                Objects.equals(historiqueModificationRepository, that.historiqueModificationRepository) &&
                Objects.equals(jobMissionRepository, that.jobMissionRepository) &&
                Objects.equals(jobOfferRepository, that.jobOfferRepository) &&
                Objects.equals(lignePanierRepository, that.lignePanierRepository) &&
                Objects.equals(marquesRepository, that.marquesRepository) &&
                Objects.equals(mailContactRepository, that.mailContactRepository) &&
                Objects.equals(matieresRepository, that.matieresRepository) &&
                Objects.equals(motifContactRepository, that.motifContactRepository) &&
                Objects.equals(motsClesProduitsRepository, that.motsClesProduitsRepository) &&
                Objects.equals(motsClesRepository, that.motsClesRepository) &&
                Objects.equals(newsletterRepository, that.newsletterRepository) &&
                Objects.equals(produitsRepository, that.produitsRepository) &&
                Objects.equals(panierRepository, that.panierRepository) &&
                Objects.equals(produitsAlternatifsRepository, that.produitsAlternatifsRepository) &&
                Objects.equals(produitsComplementairesRepository, that.produitsComplementairesRepository) &&
                Objects.equals(profilsRepository, that.profilsRepository) &&
                Objects.equals(referenceRepository, that.referenceRepository) &&
                Objects.equals(secteurMailRepository, that.secteurMailRepository) &&
                Objects.equals(ssCategoriesRepository, that.ssCategoriesRepository) &&
                Objects.equals(ssFamillesRepository, that.ssFamillesRepository) &&
                Objects.equals(stockRepository, that.stockRepository) &&
                Objects.equals(telephonesRepository, that.telephonesRepository) &&
                Objects.equals(telsClientsRepository, that.telsClientsRepository) &&
                Objects.equals(unitesFacturationRepository, that.unitesFacturationRepository) &&
                Objects.equals(unitesVenteRepository, that.unitesVenteRepository) &&
                Objects.equals(utilisateursRepository, that.utilisateursRepository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adresseClientsRepository, adressesRepository, caracteristiqueReferenceRepository, caracteristiquesCategoriesRepository, caracteristiquesProduitsRepository, caracteristiquesRepository, caracteristiquesSsfamillesRepository, categoriesRepository, clientRepository, codeConfirmRepository, codepostalSecteurRepository, complementProRepository, couleursRepository, documentProduitRepository, documentRepository, famillesRepository, fideliteRepository, gammesRepository, groupeCritereRepository, groupeRepository, historiqueModificationRepository, jobMissionRepository, jobOfferRepository, lignePanierRepository, marquesRepository, mailContactRepository, matieresRepository, motifContactRepository, motsClesProduitsRepository, motsClesRepository, newsletterRepository, produitsRepository, panierRepository, produitsAlternatifsRepository, produitsComplementairesRepository, profilsRepository, referenceRepository, secteurMailRepository, ssCategoriesRepository, ssFamillesRepository, stockRepository, telephonesRepository, telsClientsRepository, unitesFacturationRepository, unitesVenteRepository, utilisateursRepository);
    }
}