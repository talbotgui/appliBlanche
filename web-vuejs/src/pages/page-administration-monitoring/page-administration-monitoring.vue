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
						<td>{{e.nbAppels | enNombre}}</td>
						<td>{{e.tempsCumule | enNombre}}</td>
						<td>{{e.tempsMoyen | enNombre(2)}}</td>
						<td>{{e.tempsMax | enNombre}}</td>
						<td>{{e.tempsMin | enNombre}}</td>
					</tr>
				</tbody>
			</table>
		</v-flex>
		<pagination ref="pagination" v-on:rechargement="chargerDonnees"></pagination>
	</v-layout>
</template>

<script lang="ts" src="./page-administration-monitoring.ts"/>
<style scoped lang="scss" src="./page-administration-monitoring.scss"/>
