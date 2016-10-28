package findatms.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import com.google.gson.JsonSyntaxException;

import findatms.model.Atm;
import findatms.service.AtmService;
import findatms.service.UserService;

 /* Flow is as follows: login screen->selection page->atm page 
  * selection page shows list of atm types and cities
  * atm page shows filtered list of atms (by city or type)
  * logging out from any page takes you to the login screen
  */
@Controller
@RequestMapping("/")
public class FindAtmsController {
	@Autowired
	private AtmService atmService;
	
	@Autowired
	private UserService userService;
	
	public FindAtmsController() {
	};
	
	//for testing
	public FindAtmsController(AtmService atmService, UserService userService) {
		this.atmService = atmService;
		this.userService = userService;
	}
	
	//returns sorted lists of atm types and cities, used for the selection page
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getTypes(ModelMap model) {
		List<Atm> atms;
		try {
			atms = atmService.getAtms();
		} catch (RestClientException e) {
			model.addAttribute("exception", e.getMessage());
			return "pageNotFound";
		} catch (JsonSyntaxException e) {
			model.addAttribute("exception", e.getMessage());
			return "badJson";
		}
		
		List<String> types = atms.stream()
				.map(atm->atm.getType())
				.distinct().sorted((s1,s2) -> s1.compareTo(s2))
				.collect(Collectors.toList());
		List<String> cities = atms.stream().
				map(atm->atm.getAddress().getCity())
				.distinct()
				.sorted((s1,s2) -> s1.compareTo(s2))
				.collect(Collectors.toList());
		model.addAttribute("user", userService.getPrincipal());
		model.addAttribute("types", types);
		model.addAttribute("cities", cities);
		return "select";
	}
 
	//returns sorted (by city) atms matched by request parameter type
    @RequestMapping(value = "/type/{atmType}", method = RequestMethod.GET)
    public String getAtmsByType(@PathVariable String atmType, ModelMap model) {
    	List<Atm> atms;
    	try {
			atms = atmService.getAtms();
		} catch (RestClientException e) {
			model.addAttribute("exception", e.getMessage());
			return "pageNotFound";
		} catch (JsonSyntaxException e) {
			model.addAttribute("exception", e.getMessage());
			return "badJson";
		}
    	
        atms = atmService.getAtms().stream()
        			.filter(atm->atm.getType().toLowerCase().equals(atmType.toLowerCase()))
        			.sorted((atm1,atm2) -> atm1.getAddress().getCity().compareTo(atm2.getAddress().getCity()))
        			.collect(Collectors.toList());
        model.addAttribute("user", userService.getPrincipal());
        model.addAttribute("atms", atms);
        return "atms";
    }
    
    //returns sorted (by type) atms matched by request parameter type
    @RequestMapping(value = "/city/{city}", method = RequestMethod.GET)
    public String getAtmsByCity(@PathVariable String city, ModelMap model) {
    	List<Atm> atms;
    	try {
			atms = atmService.getAtms();
		} catch (RestClientException e) {
			model.addAttribute("exception", e.getMessage());
			return "pageNotFound";
		} catch (JsonSyntaxException e) {
			model.addAttribute("exception", e.getMessage());
			return "badJson";
		}
    	
        atms = atmService.getAtms().stream()
        			.filter(atm->atm.getAddress().getCity().toLowerCase().equals(city.toLowerCase()))
        			.sorted((atm1,atm2)->atm1.getType().compareTo(atm2.getType()))
        			.collect(Collectors.toList());
        model.addAttribute("user", userService.getPrincipal());
        model.addAttribute("atms", atms);
        return "atms";
    }
    
    //logout and return to login screen
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       if (auth != null){    
          new SecurityContextLogoutHandler().logout(request, response, auth);
       }
       return "redirect:/";
    }
    
    //user does not have permission to view atms
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", userService.getPrincipal());
        return "accessDenied";
    }
}