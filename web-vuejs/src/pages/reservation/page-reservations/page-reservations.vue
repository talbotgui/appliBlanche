<template>
	<v-layout wrap align-center>

		<!-- Le tableau des réservations-->
		<v-flex xs12 md6 xl9 d-flex>
			<cadre-calendrier></cadre-calendrier>
		</v-flex>

		<!-- Le formulaire de détail -->
		<v-flex xs12 md6 xl3 d-flex>
			<cadre-reservation id="cadre-reservation"></cadre-reservation>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./page-reservations.ts"/>
<style scoped lang="scss" src="./page-reservations.scss"/>
