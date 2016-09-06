package com.softtek.medicine.java.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Field;
import com.bmc.arsys.api.OutputInteger;
import com.bmc.arsys.api.QualifierInfo;
import com.bmc.arsys.api.SortInfo;
import com.bmc.arsys.api.StatusInfo;
import com.bmc.arsys.api.Value;
import com.softtek.medicine.java.model.Incident;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.client.RestTemplate;

public class MedicineUtil {

    public static final String SERVER_URI = "http://medicineweb-stkbr.rhcloud.com/";
    private String user;
    private String pass;
    /**
     * Date format Time Stamp 7/1/2016 12:00:00 AM
     */
    private String submitDate;

    private ARServerUser ctx;
    private String formName = "HPD:Help Desk";
    private String groupName = "BRL - Maintenance Softtek BR HD";

    public final List<Incident> recoverIncidents(String user, String pass, String submitDate) {

        List<Incident> incidentList = new ArrayList<>();
        if (null == submitDate || submitDate.equals("")) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
            Calendar cal = Calendar.getInstance();
            System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22
            this.submitDate = dateFormat.format(cal.getTime());
        } else {
            this.submitDate = submitDate;
        }
        this.user = user;
        this.pass = pass;
        this.ctx = this.login(this.user, this.pass);

        incidentList = queryEntrysByQual("( \'Assigned Group\' = \"" + groupName
                + "\" AND \'Submit Date\' > \"" + this.submitDate + "\" )");

        this.cleanup();
        return incidentList;
    }

    public void cleanup() {
        // Logout the user from the server. This releases the resource
        // allocated on the server for the user.
        ctx.logout();
        System.out.println();
        System.out.println("User logged out.");
    }

    private ARServerUser login(String user, String pass) {
        ARServerUser ctx = new ARServerUser();
        ctx.setServer("BSMReports");
        ctx.setPort(24341);
        ctx.setUser(user);
        ctx.setPassword(pass);

        try {
            ctx.verifyUser();
        } catch (ARException e) {
            System.out.println("com.softtek.medicine.util.MedicineUtil.login() :\n" + e.getMessage());
        }
        System.out.println("Connected to AR Server " + ctx.getServer());
        return ctx;
    }

    public List<Incident> queryEntrysByQual(String qualStr) {
        List<Entry> entryList = new ArrayList<Entry>();
        List<Incident> incidentList = new ArrayList<Incident>();

        System.out.println();
        System.out.println("Retrieving entryies with qualification " + qualStr);
        try {
            // Retrieve the detail info of all fields from the form.
            List<Field> fields = ctx.getListFieldObjects(formName);
            // Create the search qualifier.
            QualifierInfo qual = ctx.parseQualification(qualStr, fields, null,
                    Constants.AR_QUALCONTEXT_DEFAULT);

            int[] fieldIds = {1000000164, // priority
                1000000163, // impact
                1000000162, // urgency
                1000000161, // incidentNumber
                1000000099, // serviceType
                1000000082, // contactCompany
                1000000063, // categorizationTier1
                1000000064, // categorizationTier2
                1000000056, // phoneNumber
                1000000019, // firstName
                1000000018, // lastName
                1000000000, // description
                200000003, // ProductCategorizationTier1
                200000004, // ProductCategorizationTier2
                200000005, // productCategorizationTier3
                1, // entryId
                2, // submitter
                3, // submitDate
                5, // lastModifiedBy
                6, // lastModifiedDate
                7, // status
                1000000001 // company
        };

            OutputInteger nMatches = new OutputInteger();
            List<SortInfo> sortOrder = new ArrayList<SortInfo>();
            sortOrder.add(new SortInfo(2, Constants.AR_SORT_DESCENDING));
            // Retrieve entries from the form using the given
            // qualification.
            entryList = ctx.getListEntryObjects(formName, qual, 0,
                    Constants.AR_NO_MAX_LIST_RETRIEVE, sortOrder, fieldIds,
                    true, nMatches);

            for (Entry lst : entryList) {
                if (!lst.values().isEmpty()) {

                    Incident incident = new Incident();

                    Iterator<Value> it = lst.values().iterator();

                    //	@SuppressWarnings("unchecked")
                    //	Map<Integer, Value> qsvM = (Map<Integer, Value>) it.next()
                    //			.getValue();
                    System.out.println(lst.values());

                    Object[] list = lst.values().toArray();

                    System.out.println(list[4]);

                    /* mandatory fields */
                    incident.setEntryId(list[0] == null ? null : list[0].toString()); // 1
                    /* mandatory fields */
                    incident.setPriority(list[1] == null ? null : Integer.parseInt(list[1].toString())); // 1000000164
                    incident.setImpact(list[2] == null ? null : Integer.parseInt(list[2].toString())); // 1000000163
                    incident.setUrgency(list[3] == null ? null : Integer.parseInt(list[3].toString())); // 1000000162
                    incident.setIncidentNumber(list[4] == null ? null : list[4].toString()); // 1000000161
                    incident.setServiceType(list[5] == null ? null : Integer.parseInt(list[5].toString())); // 1000000099
                    incident.setContactCompany(list[6] == null ? null : list[6].toString()); // 1000000082
                    incident.setCategorizationTier1(list[7] == null ? null : list[7].toString()); // 1000000063
                    incident.setCategorizationTier2(list[8] == null ? null : list[8].toString()); // 1000000064
                    incident.setPhoneNumber(list[9] == null ? null : list[9].toString()); // 1000000056
                    incident.setFirstName(list[10] == null ? null : list[10].toString()); // 1000000019
                    incident.setLastName(list[11] == null ? null : list[11].toString()); // 1000000018
                    incident.setDescription(list[12] == null ? null : list[12].toString()); // 1000000000
                    incident.setProductCategorizationTier1(list[13] == null ? null : list[13].toString()); // 200000003
                    incident.setProductCategorizationTier2(list[14] == null ? null : list[14].toString()); // 200000004
                    incident.setProductCategorizationTier3(list[15] == null ? null : list[15].toString()); // 200000005
                    /* mandatory fields */
                    incident.setSubmitter(list[16] == null ? null : list[16].toString()); // 2
                    // TODO VERIFICAR
                    //	incident.setSubmitDate(list[17]==null?null: new Date(Long.parseLong(list[17].toString()))); // 3

                    incident.setLastModifiedBy(list[18] == null ? null : list[18].toString()); // 5
                    // TODO VERIFICAR
                    //	incident.setLastModifiedDate(list[19]==null?null: new Date(Long.parseLong(list[19].toString()))); // 6
                    incident.setStatus(list[20] == null ? null : Integer.parseInt(list[20].toString())); // 7
                    incident.setCompany(list[21] == null ? null : list[21].toString()); // 1000000001

                    // TODO NOT FOUND
                    // String setTemplate(list[0].toString()); // 303558600
                    // TODO NOT FOUND
                    // String setCreatedBy(list[0].toString()); // 300617700
                    // TODO NOT FOUND
                    // incident.setCustomer(list[0].toString()); // 303530000
                    // TODO NOT FOUND
                    // incident.setFullName(list[0].toString()); // 1000000017
                    incidentList.add(incident);

                }
            }

            System.out.println("Query returned " + nMatches + " matches.");
        } catch (ARException e) {

            ARExceptionHandler(e, "Problem while querying by qualifier. ");
        }

        return incidentList;

    }

    public void ARExceptionHandler(ARException e, String errMessage) {
        System.out.println(errMessage);
        printStatusList(ctx.getLastStatus());
        System.out.print("Stack Trace:");
        e.printStackTrace();
    }

    public void printStatusList(List<StatusInfo> statusList) {
        if (statusList == null || statusList.size() == 0) {
            System.out.println("Status List is empty.");
            return;
        }
        System.out.print("Message type: ");
        switch (statusList.get(0).getMessageType()) {
            case Constants.AR_RETURN_OK:
                System.out.println("Note");
                break;
            case Constants.AR_RETURN_WARNING:
                System.out.println("Warning");
                break;
            case Constants.AR_RETURN_ERROR:
                System.out.println("Error");
                break;
            case Constants.AR_RETURN_FATAL:
                System.out.println("Fatal Error");
                break;
            default:
                System.out.println("Unknown (" + statusList.get(0).getMessageType() + ")");
                break;
        }
        System.out.println("Status List:");
        for (int i = 0; i < statusList.size(); i++) {

            System.out.println(statusList.get(i).getMessageText());

            System.out.println(statusList.get(i).getAppendedText());
        }
    }

//    public static boolean statusServer() {
//        RestTemplate restTemplateTest = new RestTemplate();
//        String resp = restTemplateTest.getForObject(SERVER_URI + "dr/incident", String.class);
//        return false;
//    }
    public static boolean statusServer() {
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet("http://medicineweb-stkbr.rhcloud.com/dr/incident");
          //  getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                return false;

            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;

        } catch (ClientProtocolException e) {
            System.out.println(e.getMessage());
//e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        return true;

    }

}
