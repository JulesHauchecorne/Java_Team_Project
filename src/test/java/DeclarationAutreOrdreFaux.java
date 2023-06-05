import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DeclarationAutreOrdreFaux {
    public JSONObject getDeclarationJSON_EntreFaux() {

        JSONArray activiteArray = new JSONArray();
        JSONObject jsonActivities = new JSONObject();
        jsonActivities.accumulate("description", "");
        jsonActivities.accumulate("categorie", "cours");
        jsonActivities.accumulate("heures", "14");
        jsonActivities.accumulate("date", "208-04-01");

        activiteArray.add(jsonActivities);

        JSONObject declarationJSON_EntreValide = new JSONObject();
        declarationJSON_EntreValide.accumulate("ordre", "canada");
        declarationJSON_EntreValide.accumulate("numero_de_permis", "W1234");
        declarationJSON_EntreValide.accumulate("cycle", "2014-2016");
        declarationJSON_EntreValide.accumulate("activites", activiteArray);

        return declarationJSON_EntreValide;
    }
}
