<!-- Le tableau des réservations-->
<template>
	<v-layout wrap align-center>

		<!-- Le tableau des réservations-->
		<v-flex class="xs12 d-flex">
			<h3 v-t="'reservations_titre_calendrier'"></h3>
		</v-flex>

		<!-- Entete principal-->
		<v-flex class="xs12 d-flex tableauDonnees entete" v-if="!flagSaisieDatesPersonalisees">

			<span class="boutonsAction">
				<v-tooltip bottom>
					<template #activator="data"><em class="fa fa-angle-double-left" @click="deplacerDateParJour(-14);"></em></template>
					<span v-t="'calendrier_tooltip_moinsMoins'"></span>
				</v-tooltip>
				<v-tooltip bottom>
					<template #activator="data"><em class="fa fa-angle-left" @click="deplacerDateParJour(-7);"></em></template>
					<span v-t="'calendrier_tooltip_moins'"></span>
				</v-tooltip>
			</span>

			<!-- Dates de début et fin-->
			<span v-t="'reservations_form_du'"></span>&nbsp;
			<span> {{dateDebut.date | dateCourte}} </span>&nbsp;
			<span v-t="'reservations_form_au'"></span>&nbsp;
			<span> {{dateFin.date | dateCourte}}</span>

			<span class="boutonsAction">
				<v-tooltip bottom>
					<template #activator="data"><em class="fa fa-angle-right" @click="deplacerDateParJour(7);"></em></template>
					<span v-t="'calendrier_tooltip_plus'"></span>
				</v-tooltip>
				<v-tooltip bottom>
					<template #activator="data"><em class="fa fa-angle-double-right" @click="deplacerDateParJour(14);"></em></template>
					<span v-t="'calendrier_tooltip_plusPlus'"></span>
				</v-tooltip>
				<v-tooltip bottom>
					<template #activator="data"><em class="fa fa-calendar" @click="deplacerDateParDefaut();"></em></template>
					<span v-t="'calendrier_tooltip_dateParDefaut'"></span>
				</v-tooltip>
				<v-tooltip bottom>
					<template #activator="data"><em class="fa fa-search" @click="flagSaisieDatesPersonalisees=true"></em></template>
					<span v-t="'commun_tooltip_rechercher'"></span>
				</v-tooltip>
				<v-tooltip bottom>
					<template #activator="data"><em class="fa fa-plus" @click="initaliserNouvelleReservation()"></em></template>
					<span v-t="'commun_tooltip_ajouter'"></span>
				</v-tooltip>
			</span>
		</v-flex>

		<!-- Entete secondaire-->
		<v-flex class="xs12 d-flex tableauDonnees entete" v-if="flagSaisieDatesPersonalisees">

			<!-- Date de début -->
			<span class="libelleDatePicker" v-t="'reservations_form_du'"></span>
			<v-menu v-model="dateDebut.datePick" :close-on-content-click="false" :nudge-right="40" lazy transition="scale-transition" offset-y class="petitdatepicker"
			        full-width min-width="100px">
				<template v-slot:activator="{ on }">
					<v-text-field v-model="dateDebut.dateCourte" readonly v-on="on"></v-text-field>
				</template>
				<v-date-picker v-model="dateDebut.dateComplete" @input="dateDebut.datePick = false"></v-date-picker>
			</v-menu>

			<!-- Date de fin -->
			<span class="libelleDatePicker" v-t="'reservations_form_au'"></span>
			<v-menu v-model="dateFin.datePick" :close-on-content-click="false" :nudge-right="40" lazy transition="scale-transition" offset-y class="petitdatepicker"
			        full-width min-width="100px">
				<template v-slot:activator="{ on }">
					<v-text-field v-model="dateFin.dateCourte" readonly v-on="on"></v-text-field>
				</template>
				<v-date-picker v-model="dateFin.dateComplete" @input="dateFin.datePick = false"></v-date-picker>
			</v-menu>

			<span class="boutonsAction">
				<v-tooltip bottom>
					<template #activator="data"><em class="fa fa-search" @click="chargerDonnees();"></em></template>
					<span v-t="'commun_tooltip_rechercher'"></span>
				</v-tooltip>
				<v-tooltip bottom>
					<template #activator="data"><em class="fa fa-ban" @click="flagSaisieDatesPersonalisees=false"></em></template>
					<span v-t="'commun_tooltip_annuler'"></span>
				</v-tooltip>

				<v-tooltip bottom>
					<template #activator="data"><em class="fa fa-plus" @click="initaliserNouvelleReservation()"></em></template>
					<span v-t="'commun_tooltip_ajouter'"></span>
				</v-tooltip>
			</span>
		</v-flex>

		<!-- Ligne des chambres -->
		<v-flex class="xs12 d-flex tableauDonnees enteteTableau">
			<v-flex class="xs2 d-flex"><span v-t="'reservations_header_date'"></span></v-flex>
			<v-flex :class="'d-flex xs' + nbColParChambre" v-for="c of chambres" :key="c.reference"><span>{{c.nom}}</span></v-flex>
		</v-flex>

		<!-- Tableau de données-->
		<v-flex :class="(j.getDay()==0||j.getDay()==6)?'xs12 d-flex tableauDonnees ligneCal weekend':'xs12 d-flex tableauDonnees ligneCal'" v-for="j of jours"
		        :key="j.time">
			<v-flex class="xs2 d-flex"><span>{{j.getDate()}}/{{j.getMonth() + 1}}</span></v-flex>
			<v-flex :class="'d-flex xs'+ nbColParChambre" v-for="c of chambres" :key="c.reference">
				<!-- Case avec une reservation -->
				<div class="reservation" :style="reservations[c.reference][j.toISOString()].style" v-if="!!reservations[c.reference] && !!reservations[c.reference][j.toISOString()]"
				     @click="afficherDetail(reservations[c.reference][j.toISOString()].reservation)">
					<span>{{reservations[c.reference][j.toISOString()].texte}}</span>
				</div>
				<!-- Case sans reservation -->
				<div v-if="!reservations[c.reference] || !reservations[c.reference][j.toISOString()]" @click="afficherDetail(undefined)"></div>
			</v-flex>
		</v-flex>
	</v-layout>
</template>

<script lang="ts" src="./cadre-calendrier.ts"/>
<style scoped lang="scss" src="./cadre-calendrier.scss"/>