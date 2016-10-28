package findatms.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import findatms.context.TestContext;
import findatms.model.Atm;
import findatms.service.AtmService;
import findatms.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class FindAtmsControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private AtmService atmServiceMock;
	
	@Mock
	private UserService userServiceMock;
	
	@Before
    public void setUp() {
		atmServiceMock = Mockito.mock(AtmService.class);
		userServiceMock = Mockito.mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new FindAtmsController(atmServiceMock, userServiceMock))
                .setHandlerExceptionResolvers(exceptionResolver())
                .setViewResolvers(viewResolver())
                .build();
        
    }
	
	@Test
	public void testSelectPage() throws Exception {
		Atm atm1 = new Atm("ING", "1.0", "Peachtree Street", "6210", "12345", "Columbus", "1.23", "4.56");
		Atm atm2 = new Atm("ALBERT_HEIJN", "2.0", "Watling Lane", "471", "54321", "Stone Mountain", "3.45", "2.34");
		
		when(atmServiceMock.getAtms()).thenReturn(Arrays.asList(atm1, atm2));
		when(userServiceMock.getPrincipal()).thenReturn("marshall");
		
		mockMvc.perform(get("/")) //login
        .andExpect(status().isOk()) 
        .andExpect(view().name("select")) //move to selection page
        .andExpect(forwardedUrl("/WEB-INF/jsp/select.jsp"))
        .andExpect(model().attribute("types", hasSize(2))) //with all atms
        .andExpect(model().attribute("cities", hasSize(2)));
	}
	
	@Test
	public void testSelectType() throws Exception {
		Atm atm1 = new Atm("ING", "1.0", "Peachtree Street", "6210", "12345", "Columbus", "1.23", "4.56");
		Atm atm2 = new Atm("ALBERT_HEIJN", "2.0", "Watling Lane", "471", "54321", "Stone Mountain", "3.45", "2.34");
		
		when(atmServiceMock.getAtms()).thenReturn(Arrays.asList(atm1, atm2));
		when(userServiceMock.getPrincipal()).thenReturn("marshall");
		
		mockMvc.perform(get("/type/ING")) //atms filtered by type ING
		.andExpect(status().isOk())
		.andExpect(view().name("atms")) //move to atm page
		.andExpect(forwardedUrl("/WEB-INF/jsp/atms.jsp"))
		.andExpect(model().attribute("atms", hasSize(1))) //results filtered by type ING
		.andExpect(model().attribute("atms", hasItem(hasProperty("type", is("ING")))));
	}
	
	@Test
	public void testSelectCity() throws Exception {
		Atm atm1 = new Atm("ING", "1.0", "Peachtree Street", "6210", "12345", "Columbus", "1.23", "4.56");
		Atm atm2 = new Atm("ALBERT_HEIJN", "2.0", "Watling Lane", "617", "30083", "Stone Mountain", "3.45", "2.34");
		
		when(atmServiceMock.getAtms()).thenReturn(Arrays.asList(atm1, atm2));
		when(userServiceMock.getPrincipal()).thenReturn("marshall");
		
		mockMvc.perform(get("/city/Columbus")) //atms filtered by city Columbus
		.andExpect(status().isOk())
		.andExpect(view().name("atms")) //move to atm page
		.andExpect(forwardedUrl("/WEB-INF/jsp/atms.jsp"))
		.andExpect(model().attribute("atms", hasSize(1))) //results filterd by city Columbus
		.andExpect(model().attribute("atms", hasItem(hasProperty("address", hasProperty("city", is("Columbus"))))));
	}
	 
	 private HandlerExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
 
        Properties exceptionMappings = new Properties();
 
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");
 
        exceptionResolver.setExceptionMappings(exceptionMappings);
 
        Properties statusCodes = new Properties();
 
        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");
 
        exceptionResolver.setStatusCodes(statusCodes);
 
        return exceptionResolver;
    }
	 
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
 
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
 
        return viewResolver;
    }

}
