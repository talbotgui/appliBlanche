export class DateUtils {
    /** Format une Date en chaine de caractères */
    public formaterDate(laDate: Date): string {
        if (laDate) {
            return laDate.getFullYear() + '-' + this.formatNombre(laDate.getMonth() + 1) + '-' + this.formatNombre(laDate.getDate());
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
