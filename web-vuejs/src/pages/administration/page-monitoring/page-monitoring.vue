<template>
	<v-layout wrap align-center>
		<v-flex xs12 d-flex>
			<h3 v-t="'monitoring_titre'"></h3>
		</v-flex>
		<v-flex xs12 d-flex>
			<v-btn @click="exporterInformations()" v-t="'monitoring_bouton_export'"></v-btn>
		</v-flex>
		<v-flex xs12 d-flex>
			<v-data-table :headers="dtDto.entetes" :items="dtDto.lignesDuTableau" :loading="dtDto.chargementEnCours" :pagination.sync="dtDto.pagination"
			              :total-items="dtDto.nombreTotalElements" :rows-per-page-items="dtDto.listeOptionNombreElementsParPage" :must-sort="true">
				<template v-slot:no-data>Aucune donnée disponible</template>
				<v-progress-linear v-slot:progress color="blue" indeterminate></v-progress-linear>
				<template slot="headerCell" slot-scope="props"><span v-t="props.header.text"></span></template>
				<template v-slot:items="ligne">
					<td>{{ ligne.item.clef }}</td>
					<td>{{ ligne.item.nbAppels | enNombre }}</td>
					<td>{{ ligne.item.tempsCumule | enNombre }}</td>
					<td>{{ ligne.item.tempsMoyen | enNombre(2) }}</td>
					<td>{{ ligne.item.tempsMax | enNombre }}</td>
					<td>{{ ligne.item.tempsMin | enNombre }}</td>
				</template>
			</v-data-table>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./page-monitoring.ts"/>
<style scoped lang="scss" src="./page-monitoring.scss"/>
