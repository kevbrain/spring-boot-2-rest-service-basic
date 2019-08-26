package com.in28minutes.springboot.rest.example.springboot2restservicebasic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	@GetMapping("/students")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Student> retrieveAllStudents() {
		return  studentRepository.findAll();		
		
	}
	
	@GetMapping("/instances")
	@CrossOrigin(origins = "http://localhost:4200")
	public Map<String, InstanceOpenShift> retrieveAllInstances() {

		return new  OCService().retrieveAllInstances();
	}
	
	@GetMapping("/{oc}/resources/{resourcetype}/{project}")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Resource> getArrayResources(@PathVariable String oc,@PathVariable String resourcetype,@PathVariable String project ) {
		return new OCService().getArrayResources(oc,resourcetype, project);
	}
	
	@GetMapping("/{oc}/config/project/{project}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String loadProject(@PathVariable String oc,@PathVariable String project) {	
		return new OCService().loadProject(oc,project);
	}
	
	@GetMapping("/{oc}/config/{ressourceType}/{project}/{resourceKey}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String loadconfig(@PathVariable String oc,@PathVariable String ressourceType,@PathVariable String project,@PathVariable String resourceKey) {
		
		if (ressourceType.equalsIgnoreCase(DeploymentConfig.TYPE)) {
			return new OCService().loadDC(oc,project,resourceKey,null);
		}
		if (ressourceType.equalsIgnoreCase(Services.TYPE)) {
			return new OCService().loadService(oc,project,resourceKey,null);
		}
		if (ressourceType.equalsIgnoreCase(Routes.TYPE)) {
			return new OCService().loadRoute(oc,project,resourceKey,null);
		}
		if (ressourceType.equalsIgnoreCase(Secret.TYPE)) {
			return new OCService().loadSecret(oc,project,resourceKey,null);
		}
		if (ressourceType.equalsIgnoreCase(ConfigMap.TYPE)) {
			return new OCService().loadConfimap(oc,project,resourceKey,null);
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
	public Map<String,ProjectCompare> compareInstances(@PathVariable String ocA,@PathVariable String ocB) {
		
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
	public Map<String,DCCompare> compareDistinctProjects(@PathVariable String ocA,@PathVariable String ocB,@PathVariable String projectNameA,@PathVariable String projectNameB) {
		
		return new OCService().compareDistinctProjects(ocA, ocB, projectNameA, projectNameB);
	}
	
		
	@GetMapping("/compare/{ocA}/{ocB}/commonproject/{projectName}")
	@CrossOrigin(origins = "http://localhost:4200")
	public Map<String,DCCompare> compareProjects(@PathVariable String ocA,@PathVariable String ocB,@PathVariable String projectName) {
				
		return new OCService().compareDistinctProjects(ocA, ocB, projectName, projectName);
		
	}
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBoot2RestServiceBasicApplication.class, args);
		
		
	}

}
