<template>
	<v-layout wrap align-center>

		<!-- Liste des chambres -->
		<v-flex xs4 d-flex>
			<cadre-chambres></cadre-chambres>
		</v-flex>

		<!-- Liste des formules -->
		<v-flex xs6 d-flex>
			<cadre-formules></cadre-formules>
		</v-flex>
		
		<!-- Liste des options -->
		<v-flex xs6 d-flex>
			<cadre-options></cadre-options>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./page-adminreservations.ts"/>
<style scoped lang="scss" src="./page-adminreservations.scss"/>
