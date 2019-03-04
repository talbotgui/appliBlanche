<template>
	<v-layout wrap align-center>
		<v-flex xs12 d-flex>
			<h3 v-t="'utilisateur_titre'"></h3>
		</v-flex>
		<v-flex xs12 d-flex>
			<table>
				<thead>
					<tr>
						<th v-t="'utilisateur_entete_identifiant'"></th>
						<th v-t="'utilisateur_entete_roles'"></th>
						<th>
							<span v-t="'commun_entete_actions'"></span>
							<span>&nbsp;</span>
							<v-tooltip bottom>
								<template #activator="data"><em class="actionDansUnEntete fa fa-plus" @click="creerUtilisateur();"></em></template>
								<span v-t="'commun_tooltip_ajouter'"></span>
							</v-tooltip>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="u in utilisateurs" v-bind:key="u.reference">
						<td>{{u.login}}</td>
						<td>
							<v-chip v-for="r in tousLesRoles" v-bind:key="r.nom" :selected="u.roles.indexOf(r.nom) !== -1" @click="ajouterRetirerRole(u, r, !(u.roles.indexOf(r.nom) !== -1))">
								<span>{{r.nom}}</span>
							</v-chip>
						</td>
						<td>
							<v-tooltip bottom>
								<template #activator="data"><em class="fa fa-edit" @click="selectionnerUtilisateur(u)"></em></template>
								<span v-t="'commun_tooltip_editer'"></span>
							</v-tooltip>
							<v-tooltip bottom>
								<template #activator="data"><em class="fa fa-trash-alt" @click="supprimerUtilisateur(u)"></em></template>
								<span v-t="'commun_tooltip_supprimer'"></span>
							</v-tooltip>
						</td>
					</tr>
				</tbody>
			</table>
		</v-flex>

		<v-flex xs12 d-flex>
			<!-- Création ou édition d'utilisateur -->
			<v-form v-model="valide" @keyup.native.enter="valide && sauvegarderUtilisateur()" v-on:submit.prevent>
				<div id="formulaireUtilisateur" v-if="!!utilisateurSelectionne">

					<!-- Titre -->
					<hr />
					<h3 v-t="'utilisateur_formulaire_titre'"></h3>

					<!-- Champs -->
					<v-text-field required minlength="6" v-model="utilisateurSelectionne.login" name="login" :rules="loginRegles" :label="$t('utilisateur_placeholder_login')"
					              :disabled="!creation"></v-text-field>
					<v-text-field matInput required minlength="6" type="password" v-model="utilisateurSelectionne.mdp" name="mdp" :rules="mdpRegles"
					              :label="$t('utilisateur_placeholder_mdp')"></v-text-field>

					<!-- Boutons -->
					<v-btn @click="sauvegarderUtilisateur()" :disabled="!valide" v-t="'utilisateur_bouton_creer'"></v-btn>
					<v-btn @click="annulerCreationUtilisateur()" v-t="'utilisateur_bouton_annuler'"></v-btn>
				</div>
			</v-form>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./page-utilisateurs.ts"/>
<style scoped lang="scss" src="./page-utilisateurs.scss"/>
