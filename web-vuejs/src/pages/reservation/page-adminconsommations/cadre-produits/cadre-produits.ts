import { Component, Vue } from 'vue-property-decorator';
import { Produit } from '@/model/reservation-model';
import AnimationUtils from '@/services/utilitaire/animationUtils';
import { ReservationService } from '@/services/reservations/reservation.service';

@Component
export default class CadreProduits extends Vue {

    /** Liste des couleurs disponibles */
    public couleursPossibles: string[] = ['AliceBlue', 'AntiqueWhite', 'Aqua', 'Aquamarine', 'Azure', 'Beige', 'Bisque', 'Black', 'BlanchedAlmond', 'Blue',
        'BlueViolet', 'Brown', 'BurlyWood', 'CadetBlue', 'Chartreuse', 'Chocolate', 'Coral', 'CornflowerBlue', 'Cornsilk',
        'Crimson', 'Cyan', 'DarkBlue', 'DarkCyan', 'DarkGoldenRod', 'DarkGray', 'DarkGrey', 'DarkGreen', 'DarkKhaki', 'DarkMagenta',
        'DarkOliveGreen', 'DarkOrange', 'DarkOrchid', 'DarkRed', 'DarkSalmon', 'DarkSeaGreen', 'DarkSlateBlue', 'DarkSlateGray',
        'DarkSlateGrey', 'DarkTurquoise', 'DarkViolet', 'DeepPink', 'DeepSkyBlue', 'DimGray', 'DimGrey', 'DodgerBlue', 'FireBrick',
        'FloralWhite', 'ForestGreen', 'Fuchsia', 'Gainsboro', 'GhostWhite', 'Gold', 'GoldenRod', 'Gray', 'Grey', 'Green',
        'GreenYellow', 'HoneyDew', 'HotPink', 'IndianRed', 'Indigo', 'Ivory', 'Khaki', 'Lavender', 'LavenderBlush', 'LawnGreen',
        'LemonChiffon', 'LightBlue', 'LightCoral', 'LightCyan', 'LightGoldenRodYellow', 'LightGray', 'LightGrey', 'LightGreen',
        'LightPink', 'LightSalmon', 'LightSeaGreen', 'LightSkyBlue', 'LightSlateGray', 'LightSlateGrey', 'LightSteelBlue',
        'LightYellow', 'Lime', 'LimeGreen', 'Linen', 'Magenta', 'Maroon', 'MediumAquaMarine', 'MediumBlue', 'MediumOrchid',
        'MediumPurple', 'MediumSeaGreen', 'MediumSlateBlue', 'MediumSpringGreen', 'MediumTurquoise', 'MediumVioletRed',
        'MidnightBlue', 'MintCream', 'MistyRose', 'Moccasin', 'NavajoWhite', 'Navy', 'OldLace', 'Olive', 'OliveDrab', 'Orange',
        'OrangeRed', 'Orchid', 'PaleGoldenRod', 'PaleGreen', 'PaleTurquoise', 'PaleVioletRed', 'PapayaWhip', 'PeachPuff', 'Peru',
        'Pink', 'Plum', 'PowderBlue', 'Purple', 'RebeccaPurple', 'Red', 'RosyBrown', 'RoyalBlue', 'SaddleBrown', 'Salmon',
        'SandyBrown', 'SeaGreen', 'SeaShell', 'Sienna', 'Silver', 'SkyBlue', 'SlateBlue', 'SlateGray', 'SlateGrey', 'Snow',
        'SpringGreen', 'SteelBlue', 'Tan', 'Teal', 'Thistle', 'Tomato', 'Turquoise', 'Violet', 'Wheat', 'White', 'WhiteSmoke',
        'Yellow', 'YellowGreen'];

    /** Element en cours d'édition */
    public nouveauProduit: Produit | null = null;

    /** Données du tableau */
    public produits: Produit[] = [];

    /** Flag indiquant l'état de validation du formulaire */
    public valide: boolean = true;

    /** Regles de validation du formulaire */
    public nomRegles = [(v: any) => (!!v) || 'todo'];
    public couleurRegles = [(v: any) => (!!v) || 'todo'];
    public prixRegles = [(v: any) => (!!v && v >= 0) || 'todo'];

    /** Une dépendance */
    private reservationService: ReservationService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.reservationService = new ReservationService();
    }

    public mounted() {
        // Reset du formulaire
        this.annulerCreation();
        // Creation du datasource
        this.chargerDonnees();
    }

    /** On annule la creation en masquant le formulaire */
    public annulerCreation() {
        this.nouveauProduit = null;
    }

    /** Initialisation de l'objet Utilisateur pour accueillir les données du formulaire */
    public creer() {
        this.nouveauProduit = new Produit('', '', '', 0);
        this.deplacerLaVueSurLeFormulaire();
    }

    /** Appel au service de sauvegarde puis rechargement des données */
    public sauvegarder() {
        if (this.nouveauProduit) {
            const mdp = new Produit(this.nouveauProduit.reference, this.nouveauProduit.couleur, this.nouveauProduit.nom, this.nouveauProduit.prix);
            this.reservationService.sauvegarderProduit(mdp).subscribe((retour: any) => {
                // Reset du formulaire
                this.annulerCreation();
                // Creation du datasource
                this.chargerDonnees();
            });
        }
    }

    /** A la sélection */
    public selectionner(mdp: Produit) {
        this.nouveauProduit = mdp;
        this.deplacerLaVueSurLeFormulaire();
    }

    /** A la suppression  */
    public supprimer(mdp: Produit) {
        this.reservationService.supprimerProduit(mdp.reference)
            .subscribe((retour: any) => {
                this.chargerDonnees();
                this.annulerCreation();
            });
    }

    private chargerDonnees() {
        this.reservationService.listerProduits().subscribe((produits: any) => this.produits = produits);
    }

    private deplacerLaVueSurLeFormulaire() {
        (new AnimationUtils()).deplacerLaVueSurLeComposant('formulaire', true);
    }

}
