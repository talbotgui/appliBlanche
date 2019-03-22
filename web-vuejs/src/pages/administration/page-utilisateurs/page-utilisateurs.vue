<template>
	<v-layout wrap align-center>
		<v-flex xs12 d-flex>
			<h3 v-t="'utilisateur_titre'"></h3>
		</v-flex>
		<v-flex xs12 d-flex>
			<v-data-table :headers="entetes" :items="utilisateurs" :must-sort="true">

				<template v-slot:no-data>Aucune donnée disponible</template>

				<template slot="headerCell" slot-scope="props">
					<span v-t="props.header.text"></span>

					<!-- uniquement dans la colonne 'action' -->
					<span v-if="props.header.value=='action'">&nbsp;</span>
					<v-tooltip v-if="props.header.value=='action'" bottom>
						<template v-slot:activator="{ on }">
							<span v-on="on"><em class="actionDansUnEntete fa fa-plus" @click="creerUtilisateur();">&nbsp;</em></span>
						</template>
						<span v-t="'commun_tooltip_ajouter'"></span>
					</v-tooltip>
				</template>

				<template v-slot:items="ligne">
					<td>{{ ligne.item.login }}</td>
					<td>
						<v-chip v-for="r in tousLesRoles" v-bind:key="r.nom" :selected="ligne.item.roles.indexOf(r.nom) !== -1" @click="ajouterRetirerRole(ligne.item, r, !(ligne.item.roles.indexOf(r.nom) !== -1))">
							<span>{{r.nom}}</span>
						</v-chip>
					</td>
					<td>
						<v-tooltip bottom>
							<template v-slot:activator="{ on }">
								<span v-on="on"><em class="actionDansUnEntete fa fa-edit" @click="selectionnerUtilisateur(ligne.item);">&nbsp;</em></span>
							</template>
							<span v-t="'commun_tooltip_supprimer'"></span>
						</v-tooltip>
						<v-tooltip bottom>
							<template v-slot:activator="{ on }">
								<span v-on="on"><em class="actionDansUnEntete fa fa-trash-alt" @click="supprimerUtilisateur(ligne.item);">&nbsp;</em></span>
							</template>
							<span v-t="'commun_tooltip_supprimer'"></span>
						</v-tooltip>
					</td>
				</template>
			</v-data-table>
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
					<v-text-field required minlength="6" type="password" v-model="utilisateurSelectionne.mdp" name="mdp" :rules="mdpRegles" :label="$t('utilisateur_placeholder_mdp')"></v-text-field>

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
