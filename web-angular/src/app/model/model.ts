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

/** Gestion du menu (cette classe n'a pas d'équivalent dans le backend) */
export class PageApplicative {
  /**
   *  Constructeur
   * @param nom Nom
   * @param icone Icone FontAwesome
   * @param clefApi Clef de l'API
   * @param route Route dans le routeur Angular
   */
  constructor(public nom: string, public icone: string, public clefApi: string, public route: string) { }
}

/** Module applicatif dans le menu (cette classe n'a pas d'équivalent dans le backend) */
export class ModuleApplicatif {
  /**
   * Constructeur
   * @param nom Nom
   * @param icone Icone FontAwesome
   * @param pages Pages du module
   */
  constructor(public nom: string, public icone: string, public pages: PageApplicative[]) { }
}
