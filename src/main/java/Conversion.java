/*
 * Copyright 2020 Jules Hauchecorne  , kousseyla kadri, Martine Lemelin , Jean-Michel Landry .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Conversion {

    /**
     * Methode qui lit un fichier de type JSON et le convertit
     * en JSONObject
     *
     * @param jsonTxt le fichier a convertir
     * @return info le JSONObject contenant les informations de l'employes
     */

    public static JSONObject lireJson(String jsonTxt) {

        return (JSONObject) JSONSerializer.toJSON(jsonTxt);
    }

    /**
     * Methode qui retourne le nombre d'heures du cycle precedent
     *
     * @param info l'objet duqel on obtient les heures transferees
     * @return les heures_transferees_du_cycle_precedent
     */

    public static int getheures_Transfere(JSONObject info) {
        int heureTransferes = 0;
        if (info.getString("ordre").equals("architectes")) {
            heureTransferes = info.getInt("heures_transferees_du_cycle_precedent");
        }
        return heureTransferes;
    }

    /**
     * Obtient la date valide pour architectes de début selon le cycle
     *
     * @param info
     * @return un booléen
     */
    public static String getDateDebutArchitecte(JSONObject info) {
        String debut;
        String cycle = info.getString("cycle");
        if (cycle.equals("2014-2016")) {
            debut = "2014-04-01";
        } else if (cycle.equals("2016-2018")) {
            debut = "2016-04-01";
        } else {
            debut = "2018-04-01";
        }
        return debut;
    }

    /**
     * Obtient la date de début géologues
     *
     * @return Une string
     */
    public static String getDateDebutGeologues() {
        return "2016-06-01";
    }

    /**
     * Obtient la date de début psychologues
     *
     * @return Une string
     */
    public static String getDateDebutPsychologues() {
        return "2016-01-01";
    }

    /**
     * Appel la bonne méthode de date de début selon l'ordre
     *
     * @param info
     * @return début
     */
    public static String getDateDebut(JSONObject info) {
        String ordre = info.getString("ordre");
        String debut;
        if (ordre.equals("architectes")) {
            debut = getDateDebutArchitecte(info);
        } else if (ordre.equals("geologues")) {
            debut = getDateDebutGeologues();
        } else {
            debut = getDateDebutPsychologues();
        }
        return debut;
    }

    /**
     * Obtient la date valide pour architectes de fin selon le cycle
     *
     * @param info
     * @return fin
     */
    public static String getDateFinArchitectes(JSONObject info) {
        String cycle = info.getString("cycle");
        String fin;
        if (cycle.equals("2014-2016")) {
            fin = "2014-07-01";
        } else if (cycle.equals("2016-2018")) {
            fin = "2018-04-01";
        } else {
            fin = "2020-04-01";
        }
        return fin;
    }


    public static String getDateFinGeologues() {
        return "2019-06-01";
    }

    public static String getDateFinPsychologues() {
        return "2021-01-01";
    }

    /**
     * Appel la bonne méthode de date de fin selon l'ordre
     *
     * @param info
     * @return fin
     */
    public static String getDateFin(JSONObject info) {
        String ordre = info.getString("ordre");
        String fin;
        if (ordre.equals("architectes")) {
            fin = getDateFinArchitectes(info);
        } else if (ordre.equals("geologues")) {
            fin = getDateFinGeologues();
        } else {
            fin = getDateFinPsychologues();
        }
        return fin;
    }

    /**
     * Methode qui permet d'obtenir un tableau d'activite a partir du JSONOobject retourner
     * par la methode lireJson()
     *
     * @param info l'objet duqel on obtient le numero de permis
     * @return Activites[] un tableau de types activites
     */

    public static Activite[] getActivites(JSONObject info) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        JSONArray activites = info.getJSONArray("activites");
        Activite[] formation = new Activite[activites.size()];
        for (int i = 0; i < activites.size(); i++) {
            formation[i] = new Activite();
            formation[i].setDescription(activites.getJSONObject(i).getString("description"));
            formation[i].setCategorie(activites.getJSONObject(i).getString("categorie"));
            formation[i].setHeures(activites.getJSONObject(i).getInt("heures"));
            formation[i].setDate(activites.getJSONObject(i).getString("date"));
        }
        return formation;
    }

    /**
     * Obtient la méthode de complétion selon l'ordre.
     *
     * @param info
     * @return complete
     */
    public static boolean obtenirCompletion(JSONObject info) {
        String ordre = info.getString("ordre");
        boolean complete;
        if (ordre.equals("architectes")) {
            complete = ValidationFormationArchitecte.estCycleComplete(info);
        } else if (ordre.equals("geologues")) {
            complete = ValidationFormationGeologue.estCycleComplete(info);
        } else {
            complete = ValidationFormationPsychologues.estCycleComplete(info);
        }
        return complete;
    }

    /**
     * Methode qui permet de creer tableau d'erreur
     *
     * @param Erreurs Le ArrayList de type String contenant l'ensemble
     *                des erreures contenues dans le fichiers entree
     * @return Un String  contenant le boolean complete
     * et le ArrayListErreur qui ont ete convertit du JSON en String
     */
    public static String setResultats(ArrayList<String> Erreurs, JSONObject info) {
        JSONObject json = new JSONObject();
        json.element("complet", obtenirCompletion(info));
        JSONArray ErreursJSON = new JSONArray();
        for (int i = 0; i < Erreurs.size(); i++) {
            ErreursJSON.add(Erreurs.get(i));
        }
        json.element("Erreurs", ErreursJSON);
        return json.toString(2);
    }
}
