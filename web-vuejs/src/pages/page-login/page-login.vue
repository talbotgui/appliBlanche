<template>
	<div>
		<h2 v-t="'connexion_titre'"></h2>
		<v-form v-model="valid">
			<v-text-field type="text" name="login" v-model="login" :rules="loginRegles" :label="$t('connexion_identifiant')"></v-text-field>
			<v-text-field type="password" autocomplete="current-password" name="password" v-model="mdp" :rules="mdpRegles" :label="$t('connexion_motDePasse')"></v-text-field>
			<v-btn @click="connecter()" :disabled="!valid" v-t="'connexion_boutonConnexion'"></v-btn>
		</v-form>
	</div>
</template>

<script lang="ts" src="./page-login.ts"/>
<style scoped lang="scss" src="./page-login.scss"/>
