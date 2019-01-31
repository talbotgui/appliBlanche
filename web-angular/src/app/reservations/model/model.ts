/** Constante d'état */
const ETAT_RESERVATION_ENREGISTREE = 'ENREGISTREE';
/** Constante d'état */
const ETAT_RESERVATION_EN_COURS = 'EN_COURS';
/** Constante d'état */
const ETAT_RESERVATION_TERMINEE = 'TERMINEE';

/** Chambre */
export class Chambre {
  /**
   * Constructeur.
   * @param reference Identifiant unique
   * @param nom Nom
   */
  constructor(
    /** référence unique */
    public reference: string,
    /** Nom de la chambre */
    public nom: string) { }
}

/** Formule */
export class Formule {
  /**
   * Constructeur.
   * @param reference Identifiant unique
   * @param nom Nom
   * @param prixParNuit Prix par nuit
   */
  constructor(
    /** référence unique */
    public reference: string,
    /** Nom de la chambre */
    public nom: string,
    /** Prix par nuit */
    public prixParNuit: number) { }
}

/** Option */
export class Option {
  /**
   * Constructeur.
   * @param reference Identifiant unique
   * @param nom Nom
   * @param prix Prix
   * @param parNuit Flag si le prix est par nuit
   * @param parPersonne Flag si le prix est par personne
   */
  constructor(
    /** référence unique */
    public reference: string,
    /** Nom de la chambre */
    public nom: string,
    /** Prix */
    public prix: number,
    /** Flag si le prix est par nuit */
    public parNuit: boolean,
    /** Flag si le prix est par personne */
    public parPersonne: boolean) { }
}

/** Objet métier Reservation */
export class Reservation {

  /** Options associées */
  public options: Option[];

  /** Paiements associées */
  public paiements: Paiement[];

  /** Statut */
  public etatCourant: string;

  /** Nombre de personnes à loger */
  public nombrePersonnes: number;

  /** Constructeur. */
  constructor(
    /** référence unique */
    public reference: string,
    /** Date de début de la réservation */
    public dateDebut: Date,
    /** Date de fin de la réservation */
    public dateFin: Date,
    /** Nom du client */
    public client: string,
    /** Chambre associée */
    public chambre: Chambre,
    /** Formule associée */
    public formule: Formule
  ) { }
}

/** Objet métier Consommation */
export class Consommation {

  /** Constructeur. */
  constructor(
    /** référence unique */
    public reference: string,
    /** Date de saisie de la consommation */
    public dateCreation: Date,
    /** Prix */
    public prixPaye: number | undefined,
    /** Quantite */
    public quantite: number,
    /** Reservation associee */
    public reservation: Reservation,
    /** Produit associe */
    public produit: Produit
  ) { }
}

/** Objet métier MoyenDePaiement */
export class MoyenDePaiement {

  /** Constructeur. */
  constructor(
    /** référence unique */
    public reference: string,
    /** Montant associé (pour les box notamment) */
    public montantAssocie: number,
    /** Nom */
    public nom: string
  ) { }
}

/** un produit */
export class Produit {

  /** Constructeur. */
  constructor(
    /** référence unique */
    public reference: string,
    /** couleur à utiliser */
    public couleur: string,
    /** Nom  */
    public nom: string,
    /** Prix */
    public prix: number) { }
}

/** Map<String, T> */
export interface IStringToAnyMap<T> {
  /** clef de type String et valeur paramétrée */
  [key: string]: T;
}

/** facture */
export class Facture {
  /** Montant total */
  public montantTotal: number;

  /** Contenu du PDF de la facture en base64 */
  public pdf: Blob;
}

/** Un paiement */
export class Paiement {
  /** Date de création du paiement */
  public dateCreation: Date;
  /** Montant */
  public montant: number | undefined;
  /** Moyen de paiement */
  public moyenDePaiement: MoyenDePaiement;
}
