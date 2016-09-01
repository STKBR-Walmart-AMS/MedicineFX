/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softtek.medicine.java.util;

import com.softtek.medicine.java.model.Incident;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ariane.dutra
 */
public class MedicineRestClientUtil {

    public static final String SERVER_URI = "http://medicineweb-stkbr.rhcloud.com";
    // "Aug 19, 2016 12:00:00 AM"
    //public static final DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
    public static final DateFormat formatter = new SimpleDateFormat("MMM d, yyyy HH:mm:ss a");

    public static void main(String args[]) throws ParseException {

        testPostIncidents();
        System.out.println("*****");

    }

    private static void testPostIncidents() throws ParseException {
        // TODO Auto-generated method stub
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("recover incidents\n");
        List<Incident> incidentList = new ArrayList<Incident>();
        incidentList = getAllIncidents();
        //System.out.println("tentar post");
        //String result = restTemplate.postForObject(SERVER_URI + "/dr/incidents/update", incidentList, String.class);
        //System.out.println(result);

    }

    public static List<Incident> getAllIncidents() throws ParseException {
        List<Incident> incList = new ArrayList<Incident>();
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI + "/dr/incidents/request", List.class);
        System.out.println(emps.size());

        /**
         * Trecho comentado para não fazer uppdate de todos incidentes durante
         * os testes
         */
        for (LinkedHashMap map : emps) {
            //System.out.println("incidentNumber=" + map.get("incidentNumber"));
            Incident in = new Incident();
            in.setIncidentNumber(map.get("incidentNumber").toString());
            in.setPriority(Integer.parseInt(map.get("priority").toString()));
            in.setImpact(Integer.parseInt(map.get("impact").toString()));
            in.setUrgency(Integer.parseInt(map.get("urgency").toString()));
            in.setServiceType(Integer.parseInt(map.get("serviceType").toString()));
            in.setContactCompany(map.get("contactCompany").toString());
            in.setCategorizationTier1(map.get("categorizationTier1").toString());
            in.setCategorizationTier2(map.get("categorizationTier2").toString());
            in.setPhoneNumber(map.get("phoneNumber").toString());
            in.setFirstName(map.get("firstName").toString());
            in.setLastName(map.get("lastName").toString());
            in.setDescription(map.get("description").toString());
//            in.setProductCategorizationTier1(map.get("productCategorizationTier1").toString());
//            in.setProductCategorizationTier2(map.get("productCategorizationTier2").toString());
//            in.setProductCategorizationTier3(map.get("productCategorizationTier3").toString());
            in.setEntryId(map.get("entryId").toString());
            in.setSubmitter(map.get("submitter").toString());
//            in.setSubmitDate((Date) formatter.parse(map.get("submitDate").toString()));
            in.setTemplate(map.get("template").toString());
            in.setLastModifiedBy(map.get("lastModifiedBy").toString());
//            in.setLastModifiedDate((Date) formatter.parse(map.get("lastModifiedDate").toString()));
            in.setStatus(Integer.parseInt(map.get("status").toString()));
            in.setCreatedBy(map.get("createdBy").toString());
            in.setCustomer(map.get("customer").toString());
            in.setCompany(map.get("company").toString());
            in.setFullName(map.get("fullName").toString());
            incList.add(in);
        }

//        Incident in = new Incident();
//        in.setIncidentNumber(emps.get(0).get("incidentNumber").toString());
//        incList.add(in);
        return incList;
    }
}
