/** Tri dans une page de résultats (identique à la classe de Spring) */
export class Sort {
  /** non trié */
  unsorted: boolean;
  /** trié */
  sorted: boolean;
  /** colonne de tri */
  sortColonne: string;
  /** sans du tri */
  sortOrder: string;
}
/** Page de résultats (identique à la classe de Spring) */
export class Page<T> {
  /** Elements de la page */
  content: T[];
  /** Nombre d'éléments dans la page */
  numberElements: number;
  /** Nombre d'éléments au total */
  totalElements: number;
  /** Nombre de pages au total */
  totalPages: number;
  /** Paramètres de tri */
  sort: Sort | undefined;
  /** Constructeur (number est le nom de l'attribut dans la classe Java de Spring) */
  constructor(public size: number,
    /* tslint:disable-next-line */
    public number: number) { }
}

/** DTO pour un client */
export class ClientDto {
  /** Identifiant unique */
  reference: string;
  /** Nom du client */
  nomClient: string;
  /** Ville de l'adresse */
  ville: string;
  /** Nombre de dossiers existants */
  nbDossiers: number;
  /** Nombre de demandes existantes */
  nbDemandes: number;
  /** Date de création du dossier le plus récent */
  dateCreationDernierDossier: Date;
}

/** Objet métier Client */
export class Client {
  /** Identifiant unique */
  reference: string;
  /** Nom */
  nom: string;
  /** Date de création en BDD */
  dateCreation: Date;
}

/** Objet métier Utilisateur */
export class Utilisateur {
  /** Identifiant unique */
  login: string;
  /** Mot de passe */
  mdp: string;
  /** Liste des rôles */
  roles: Role[];
  /** Date d'un échec de connexion */
  premierEchec: Date;
  /** Date d'un échec de connexion */
  secondEchec: Date;
  /** Date d'un échec de connexion */
  troisiemeEchec: Date;
}

/** Objet métier Role */
export class Role {
  /** Identifiant unique */
  nom: string;
  /** Liste des ressources autorisées */
  ressourcesAutorisees: Ressource[];
}

/** Objet métier Ressource */
export class Ressource {
  /** Identifiant unique */
  clef: string;
  /** Description */
  description: string;
}
