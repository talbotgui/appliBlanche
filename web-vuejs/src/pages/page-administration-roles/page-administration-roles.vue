<template>
	<v-layout wrap align-center>
		<v-flex xs12 d-flex>
			<h3 v-t="'role_titre'"></h3>
		</v-flex>
		<v-flex xs12 d-flex>
			<table>
				<thead>
					<tr>
						<th v-t="'role_entete_nom'"></th>
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
					<tr v-for="r in page.content" v-bind:key="r.nom">
						<td>{{r.nom}}</td>
						<td>
							<v-tooltip bottom>
								<template #activator="data"><em class="fa fa-trash-alt" @click="supprimer(r)"></em></template>
								<span v-t="'commun_tooltip_supprimer'"></span>
							</v-tooltip>
						</td>
					</tr>
				</tbody>
			</table>
		</v-flex>
		<v-flex xs12 d-flex>
			<pagination ref="pagination" v-on:rechargement="chargerDonnees"></pagination>
		</v-flex>

		<v-flex xs12 d-flex>
			<!-- Création ou édition -->
			<v-form v-model="valide" @keyup.native.enter="valide && sauvegarder()" v-on:submit.prevent>
				<div id="formulaireRole" v-if="!!elementSelectionne">

					<!-- Titre -->
					<hr />
					<h3 v-t="'role_formulaire_titre'"></h3>

					<!-- Champs -->
					<v-text-field required v-model="elementSelectionne.nom" name="nom" :rules="nomRegles" :label="$t('role_placeholder_nom')"></v-text-field>

					<!-- Boutons -->
					<v-btn @click="sauvegarder()" :disabled="!valide" v-t="'role_bouton_creer'"></v-btn>
					<v-btn @click="annulerCreation()" v-t="'role_bouton_annuler'"></v-btn>
				</div>
			</v-form>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./page-administration-roles.ts"/>
<style scoped lang="scss" src="./page-administration-roles.scss"/>
