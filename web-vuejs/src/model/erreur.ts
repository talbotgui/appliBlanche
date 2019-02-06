
/** Liste des sévérités */
export enum Severite { Info, Warn, Error }

/** Message d'erreur */
export class MessageErreur {
    constructor(public message: string, public severite: Severite) { }
}
