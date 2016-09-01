/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softtek.medicine.java.util;

import com.softtek.medicine.java.model.Incident;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ariane.dutra
 */
public class TestRestClient {

    public static final String SERVER_URI = "http://medicineweb-stkbr.rhcloud.com";

    public static void main(String args[]) {

        testPostIncidents();
        System.out.println("*****");

    }

    private static void testPostIncidents() {
        // TODO Auto-generated method stub
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("recover incidents\n");
        List<Incident> incidentList = new ArrayList<Incident>();
        incidentList = getAllIncidents();
        //System.out.println("tentar post");
        //String result = restTemplate.postForObject(SERVER_URI + "/dr/incidents/update", incidentList, String.class);
        //System.out.println(result);

    }

    private static List<Incident> getAllIncidents() {
        List<Incident> incList = new ArrayList<Incident>();
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI + "/dr/incidents/request", List.class);
        System.out.println(emps.size());

        /**
         * Trecho comentado para não fazer uppdate de todos incidentes durante
         * os testes
         */
//		for (LinkedHashMap map : emps) {
//			System.out.println("incidentNumber=" + map.get("incidentNumber"));
//			Incident in = new Incident();
//			in.setIncidentNumber(map.get("incidentNumber").toString());
//			incList.add(in);
//		}
        Incident in = new Incident();
        in.setIncidentNumber(emps.get(0).get("incidentNumber").toString());
        incList.add(in);

        return incList;
    }
}
