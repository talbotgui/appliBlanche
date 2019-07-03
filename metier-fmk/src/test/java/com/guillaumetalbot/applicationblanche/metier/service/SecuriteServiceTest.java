package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.application.SpringApplicationForTests;
import com.guillaumetalbot.applicationblanche.metier.dto.UtilisateurAvecRolesEtAutorisations;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;

@SpringBootTest(classes = SpringApplicationForTests.class)
public class SecuriteServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(SecuriteServiceTest.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SecuriteService securiteService;

	@BeforeEach
	public void before() throws IOException, URISyntaxException {
		LOG.info("---------------------------------------------------------");

		// Destruction des données
		final Collection<String> strings = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("db/dataPurge.sql").toURI()));
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		LOG.info("Execute SQL : {}", strings);
		jdbc.batchUpdate(strings.toArray(new String[strings.size()]));

	}

	@Test
	public void test01Utilisateur01Ok() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String login = "monLogin";
		final String mdp = "unBonMdp";

		//
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from UTILISATEUR where login=?", new Object[] { login }, Long.class));
		Assertions.assertNotEquals(mdp, jdbc.queryForObject("select mdp from UTILISATEUR where login=?", new Object[] { login }, String.class));
	}

	@Test
	public void test01Utilisateur02KoLogin() {
		//
		final String login = "";
		final String mdp = "unBonMdp";

		//
		final Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() -> {
			this.securiteService.sauvegarderUtilisateur(login, mdp);
		});

		//
		Assertions.assertNotNull(thrown);
		Assertions.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.ERREUR_LOGIN_MDP));
	}

	@Test
	public void test01Utilisateur03KoMdp() {
		//
		final String login = "monLogin";
		final String mdp = "un";

		//
		final Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() -> {
			this.securiteService.sauvegarderUtilisateur(login, mdp);
		});

		//
		Assertions.assertNotNull(thrown);
		Assertions.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.ERREUR_LOGIN_MDP));
	}

	@Test
	public void test01Utilisateur04ModifOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		final String mdp2 = "unBonMdp2";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		this.securiteService.sauvegarderUtilisateur(login, mdp2);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from UTILISATEUR where login=?", new Object[] { login }, Long.class));
		Assertions.assertNotEquals(mdp2, jdbc.queryForObject("select mdp from UTILISATEUR where login=?", new Object[] { login }, String.class));
	}

	@Test
	public void test01Utilisateur05Supprimer() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		this.securiteService.supprimerUtilisateur(login);

		//
		Assertions.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from UTILISATEUR where login=?", new Object[] { login }, Long.class));
	}

	@Test
	public void test01Utilisateur06Lister() {
		//
		this.securiteService.sauvegarderUtilisateur("monLogin1", "monMdp");
		this.securiteService.sauvegarderUtilisateur("monLogin2", "monMdp");
		this.securiteService.sauvegarderUtilisateur("monLogin3", "monMdp");
		this.securiteService.sauvegarderUtilisateur("monLogin4", "monMdp");

		//
		final Collection<Utilisateur> liste = this.securiteService.listerUtilisateurs();

		//
		Assertions.assertEquals(4, liste.size());
	}

	@Test
	public void test01Utilisateur07Charger() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		final Utilisateur u = this.securiteService.chargerUtilisateurReadOnly(login);

		//
		Assertions.assertEquals(login, u.getLogin());
		Assertions.assertNotEquals(mdp, u.getMdp());
	}

	@Test
	public void test01Utilisateur08ChargerCompletement() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);
		final String nomRole1 = "nomRole1";
		this.securiteService.sauvegarderRole(nomRole1);
		this.securiteService.associerUtilisateurEtRole(login, nomRole1);
		final String nomRole2 = "nomRole2";
		this.securiteService.sauvegarderRole(nomRole2);
		this.securiteService.associerUtilisateurEtRole(login, nomRole2);
		final String clefRessource1 = "clefRessource1";
		final String clefRessource2 = "clefRessource2";
		jdbc.update("insert into RESSOURCE (CLEF, CHEMIN, DESCRIPTION) values (?,?,?)", clefRessource1, "GET /chemin1", "description1");
		jdbc.update("insert into RESSOURCE (CLEF, CHEMIN, DESCRIPTION) values (?,?,?)", clefRessource2, "GET /chemin2", "description2");
		this.securiteService.associerRoleEtRessource(nomRole1, clefRessource1);
		this.securiteService.associerRoleEtRessource(nomRole1, clefRessource2);
		this.securiteService.associerRoleEtRessource(nomRole2, clefRessource1);
		this.securiteService.associerRoleEtRessource(nomRole2, clefRessource2);

		//
		final Utilisateur u = this.securiteService.chargerUtilisateurAvecRolesEtRessourcesAutoriseesReadOnly(login);

		//
		Assertions.assertEquals(login, u.getLogin());
		Assertions.assertNotEquals(mdp, u.getMdp());
		Assertions.assertEquals(2, u.getRoles().size());
		final Iterator<Role> iterRoles = u.getRoles().iterator();
		Assertions.assertEquals(2, iterRoles.next().getRessourcesAutorisees().size());
		Assertions.assertEquals(2, iterRoles.next().getRessourcesAutorisees().size());
	}

	@Test
	public void test01Utilisateur08ReinitialiserPassword() {
		//
		final String login = "monLoginToReset";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);
		final String mdpChiffre = this.securiteService.chargerUtilisateurReadOnly(login).getMdp();

		//
		this.securiteService.reinitialiserMotDePasse(login);

		//
		Assertions.assertNotEquals(mdpChiffre, this.securiteService.chargerUtilisateurReadOnly(login).getMdp());
	}

	@Test
	public void test01Utilisateur09ListerUtilisateursAvecRolesEtAutorisations() {
		//
		final String role = "role";
		this.securiteService.sauvegarderRole(role);
		final Page<Ressource> ressources = this.securiteService.listerRessources(new QPageRequest(1, 20));
		for (final Ressource r : ressources) {
			this.securiteService.associerRoleEtRessource(role, r.getClef());
		}

		String login = this.securiteService.sauvegarderUtilisateur("monLogin1", "monMdp").getLogin();
		this.securiteService.associerUtilisateurEtRole(login, role);
		login = this.securiteService.sauvegarderUtilisateur("monLogin2", "monMdp").getLogin();
		this.securiteService.associerUtilisateurEtRole(login, role);
		login = this.securiteService.sauvegarderUtilisateur("monLogin3", "monMdp").getLogin();
		this.securiteService.associerUtilisateurEtRole(login, role);
		login = this.securiteService.sauvegarderUtilisateur("monLogin4", "monMdp").getLogin();
		this.securiteService.associerUtilisateurEtRole(login, role);

		//
		final Collection<UtilisateurAvecRolesEtAutorisations> liste = this.securiteService.listerUtilisateursAvecRolesEtAutorisations();

		//
		Assertions.assertEquals(4, liste.size());
		for (final UtilisateurAvecRolesEtAutorisations u : liste) {
			Assertions.assertEquals(1, u.getRoles().size());
			Assertions.assertEquals(ressources.getTotalElements(), u.getRessources().size());
		}

	}

	@Test
	public void test02Role01Creer() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "nomRole";

		//
		this.securiteService.sauvegarderRole(nomRole);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE", new Object[] {}, Long.class));
	}

	@Test
	public void test02Role02CreerDejaExistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "nomRole";
		this.securiteService.sauvegarderRole(nomRole);

		//
		final Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() -> {
			this.securiteService.sauvegarderRole(nomRole);
		});

		//
		Assertions.assertNotNull(thrown);
		Assertions.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_DEJA_EXISTANT));
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE", new Object[] {}, Long.class));
	}

	@Test
	public void test02Role03CreerNomTropCourt() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "n";

		//
		final Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() -> {
			this.securiteService.sauvegarderRole(nomRole);
		});

		//
		Assertions.assertNotNull(thrown);
		Assertions.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.ERREUR_ROLE_NOM));
		Assertions.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from ROLE", new Object[] {}, Long.class));
	}

	@Test
	public void test02Role04Lister() {
		//
		this.securiteService.sauvegarderRole("nomRole1");
		this.securiteService.sauvegarderRole("nomRole2");

		//
		final Page<Role> roles = this.securiteService.listerRoles(new QPageRequest(0, 5));

		//
		Assertions.assertNotNull(roles);
		Assertions.assertEquals(2, roles.getTotalElements());

	}

	@Test
	public void test02Role05SupprimerKo() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "nomRole1";
		final String login = "unLoginCommeUnAutre";
		this.securiteService.sauvegarderRole(nomRole);
		this.securiteService.sauvegarderRole("nomRole2");
		this.securiteService.sauvegarderUtilisateur(login, login);
		this.securiteService.associerUtilisateurEtRole(login, nomRole);

		//
		final Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() -> {
			this.securiteService.supprimerRole(nomRole);
		});

		//
		Assertions.assertNotNull(thrown);
		Assertions.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES));
		Assertions.assertEquals((Long) 2L, jdbc.queryForObject("select count(*) from ROLE", Long.class));
	}

	@Test
	public void test02Role05SupprimerOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "nomRole1";
		this.securiteService.sauvegarderRole(nomRole);
		this.securiteService.sauvegarderRole("nomRole2");

		//
		this.securiteService.supprimerRole(nomRole);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE", Long.class));
	}

	@Test
	public void test03LienRoleUtilisateur01Creer() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);
		final String nomRole = "nomRole";
		this.securiteService.sauvegarderRole(nomRole);

		//
		this.securiteService.associerUtilisateurEtRole(login, nomRole);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));
	}

	@Test
	public void test03LienRoleUtilisateur02CreerDeuxFois() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);
		final String nomRole = "nomRole";
		this.securiteService.sauvegarderRole(nomRole);
		this.securiteService.associerUtilisateurEtRole(login, nomRole);

		//
		this.securiteService.associerUtilisateurEtRole(login, nomRole);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));
	}

	@Test
	public void test03LienRoleUtilisateur03SupprimerNonExistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);
		final String nomRole = "nomRole";
		this.securiteService.sauvegarderRole(nomRole);

		//
		final Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() -> {
			this.securiteService.desassocierUtilisateurEtRole(login, nomRole);
		});

		//
		Assertions.assertNotNull(thrown);
		Assertions.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assertions.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));
	}

	@Test
	public void test03LienRoleUtilisateur04SupprimerExistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);
		final String nomRole = "nomRole";
		this.securiteService.sauvegarderRole(nomRole);
		this.securiteService.associerUtilisateurEtRole(login, nomRole);

		//
		this.securiteService.desassocierUtilisateurEtRole(login, nomRole);

		//
		Assertions.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));
	}

	@Test
	public void test05NotifierConnexion01Ok() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		this.securiteService.notifierConnexion(login, true);

		//
		final Utilisateur u = this.securiteService.chargerUtilisateurReadOnly(login);
		Assertions.assertNull(u.getPremierEchec());
		Assertions.assertNull(u.getSecondEchec());
		Assertions.assertNull(u.getTroisiemeEchec());
		Assertions.assertFalse(u.isVerrouille());
	}

	@Test
	public void test05NotifierConnexion02AvecUnKo() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		this.securiteService.notifierConnexion(login, false);

		//
		final Utilisateur u = this.securiteService.chargerUtilisateurReadOnly(login);
		Assertions.assertNotNull(u.getPremierEchec());
		Assertions.assertNull(u.getSecondEchec());
		Assertions.assertNull(u.getTroisiemeEchec());
		Assertions.assertFalse(u.isVerrouille());
	}

	@Test
	public void test05NotifierConnexion03AvecTroisKo() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		this.securiteService.notifierConnexion(login, false);
		this.securiteService.notifierConnexion(login, false);
		this.securiteService.notifierConnexion(login, false);

		//
		final Utilisateur u = this.securiteService.chargerUtilisateurReadOnly(login);
		Assertions.assertNotNull(u.getPremierEchec());
		Assertions.assertNotNull(u.getSecondEchec());
		Assertions.assertNotNull(u.getTroisiemeEchec());
		Assertions.assertTrue(u.isVerrouille());
	}

	@Test
	public void test05NotifierConnexion04BonneConnexionApresVerrouillage() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		this.securiteService.notifierConnexion(login, false);
		this.securiteService.notifierConnexion(login, false);
		this.securiteService.notifierConnexion(login, false);
		this.securiteService.notifierConnexion(login, true);

		//
		final Utilisateur u = this.securiteService.chargerUtilisateurReadOnly(login);
		Assertions.assertNull(u.getPremierEchec());
		Assertions.assertNull(u.getSecondEchec());
		Assertions.assertNull(u.getTroisiemeEchec());
		Assertions.assertFalse(u.isVerrouille());
	}

	@Test
	public void test05NotifierConnexion04Deverrouillage() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		this.securiteService.notifierConnexion(login, false);
		this.securiteService.notifierConnexion(login, false);
		this.securiteService.notifierConnexion(login, false);
		this.securiteService.deverrouillerUtilisateur(login);

		//
		final Utilisateur u = this.securiteService.chargerUtilisateurReadOnly(login);
		Assertions.assertNull(u.getPremierEchec());
		Assertions.assertNull(u.getSecondEchec());
		Assertions.assertNull(u.getTroisiemeEchec());
		Assertions.assertFalse(u.isVerrouille());
	}

	@Test
	public void test05NotifierConnexion05DeverrouillageUtilisateurInexistant() {
		//
		final String login = "monLogin";

		//
		final Throwable t = org.assertj.core.api.Assertions.catchThrowable(() -> {
			this.securiteService.deverrouillerUtilisateur(login);
		});

		//
		Assertions.assertNotNull(t);
		Assertions.assertTrue(BusinessException.equals((Exception) t, BusinessException.OBJET_NON_EXISTANT));
	}

	@Test
	public void test06LienRoleRessource01Creer() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "nomRole";
		this.securiteService.sauvegarderRole(nomRole);
		final String clefRessource1 = "clefRessource1";
		jdbc.update("insert into RESSOURCE (CLEF, DESCRIPTION) values (?,?)", clefRessource1, "description");

		//
		this.securiteService.associerRoleEtRessource(nomRole, clefRessource1);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));
	}

	@Test
	public void test06LienRoleRessource02CreerDeuxFois() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "nomRole";
		this.securiteService.sauvegarderRole(nomRole);
		final String clefRessource1 = "clefRessource1";
		jdbc.update("insert into RESSOURCE (CLEF, DESCRIPTION) values (?,?)", clefRessource1, "description");
		this.securiteService.associerRoleEtRessource(nomRole, clefRessource1);

		//
		this.securiteService.associerRoleEtRessource(nomRole, clefRessource1);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));
	}

	@Test
	public void test06LienRoleRessource03SupprimerNonExistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "nomRole";
		this.securiteService.sauvegarderRole(nomRole);
		final String clefRessource1 = "clefRessource1";
		jdbc.update("insert into RESSOURCE (CLEF, DESCRIPTION) values (?,?)", clefRessource1, "description");

		//
		final Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() -> {
			this.securiteService.desassocierRoleEtRessource(nomRole, clefRessource1);
		});

		//
		Assertions.assertNotNull(thrown);
		Assertions.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assertions.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));
	}

	@Test
	public void test06LienRoleRessource04SupprimerExistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "nomRole";
		this.securiteService.sauvegarderRole(nomRole);
		final String clefRessource1 = "clefRessource1";
		jdbc.update("insert into RESSOURCE (CLEF, DESCRIPTION) values (?,?)", clefRessource1, "description");
		this.securiteService.associerRoleEtRessource(nomRole, clefRessource1);

		//
		this.securiteService.desassocierRoleEtRessource(nomRole, clefRessource1);

		//
		Assertions.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));
	}

	@Test
	public void test07Ressource01Lister() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		jdbc.update("insert into RESSOURCE (CLEF, DESCRIPTION) values (?,?)", "clefRessource1", "description1");
		jdbc.update("insert into RESSOURCE (CLEF, DESCRIPTION) values (?,?)", "clefRessource2", "description2");
		jdbc.update("insert into RESSOURCE (CLEF, DESCRIPTION) values (?,?)", "clefRessource3", "description3");
		jdbc.update("insert into RESSOURCE (CLEF, DESCRIPTION) values (?,?)", "clefRessource4", "description4");

		//
		final Page<Ressource> ressources = this.securiteService.listerRessources(new QPageRequest(1, 20));

		//
		Assertions.assertEquals(4, ressources.getTotalElements());
	}

	@Test
	public void test08InitialiserOuCompleterConfigurationSecurite01BaseVide() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Collection<Ressource> ressources = Arrays.asList(new Ressource("r1", ""), new Ressource("r2", ""), new Ressource("r3", ""));
		final Collection<String> clefs = ressources.stream().map(r -> r.getClef()).collect(Collectors.toList());
		final String login = "loginDuPremierUtilisateur";
		final String role = "roleDeBase";

		//
		this.securiteService.initialiserOuCompleterConfigurationSecurite(ressources, login, login, role);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from UTILISATEUR", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 3L, jdbc.queryForObject("select count(*) from RESSOURCE", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 3L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));

		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from UTILISATEUR where LOGIN=?", new Object[] { login }, Long.class));
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE where NOM=?", new Object[] { role }, Long.class));
		org.assertj.core.api.Assertions.assertThat(jdbc.queryForList("select CLEF from RESSOURCE", String.class))
				.containsExactlyInAnyOrderElementsOf(clefs);
	}

	@Test
	public void test08InitialiserOuCompleterConfigurationSecurite02DejaFaitIdentique() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Collection<Ressource> ressources = Arrays.asList(new Ressource("r1", ""), new Ressource("r2", ""), new Ressource("r3", ""));
		final Collection<String> clefs = ressources.stream().map(r -> r.getClef()).collect(Collectors.toList());
		final String login = "loginDuPremierUtilisateur";
		final String role = "roleDeBase";
		this.securiteService.initialiserOuCompleterConfigurationSecurite(ressources, login, login, role);

		//
		this.securiteService.initialiserOuCompleterConfigurationSecurite(ressources, login, login, role);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from UTILISATEUR", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 3L, jdbc.queryForObject("select count(*) from RESSOURCE", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 3L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));

		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from UTILISATEUR where LOGIN=?", new Object[] { login }, Long.class));
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE where NOM=?", new Object[] { role }, Long.class));
		org.assertj.core.api.Assertions.assertThat(jdbc.queryForList("select CLEF from RESSOURCE", String.class))
				.containsExactlyInAnyOrderElementsOf(clefs);
		org.assertj.core.api.Assertions.assertThat(jdbc.queryForList("select UTILISATEUR_LOGIN from LIEN_UTILISATEUR_ROLE", String.class))
				.containsExactlyInAnyOrder(login);
		org.assertj.core.api.Assertions.assertThat(jdbc.queryForList("select RESSOURCE_CLEF from LIEN_ROLE_RESSOURCE", String.class))
				.containsExactlyInAnyOrderElementsOf(clefs);
	}

	@Test
	public void test08InitialiserOuCompleterConfigurationSecurite03DejaFaitAvecAjoutEtSuppression() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Collection<Ressource> ressources = Arrays.asList(new Ressource("r1", ""), new Ressource("r2", ""), new Ressource("r3", ""));
		final String login = "loginDuPremierUtilisateur";
		final String role = "roleDeBase";
		this.securiteService.initialiserOuCompleterConfigurationSecurite(ressources, login, login, role);
		final Collection<Ressource> ressourcesModifiees = Arrays.asList(new Ressource("r4", ""), new Ressource("r2", ""), new Ressource("r3", ""));
		final Collection<String> clefsModifiees = ressourcesModifiees.stream().map(r -> r.getClef()).collect(Collectors.toList());

		//
		this.securiteService.initialiserOuCompleterConfigurationSecurite(ressourcesModifiees, login, login, role);

		//
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from UTILISATEUR", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 3L, jdbc.queryForObject("select count(*) from RESSOURCE", new Object[] {}, Long.class));
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));

		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from UTILISATEUR where LOGIN=?", new Object[] { login }, Long.class));
		Assertions.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE where NOM=?", new Object[] { role }, Long.class));
		org.assertj.core.api.Assertions.assertThat(jdbc.queryForList("select CLEF from RESSOURCE", String.class))
				.containsExactlyInAnyOrderElementsOf(clefsModifiees);
		org.assertj.core.api.Assertions.assertThat(jdbc.queryForList("select UTILISATEUR_LOGIN from LIEN_UTILISATEUR_ROLE", String.class))
				.containsExactlyInAnyOrder(login);
		// Les ressources sont bien à jour. Mais un utilisateur existe déjà, les nouvelles ressources ne sont donc pas associée à un role d'office.
		Assertions.assertEquals((Long) 2L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));
		org.assertj.core.api.Assertions.assertThat(jdbc.queryForList("select RESSOURCE_CLEF from LIEN_ROLE_RESSOURCE", String.class))
				.containsExactlyInAnyOrder("r2", "r3");
	}
}
