export class Page<T> {
  constructor(public size: number, public number: number) { }
  content: T[];
  next: string;
  previous: string;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  totalOfElements: number;
  totalPages: number;
}

export class ClientDto {
  reference: string;
  nomClient: string;
  ville: string;
  nbDossiers: number;
  nbDemandes: number;
  dateCreationDernierDossier: Date;
}

export class Client {
  reference: string;
  nom: string;
  dateCreation: Date;
}

export class Utilisateur {
  login: string;
  mdp: string;
  premierEchec: Date;
  roles: Role[];
  secondEchec: Date;
  troisiemeEchec: Date;
}

export class Role {
  nom: string;
  ressourcesAutorisees: Ressource[];
}

export class Ressource {
  clef: string;
  description: string;
}

