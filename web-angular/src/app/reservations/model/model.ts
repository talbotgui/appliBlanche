export class Chambre {
  /**
   * Constructeur.
   * @param reference Identifiant unique
   * @param nom Nom
   */
  constructor(public reference: string, public nom: string) { }
}

/** Objet métier Reservation */
export class Reservation {

  /** Constructeur.
   * @param reference Identifiant unique
   * @param dateDebut Date de debut
   * @param dateFin Date de fin
   * @param client Client
   * @param chambre Chambre
   */
  constructor(
    public reference: string,
    public dateDebut: Date,
    public dateFin: Date,
    public client: string,
    public chambre: Chambre) { }
}

export interface IStringToAnyMap<T> {
  [key: string]: T;
}
