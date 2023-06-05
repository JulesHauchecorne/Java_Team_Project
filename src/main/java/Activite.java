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

public class Activite {

    private String description;
    private String categorie;
    private int heures;
    private String date;


    /**
     * Methode qui permet d'obtenir la valeur de l'attribut description.
     *
     * @return la description de l'Activite.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Cette methode permet de modifier la valeur de l'attribut description.
     *
     * @param description la nouvelle valeur pour la description.
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Methode qui permet d'obtenir la valeur de l'attribut categorie.
     *
     * @return la categorie de l'Activite.
     */
    public String getCategorie() {

        return categorie;
    }

    /**
     * Cette methode permet de modifier la valeur de l'attribut categorie.
     *
     * @param categorie la nouvelle valeur pour la categorie.
     */

    public void setCategorie(String categorie) {

        this.categorie = categorie;
    }

    /**
     * Setter
     */

    /**
     * Methode qui permet d'obtenir la valeur de l'attribut categorie.
     *
     * @return les heures de l'Activite .
     */
    public int getHeures() {

        return heures;
    }

    /**
     * Cette methode permet de modifier la valeur de l'attribut categorie.
     *
     * @param heures la nouvelle valeur de l'heure.
     */
    public void setHeures(int heures) {

        this.heures = heures;
    }

    /**
     * Methode qui permet d'obtenir la valeur de l'attribut categorie.
     *
     * @return la date de l'Activite.
     */
    public String getDate() {

        return date;
    }

    /**
     * Cette methode permet de modifier la valeur de l'attribut date.
     *
     * @param date la nouvelle valeur de la date.
     */

    public void setDate(String date) {

        this.date = date;
    }

}



