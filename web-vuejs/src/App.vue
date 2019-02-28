<template>
	<v-app>
		<v-container fluid grid-list-xl>
			<Menu></Menu>

			<v-content>
				<router-view />
			</v-content>

			<Snackbar></Snackbar>

		</v-container>
	</v-app>
</template>

<!-- Balises pour charger le ts -->
<script lang="ts" src="./App.ts"/>

<!-- Balise pour charger le CSS ("scoped" pour limiter le CSS à ce composant)-->
<style scoped lang="scss" src="./App.scss"/>
<style lang="scss" src="./AppGlobal.scss"/>
