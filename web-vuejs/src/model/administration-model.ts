export class ElementMonitoring {
    /** Clef de la méthode */
    public clef!: string;
    /** Nombre d'appel */
    public nbAppels!: number;
    /** Temps d'exécution cumulé de tous les appels */
    public tempsCumule!: number;
    /** Temps maximal d'un appel */
    public tempsMax!: number;
    /** Temps minimal d'un appel */
    public tempsMin!: number;
    /** Temps moyen d'un appel */
    public tempsMoyen!: number;
}
