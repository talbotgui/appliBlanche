export class DateUtils {
    /** Format une Date en chaine de caractères. Format : 2019-12-24 */
    public formaterDate(laDate: Date): string {
        if (laDate) {
            return laDate.getFullYear() + '-' + this.formatNombre(laDate.getMonth() + 1) + '-' + this.formatNombre(laDate.getDate());
        } else {
            return '';
        }
    }

    /**
     *  D'une chaine de caractères à une date. Format : 2019-12-24
     * @param chaine chaine typée en ANY pour prendre en paramètre un attribut typé DATE mais contenant une STRING.
     */
    public parserDateYYYYMMDD(chaine: string | Date): Date {
        if (chaine instanceof String) {
            return new Date(chaine + 'T00:00:00.000Z');
        } else {
            return new Date(chaine);
        }
    }

    /** D'une chaine de caractères à une date. Format : 19-12-24 */
    public parserDateYYMMDD(chaine: string) {
        const annee = 2000 + Number.parseInt(chaine.substring(0, 2), 10);
        const mois = Number.parseInt(chaine.substring(3, 5), 10) - 1;
        const jour = Number.parseInt(chaine.substring(6, 8), 10);
        return new Date(annee, mois, jour);
    }

    /** Format une Date en chaine de caractères. Format : 24/12 */
    public formaterDateDDMM(laDate: Date): string {
        if (laDate) {
            return this.formatNombre(laDate.getDate()) + '/' + this.formatNombre(laDate.getMonth() + 1);
        } else {
            return '';
        }
    }

    /** Format une Date en chaine de caractères Format : 19-12-24 */
    public formaterDateYYMMDD(laDate: Date): string {
        if (laDate) {
            return (laDate.getFullYear() + '').substring(2, 4) + '-' + this.formatNombre(laDate.getMonth() + 1) + '-' + this.formatNombre(laDate.getDate());
        } else {
            return '';
        }
    }

    /** Retourne un nombre sur 2 caractères */
    private formatNombre(n: number): string {
        if (n < 10) {
            return '0' + n;
        } else {
            return '' + n;
        }
    }

}
