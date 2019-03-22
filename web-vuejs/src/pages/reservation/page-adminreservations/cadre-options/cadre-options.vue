<template>
	<v-layout wrap align-center>

		<v-flex xs12 d-flex>
			<h3>
				<span v-t="'adminResa_titre_listeDesOptions'"></span>
				<v-tooltip bottom>
					<template #activator="data"><em class="actionDansUnEntete fa fa-plus" @click="creer();"></em></template>
					<span v-t="'commun_tooltip_ajouter'"></span>
				</v-tooltip>
			</h3>
		</v-flex>

		<v-flex xs12 d-flex>
			<ul>
				<li v-for="o in options" v-bind:key="o.reference">
					<span>{{o.nom}} ({{o.prix}}€</span>
					<span v-if="o.parNuit" v-t="'commun_parNuit'"></span>
					<span v-if="o.parPersonne" v-t="'commun_parPersonne'"></span>
					<span>)</span>
					<v-tooltip bottom>
						<template #activator="data"><em class="fa fa-edit" @click="selectionner(o)"></em></template>
						<span v-t="'commun_tooltip_editer'"></span>
					</v-tooltip>
					<v-tooltip bottom>
						<template #activator="data"><em class="fa fa-trash-alt" @click="supprimer(o)"></em></template>
						<span v-t="'commun_tooltip_supprimer'"></span>
					</v-tooltip>
				</li>
			</ul>
		</v-flex>

		<!-- Création ou édition -->
		<v-flex xs12 d-flex>
			<v-form v-model="valide" @keyup.native.enter="valide && sauvegarder()" v-on:submit.prevent>
				<div id="formulaire" v-if="!!nouvelleOption">

					<!-- Titre -->
					<hr />

					<!-- Champs -->
					<v-text-field required v-model="nouvelleOption.nom" name="nom" :label="$t('adminResa_placeholder_nomOption')" :rules="[(v) => (!!v) || $t('commmun_champ_obligatoire')]"></v-text-field>
					<v-text-field type="number" required v-model="nouvelleOption.prix" name="prix" :label="$t('adminResa_placeholder_prix')" :rules="[(v) => (!!v && v >= 0) || $t('commmun_champ_obligatoire')]"></v-text-field>
					<v-checkbox v-model="nouvelleOption.parNuit" name="parNuit" :label="$t('adminResa_placeholder_prixParNuit')"></v-checkbox>
					<v-checkbox v-model="nouvelleOption.parPersonne" name="parPersonne" :label="$t('adminResa_placeholder_prixParPersonne')"></v-checkbox>

					<!-- Boutons -->
					<v-btn @click="sauvegarder()" :disabled="!valide">{{$t('commun_tooltip_valider')}}</v-btn>
					<v-btn @click="annulerCreation()">{{$t('commun_tooltip_annuler')}}</v-btn>
				</div>
			</v-form>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./cadre-options.ts"/>
<style scoped lang="scss" src="./cadre-options.scss"/>
