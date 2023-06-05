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

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Principale est la classe qui effectue la validation d'activites de
 * formation continue pour les membres d'un ordre professionnel.
 *
 * @author Kousseyla Kadri
 * @author Jean-Michel Landry
 * @author Jules Hauchecorne
 * @author Martine Lemelin
 *
 * Cours : INF2050-20
 * @version 2020-03-23
 */

public class Principale {

    public static String fichier;

    /**
     * Insère les erreurs dans le tableau d'erreurs selon l'ordre en appelant la bonne méthode d'insertion erreur.
     *
     * @param info
     * @return un tableau d'erreur
     */
    public static ArrayList <String> insererErreursCommise (JSONObject info) {
        String ordre = info.getString("ordre");
        ArrayList <String> Erreur;
        if (ordre.equals("architectes")) {
            Erreur = ValidationFormationArchitecte.getErreurs(info);
        }else if (ordre.equals("psychologues")){
            Erreur = ValidationFormationPsychologues.getErreurs(info);
        } else {
            Erreur = ValidationFormationGeologue.getErreurs(info);
        }
        return Erreur;
    }

    /**
     * Créer un tableau d'erreur différent dans le cas d'un fichier invalide
     *
     * @param info
     */
    public static void sortieErreurFichier(JSONObject info){
        ArrayList<String> erreurs = new ArrayList(Arrays.asList("Fichier d'entrée est invalide!"));
        fichier = Conversion.setResultats(erreurs, info);
    }


    /**
     * Methode principale permettant la conversion d'un fichier Json,
     * la validation  du contenu et calculs des heures declarees,
     * l'ecriture dans le fichier de sortie Json "resultat" de:
     * -l'etat du cycle de formation(complet ou non)
     * -messages d'erreurs des methodes de validations dates, heures,
     * activites, cycle,
     * -messages d'erreur de fichier d'entree et de sortie donnes en
     * arguments vides ou non en format json, de problemes de conversion.
     *
     * @throw IOExceptions, ParseExceptions, IllegalArgumentException,
     * FileNotFoundException
     */

    public static void main(String[] args) throws IOException {
        try {
            String jsonTxt = DiskFile.loadFileIntoString(args[0]);
            JSONObject info = Conversion.lireJson(jsonTxt);

            if (args.length == 2 && args[0].endsWith(".json") && args[1].endsWith(".json")) {
                if(ValidationFichier.estFichierValide(info)) {
                    ArrayList<String> erreurs = insererErreursCommise(info);
                    fichier = Conversion.setResultats(erreurs, info);
                    DiskFile.saveStringIntoFile(args[1], fichier);
                } else {
                    sortieErreurFichier(info);
                    DiskFile.saveStringIntoFile(args[1], fichier);
                    System.err.println("Fichier d'entrée est invalide");
                    System.exit(-1);
                }
            } else {
                System.err.println("Le fichier est erronné!");
                System.exit(-1);
            }
        } catch (JSONException | ParseException e) {
            System.err.println("Le format du fichier json n'est pas bon!");
            System.exit(-1);
        }


    }
}
