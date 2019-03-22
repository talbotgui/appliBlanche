<template>
	<v-layout wrap align-center>

		<v-flex xs12 d-flex>
			<h3>
				<span v-t="'adminResa_titre_listeDesFormules'"></span>
				<v-tooltip bottom>
					<template #activator="data"><em class="actionDansUnEntete fa fa-plus" @click="creer();"></em></template>
					<span v-t="'commun_tooltip_ajouter'"></span>
				</v-tooltip>
			</h3>
		</v-flex>

		<v-flex xs12 d-flex>
			<ul>
				<li v-for="f in formules" v-bind:key="f.reference">
					<span>{{f.nom}} ({{f.prixParNuit}}€)</span>
					<v-tooltip bottom>
						<template #activator="data"><em class="fa fa-edit" @click="selectionner(f)"></em></template>
						<span v-t="'commun_tooltip_editer'"></span>
					</v-tooltip>
					<v-tooltip bottom>
						<template #activator="data"><em class="fa fa-trash-alt" @click="supprimer(f)"></em></template>
						<span v-t="'commun_tooltip_supprimer'"></span>
					</v-tooltip>
				</li>
			</ul>
		</v-flex>

		<!-- Création ou édition -->
		<v-flex xs12 d-flex>
			<v-form v-model="valide" @keyup.native.enter="valide && sauvegarder()" v-on:submit.prevent>
				<div id="formulaire" v-if="!!nouvelleFormule">

					<!-- Titre -->
					<hr />

					<!-- Champs -->
					<v-text-field required v-model="nouvelleFormule.nom" name="nom" :label="$t('adminResa_placeholder_nomFormule')" :rules="[(v) => (!!v) || $t('commmun_champ_obligatoire')]"></v-text-field>
					<v-text-field type="number" required v-model="nouvelleFormule.prixParNuit" name="prixParNuit" :label="$t('adminResa_placeholder_prixParNuit')"
					              :rules="[(v) => (!!v && v >= 0) || $t('commmun_champ_obligatoire')]"></v-text-field>

					<!-- Boutons -->
					<v-btn @click="sauvegarder()" :disabled="!valide">$t('commun_tooltip_valider')</v-btn>
					<v-btn @click="annulerCreation()">{{$t('commun_tooltip_annuler')}}</v-btn>
				</div>
			</v-form>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./cadre-formules.ts"/>
<style scoped lang="scss" src="./cadre-formules.scss"/>
