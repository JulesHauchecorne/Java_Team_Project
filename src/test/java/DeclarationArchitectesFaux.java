import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DeclarationArchitectesFaux {
    public JSONObject getDeclarationJSON_EntreInvalide() {

        JSONArray activiteArray = new JSONArray();
        JSONObject jsonActivities = new JSONObject();
        jsonActivities.accumulate("description", "Cours sur la déontologie");
        jsonActivities.accumulate("categorie", "cours");
        jsonActivities.accumulate("heures", "14");
        jsonActivities.accumulate("date", "2018-04-01");

        activiteArray.add(jsonActivities);

        JSONObject declarationJSON_EntreValide = new JSONObject();
        declarationJSON_EntreValide.accumulate("ordre", "architectes");
        declarationJSON_EntreValide.accumulate("heures_transferees_du_cycle_precedent", "8");
        declarationJSON_EntreValide.accumulate("numero_de_permis", "R1234");
        declarationJSON_EntreValide.accumulate("cycle", "2014-2016");
        declarationJSON_EntreValide.accumulate("activites", activiteArray);

        return declarationJSON_EntreValide;
    }
}
