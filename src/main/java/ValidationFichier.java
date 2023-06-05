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

import net.sf.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidationFichier {

    public static final String MSG_ERR_CYCLE = "Le cycle n'est pas valide.";
    public static final String MSG_ERR_DATE = " est hors du cycle actuel, l'activité" + " sera ignorée.";
    public static final String MSG_ERR_FORMAT_DATE = " n'est pas le bon format de " + "date.";
    public static final String MSG_ERR_PERMIS = "Le numéro de permis n'est pas valide.";

    /**
     * Valider le format de la date yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static boolean formatDate(String date) throws ParseException {
        String format = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        boolean valide = false;
        if (date.matches(format)) {
                dateFormat.parse(date);
                valide = true;
        }
        return valide;
    }

    /**
     * @param info
     * @return
     */
    public static boolean estCycleValide(JSONObject info) {
        boolean valide = false;
        String cycle = info.getString("cycle");
        String ordre = info.getString("ordre");
        if (ordre.equals("architectes") && ((cycle.equals("2014-2016")) || (cycle.equals("2016-2018")) || (cycle.equals("2018-2020")))) {
            valide = true;
        } else if (ordre.equals("geologues") && (cycle.equals("2016-2019"))) {
            valide = true;
        } else if (ordre.equals("psychologues") && (cycle.equals("2016-2021"))) {
            valide = true;
        }
        return valide;
    }

    public static boolean DureeActivite(int temps) {

        return (temps >= 1);
    }

    public static boolean estNumPermisValide(String numeroPermis) {

        return numeroPermis.matches("[ASRZ]{1}\\d{4}");
    }


    public static boolean estDescriptionPresente(String description) {

        return (description.length() > 20);
    }


    public static boolean estFichierValide(JSONObject info) throws ParseException {
        boolean valide = true;
        Activite[] Formation = Conversion.getActivites(info);
        for (int i = 0; i < Formation.length; i++) {
            if (!ValidationFichier.estDescriptionPresente(Formation[i].getDescription()) || !ValidationFichier.formatDate(Formation[i].getDate())
                    || !ValidationFichier.DureeActivite(Formation[i].getHeures())) {
                valide = false;
                }
            }
        if (!ValidationFichier.estNumPermisValide(info.getString("numero_de_permis"))) {
            valide = false;
        }
        return valide;
    }
}
