<!-- Le formulaire de détail -->
<template>
	<v-layout wrap align-center v-if="!!reservationSelectionnee.reference || creation">

		<v-flex xs12 d-flex>
			<h3>
				<span v-t="'reservation_titre'"></span>
				<span>{{ reservationSelectionnee.reference.split('-')[1] }}</span>
			</h3>
		</v-flex>

		<v-form v-model="valide" @keyup.native.enter="valide && enregistrerReservationSelectionnee()" v-on:submit.prevent>

			<v-flex xs12 d-flex>
				<v-menu v-model="dateDebut.datePick" :close-on-content-click="false" :nudge-right="40" lazy transition="scale-transition" offset-y
				        class="petitdatepicker" full-width min-width="100px">
					<template v-slot:activator="{ on }">
						<v-text-field :label="$t('reservation_placeholder_dateDebut')" v-model="dateDebut.dateCourte" readonly v-on="on" :disabled="reservationSelectionnee.etatCourant==='TERMINEE'"></v-text-field>
					</template>
					<v-date-picker :locale="$i18n.locale" v-model="dateDebut.dateComplete" @input="dateDebut.datePick = false" :max="dateFin.dateComplete"></v-date-picker>
				</v-menu>
			</v-flex>

			<v-flex xs12 d-flex>
				<v-menu v-model="dateFin.datePick" :close-on-content-click="false" :nudge-right="40" lazy transition="scale-transition" offset-y class="petitdatepicker"
				        full-width min-width="100px">
					<template v-slot:activator="{ on }">
						<v-text-field :label="$t('reservation_placeholder_dateFin')" v-model="dateFin.dateCourte" readonly v-on="on" :disabled="reservationSelectionnee.etatCourant==='TERMINEE'"></v-text-field>
					</template>
					<v-date-picker :locale="$i18n.locale" v-model="dateFin.dateComplete" @input="dateFin.datePick = false" :min="dateDebut.dateComplete"></v-date-picker>
				</v-menu>
			</v-flex>

			<v-flex xs12 d-flex>
				<v-text-field required v-model="reservationSelectionnee.client" name="client" :label="$t('reservation_placeholder_client')" :rules="[(v) => (!!v) || $t('commmun_champ_obligatoire')]"
				              :disabled="reservationSelectionnee.etatCourant==='TERMINEE'"></v-text-field>
			</v-flex>

			<v-flex xs12 d-flex>
				<v-select :items="chambres" item-text="nom" item-value="reference" required v-model="reservationSelectionnee.chambre.reference" name="chambre"
				          :label="$t('reservation_placeholder_chambre')" :disabled="reservationSelectionnee.etatCourant==='TERMINEE'"></v-select>
			</v-flex>

			<v-flex xs12 d-flex>
				<v-select :items="formules" :item-text="calculerLibelleFormule" item-value="reference" required v-model="reservationSelectionnee.formule.reference"
				          name="formule" :label="$t('reservation_placeholder_formule')" :disabled="reservationSelectionnee.etatCourant==='TERMINEE'">
				</v-select>
			</v-flex>

			<v-flex xs12 d-flex>
				<v-text-field type="number" required v-model="reservationSelectionnee.nombrePersonnes" name="nombrePersonnes" :rules="[(v) => (!!v) || $t('commmun_champ_obligatoire')]"
				              :label="$t('reservation_placeholder_nombrePersonnes')"></v-text-field>
			</v-flex>

			<v-flex xs12 d-flex v-for="(o, index) of options" :key="o.reference">
				<v-checkbox :name="'parNuit'+index" v-model="optionsCalculeesPourLaReservationSelectionnee[o.reference]" :disabled="reservationSelectionnee.etatCourant==='TERMINEE'"
				            :label="o.nom + ' (' + o.prix + '€' + ((o.parNuit)?$t('commun_parNuit'):'') + ((o.parPersonne)?$t('commun_parPersonne'):'') + ')'"></v-checkbox>
			</v-flex>

			<v-flex xs12 d-flex>
				<button @click="enregistrerReservationSelectionnee()" v-if="reservationSelectionnee.etatCourant!=='TERMINEE'" :disabled="!valide" v-t="'reservation_bouton_enregistrer'"></button>
				<button @click="changerEtatEnCours()" v-if="reservationSelectionnee.etatCourant==='ENREGISTREE'" v-t="'reservation_bouton_arriveeClient'"></button>
				<button @click="annulerOuFermer()" v-if="reservationSelectionnee.reference" v-t="'reservation_bouton_fermer'"></button>
				<button @click="annulerOuFermer()" v-if="!reservationSelectionnee.reference" v-t="'reservation_bouton_annuler'"></button>
			</v-flex>
		</v-form>
	</v-layout>
</template>

<script lang="ts" src="./cadre-reservation.ts"/>
<style scoped lang="scss" src="./cadre-reservation.scss"/>