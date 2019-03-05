<template>
	<v-layout wrap align-center>

		<v-flex xs12 d-flex>
			<h3 v-t="'moyendepaiement_titre'"></h3>
		</v-flex>

		<v-flex xs12 d-flex>
			<table>
				<thead>
					<tr>
						<th v-t="'moyendepaiement_entete_nom'"></th>
						<th v-t="'moyendepaiement_entete_montantAssocie'"></th>
						<th>
							<span v-t="'commun_entete_actions'"></span>
							<span>&nbsp;</span>
							<v-tooltip bottom>
								<template #activator="data"><em class="actionDansUnEntete fa fa-plus" @click="creer();"></em></template>
								<span v-t="'commun_tooltip_ajouter'"></span>
							</v-tooltip>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="mdp in moyensDePaiement" v-bind:key="mdp.reference">
						<td>{{mdp.nom}}</td>
						<td>{{mdp.montantAssocie}}</td>
						<td>
							<v-tooltip bottom>
								<template #activator="data"><em class="fa fa-edit" @click="selectionner(mdp)"></em></template>
								<span v-t="'commun_tooltip_editer'"></span>
							</v-tooltip>
							<v-tooltip bottom>
								<template #activator="data"><em class="fa fa-trash-alt" @click="supprimer(mdp)"></em></template>
								<span v-t="'commun_tooltip_supprimer'"></span>
							</v-tooltip>
						</td>
					</tr>
				</tbody>
			</table>
		</v-flex>

		<!-- Création ou édition -->
		<v-flex xs12 d-flex>
			<v-form v-model="valide" @keyup.native.enter="valide && sauvegarder()" v-on:submit.prevent>
				<div id="formulaire" v-if="!!nouveauMoyenDePaiement">

					<!-- Titre -->
					<hr />
					<h3 v-t="'moyendepaiement_formulaire_titre'"></h3>

					<!-- Champs -->
					<v-text-field required v-model="nouveauMoyenDePaiement.nom" name="nom" :rules="nomRegles" :label="$t('moyendepaiement_placeholder_nom')"></v-text-field>
					<v-text-field type="number" required v-model="nouveauMoyenDePaiement.montantAssocie" name="montantAssocie" :label="$t('moyendepaiement_placeholder_montantAssocie')"></v-text-field>

					<!-- Boutons -->
					<v-btn @click="sauvegarder()" :disabled="!valide" v-t="'moyendepaiement_bouton_creer'"></v-btn>
					<v-btn @click="annulerCreation()" v-t="'moyendepaiement_bouton_annuler'"></v-btn>
				</div>
			</v-form>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./cadre-moyendepaiement.ts"/>
<style scoped lang="scss" src="./cadre-moyendepaiement.scss"/>
