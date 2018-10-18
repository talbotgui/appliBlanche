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
    public prixPaye: number,
    /** Quantite */
    public quantite: number,
    /** Reservation associee */
    public reservation: Reservation,
    /** Produit associe */
    public produit: Produit
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
