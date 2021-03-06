package kr.co.inogard.enio.agent.item;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.commons.constant.EnioMediaType;
import kr.co.inogard.enio.agent.support.WithMockAgentUserDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class ItemRestControllerTest {

  MockMvc mockMvc;

  @Autowired
  WebApplicationContext context;

  @Autowired
  ObjectMapper objectMapper;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  @WithMockUser(username = "S0037", roles = {"ADMIN"})
  public void getSendItems() throws Exception {
    mockMvc.perform(get("/send/items").accept(MediaType.APPLICATION_JSON_UTF8).locale(Locale.KOREA))
        .andDo(print()).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "S0037", roles = {"ADMIN"})
  public void getSendItemsWithDatatables() throws Exception {
    mockMvc
        .perform(get("/send/items").accept(MediaType.APPLICATION_JSON_UTF8).locale(Locale.KOREA)
            .param("draw", "0").param("start", "0").param("length", "10"))
        .andDo(print()).andExpect(status().isOk());
  }

  @Test
  @WithMockAgentUserDetails("S0037")
  public void getSendItemsByApi() throws Exception {
    mockMvc
        .perform(get("/agent/v1/send/items")
            .contentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType())
            .accept(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()).locale(Locale.KOREA))
        .andDo(print()).andExpect(status().isOk());
  }

  @Test
  @WithMockAgentUserDetails("S0037")
  public void getSendItemsWithDatatablesByApi() throws Exception {
    mockMvc
        .perform(get("/agent/v1/send/items").accept(MediaType.APPLICATION_JSON_UTF8)
            .locale(Locale.KOREA).param("draw", "0").param("start", "0").param("length", "10"))
        .andDo(print()).andExpect(status().isOk());
  }
}
