package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

/**
 * Liste des états dans leur ordre naturel :
 *
 * ENREGISTREE EN_COURS TERMINEE FACTUREE ANNULEE
 *
 */
public enum EtatReservation {

	ANNULEE(9), EN_COURS(1), ENREGISTREE(0), FACTUREE(2), TERMINEE(3);

	protected static EtatReservation getEtatReservation(final int numero) {
		for (final EtatReservation e : EtatReservation.values()) {
			if (e.numero == numero) {
				return e;
			}
		}
		return null;
	}

	private final int numero;

	private EtatReservation(final int numero) {
		this.numero = numero;
	}

	public int getNumero() {
		return this.numero;
	}

}
