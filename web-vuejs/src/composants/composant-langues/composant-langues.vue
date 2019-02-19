<template>
  <!-- Gestion des langues-->
		<div class="drapeaux">

			<v-tooltip bottom>
      	<template #activator="data">
					<a href="#" class="en" @click="changerLaLangueDesLibelles('en');">
						<img src="@/assets/flag_en.jpg" alt="flag_en" />
					</a>
				</template>
      	<span>{{ $t('commun_tooltip_flagEn') }}</span>
    	</v-tooltip>

			<v-tooltip bottom>
      	<template #activator="data">
					<a href="#" class="fr" @click="changerLaLangueDesLibelles('fr');">
						<img src="@/assets/flag_fr.jpg" alt="flag_fr" />
					</a>
				</template>
      	<span>{{ $t('commun_tooltip_flagFr') }}</span>
    	</v-tooltip>

		</div>
</template>

<script lang="ts" src="./composant-langues.ts"/>
<style scoped lang="scss" src="./composant-langues.scss"/>
