<!-- Le formulaire de détail -->
<template>
	<v-layout wrap align-center v-if="!!reservationSelectionnee.reference || creation">

		<v-flex xs12 d-flex>
			<h3>
				<span v-t="'reservation_titre'"></span>
				<span>{{ reservationSelectionnee.reference.split('-')[1] }}</span>
			</h3>
		</v-flex>

		<v-flex xs12 d-flex>
			<form novalidate class="formFieldFullWidth">
			</form>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./cadre-reservation.ts"/>
<style scoped lang="scss" src="./cadre-reservation.scss"/>