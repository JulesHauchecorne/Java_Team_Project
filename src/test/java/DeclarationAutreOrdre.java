import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DeclarationAutreOrdre {
    public JSONObject getDeclarationJSON_EntreValide() {

        JSONArray activiteArray = new JSONArray();
        JSONObject jsonActivities = new JSONObject();
        jsonActivities.accumulate("description", "Cours sur la déontologie");
        jsonActivities.accumulate("categorie", "cours");
        jsonActivities.accumulate("heures", "14");
        jsonActivities.accumulate("date", "2018-04-01");

        activiteArray.add(jsonActivities);

        JSONObject declarationJSON_EntreValide = new JSONObject();
        declarationJSON_EntreValide.accumulate("ordre", "geologues");
        declarationJSON_EntreValide.accumulate("numero_de_permis", "S1234");
        declarationJSON_EntreValide.accumulate("cycle", "2016-2019");
        declarationJSON_EntreValide.accumulate("activites", activiteArray);

        return declarationJSON_EntreValide;
    }
}
