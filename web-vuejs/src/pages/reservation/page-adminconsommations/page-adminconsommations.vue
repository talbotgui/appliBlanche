<template>
	<v-layout wrap align-center>

		<!-- Liste des produits -->
		<v-flex xs6 d-flex>
			<cadre-produits></cadre-produits>
		</v-flex>

		<!-- Liste des moyens de paiement -->
		<v-flex xs6 d-flex>
			<cadre-moyendepaiement></cadre-moyendepaiement>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./page-adminconsommations.ts"/>
<style scoped lang="scss" src="./page-adminconsommations.scss"/>
