<template>
	<v-layout wrap align-center>
		<v-flex xs12 d-flex>
			<h3 v-t="'monitoring_titre'"></h3>
		</v-flex>
		<v-flex xs12 d-flex>
			<table>
				<thead>
					<tr>
						<th v-t="'monitoring_entete_clef'"></th>
						<th v-t="'monitoring_entete_nbAppels'"></th>
						<th v-t="'monitoring_entete_tempsCumule'"></th>
						<th v-t="'monitoring_entete_tempsMoyen'"></th>
						<th v-t="'monitoring_entete_tempsMax'"></th>
						<th v-t="'monitoring_entete_tempsMin'"></th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="e in page.content" v-bind:key="e.clef">
						<td>{{e.clef}}</td>
						<td>{{e.nbAppels}}</td>
						<td>{{e.tempsCumule}}</td>
						<td>{{e.tempsMoyen}}</td>
						<td>{{e.tempsMax}}</td>
						<td>{{e.tempsMin}},0000 000'00</td>
					</tr>
				</tbody>
			</table>
		</v-flex>
		<v-flex xs4 sm2 d-flex>
			<v-select v-model="page.size" :items="listeNbElementsParPage" v-on:change="selectionnerNbElements" label="Elements par page"></v-select>
		</v-flex>
		<v-flex xs4 sm2 d-flex>
			<v-select v-model="page.number" :items="listeIndexesDePage" v-on:change="selectionnerIndexPage" label="Numéro de page"></v-select>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./page-administration-monitoring.ts"/>
<style scoped lang="scss" src="./page-administration-monitoring.scss"/>
