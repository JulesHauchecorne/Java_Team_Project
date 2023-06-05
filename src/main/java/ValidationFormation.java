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

public class ValidationFormation {

    public static final String MSG_ERR = "L'activité ";
    public static final String MSG_ERR_CATEGORIE = " est dans une catégorie non r" +
            "econnue. elle sera ignorée. ";
    public static final String MSG_ERR_ACTIVITE_HORS_CYCLE = " est à l'extérieur du cycle valide.";
    public static final String MSG_ERR_MIN_HEURE1 = "Le minimum de ";
    public static final String MSG_ERR_MIN_HEURE_ARCHITECTE = "heures n'a pas " +
            "été atteint pour les catégories suivantes : cours, atelier, séminaire," +
            " colloque, conférence, lecture dirigée.";
    public static final String MSG_ERR_MIN_HEURE_PSYCHOLOGUE = "heures n'a pas été atteint pour la catégorie cours.";
    public static final String MSG_ERR_MIN_HEURE_GEOLOGUE = "heures n'a pas été atteint pour les catégorie cours, projet de recherche" +
            ", groupe de discussion.";
    public static final String MSG_ERR_HEURES_PRECEDENTES = "Heures_transferees_du_cycle_precedent est supérieur à 7 ";
    public static final String MSG_ERR_CYCLE_INVALIDE = "Le cycle n'est pas valide.";


    /**
     * Valide si le nombre d'heure du cycle precedent
     * est inférieur ou égal au nombre d'heures permises,
     * pour l'ORDRE DES ARCHITECTES.
     *
     * @param info le fichierJSONObject
     * @return vrai     ou faux selon la validation du nombre d'heures.
     */
    protected static boolean possedeHeureTransfereValide(JSONObject info) {
        boolean valide = true;
        String ordre = info.getString("ordre");
        if (ordre == "architectes") {
            if (Conversion.getheures_Transfere(info) > 7) {
                valide = false;
            }
        }
        return valide;
    }

    /**
     * Methode validant que la categorie de l'actite fait partie
     * de l'ensemble des activites reconnues de formation.
     *
     * @param formation
     * @return Si la categorie est une Formation reconnues
     */
    protected static boolean estCategorieValide(Activite formation) {
        boolean valide = false;
        String categorie = formation.getCategorie();
        String[] categoriesReconnues = new String[]{"cours", "atelier", "séminaire", "colloque", "conférence",
        "lecture dirigée", "présentation", "groupe de discussion", "projet de recherche", "rédaction professionnelle"};
        for (int i = 0; i < categoriesReconnues.length; i++) {
            if (categorie.equals(categoriesReconnues[i]))
                valide = true;
        }
        return valide;
    }

    /**
     * Verification que la date est bien dans l'intervalle [min,max]
     *
     * @param date
     * @param min
     * @param max
     * @return
     */
    protected static boolean estDansDateIntervalle(String date, String min, String max) {
        boolean bonFormat = false;
        String format = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(bonFormat);
        try {
            dateFormat.parse(date);
            if ((date.matches(format) && min.matches(format) && max.matches(format))) {
                if (date.compareTo(min) >= 0 && date.compareTo(max) <= 0) {
                    bonFormat = true;
                }
            }
        } catch (ParseException ex) {
            bonFormat = false;
        }
        return bonFormat;
    }

    /**
     * validation de l'activites
     *
     * @param Formation
     * @param info
     * @return
     */
    public static boolean estActivitesValides(Activite Formation, JSONObject info) {
        String debut = Conversion.getDateDebut(info);
        String fin = Conversion.getDateFin(info);
        String date = Formation.getDate();
        return (estCategorieValide(Formation) && estDansDateIntervalle(date, debut, fin));
    }


}
