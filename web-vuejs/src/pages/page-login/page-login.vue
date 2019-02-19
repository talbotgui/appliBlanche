<template>
	<div>
		<h2 v-t="'connexion_titre'"></h2>
		<v-text-field type="text" name="login" v-model="login" :label="$t('connexion_identifiant')"></v-text-field>
		<v-text-field type="password" autocomplete="current-password" name="password" v-model="mdp" :label="$t('connexion_motDePasse')"></v-text-field>
		<v-btn @click="connecter()" v-t="'connexion_boutonConnexion'"></v-btn>
	</div>
</template>

<script lang="ts" src="./page-login.ts"/>
<style scoped lang="scss" src="./page-login.scss"/>
