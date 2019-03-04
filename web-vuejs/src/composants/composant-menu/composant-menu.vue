<template>
	<v-layout wrap align-center>
		<template v-if="modules.length > 0">

			<v-flex d-flex>

				<!-- Accueil -->
				<router-link to="/" tag="button">
					<em class="iconeBouton fa fa-home"></em>
					<span class="libelleBouton" v-t="'menu_accueil'"></span>
				</router-link>

				<!-- Pour chaque module -->
				<v-menu offset-y v-for="m in modules" :key="m.nom">
					<button @click="moduleSelectionne=m" slot="activator" class="router-link-exact-active router-link-active">
						<em :class="'iconeBouton fa fa-' + m.icone"></em>
						<span class="libelleBouton" v-t="m.nom"></span>
					</button>
					<v-list>
						<v-list-tile v-for="p in moduleSelectionne.pages" :key="p.nom">
							<v-list-tile-title>
								<router-link :to="p.route" class="lienDuMenu">
									<em :class="'iconeBouton fa fa-' + p.icone"></em>
									<span class="libelleMenu" v-t="p.nom"></span></router-link>
							</v-list-tile-title>
						</v-list-tile>
					</v-list>
				</v-menu>

				<!-- Deconnexion -->
				<button @click="deconnecter" class="router-link-exact-active router-link-active">
					<em class="iconeBouton fa fa-window-close"></em>
					<span class="libelleBouton" v-t="'menu_deconnexion'"></span>
				</button>

			</v-flex>
		</template>

		<!-- Changement de langue -->
		<div :class="(modules.length === 0)?'forceDivAdroite':''">
			<Langues></Langues>
		</div>

	</v-layout>
</template>

<script lang="ts" src="./composant-menu.ts"/>
<style scoped lang="scss" src="./composant-menu.scss"/>
