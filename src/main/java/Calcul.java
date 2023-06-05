/*
 * Copyright 2020 Jules Hauchecorne  , Kousseyla Kadri , Martine Lemelin , Jean-Michel Landry .
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

public class Calcul {

    /**
     * Calcul la somme des heures validées par categories
     *
     * @param Categorie la categorie a laquelle on cherche obtenir le nombre d'heure associe.
     * @return Le nombre d'heure associe a une categorie.
     */
    public static int getHeures_Categories(JSONObject info, String Categorie) {
        Activite[] formation = Conversion.getActivites(info);
        int heures = 0;
        for (int i = 0; i < formation.length; i++) {
            if (formation[i].getCategorie().equals(Categorie)
                    && ValidationFormation.estActivitesValides(formation[i], info)) {
                heures += formation[i].getHeures();
            }
        }
        return heures;
    }


    /**
     * Calcul si les catégories présentation, groupe de discussion, projet de recherce et rédaction professionnelle
     * respectent leur maximum d'heures attribuables. S'il y a un dépassement, ces heures ne seront pas considérées
     * dans le 40 heures minimum.
     * *
     *
     * @param info
     * @return un entier représentant le nombre d'heures qui ne seront pas considérées.
     */
    public static int calculHeuresSurplusArchitectes(JSONObject info) {
        int surplus = 0;
        if (getHeures_Categories(info, "présentation") > 23) {
            surplus += (getHeures_Categories(info, "présentation") - 23);
        }
        if (getHeures_Categories(info, "groupe de discussion") > 17) {
            surplus += (getHeures_Categories(info, "groupe de discussion") - 17);
        }
        if (getHeures_Categories(info, "projet de recherche") > 23) {
            surplus += (getHeures_Categories(info, "projet de recherche") - 23);
        }
        if (getHeures_Categories(info, "rédaction professionnelle") > 17) {
            surplus += (getHeures_Categories(info, "rédaction professionnelle") - 17);
        }
        return surplus;
    }

    public static int calculHeuresSurplusGeologues(JSONObject info) {
        int surPlus = 0;
        if (getHeures_Categories(info, "cours") > 25) {
            surPlus = (getHeures_Categories(info, "cours") - 25);
        }
        return surPlus;
    }

    public static int calculHeuresSurplus(JSONObject info) {
        String ordre = info.getString("ordre");
        int surplus = 0;
        if (ordre.equals("architectes")) {
            surplus = calculHeuresSurplusArchitectes(info);
        } else if (ordre.equals("geologues")) {
            surplus = calculHeuresSurplusGeologues(info);
        }
        return surplus;
    }

    /**
     * Calcul la somme des heures valide et des heures transférées du cycle précédent.
     *
     * @param info l'ensemble des informations contenue dans la fiche employee
     *             sous format JSON.
     * @return heures le nombre Total associe a une fiche d'employee.
     */

    public static int getHeuresTotal(JSONObject info) {
        int heures = Math.min(Conversion.getheures_Transfere(info), 7);
        Activite[] formation = Conversion.getActivites(info);
        for (int i = 0; i < formation.length; i++) {
            if (ValidationFormation.estActivitesValides(formation[i], info)) {
                heures += formation[i].getHeures();
            }
        }
        heures = heures - calculHeuresSurplus(info);
        return heures;
    }

    /**
     * Calcul la somme des heures des catégories cours, ateliers, séminaire, colloque, conférence,
     * lecture dirigé et les heures transférées du cycle précédent.
     *
     * @param info l'ensemble des informations contenue dans la fiche employee
     *             sous format JSON.
     * @return total un entier représentant le nombre d'heure associe au categorie
     * necessitant un nombre d'heure minimal.
     */

    public static int heuresCategoriesMinimumArchitecte(JSONObject info) {
        String[] categoriesMin = new String[]{"cours", "atelier",
                "séminaire", "colloque", "conférence", "lecture dirigée"};
        int heureSuivit = 0;
        for (int i = 0; i < categoriesMin.length; i++) {
            heureSuivit += Calcul.getHeures_Categories(info, categoriesMin[i]);
        }
        heureSuivit += Math.min(Conversion.getheures_Transfere(info), 7);
        return heureSuivit;
    }
}
