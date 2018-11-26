package com.guillaumetalbot.applicationblanche.rest.securite;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.method.HandlerMethod;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.rest.controleur.MonitoringRestControler;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.AuthenticationToken;

public class IntercepteurDesRessourcesAutoriseesTest {

	@Test
	public void test01CasAnonymousAuthenticationToken() throws Exception {

		//
		final Authentication authentication = new AnonymousAuthenticationToken("clef", "Principal", Arrays.asList(new SimpleGrantedAuthority("r")));
		final SecurityContextImpl context = new SecurityContextImpl(authentication);
		SecurityContextHolder.setContext(context);

		final IntercepteurDesRessourcesAutorisees i = new IntercepteurDesRessourcesAutorisees("RestControler");

		//
		final boolean r = i.preHandle(null, null, null);

		//
		Assert.assertTrue(r);
	}

	@Test
	public void test02CasNonAuthenticationToken() throws Exception {

		//
		final Authentication authentication = new TestingAuthenticationToken("Principal", "Credentials");
		final SecurityContextImpl context = new SecurityContextImpl(authentication);
		SecurityContextHolder.setContext(context);

		final IntercepteurDesRessourcesAutorisees i = new IntercepteurDesRessourcesAutorisees("RestControler");

		final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.doNothing().when(response).setStatus(Mockito.anyInt());

		//
		final boolean r = i.preHandle(null, response, null);

		//
		Assert.assertFalse(r);
		Mockito.verify(response).setStatus(HttpStatus.FORBIDDEN.value());
	}

	@Test
	public void test03CasReelSansHandler() throws Exception {

		//
		final Authentication authentication = new AuthenticationToken("Principal", "Credentials", Arrays.asList("clef1", "clef2", "clef3"));
		final SecurityContextImpl context = new SecurityContextImpl(authentication);
		SecurityContextHolder.setContext(context);

		final IntercepteurDesRessourcesAutorisees i = new IntercepteurDesRessourcesAutorisees("RestControler");

		final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.doNothing().when(response).setStatus(Mockito.anyInt());

		//
		final boolean r = i.preHandle(request, response, null);

		//
		Assert.assertFalse(r);
		Mockito.verify(response).setStatus(HttpStatus.NOT_FOUND.value());
		Mockito.verifyNoMoreInteractions(response);
	}

	@Test
	public void test04CasReelNominalNonAutorise() throws Exception {

		//
		final Authentication authentication = new AuthenticationToken("Principal", "Credentials", Arrays.asList("clef1", "clef2", "clef3"));
		final SecurityContextImpl context = new SecurityContextImpl(authentication);
		SecurityContextHolder.setContext(context);

		final IntercepteurDesRessourcesAutorisees i = new IntercepteurDesRessourcesAutorisees("RestControler");

		final HandlerMethod hm = Mockito.mock(HandlerMethod.class);
		Mockito.doReturn(MonitoringRestControler.class).when(hm).getBeanType();
		Mockito.doReturn(
				MonitoringRestControler.class.getMethod("lireDonneesDuMonitoring", Integer.class, Integer.class, String.class, Boolean.class))
				.when(hm).getMethod();
		final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.doNothing().when(response).setStatus(Mockito.anyInt());

		//
		final boolean r = i.preHandle(request, response, hm);

		//
		Assert.assertFalse(r);
		Mockito.verify(response).setStatus(HttpStatus.FORBIDDEN.value());
		Mockito.verifyNoMoreInteractions(response);
	}

	@Test
	public void test05CasReelNominalAutorise() throws Exception {

		//
		final Authentication authentication = new AuthenticationToken("Principal", "Credentials",
				Arrays.asList("MONITORING.LIREDONNEESDUMONITORING"));
		final SecurityContextImpl context = new SecurityContextImpl(authentication);
		SecurityContextHolder.setContext(context);

		final IntercepteurDesRessourcesAutorisees i = new IntercepteurDesRessourcesAutorisees("RestControler");

		final HandlerMethod hm = Mockito.mock(HandlerMethod.class);
		Mockito.doReturn(MonitoringRestControler.class).when(hm).getBeanType();
		Mockito.doReturn(
				MonitoringRestControler.class.getMethod("lireDonneesDuMonitoring", Integer.class, Integer.class, String.class, Boolean.class))
				.when(hm).getMethod();
		final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.doNothing().when(response).setStatus(Mockito.anyInt());

		//
		final boolean r = i.preHandle(request, response, hm);

		//
		Assert.assertTrue(r);
		Mockito.verifyNoMoreInteractions(response);
	}

}
