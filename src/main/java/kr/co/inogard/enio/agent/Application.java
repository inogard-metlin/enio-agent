package kr.co.inogard.enio.agent;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import kr.co.inogard.enio.agent.domain.agent.AgentConfig;
import kr.co.inogard.enio.agent.domain.dept.DeptDto;
import kr.co.inogard.enio.agent.domain.dept.DeptDum;
import kr.co.inogard.enio.agent.domain.item.ItemDto;
import kr.co.inogard.enio.agent.domain.item.ItemDum;
import kr.co.inogard.enio.agent.domain.pr.PrDto;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.domain.user.UserDto;
import kr.co.inogard.enio.agent.domain.user.UserDum;
import kr.co.inogard.enio.agent.mappers.agent.AgentMapper;

@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mm = new ModelMapper();
		mm.addMappings(new PropertyMap<PrDum, PrDto.Create>() {
			protected void configure() {
				map().setPrNo(source.getErpPrNo());
			}
		});

		mm.addMappings(new PropertyMap<PrDum, PrDto.Response>() {
			protected void configure() {
				map().setPrNo(source.getE4uPrNo());
			}
		});

		mm.addMappings(new PropertyMap<ItemDum, ItemDto.Create>() {
			protected void configure() {
				map().setItemCd(source.getErpItemCd());
			}
		});

		mm.addMappings(new PropertyMap<ItemDum, ItemDto.Response>() {
			protected void configure() {
				map().setItemCd(source.getE4uItemCd());
			}
		});

		mm.addMappings(new PropertyMap<DeptDum, DeptDto.CreateEntry>() {
			protected void configure() {
				map().setDeptCd(source.getErpDeptCd());
			}
		});

		mm.addMappings(new PropertyMap<UserDum, UserDto.CreateEntry>() {
			protected void configure() {
				map().setUserCd(source.getErpUserCd());
			}
		});

		return mm;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Value("${enio.univ-cd}")
	private String agtCd;

	@Bean
	@Autowired
	public AgentConfig agentConfig(AgentMapper agentMapper) {
		return new AgentConfig(agentMapper.findOne(agtCd));
	}
}