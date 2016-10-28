package findatms.service;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import findatms.model.Atm;

@Component
@PropertySource("classpath:application.properties")
public class AtmService{

	private Gson gson = new Gson();
	
	@Value("${uri}")
	private String uri;
	
	public List<Atm> getAtms() throws RestClientException, JsonSyntaxException {
    	RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        int index = result.indexOf('[');
        if (index < 0) {
        	throw new JsonSyntaxException("bad json syntax, need an array, got :" + result);
        }
        result = result.substring(result.indexOf('['));
        
        Type type = new TypeToken<List<Atm>>() {}.getType();
        return gson.fromJson(result, type);
    }
	
	
		@Bean
		public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
			return new PropertySourcesPlaceholderConfigurer();
		}
}
