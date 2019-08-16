package com.in28minutes.springboot.rest.example.springboot2restservicebasic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.openshift.compare.DCCompare;
import com.openshift.compare.ProjectCompare;
import com.openshift.resources.ConfigMap;
import com.openshift.resources.DeploymentConfig;
import com.openshift.resources.InstanceOpenShift;
import com.openshift.resources.OpenshiftProject;
import com.openshift.resources.Resource;
import com.openshift.resources.Routes;
import com.openshift.resources.Secret;
import com.openshift.resources.Services;
import com.openshift.services.OCService;



@RestController
@SpringBootApplication
public class SpringBoot2RestServiceBasicApplication {
	
		
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	
	@Autowired
	StudentDataRestRepository studentRepository;
	
	@RequestMapping("/")
	String home() {
		return "Welcome on SpringBoot application";
	}
	
	@GetMapping("/students")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Student> retrieveAllStudents() {
		List<Student> myStudents = studentRepository.findAll();		
		return myStudents;
	}
	
	@GetMapping("/instances")
	@CrossOrigin(origins = "http://localhost:4200")
	public HashMap<String, InstanceOpenShift> retrieveAllInstances() {

		return new  OCService().retrieveAllInstances();
	}
	
	@GetMapping("/{oc}/resources/{resourcetype}/{project}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ArrayList<Resource> getArrayResources(@PathVariable String oc,@PathVariable String resourcetype,@PathVariable String project ) {
		return new OCService().getArrayResources(oc,resourcetype,project);
	}
	
	@GetMapping("/{oc}/config/project/{project}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String loadProject(@PathVariable String oc,@PathVariable String project) {
		return new OCService().loadProject(oc,project);
	}
	
	@GetMapping("/{oc}/config/{ressourceType}/{project}/{dc}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String loadconfig(@PathVariable String oc,@PathVariable String ressourceType,@PathVariable String project,@PathVariable String dc) {
		
		if (ressourceType.equalsIgnoreCase("deploymentconfigs")) {
			return new OCService().loadDC(oc,project,dc,null);
		}
		if (ressourceType.equalsIgnoreCase("services")) {
			return new OCService().loadService(oc,project,dc,null);
		}
		if (ressourceType.equalsIgnoreCase("routes")) {
			return new OCService().loadRoute(oc,project,dc,null);
		}
		if (ressourceType.equalsIgnoreCase("secrets")) {
			return new OCService().loadSecret(oc,project,dc,null);
		}
		if (ressourceType.equalsIgnoreCase("configmaps")) {
			return new OCService().loadConfimap(oc,project,dc,null);
		}
		
		return null;
	}
	
	@GetMapping("/{oc}/configOverwrite/{ressourceType}/{project}/{dc}/{namespace}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String loadconfigWithNameSpaceOverwrite(@PathVariable String oc,@PathVariable String ressourceType,@PathVariable String project,@PathVariable String dc,@PathVariable String namespace) {
		
		if (ressourceType.equalsIgnoreCase("deploymentconfigs")) {
			return new OCService().loadDC(oc,project,dc,namespace);
		}
		if (ressourceType.equalsIgnoreCase("services")) {
			return new OCService().loadService(oc,project,dc,namespace);
		}
		if (ressourceType.equalsIgnoreCase("routes")) {
			return new OCService().loadRoute(oc,project,dc,namespace);
		}
		if (ressourceType.equalsIgnoreCase("secrets")) {
			return new OCService().loadSecret(oc,project,dc,namespace);
		}
		if (ressourceType.equalsIgnoreCase("configmaps")) {
			return new OCService().loadConfimap(oc,project,dc,namespace);
		}
		
		return null;
	}
	
	@GetMapping("/{oc}/config/persistentvolume/{persistentvolume}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String loadPersitentVolume(@PathVariable String oc,@PathVariable String persistentvolume) {
		return new OCService().loadPersitentVolume(oc,persistentvolume);
	}
	
	@GetMapping("/{oc}/projects")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<OpenshiftProject> retrieveAllProjects(@PathVariable String oc) {

		return new  OCService().retrieveAllProjects(oc);
	}
	
	@GetMapping("/{oc}/dc/{project}")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<DeploymentConfig> retrieveDCFromProject(@PathVariable String oc,@PathVariable String project) {

		return new  OCService().retrieveDCFromProject(oc,project);
	}
	
	@GetMapping("/{oc}/services")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Services> retrieveAllServices(@PathVariable String oc) {

		return new  OCService().retrieveAllServices(oc);
	}
	
	@GetMapping("/{oc}/routes")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Routes> retrieveAllRoutes(@PathVariable String oc) {

		return new  OCService().retrieveAllRoutes(oc);
	}
	
	@GetMapping("/{oc}/secrets")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Secret> retrieveAllSecrets(@PathVariable String oc) {

		return new  OCService().retrieveAllSecrets(oc);
	}
	
	@GetMapping("/{oc}/cm")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<ConfigMap> retrieveAllConfigMap(@PathVariable String oc) {

		return new  OCService().retrieveAllConfigMap(oc);
	}
	
	@GetMapping("/compare/{ocA}/{ocB}/instances")
	@CrossOrigin(origins = "http://localhost:4200")
	public HashMap<String,ProjectCompare> compareInstances(@PathVariable String ocA,@PathVariable String ocB) {
		
		return new OCService().compareInstances(ocA, ocB);
	}
	
	@GetMapping("/compare/{ocA}/{ocB}/distinctdc/{projectNameA}/{projectNameB}/{dcNameA}/{dcNameB}")
	@CrossOrigin(origins = "http://localhost:4200")
	public DCCompare compareDistinctDC(@PathVariable String ocA, @PathVariable String ocB ,@PathVariable String projectNameA ,@PathVariable String projectNameB, @PathVariable String dcNameA,@PathVariable  String dcNameB) {

		return new OCService().compareDistinctDC(ocA, ocB, projectNameA, projectNameB, dcNameA, dcNameB);
	}
	
	@GetMapping("/compare/{ocA}/{ocB}/commondc/{projectNameA}/{projectNameB}/{dcName}")
	@CrossOrigin(origins = "http://localhost:4200")
	public DCCompare comparecommonDC(@PathVariable String ocA, @PathVariable String ocB ,@PathVariable String projectNameA ,@PathVariable String projectNameB, @PathVariable String dcName) {

		return new OCService().compareDistinctDC(ocA, ocB, projectNameA, projectNameB, dcName, dcName);
	}
	

	@GetMapping("/compare/{ocA}/{ocB}/distinctproject/{projectNameA}/{projectNameB}")
	@CrossOrigin(origins = "http://localhost:4200")
	public HashMap<String,DCCompare> compareDistinctProjects(@PathVariable String ocA,@PathVariable String ocB,@PathVariable String projectNameA,@PathVariable String projectNameB) {
		
		return new OCService().compareDistinctProjects(ocA, ocB, projectNameA, projectNameB);
	}
	
		
	@GetMapping("/compare/{ocA}/{ocB}/commonproject/{projectName}")
	@CrossOrigin(origins = "http://localhost:4200")
	public HashMap<String,DCCompare> compareProjects(@PathVariable String ocA,@PathVariable String ocB,@PathVariable String projectName) {
				
		return new OCService().compareDistinctProjects(ocA, ocB, projectName, projectName);
		
	}
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBoot2RestServiceBasicApplication.class, args);
		
		
	}

}
