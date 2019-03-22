<template>
	<v-layout wrap align-center>
		<v-flex xs12 d-flex>
			<h3 v-t="'role_titre'"></h3>
		</v-flex>
		<v-flex xs12 d-flex>
			<v-data-table :headers="dtDto.entetes" :items="dtDto.lignesDuTableau" :loading="dtDto.chargementEnCours" :pagination.sync="dtDto.pagination"
			              :total-items="dtDto.nombreTotalElements" :rows-per-page-items="dtDto.listeOptionNombreElementsParPage" :must-sort="true">

				<template v-slot:no-data>Aucune donnée disponible</template>

				<v-progress-linear v-slot:progress color="blue" indeterminate></v-progress-linear>

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
					<td>
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

		<v-flex xs12 d-flex>
			<!-- Création ou édition -->
			<v-form v-model="valide" @keyup.native.enter="valide && sauvegarder()" v-on:submit.prevent>
				<div id="formulaireRole" v-if="!!elementSelectionne">

					<!-- Titre -->
					<hr />
					<h3 v-t="'role_formulaire_titre'"></h3>

					<!-- Champs -->
					<v-text-field required v-model="elementSelectionne.nom" name="nom" :label="$t('role_placeholder_nom')" :rules="[(v) => (!!v) || $t('commmun_champ_obligatoire')]"></v-text-field>

					<!-- Boutons -->
					<v-btn @click="sauvegarder()" :disabled="!valide" v-t="'role_bouton_creer'"></v-btn>
					<v-btn @click="annulerCreation()" v-t="'role_bouton_annuler'"></v-btn>
				</div>
			</v-form>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./page-roles.ts"/>
<style scoped lang="scss" src="./page-roles.scss"/>
