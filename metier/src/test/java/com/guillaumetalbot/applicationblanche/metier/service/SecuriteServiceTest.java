package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.application.SpringApplicationForTests;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringApplicationForTests.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecuriteServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(SecuriteServiceTest.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SecuriteService securiteService;

	@Before
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
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from UTILISATEUR where login=?", new Object[] { login }, Long.class));
		Assert.assertNotEquals(mdp, jdbc.queryForObject("select mdp from UTILISATEUR where login=?", new Object[] { login }, String.class));
	}

	@Test
	public void test01Utilisateur02KoLogin() {
		//
		final String login = "";
		final String mdp = "unBonMdp";

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.securiteService.sauvegarderUtilisateur(login, mdp);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.ERREUR_LOGIN_MDP));
	}

	@Test
	public void test01Utilisateur03KoMdp() {
		//
		final String login = "monLogin";
		final String mdp = "un";

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.securiteService.sauvegarderUtilisateur(login, mdp);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.ERREUR_LOGIN_MDP));
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
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from UTILISATEUR where login=?", new Object[] { login }, Long.class));
		Assert.assertNotEquals(mdp2, jdbc.queryForObject("select mdp from UTILISATEUR where login=?", new Object[] { login }, String.class));
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
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from UTILISATEUR where login=?", new Object[] { login }, Long.class));
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
		Assert.assertEquals(4, liste.size());
	}

	@Test
	public void test01Utilisateur07Charger() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		final Utilisateur u = this.securiteService.chargerUtilisateurReadOnly(login);

		//
		Assert.assertEquals(login, u.getLogin());
		Assert.assertNotEquals(mdp, u.getMdp());
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
		jdbc.update("insert into RESSOURCE (CLEF, DESCRIPTION) values (?,?)", clefRessource1, "description");
		jdbc.update("insert into RESSOURCE (CLEF, DESCRIPTION) values (?,?)", clefRessource2, "description");
		this.securiteService.associerRoleEtRessource(nomRole1, clefRessource1);
		this.securiteService.associerRoleEtRessource(nomRole1, clefRessource2);
		this.securiteService.associerRoleEtRessource(nomRole2, clefRessource1);
		this.securiteService.associerRoleEtRessource(nomRole2, clefRessource2);

		//
		final Utilisateur u = this.securiteService.chargerUtilisateurAvecRolesEtRessourcesAutoriseesReadOnly(login);

		//
		Assert.assertEquals(login, u.getLogin());
		Assert.assertNotEquals(mdp, u.getMdp());
		Assert.assertEquals(2, u.getRoles().size());
		final Iterator<Role> iterRoles = u.getRoles().iterator();
		Assert.assertEquals(2, iterRoles.next().getRessourcesAutorisees().size());
		Assert.assertEquals(2, iterRoles.next().getRessourcesAutorisees().size());
	}

	@Test
	public void test01Utilisateur08ReinitialiserPassword() {
		//
		final String login = "monLoginToReset";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);
		this.securiteService.verifierUtilisateur(login, mdp);

		//
		this.securiteService.reinitialiserMotDePasse(login);

		//
		this.securiteService.verifierUtilisateur(login, login);
	}

	@Test
	public void test02Role01Creer() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "nomRole";

		//
		this.securiteService.sauvegarderRole(nomRole);

		//
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE", new Object[] {}, Long.class));
	}

	@Test
	public void test02Role02CreerDejaExistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nomRole = "nomRole";
		this.securiteService.sauvegarderRole(nomRole);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.securiteService.sauvegarderRole(nomRole);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_DEJA_EXISTANT));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ROLE", new Object[] {}, Long.class));
	}

	@Test
	public void test02Role02Lister() {
		//
		this.securiteService.sauvegarderRole("nomRole1");
		this.securiteService.sauvegarderRole("nomRole2");

		//
		final Collection<Role> roles = this.securiteService.listerRoles();

		//
		Assert.assertNotNull(roles);
		Assert.assertEquals(2, roles.size());

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
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));
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
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));
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
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.securiteService.desassocierUtilisateurEtRole(login, nomRole);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));
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
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from LIEN_UTILISATEUR_ROLE", new Object[] {}, Long.class));
	}

	@Test
	public void test05VerifierUtilisateur01Ok() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		this.securiteService.verifierUtilisateur(login, mdp);

		//
	}

	@Test
	public void test05VerifierUtilisateur02KoMdp() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		final String mauvaisMdp = "pasBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(login, mauvaisMdp);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.ERREUR_LOGIN));
	}

	@Test
	public void test05VerifierUtilisateur03KoLogin() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		final String mauvaisLogin = "pasMonLogin";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(mauvaisLogin, mdp);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.ERREUR_LOGIN));
	}

	@Test
	public void test05VerifierUtilisateur04Verrouillage() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		final String mauvaisMdp = "pasBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);

		//
		final Throwable e1 = Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(login, mauvaisMdp);
		});
		final Throwable e2 = Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(login, mauvaisMdp);
		});
		final Throwable e3 = Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(login, mauvaisMdp);
		});
		final Throwable e4 = Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(login, mdp);
		});
		final Throwable e5 = Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(login, mauvaisMdp);
		});
		final Throwable e6 = Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(login, mauvaisMdp);
		});

		//
		Assert.assertNotNull(e1);
		Assert.assertTrue(BusinessException.equals((Exception) e1, BusinessException.ERREUR_LOGIN));
		Assert.assertNotNull(e2);
		Assert.assertTrue(BusinessException.equals((Exception) e2, BusinessException.ERREUR_LOGIN));
		Assert.assertNotNull(e3);
		Assert.assertTrue(BusinessException.equals((Exception) e3, BusinessException.ERREUR_LOGIN));
		Assert.assertNotNull(e4);
		Assert.assertTrue(BusinessException.equals((Exception) e4, BusinessException.ERREUR_LOGIN_VEROUILLE));
		Assert.assertNotNull(e5);
		Assert.assertTrue(BusinessException.equals((Exception) e5, BusinessException.ERREUR_LOGIN_VEROUILLE));
		Assert.assertNotNull(e6);
		Assert.assertTrue(BusinessException.equals((Exception) e6, BusinessException.ERREUR_LOGIN_VEROUILLE));
	}

	@Test
	public void test05VerifierUtilisateur05Deverrouillage() {
		//
		final String login = "monLogin";
		final String mdp = "unBonMdp";
		final String mauvaisMdp = "pasBonMdp";
		this.securiteService.sauvegarderUtilisateur(login, mdp);
		Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(login, mauvaisMdp);
		});
		Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(login, mauvaisMdp);
		});
		Assertions.catchThrowable(() -> {
			this.securiteService.verifierUtilisateur(login, mauvaisMdp);
		});

		//
		this.securiteService.deverrouillerUtilisateur(login);

		//
		this.securiteService.verifierUtilisateur(login, mdp);
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
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));
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
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));
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
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.securiteService.desassocierRoleEtRessource(nomRole, clefRessource1);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));
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
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from LIEN_ROLE_RESSOURCE", new Object[] {}, Long.class));
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
		final Collection<Ressource> ressources = this.securiteService.listerRessources();

		//
		Assert.assertEquals(4, ressources.size());
	}

}
