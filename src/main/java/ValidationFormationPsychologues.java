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

import java.util.ArrayList;

public class ValidationFormationPsychologues extends ValidationFormation {

    /**
     * Valide si le nombre d'heure minimum est atteint pour les formation spécifiques.
     *
     * @param info
     * @return boolean
     */
    public static boolean possedeHeureFormationMinimum(JSONObject info) {
        return (Calcul.getHeures_Categories(info, "cours") >= 25);
    }

    /**
     * alide si le nombre d'heure minimum est atteint.
     *
     * @param info
     * @return boolean
     */
    public static boolean possedeNombreHeuresTotalSuffisant(JSONObject info) {
        return (Calcul.getHeuresTotal(info) >= 90);
    }

    /**
     * Valide si le cycle est complet
     *
     * @param info
     * @return un boolean
     */
    public static boolean estCycleComplete(JSONObject info) {

        return (possedeNombreHeuresTotalSuffisant(info)
                && possedeHeureFormationMinimum(info)
                && ValidationFichier.estCycleValide(info));
    }

    /**
     * Methode permettant d'obtenir les messages d'erreurs de validité d'un
     * cycle, de declaration d'heures d'un cycle precedent, d'un nombre
     * d'heures permise par categorie d'activite, format  et de validite de date,
     * categorie d'activite de formation valide, de nombre d'heures pour
     * completer un cycle d'activites, a partir des informations d'un fichier
     * Json fourni en parametre.
     *
     * @param info fichier de format Json
     * @return erreur      une liste des messages d'erreurs;
     */

    public static ArrayList<String> getErreurs(JSONObject info) {
        Activite[] Formation = Conversion.getActivites(info);
        ArrayList<String> erreur = new ArrayList<>();
        if (!(ValidationFormation.possedeHeureTransfereValide(info))) {
            erreur.add(MSG_ERR_HEURES_PRECEDENTES);
        }
        for (int i = 0; i < Formation.length; i++) {
            if (!(ValidationFormation.estCategorieValide(Formation[i]))) {
                erreur.add(MSG_ERR + Formation[i].getDescription() + MSG_ERR_CATEGORIE);
            }
            if (!ValidationFormation.estActivitesValides(Formation[i], info)) {
                erreur.add(MSG_ERR + Formation[i].getDescription() + MSG_ERR_ACTIVITE_HORS_CYCLE);
            }
        }
        if (!(possedeNombreHeuresTotalSuffisant(info))) {
            erreur.add("Il manque " + (90 - Calcul.getHeuresTotal(info)) + " heures pour compléter le cycle");
        }
        if (!possedeHeureFormationMinimum(info)) {
            erreur.add(MSG_ERR_MIN_HEURE1 + " 25 " + MSG_ERR_MIN_HEURE_PSYCHOLOGUE);
        }
        if (!ValidationFichier.estCycleValide(info)) {
            erreur.add(MSG_ERR_CYCLE_INVALIDE);
        }
        return erreur;
    }
}
