<template>
	<v-layout wrap align-center>
		<v-flex xs12 d-flex>
			<h3 v-t="'ressource_titre'"></h3>
		</v-flex>
		<v-flex xs12 d-flex>
			<v-data-table :headers="entetes" :items="lignesDuTableau" :loading="chargementEnCours" class="elevation-1" :pagination.sync="pagination"
			              :total-items="nombreTotalElements" :rows-per-page-items="listeOptionNombreElementsParPage" :must-sort="true">
				<template v-slot:no-data>Aucune donnée disponible</template>
				<v-progress-linear v-slot:progress color="blue" indeterminate></v-progress-linear>
				<template v-slot:items="ligne">
					<td>{{ ligne.item.clef }}</td>
					<td>{{ ligne.item.description }}</td>
				</template>
			</v-data-table>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./page-ressources.ts"/>
<style scoped lang="scss" src="./page-ressources.scss"/>
