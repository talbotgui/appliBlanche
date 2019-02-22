
/** Liste des sévérités */
export enum Severite { Info, Warn, Error }

/** Message d'erreur */
export class MessageErreur {
    constructor(public codeMessage: string, public severite: Severite, public parametresMessage: string[]) { }
}
