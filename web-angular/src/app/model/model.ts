
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

