package com.daylight.devleague.controllers.dashboard;

//import static org.hamcrest.CoreMatchers.containsString;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.daylight.devleague.controllers.users.login.UserLoginController;
//import com.daylight.devleague.dto.dashboard.GetSummaryResponseDTO;
//import com.daylight.devleague.services.dashboard.DashboardService;
//import com.daylight.devleague.services.users.login.UserLoginService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import com.jayway.jsonpath.JsonPath;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { DashboardController.class, UserLoginController.class })
public class DashboardControllerTest {

//	private ObjectMapper mapper = new ObjectMapper();
//
//	@Autowired
//	private DashboardController dashboardController;
//
//	@Autowired
//	private UserLoginController loginController;
//
//	@MockBean
//	private DashboardService dashboardService;
//
//	@MockBean
//	private UserLoginService loginService;
//
//	private MockMvc mockMvc;
//
//	@Before
//	public void setUp() {
//		mockMvc = MockMvcBuilders.standaloneSetup(dashboardController, loginController).build();
//	}
//
//	@Test
//	public void receive_dashboard_information() throws Exception {
//		GetSummaryResponseDTO dto = new GetSummaryResponseDTO();
//		String dtoJson = new Gson().toJson(dto);
//		when(dashboardService.getSummary(anyLong())).thenReturn(dto);
//
//		final String token = extractToken(login("user", "pass").andReturn());
//		mockMvc.perform(get("/dashboard/summary").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andDo(print())
//				.andExpect(status().isOk()).andExpect(content().string(containsString(dtoJson)));
//	}
//
//	protected String json(Object o) throws IOException {
//		return mapper.writeValueAsString(o);
//	}
//
//	protected ResultActions login(String username, String password) throws Exception {
//        return mockMvc.perform(post("/login?user=" + username + "&password=" + password));
//    }
//
//	protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
//		return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
//	}
//	
//	private ResultActions registerUser(String username, String password) throws Exception {
//        return mockMvc.perform(
//                post("/api/users")
//                        .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"));
//    }

}
