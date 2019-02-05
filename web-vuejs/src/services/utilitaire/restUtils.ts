
export default class RestUtils {

    /** Creation des entetes d'appel à une méthode REST */
    public creerHeader(): { headers: any } | undefined {
        const entete: any = { 'Content-Type': 'application/json' };
        return { headers: entete };
    }

}
