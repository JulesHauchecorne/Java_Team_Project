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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse; import static org.junit.jupiter.api.Assertions.assertTrue;
public class validationFormationTest {



    /**
     * Teste pour la categorie
     * vrai pour :
     * <p>
     * "cours", "atelier", "séminaire", "colloque",
     * "conférence", "lecture dirigée", "présentation",
     * "groupe de discussion", "projet de recherche",
     * "rédaction professionnelle"
     **/

    @Test
    public void testEst_categorie_valide() {
        Activite categorie = new DeclarationPourTestValide();
        assertTrue(ValidationFormation.estCategorieValide(categorie));
    }

    @Test
    public void testEst_categorie_nonfvalide() {
        Activite categorie = new DeclarationPourTestFaux();
        assertFalse(ValidationFormation.estCategorieValide(categorie));
    }

    @Test
    public void testEst_categorie_non_valide_vide() {
        Activite categorie = new DeclarationPourTestVide();
        assertFalse(ValidationFormation.estCategorieValide(categorie));
    }


    /**
     * Teste pour la date intervale
     * 1 avril 2018 et le 1 avril 2020
     **/

    @Test
    public void testdateIntervallesDepasseMax() {

        assertFalse(ValidationFormation.estDansDateIntervalle("2018-04-02", "2016-04-01", "2018-04-01"));
    }

    @Test
    public void testdateIntervallesInferieureMin() {
        assertFalse(ValidationFormation.estDansDateIntervalle("2015-04-01", "2016-04-01", "2018-04-01"));
    }
    @Test
    public void testdateIntervallesBonFormat() {
        assertFalse(ValidationFormation.estDansDateIntervalle("20-04-02", "2016-04-01", "2018-04-01"));
    }
    @Test
    public void testdateIntervallesMois() {
        assertFalse(ValidationFormation.estDansDateIntervalle("2018-44-01", "2016-04-01", "2018-04-01"));
    }

    @Test
    public void testdateIntervallesVrai() {
        assertTrue(ValidationFormation.estDansDateIntervalle("2018-04-01", "2016-04-01", "2018-04-01"));
    }
    @Test
    public void testdateIntervallesVide() {
        assertFalse(ValidationFormation.estDansDateIntervalle("", "2016-04-01", ""));
    }

    /**
     * Teste pour le cycle
     **/

    @Test
    public void testheureCYclePrecedentVraiArchitecte() {
        JSONObject declaration = new DeclarationArchitectes().getDeclarationJSON_EntreValide();
        assertTrue(ValidationFormation.possedeHeureTransfereValide(declaration));
    }

    @Test
    public void testheureCYclePrecedentFauxArchitecte() {
        JSONObject declarationFausse = new DeclarationArchitectesFaux().getDeclarationJSON_EntreInvalide();
        assertFalse(ValidationFormation.possedeHeureTransfereValide(declarationFausse));
    }

    @Test
    public void testheureCYclePrecedentVraiAutreOrdre() {
        JSONObject declarationVrai = new DeclarationAutreOrdre().getDeclarationJSON_EntreValide();
        assertTrue(ValidationFormation.possedeHeureTransfereValide(declarationVrai));
    }

    @Test
    public void testheureCYclePrecedentVraiAutreOrdreFaux() {
        JSONObject declarationVrai = new DeclarationAutreOrdreFaux().getDeclarationJSON_EntreFaux();
        assertFalse(ValidationFormation.possedeHeureTransfereValide(declarationVrai));
    }

    /**
     * Teste Activites
     **/
   @Test
    public void testestActivitesValides(){
       JSONObject declarationVrai = new DeclarationAutreOrdre().getDeclarationJSON_EntreValide();
       Activite categorie = new DeclarationPourTestValide();
       assertTrue(ValidationFormation.estActivitesValides(categorie,declarationVrai));
    }

    @Test
    public void testestActivitesValidesFaux(){
        JSONObject declarationVrai = new DeclarationAutreOrdreFaux().getDeclarationJSON_EntreFaux();
        Activite categorie = new DeclarationPourTestFaux();
        assertFalse(ValidationFormation.estActivitesValides(categorie,declarationVrai));
    }
}


