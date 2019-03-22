<template>
	<v-layout wrap align-center>

		<v-flex xs12 d-flex>
			<h3 v-t="'moyendepaiement_titre'"></h3>
		</v-flex>

		<v-flex xs12 d-flex>
			<v-data-table :headers="entetes" :items="moyensDePaiement" :must-sort="true">

				<template v-slot:no-data>Aucune donnée disponible</template>

				<template slot="headerCell" slot-scope="props">
					<span v-t="props.header.text"></span>

					<!-- uniquement dans la colonne 'action' -->
					<span v-if="props.header.value=='action'">&nbsp;</span>
					<v-tooltip v-if="props.header.value=='action'" bottom>
						<template v-slot:activator="{ on }">
							<span v-on="on"><em class="actionDansUnEntete fa fa-plus" @click="creer();">&nbsp;</em></span>
						</template>
						<span v-t="'commun_tooltip_ajouter'"></span>
					</v-tooltip>
				</template>

				<template v-slot:items="ligne">
					<td>{{ ligne.item.nom }}</td>
					<td>{{ ligne.item.montantAssocie }}</td>
					<td>
						<v-tooltip bottom>
							<template v-slot:activator="{ on }">
								<span v-on="on"><em class="actionDansUnEntete fa fa-edit" @click="selectionner(ligne.item);">&nbsp;</em></span>
							</template>
							<span v-t="'commun_tooltip_supprimer'"></span>
						</v-tooltip>
						<v-tooltip bottom>
							<template v-slot:activator="{ on }">
								<span v-on="on"><em class="actionDansUnEntete fa fa-trash-alt" @click="supprimer(ligne.item);">&nbsp;</em></span>
							</template>
							<span v-t="'commun_tooltip_supprimer'"></span>
						</v-tooltip>
					</td>
				</template>
			</v-data-table>
		</v-flex>

		<!-- Création ou édition -->
		<v-flex xs12 d-flex>
			<v-form v-model="valide" @keyup.native.enter="valide && sauvegarder()" v-on:submit.prevent>
				<div id="formulaire" v-if="!!nouveauMoyenDePaiement">

					<!-- Titre -->
					<hr />
					<h3 v-t="'moyendepaiement_formulaire_titre'"></h3>

					<!-- Champs -->
					<v-text-field required v-model="nouveauMoyenDePaiement.nom" name="nom" :label="$t('moyendepaiement_placeholder_nom')" :rules="[(v) => (!!v) || $t('moyendepaiement_placeholder_nom_validation')]"></v-text-field>
					<v-text-field type="number" required v-model="nouveauMoyenDePaiement.montantAssocie" name="montantAssocie" :label="$t('moyendepaiement_placeholder_montantAssocie')"></v-text-field>

					<!-- Boutons -->
					<v-btn @click="sauvegarder()" :disabled="!valide">{{$t('moyendepaiement_bouton_creer')}}</v-btn>
					<v-btn @click="annulerCreation()">{{$t('moyendepaiement_bouton_annuler')}}</v-btn>
				</div>
			</v-form>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./cadre-moyendepaiement.ts"/>
<style scoped lang="scss" src="./cadre-moyendepaiement.scss"/>
