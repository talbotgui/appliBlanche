// L'usage de '!' permet de ne pas initialiser la valeur de la propriété
// Documentation : http://www.typescriptlang.org/docs/handbook/release-notes/typescript-2-7.html#strict-class-initialization

/** Tri dans une page de résultats (identique à la classe de Spring) */
export class Sort {
    /** non trié */
    public unsorted!: boolean;
    /** trié */
    public sorted!: boolean;
    /** colonne de tri */
    public sortColonne!: string;
    /** sans du tri */
    public sortOrder!: string;
}

/** Page de résultats (identique à la classe de Spring) */
export class Page<T> {
    /** Elements de la page */
    public content!: T[];
    /** Nombre d'éléments dans la page */
    public numberOfElements!: number;
    /** Nombre d'éléments au total */
    public totalElements!: number;
    /** Nombre de pages au total */
    public totalPages!: number;
    /** Paramètres de tri */
    public sort!: Sort | undefined;
    /** Constructeur (number est le nom de l'attribut dans la classe Java de Spring) */
    constructor(public size: number,
        /* tslint:disable-next-line */
        public number: number) { }
}

/** Objet métier Utilisateur */
export class Utilisateur {
    /** Identifiant unique */
    public login!: string;
    /** Mot de passe */
    public mdp!: string;
    /** Liste des rôles */
    public roles!: Role[];
    /** Date d'un échec de connexion */
    public premierEchec!: Date;
    /** Date d'un échec de connexion */
    public secondEchec!: Date;
    /** Date d'un échec de connexion */
    public troisiemeEchec!: Date;
}

/** Objet métier Role */
export class Role {
    /** Identifiant unique */
    public nom!: string;
    /** Liste des ressources autorisées */
    public ressourcesAutorisees!: Ressource[];
}

/** Objet métier Ressource */
export class Ressource {
    /** Identifiant unique */
    public clef!: string;
    /** Description */
    public description!: string;
}
