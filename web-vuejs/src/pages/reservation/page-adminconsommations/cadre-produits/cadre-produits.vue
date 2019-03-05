<template>
	<v-layout wrap align-center>

		<v-flex xs12 d-flex>
			<h3 v-t="'adminConso_titre_listeDesProduits'"></h3>
			<v-tooltip bottom>
				<template #activator="data"><em class="actionDansUnEntete fa fa-plus" @click="creer();"></em></template>
				<span v-t="'commun_tooltip_ajouter'"></span>
			</v-tooltip>
		</v-flex>

		<v-flex xs12 d-flex>
			<ul>
				<li v-for="p in produits" v-bind:key="p.reference" :style="'color:' + p.couleur">
					{{p.nom}} {{p.prix}}
					<v-tooltip bottom>
						<template #activator="data"><em class="fa fa-edit" @click="selectionner(p)"></em></template>
						<span v-t="'commun_tooltip_editer'"></span>
					</v-tooltip>
					<v-tooltip bottom>
						<template #activator="data"><em class="fa fa-trash-alt" @click="supprimer(p)"></em></template>
						<span v-t="'commun_tooltip_supprimer'"></span>
					</v-tooltip>
				</li>
			</ul>
		</v-flex>

		<!-- Création ou édition -->
		<v-flex xs12 d-flex>
			<v-form v-model="valide" @keyup.native.enter="valide && sauvegarder()" v-on:submit.prevent>
				<div id="formulaire" v-if="!!nouveauProduit">
					<hr />

					<!-- Champs -->
					<v-text-field required v-model="nouveauProduit.nom" name="nom" :rules="nomRegles" :label="$t('adminConso_placeholder_nomProduit')"></v-text-field>
					<v-select :items="couleursPossibles" required v-model="nouveauProduit.couleur" name="couleur" :rules="couleurRegles" :label="$t('adminConso_placeholder_couleurProduit')"></v-select>
					<v-text-field type="number" required v-model="nouveauProduit.prix" name="prix" :rules="prixRegles" :label="$t('adminConso_placeholder_prix')"></v-text-field>

					<!-- Boutons -->
					<v-btn @click="sauvegarder()" :disabled="!valide" v-t="'moyendepaiement_bouton_creer'"></v-btn>
					<v-btn @click="annulerCreation()" v-t="'moyendepaiement_bouton_annuler'"></v-btn>
				</div>
			</v-form>
		</v-flex>

	</v-layout>
</template>

<script lang="ts" src="./cadre-produits.ts"/>
<style scoped lang="scss" src="./cadre-produits.scss"/>
