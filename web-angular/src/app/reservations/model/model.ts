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
    public chambre: Chambre) { }
}

/** Map<String, T> */
export interface IStringToAnyMap<T> {
  /** clef de type String et valeur paramétrée */
  [key: string]: T;
}
