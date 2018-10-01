export class Chambre {
  /**
   * Constructeur.
   * @param reference Identifiant unique
   * @param nom Nom
   */
  constructor(reference: string, nom: string) { }
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
    reference: string,
    dateDebut: Date,
    dateFin: Date,
    client: string,
    chambre: Chambre) { }
}
