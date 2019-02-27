
<template>
	<v-layout wrap align-center>
		<v-flex xs4 sm2 d-flex>
			<v-select v-model="page.size" :items="listeNbElementsParPage" v-on:change="selectionnerNbElements" label="Elements par page"></v-select>
		</v-flex>
		<v-flex xs4 sm2 d-flex>
			<v-select v-model="page.number" :items="listeIndexesDePage" v-on:change="selectionnerIndexPage" label="Numéro de page"></v-select>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./composant-pagination.ts"/>
<style scoped lang="scss" src="./composant-pagination.scss"/>
