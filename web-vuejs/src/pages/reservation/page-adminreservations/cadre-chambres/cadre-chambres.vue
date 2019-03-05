<template>
	<v-layout wrap align-center>

		<v-flex xs12 d-flex>
			<h3>
				<span v-t="'adminResa_titre_listeDesChambres'"></span>
				<v-tooltip bottom>
					<template #activator="data"><em class="actionDansUnEntete fa fa-plus" @click="creer();"></em></template>
					<span v-t="'commun_tooltip_ajouter'"></span>
				</v-tooltip>
			</h3>
		</v-flex>

		<v-flex xs12 d-flex>
			<ul>
				<li v-for="c in chambres" v-bind:key="c.reference">
					<span>{{c.nom}}</span>
					<v-tooltip bottom>
						<template #activator="data"><em class="fa fa-edit" @click="selectionner(c)"></em></template>
						<span v-t="'commun_tooltip_editer'"></span>
					</v-tooltip>
					<v-tooltip bottom>
						<template #activator="data"><em class="fa fa-trash-alt" @click="supprimer(c)"></em></template>
						<span v-t="'commun_tooltip_supprimer'"></span>
					</v-tooltip>
				</li>
			</ul>
		</v-flex>

		<!-- Création ou édition -->
		<v-flex xs12 d-flex>
			<v-form v-model="valide" @keyup.native.enter="valide && sauvegarder()" v-on:submit.prevent>
				<div id="formulaire" v-if="!!nouvelleChambre">

					<!-- Titre -->
					<hr />

					<!-- Champs -->
					<v-text-field required v-model="nouvelleChambre.nom" name="nom" :rules="nomRegles" :label="$t('adminResa_placeholder_nomChambre')"></v-text-field>

					<!-- Boutons -->
					<v-btn @click="sauvegarder()" :disabled="!valide" v-t="'commun_tooltip_valider'"></v-btn>
					<v-btn @click="annulerCreation()" v-t="'commun_tooltip_annuler'"></v-btn>
				</div>
			</v-form>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./cadre-chambres.ts"/>
<style scoped lang="scss" src="./cadre-chambres.scss"/>
