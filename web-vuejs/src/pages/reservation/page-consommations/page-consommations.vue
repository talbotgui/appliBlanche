<template>
	<v-layout wrap align-center>

		<!-- Partie gauche : sélection -->
		<v-flex xs12 d-flex>
			<h3 v-t="'consommations_titre_maincourante'"></h3>
		</v-flex>

		<!-- liste des reservations -->
		<v-flex xs12 d-flex>
			<span v-if="reservations && reservations.length == 0" v-t="'consommations_titre_aucuneReservationEnCours'"></span>
			<v-chip class="chipReservation" v-for="reservation of reservations" :key="reservation.reference" @click="selectionnerReservation(reservation)"
			        :selected="reservationSelectionee && reservationSelectionee.reference===reservation.reference">
				<div class="contenuChipReservation">{{reservation.chambre.nom}} - {{reservation.client}}</div>
			</v-chip>
		</v-flex>

		<hr />

		<!-- liste des produits -->
		<v-flex xs12 v-if="reservationSelectionee && reservationSelectionee.reference">
			<div>
				<v-chip class="chipProduit" v-for="produit of produits" :key="produit.reference" :style="'backgroundColor:' + produit.couleur">
					<div class="quantiteDansChip">
						<span v-if="calculerQuantitePourProduit(produit.reference)">
							<em class="fa fa-trash-alt" @click="supprimerConsommation(produit.reference)"></em>
						</span>
						<span>{{calculerQuantitePourProduit(produit.reference)}}</span>
						<span v-if="calculerQuantitePourProduit(produit.reference)">
							<em class="fa fa-angle-down" @click="reduireConsommation(produit.reference)"></em>
						</span>
					</div>
					<div @click="ajouterConsomation(chambreSelectionee, produit)" class="nomProduit cliquable">{{produit.nom}}</div>
				</v-chip>
			</div>
		</v-flex>

	</v-layout>
</template>

<script lang="ts" src="./page-consommations.ts"/>
<style scoped lang="scss" src="./page-consommations.scss"/>
