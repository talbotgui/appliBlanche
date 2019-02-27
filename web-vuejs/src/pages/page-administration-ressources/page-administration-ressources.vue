<template>
	<v-layout wrap align-center>
		<v-flex xs12 d-flex>
			<h3 v-t="'ressource_titre'"></h3>
		</v-flex>
		<v-flex xs12 d-flex>
			<table>
				<thead>
					<tr>
						<th v-t="'ressource_entete_clef'"></th>
						<th v-t="'ressource_entete_description'"></th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="r in page.content" v-bind:key="r.clef">
						<td>{{r.clef}}</td>
						<td>{{r.description}}</td>
					</tr>
				</tbody>
			</table>
		</v-flex>
		<pagination ref="pagination" v-on:rechargement="chargerDonnees"></pagination>
	</v-layout>
</template>

<script lang="ts" src="./page-administration-ressources.ts"/>
<style scoped lang="scss" src="./page-administration-ressources.scss"/>
